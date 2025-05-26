package org.example.Faker;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import org.example.HttpExchangeResponder;

public class FakeHttpExchangeResponder implements HttpExchangeResponder {

    @Override
    public void sendSuccess(HttpExchange httpExchange) throws IOException {
        return;
    }

    @Override
    public void sendError(HttpExchange httpExchange, int status, String message)
        throws IOException {
        return;
    }
}
