package org.example.Faker;

import java.io.IOException;
import org.example.HttpResponder;
import org.example.HttpResponseContext;

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
