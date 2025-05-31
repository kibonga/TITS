package org.example.common.http.response;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class HttpExchangeResponseContext implements HttpResponseContext {

    HttpExchange httpExchange;
    private int status = 200;

    public HttpExchangeResponseContext(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void setHeader(String name, String value) {
        this.httpExchange.getResponseHeaders().add(name, value);
    }

    @Override
    public OutputStream getBodyStream() {
        return this.httpExchange.getResponseBody();
    }

    @Override
    public void commit(int contentLength) throws IOException {
        this.httpExchange.sendResponseHeaders(this.status, contentLength);
    }
}
