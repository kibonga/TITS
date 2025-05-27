package org.example.http;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.example.faker.FakeHttpExchange;
import org.example.faker.FakeHttpResponder;
import org.junit.jupiter.api.Test;

class HttpExchangeResponderImplTest {

    private final FakeHttpExchange httpExchange = new FakeHttpExchange();
    private final FakeHttpResponder httpResponder = new FakeHttpResponder();
    private final HttpExchangeResponderImpl httpExchangeResponder =
        new HttpExchangeResponderImpl(this.httpResponder);

    @Test
    void sendSuccess_validHttpExchange_shouldSendSuccess() {
        assertDoesNotThrow(
            () -> this.httpExchangeResponder.sendSuccess(this.httpExchange));
    }

    @Test
    void sendError_validHttpExchange_shouldSendError() {
        assertDoesNotThrow(
            () -> this.httpExchangeResponder.sendError(this.httpExchange, 400,
                "Fake error message"));
    }
}
