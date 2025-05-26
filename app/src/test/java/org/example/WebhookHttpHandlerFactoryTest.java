package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class WebhookHttpHandlerFactoryTest {

    private static final WebhookHttpHandlerFactory
        webhookHandlerFactory = new WebhookHttpHandlerFactory();

    @Test
    void create_shouldCreateWebhookHandler() {
        final WebhookHandler webhookHandler = webhookHandlerFactory.create();

        assertNotNull(webhookHandler);
    }
}
