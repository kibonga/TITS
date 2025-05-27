package org.example.common.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpExchange;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class JsonParserTest {

    @Test
    void extract_shouldExtractJsonNode() throws IOException {
        final InputStream mockInputStream =
            new ByteArrayInputStream("{\"key\": \"value\"}".getBytes());
        final HttpExchange mockHttpExchange = mock(HttpExchange.class);

        when(mockHttpExchange.getRequestBody()).thenReturn(mockInputStream);

        final JsonNode result = JsonParser.extract(mockHttpExchange);

        assertEquals("value", result.get("key").asText());
    }

}
