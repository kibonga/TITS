package org.example.common.http.responder;

import java.io.IOException;
import java.io.OutputStream;
import org.example.common.http.response.HttpResponseContext;

public class HttpResponderImpl implements HttpResponder {

    private static final String PLAINTEXT = "text/plain";
    private static final int OK = 200;

    @Override
    public void sendSucces(HttpResponseContext context) throws IOException {
        sendText(context, OK, "Ok");
    }

    @Override
    public void sendError(HttpResponseContext context, int status,
        String message)
        throws IOException {
        sendText(context, status, message);
    }

    private void sendText(HttpResponseContext context, int status, String text)
        throws IOException {
        send(context, PLAINTEXT, status, text);
    }

    private void send(HttpResponseContext context, String type, int status,
        String payload)
        throws IOException {
        final byte[] data = payload.getBytes();

        context.setHeader("Content-Type", type);
        context.setStatus(status);
        context.commit(data.length);

        try (final OutputStream outputStream = context.getBodyStream()) {
            outputStream.write(data);
        }
    }
}
