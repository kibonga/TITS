package org.example.handlers.webhook;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import org.example.common.http.HttpHeaderExtractor;
import org.example.common.json.JsonParser;
import org.example.handlers.github.GithubEventHandlerRegistry;
import org.example.common.http.responder.HttpExchangeResponder;
import org.example.repository.info.RepositoryInfo;
import org.example.repository.info.RepositoryInfoParser;
import org.example.common.thread.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebhookHandler implements HttpHandler {

    private static final Logger logger =
        LoggerFactory.getLogger(WebhookHandler.class);
    private static final String GITHUB_EVENT = "X-GitHub-Event";
    private static final String MISSING_GITHUB_EVENT_HEADER =
        "Missing Github Event Header";
    private static final String MISSING_GITHUB_EVENT = "Missing Github Event";

    private final RepositoryInfoParser repositoryInfoParser;
    private final GithubEventHandlerRegistry githubEventHandlerRegistry;
    private final HttpHeaderExtractor httpHeaderExtractor;
    private final HttpExchangeResponder httpExchangeResponder;
    private final Executor executor;

    public WebhookHandler(GithubEventHandlerRegistry githubEventHandlerRegistry,
        RepositoryInfoParser repositoryInfoParser,
        HttpHeaderExtractor httpHeaderExtractor,
        HttpExchangeResponder httpExchangeResponder, Executor executor) {
        this.githubEventHandlerRegistry = githubEventHandlerRegistry;
        this.httpHeaderExtractor = httpHeaderExtractor;
        this.httpExchangeResponder = httpExchangeResponder;
        this.executor = executor;
        this.repositoryInfoParser = repositoryInfoParser;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("Running the webhook handler");
        ThreadUtils.logThreadInfo();

        final var githubEventOptional = getGithubEvent(exchange);

        if (githubEventOptional.isEmpty()) {
            logger.error("Github event is empty");

            this.httpExchangeResponder.sendError(exchange, 400,
                MISSING_GITHUB_EVENT_HEADER);
            return;
        }

        final var githubEvent = githubEventOptional.get();

        final Optional<Consumer<RepositoryInfo>> handler = Optional.ofNullable(
            this.githubEventHandlerRegistry.getHandler(githubEvent));

        if (handler.isEmpty()) {
            logger.error("No handler registered for event: [{}]", githubEvent);

            this.httpExchangeResponder.sendError(exchange, 400,
                MISSING_GITHUB_EVENT);
            return;
        }

        logger.info("Handling event: [{}]", githubEvent);

        final RepositoryInfo repositoryInfo = extractRepositoryInfo(exchange);

        final Runnable task = () -> handler.get().accept(repositoryInfo);

        this.executor.execute(task);

        this.httpExchangeResponder.sendSuccess(exchange);
        logger.info("Webhook handler completed for event: [{}]", githubEvent);
    }

    private Optional<String> getGithubEvent(HttpExchange exchange) {
        return this.httpHeaderExtractor.tryExtract(exchange.getRequestHeaders(),
            GITHUB_EVENT);
    }

    private RepositoryInfo extractRepositoryInfo(HttpExchange exchange)
        throws IOException {
        return this.repositoryInfoParser.parse(JsonParser.extract(exchange));
    }
}
