package org.example.http;

import com.sun.net.httpserver.HttpHandler;
import org.example.common.handler.HandlerFactory;

public interface HttpHandlerFactory<T extends HttpHandler> extends
    HandlerFactory<T> {

    @Override
    T create();
}
