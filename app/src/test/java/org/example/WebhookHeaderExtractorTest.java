package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sun.net.httpserver.Headers;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class WebhookHeaderExtractorTest {

    private static final String HEADER_NAME = "TEST_NAME";
    private static final String HEADER_VALUE = "TEST_VALUE";

    private final WebhookHeaderExtractor webhookHeaderExtractor =
        new WebhookHeaderExtractor();
    private final Headers headers = new Headers();

    @Test
    void tryExtract_validHeader_shouldReturnOptionalWithValue() {
        this.headers.add(HEADER_NAME, HEADER_VALUE);

        final Optional<String> result =
            this.webhookHeaderExtractor.tryExtract(this.headers, HEADER_NAME);

        assertFalse(result.isEmpty());
        assertEquals(HEADER_VALUE, result.get());
    }

    @Test
    void tryExtract_invalidHeader_shouldReturnEmptyOptional() {
        final Optional<String> result =
            this.webhookHeaderExtractor.tryExtract(this.headers, HEADER_NAME);

        assertTrue(result.isEmpty());
    }
}
