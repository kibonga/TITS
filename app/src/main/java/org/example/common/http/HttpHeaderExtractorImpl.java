package org.example.common.http;

import com.sun.net.httpserver.Headers;
import java.util.Optional;

public class HttpHeaderExtractorImpl implements HttpHeaderExtractor {

    @Override
    public Optional<String> tryExtract(Headers headers, String headerName) {
        return Optional.ofNullable(headers.get(headerName))
            .flatMap(list -> list.stream().findFirst());
    }
}
