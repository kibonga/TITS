package org.example.Faker;

import org.example.HttpHandlerFactory;
import org.example.WebhookHandler;

public class FakeWebhookHttpHandlerFactory implements
    HttpHandlerFactory<WebhookHandler> {

    @Override
    public WebhookHandler create() {
        return new WebhookHandler(
            new FakePullRequestHandlerFactory().create(),
            new FakeRepositoryInfoParser(),
            new FakeHttpHeaderExtractor(),
            new FakeHttpExchangeResponder(),
            new FakeThreadExecutor()
        );
    }
}
