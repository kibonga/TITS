package org.example.faker;

import org.example.http.HttpHandlerFactory;
import org.example.webhook.WebhookHandler;

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
