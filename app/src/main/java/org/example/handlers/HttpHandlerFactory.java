package org.example.handlers;

import com.sun.net.httpserver.HttpHandler;

public interface HttpHandlerFactory<T extends HttpHandler> extends
    HandlerFactory<T> {

    @Override
    T create();
}
