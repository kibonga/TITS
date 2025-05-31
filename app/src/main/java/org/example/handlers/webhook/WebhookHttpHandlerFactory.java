package org.example.handlers.webhook;

import java.util.ArrayList;
import java.util.List;
import org.example.handlers.github.GithubEventHandlerFactory;
import org.example.handlers.github.GithubEventHandlerRegistry;
import org.example.common.http.responder.HttpExchangeResponder;
import org.example.common.http.responder.HttpExchangeResponderImpl;
import org.example.handlers.HttpHandlerFactory;
import org.example.common.http.responder.HttpResponderImpl;
import org.example.handlers.github.pullrequest.PullRequestHandlerFactory;
import org.example.repository.info.RepositoryInfoParserImpl;
import org.example.common.thread.ThreadExecutor;
import org.example.common.http.HttpHeaderExtractorImpl;
import org.jetbrains.annotations.NotNull;

public class WebhookHttpHandlerFactory implements
    HttpHandlerFactory<WebhookHandler> {

    private static @NotNull HttpExchangeResponder getHttpExchangeResponder() {
        return new HttpExchangeResponderImpl(new HttpResponderImpl());
    }

    @Override
    public WebhookHandler create() {
        return new WebhookHandler(
            new GithubEventHandlerRegistry(githubEventHandlerFactoryList()),
            new RepositoryInfoParserImpl(), new HttpHeaderExtractorImpl(),
            getHttpExchangeResponder(), new ThreadExecutor());
    }

    private static List<GithubEventHandlerFactory> githubEventHandlerFactoryList() {
        return new ArrayList<>(List.of(new PullRequestHandlerFactory()));
    }
}
