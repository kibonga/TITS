package org.example;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseUtil {

  private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);
  private static final String APPLICATION_JSON = "application/json";
  private static final String TEXT_PLAIN = "text/plain";

  private HttpResponseUtil() {
  }

  public static void sendSuccess(HttpExchange exchange) throws IOException {
    sendText(exchange, 200, "Ok");
  }

  public static void sendError(HttpExchange exchange, int status, String message)
      throws IOException {
    sendText(exchange, status, message);
  }

  private static void sendText(HttpExchange exchange, int status, String message)
      throws IOException {
    send(exchange, TEXT_PLAIN, status, message);
  }

  private static void sendJson(HttpExchange exchange, int status, String json) throws IOException {
    send(exchange, APPLICATION_JSON, status, json);
  }

  private static void send(HttpExchange exchange, String type, int status, String payload)
      throws IOException {
    final byte[] responseBytes = payload.getBytes();

    exchange.getResponseHeaders().add("Content-Type", type);
    exchange.sendResponseHeaders(status, responseBytes.length);

    try (final OutputStream outputStream = exchange.getResponseBody()) {
      outputStream.write(responseBytes);
    }
  }
}
