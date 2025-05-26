package org.example;

import com.sun.net.httpserver.Headers;
import java.util.Optional;

public class WebhookHeaderExtractor implements HttpHeaderExtractor {

    @Override
    public Optional<String> tryExtract(Headers headers, String headerName) {
        return Optional.ofNullable(headers.get(headerName))
            .flatMap(list -> list.stream().findFirst());
    }
}
