package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class JsonParser {

  private JsonParser() {
  }

  public static JsonNode extract(HttpExchange httpExchange) throws IOException {
    return new ObjectMapper().readTree(httpExchange.getRequestBody());
  }
}
