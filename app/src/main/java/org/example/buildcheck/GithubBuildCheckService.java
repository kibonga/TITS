package org.example.buildcheck;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import javax.net.ssl.HttpsURLConnection;
import org.example.common.http.connection.ConnectionFactory;
import org.example.common.env.Environment;

public class GithubBuildCheckService implements BuildCheckService {

    private static final String GITHUB_TOKEN = "GITHUB_TOKEN";
    private final ConnectionFactory connectionFactory;

    public GithubBuildCheckService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public HttpsURLConnection connection(String url)
        throws IOException, URISyntaxException {
        final HttpsURLConnection connection =
            this.connectionFactory.create(url);

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept",
            "application/vnd.github.v3+json");
        connection.setRequestProperty("Content-Type",
            "application/json; charset=UTF-8");
        connection.setRequestProperty("Authorization",
            String.format("Bearer %s", Environment.get(GITHUB_TOKEN)));

        return connection;
    }

    @Override
    public void write(HttpsURLConnection connection, byte[] buildCheckResponse)
        throws IOException {
        try (final OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(buildCheckResponse);
        }
    }
}
