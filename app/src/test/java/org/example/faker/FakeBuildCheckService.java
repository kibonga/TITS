package org.example.faker;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.net.ssl.HttpsURLConnection;
import lombok.Setter;
import org.example.buildcheck.BuildCheckService;
import org.example.common.http.connection.ConnectionFactory;

@Setter
public class FakeBuildCheckService implements BuildCheckService {

    private static final String URI_SYNTAX_EXCEPTION_MESSAGE =
        "URI syntax exception";
    private static final String IO_EXCEPTION_MESSAGE = "IO exception";

    private final ConnectionFactory connectionFactory;

    private boolean shouldThrowURISyntaxException;
    private boolean shouldThrowIOException;

    public FakeBuildCheckService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public HttpsURLConnection connection(String url)
        throws IOException, URISyntaxException {

        if (this.shouldThrowURISyntaxException) {
            throw new URISyntaxException(url, URI_SYNTAX_EXCEPTION_MESSAGE);
        } else if (this.shouldThrowIOException) {
            throw new IOException(IO_EXCEPTION_MESSAGE);
        }

        return this.connectionFactory.create(url);
    }

    @Override
    public void write(HttpsURLConnection connection, byte[] buildCheckResponse)
        throws IOException {
        return;
    }
}
