package org.example.http;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.example.faker.FakeHttpResponseContext;
import org.example.common.http.responder.HttpResponderImpl;
import org.junit.jupiter.api.Test;

class HttpResponderImplTest {

    private final HttpResponderImpl responder = new HttpResponderImpl();
    private final FakeHttpResponseContext httpResponseContext =
        new FakeHttpResponseContext();

    @Test
    void sendSuccess_validResponse_shouldSendSuccess() {
        assertDoesNotThrow(
            () -> this.responder.sendSucces(this.httpResponseContext));
    }

    @Test
    void sendError_validResponse_shouldSendError() {
        assertDoesNotThrow(
            () -> this.responder.sendError(this.httpResponseContext, 500,
                "Server error"));
    }

    @Test
    void sendError_invalidResponse_shouldThrowIOException() {
        this.httpResponseContext.setShouldCommitThrowIOException(true);

        assertThrows(IOException.class,
            () -> this.responder.sendError(this.httpResponseContext, 500,
                "Server error"));
    }
}
