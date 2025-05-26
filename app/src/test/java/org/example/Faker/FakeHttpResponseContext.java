package org.example.Faker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import lombok.Setter;
import org.example.HttpResponseContext;

@Setter
public class FakeHttpResponseContext implements HttpResponseContext {

    private boolean shouldCommitThrowIOException = false;

    @Override
    public void setStatus(int status) {
        return;
    }

    @Override
    public void setHeader(String name, String value) {
        return;
    }

    @Override
    public OutputStream getBodyStream() {
        return new ByteArrayOutputStream();
    }

    @Override
    public void commit(int contentLength) throws IOException {
        if (this.shouldCommitThrowIOException) {
            throw new IOException();
        }
    }
}
