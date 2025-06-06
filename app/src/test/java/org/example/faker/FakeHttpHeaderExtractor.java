package org.example.faker;

import com.sun.net.httpserver.Headers;
import java.util.Optional;
import org.example.common.http.HttpHeaderExtractor;

public class FakeHttpHeaderExtractor implements HttpHeaderExtractor {

    @Override
    public Optional<String> tryExtract(Headers headers, String headerName) {
        return Optional.ofNullable(headers.get(headerName))
            .flatMap(list -> list.stream().findFirst());
    }
}
