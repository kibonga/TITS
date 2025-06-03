package org.example.common.http.responder;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import org.example.common.http.response.HttpExchangeResponseContext;
import org.example.common.http.response.HttpResponseContext;

public class HttpExchangeResponderImpl implements HttpExchangeResponder {

    private final HttpResponder httpResponder;

    public HttpExchangeResponderImpl(HttpResponder httpResponder) {
        this.httpResponder = httpResponder;
    }

    @Override
    public void sendSuccess(HttpExchange httpExchange) throws IOException {
        final HttpResponseContext context =
            new HttpExchangeResponseContext(httpExchange);

        this.httpResponder.sendSucces(context);
    }

    @Override
    public void sendError(HttpExchange httpExchange, int status, String message)
        throws IOException {
        final HttpResponseContext context =
            new HttpExchangeResponseContext(httpExchange);

        this.httpResponder.sendError(context, status, message);
    }
}
