package org.example;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public interface HttpExchangeResponder {

    void sendSuccess(HttpExchange httpExchange) throws IOException;

    void sendError(HttpExchange httpExchange, int status, String message)
        throws IOException;
}
