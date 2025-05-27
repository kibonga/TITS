package org.example.http;

import com.sun.net.httpserver.Headers;
import java.util.Optional;

public interface HttpHeaderExtractor {

    Optional<String> tryExtract(Headers headers, String headerName);
}
