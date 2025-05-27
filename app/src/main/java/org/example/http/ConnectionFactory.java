package org.example.http;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.net.ssl.HttpsURLConnection;

public interface ConnectionFactory {

    HttpsURLConnection create(String url)
        throws IOException, URISyntaxException;
}
