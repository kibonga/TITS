package org.example.faker;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.net.ssl.HttpsURLConnection;
import org.example.common.http.connection.ConnectionFactory;

public class FakeConnectionFactory implements ConnectionFactory {

    @Override
    public HttpsURLConnection create(String url)
        throws IOException, URISyntaxException {
        return new FakeHttpsUrlConnection();
    }
}
