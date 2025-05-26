package org.example;

import java.io.IOException;

public interface HttpResponder {

    void sendSucces(HttpResponseContext context) throws IOException;

    void sendError(HttpResponseContext context, int status, String message)
        throws IOException;
}
