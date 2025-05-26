package org.example.Faker;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import lombok.Getter;

@Getter
public class FakeHttpExchange extends HttpExchange {

    private final Headers headers = new Headers();

    @Override
    public Headers getRequestHeaders() {
        return this.headers;
    }

    @Override
    public Headers getResponseHeaders() {
        return null;
    }

    @Override
    public URI getRequestURI() {
        return null;
    }

    @Override
    public String getRequestMethod() {
        return "";
    }

    @Override
    public HttpContext getHttpContext() {
        return null;
    }

    @Override
    public void close() {
        return;
    }

    @Override
    public InputStream getRequestBody() {
        return new ByteArrayInputStream("{\"key\": \"value\"}".getBytes());
    }

    @Override
    public OutputStream getResponseBody() {
        return null;
    }

    @Override
    public void sendResponseHeaders(int rCode, long responseLength)
        throws IOException {
        return;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public int getResponseCode() {
        return 0;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public String getProtocol() {
        return "";
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {
        return;
    }

    @Override
    public void setStreams(InputStream i, OutputStream o) {
        return;
    }

    @Override
    public HttpPrincipal getPrincipal() {
        return null;
    }
}
