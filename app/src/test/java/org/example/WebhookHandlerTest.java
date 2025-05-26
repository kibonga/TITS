package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.example.Faker.FakeHttpExchange;
import org.example.Faker.FakeWebhookHttpHandlerFactory;
import org.junit.jupiter.api.Test;

class WebhookHandlerTest {

    private static final String GITHUB_EVENT = "X-GitHub-Event";
    private static final String PULL_REQUEST = "pull_request";

    private final WebhookHandler webhookHandler =
        new FakeWebhookHttpHandlerFactory().create();
    private final FakeHttpExchange httpExchange = new FakeHttpExchange();

    @Test
    void handle_githubEventNotPresent_shouldRespondError() {
        assertDoesNotThrow(() -> this.webhookHandler.handle(this.httpExchange));
    }

    @Test
    void handle_githubEventNotValid_shouldRespondError() {
        this.httpExchange.getRequestHeaders().add(GITHUB_EVENT, "fake-event");

        assertDoesNotThrow(() -> this.webhookHandler.handle(this.httpExchange));
    }

    @Test
    void handle_validGitHubEvent_shouldRespondOk() {
        this.httpExchange.getRequestHeaders().add(GITHUB_EVENT, PULL_REQUEST);

        assertDoesNotThrow(() -> this.webhookHandler.handle(this.httpExchange));
    }
}
