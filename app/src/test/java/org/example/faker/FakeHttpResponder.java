package org.example.faker;

import java.io.IOException;
import org.example.common.http.responder.HttpResponder;
import org.example.common.http.response.HttpResponseContext;

public class FakeHttpResponder implements HttpResponder {

    @Override
    public void sendSucces(HttpResponseContext context) throws IOException {
        return;
    }

    @Override
    public void sendError(HttpResponseContext context, int status,
        String message)
        throws IOException {
        return;
    }
}
