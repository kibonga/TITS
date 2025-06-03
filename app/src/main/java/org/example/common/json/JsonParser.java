package org.example.common.json;

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
        try {
            System.out.println("Extracting JSON object from http exhange: " + httpExchange.getRequestBody());
            return objectMapper.readTree(httpExchange.getRequestBody());
        } catch (IOException ex) {
           System.out.println(ex.getMessage());
        }
        return null;
    }
}
