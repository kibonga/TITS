package org.example.buildcheck;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.net.ssl.HttpsURLConnection;

public interface BuildCheckService {

    HttpsURLConnection connection(String url)
        throws IOException, URISyntaxException;

    void write(HttpsURLConnection connection, byte[] buildCheckResponse)
        throws IOException;
}
