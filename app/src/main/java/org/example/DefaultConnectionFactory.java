package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.net.ssl.HttpsURLConnection;

public class DefaultConnectionFactory implements ConnectionFactory {

    @Override
    public HttpsURLConnection create(String url)
        throws IOException, URISyntaxException {
        return (HttpsURLConnection) new URI(url).toURL().openConnection();
    }
}
