package org.example.faker;

import java.util.ArrayList;
import java.util.List;
import org.example.handlers.github.GithubEventHandlerFactory;
import org.example.handlers.github.GithubEventHandlerRegistry;
import org.example.handlers.HttpHandlerFactory;
import org.example.handlers.webhook.WebhookHandler;

public class FakeWebhookHttpHandlerFactory implements
    HttpHandlerFactory<WebhookHandler> {

    @Override
    public WebhookHandler create() {
        return new WebhookHandler(
            new GithubEventHandlerRegistry(githubEventHandlerFactoryList()),
            new FakeRepositoryInfoParser(),
            new FakeHttpHeaderExtractor(),
            new FakeHttpExchangeResponder(),
            new FakeThreadExecutor()
        );
    }

    private static List<GithubEventHandlerFactory> githubEventHandlerFactoryList() {
        return new ArrayList<>(List.of(new FakePullRequestHandlerFactory()));
    }
}
