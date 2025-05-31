package org.example.common.http.responder;

import java.io.IOException;
import org.example.common.http.response.HttpResponseContext;

public interface HttpResponder {

    void sendSucces(HttpResponseContext context) throws IOException;

    void sendError(HttpResponseContext context, int status, String message)
        throws IOException;
}
