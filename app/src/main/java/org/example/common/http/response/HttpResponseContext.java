package org.example.common.http.response;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpResponseContext {

    void setStatus(int status);

    void setHeader(String name, String value);

    OutputStream getBodyStream();

    void commit(int contentLength) throws IOException;
}
