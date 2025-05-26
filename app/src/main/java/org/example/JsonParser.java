package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonParser() {
    }

    public static JsonNode extract(HttpExchange httpExchange)
        throws IOException {
        return objectMapper.readTree(httpExchange.getRequestBody());
    }
}
