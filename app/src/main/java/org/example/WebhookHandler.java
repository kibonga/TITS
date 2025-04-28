package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebhookHandler implements HttpHandler {

  private static final Logger logger = LoggerFactory.getLogger(WebhookHandler.class);
  private static final String GITHUB_EVENT = "X-GitHub-Event";
  private static final String MISSING_GITHUB_EVENT_HEADER = "Missing Github Event Header";
  private static final String MISSING_GITHUB_EVENT = "Missing Github Event";

  private static final Map<String, Consumer<RepositoryInfo>> githubEventHandlers = Map.of(
      "pull_request", PullRequestHandler.handle()
  );

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    logger.info("Running the webhook handler");
    ThreadUtils.logThreadInfo();

    final var githubEventOptional = getGithubEvent(exchange);

    if (githubEventOptional.isEmpty()) {
      logger.error("Github event is empty");
      HttpResponseUtil.sendError(exchange, 400, MISSING_GITHUB_EVENT_HEADER);
      return;
    }

    final var githubEvent = githubEventOptional.get();

    final Optional<Consumer<RepositoryInfo>> handler =
        Optional.ofNullable(githubEventHandlers.get(githubEvent));

    if (handler.isEmpty()) {
      logger.error("No handler registered for event: [{}]", githubEvent);
      HttpResponseUtil.sendError(exchange, 400, MISSING_GITHUB_EVENT);
      return;
    }

    logger.info("Handling event: [{}]", githubEvent);

    final RepositoryInfo repositoryInfo = extractRepositoryInfo(exchange);

    final Runnable task = () -> handler.get().accept(repositoryInfo);

    new Thread(task).start();

    HttpResponseUtil.sendSuccess(exchange);
    logger.info("Webhook handler completed for event: [{}]", githubEvent);
  }

  private static Optional<String> getGithubEvent(HttpExchange exchange) {
    return Optional.ofNullable(exchange.getRequestHeaders().get(GITHUB_EVENT))
        .flatMap(list -> list.stream().findFirst());
  }

  private static RepositoryInfo extractRepositoryInfo(HttpExchange exchange) throws IOException {
    return new RepositoryInfo(JsonParser.extract(exchange));
  }
}
