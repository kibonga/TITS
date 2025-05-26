package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.example.Faker.FakeConnectionFactory;
import org.junit.jupiter.api.Test;

class GithubBuildCheckServiceTest {

    private final FakeConnectionFactory connectionFactory =
        new FakeConnectionFactory();
    private final GithubBuildCheckService githubBuildCheckService =
        new GithubBuildCheckService(this.connectionFactory);
    private final String url = "https://test.com";

    @Test
    void connection_validUrlPassed_shouldCreateConnection() {
        assertDoesNotThrow(
            () -> this.githubBuildCheckService.connection(this.url));
    }

    @Test
    void write_validConnectionPassed_shouldWriteToFile()
        throws IOException, URISyntaxException {
        final var connection =
            this.githubBuildCheckService.connection(this.url);

        assertDoesNotThrow(() -> this.githubBuildCheckService.write(connection,
            "test".getBytes(
                StandardCharsets.UTF_8)));
    }
}
