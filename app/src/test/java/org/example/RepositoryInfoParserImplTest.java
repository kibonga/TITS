package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class RepositoryInfoParserImplTest {

    private static final String ACTION = "synchronize";
    private static final String REPOSITORY_NAME = "kibonga/sample-tits-java";
    private static final String COMMIT_HASH =
        "818266115d0b1bb9b1fb40e00c9c5e54b41fefe4";
    private static final String BRANCH = "feature/TITS-testing-pr-2";
    private static final String REPO_URL =
        "https://github.com/kibonga/sample-tits-java.git";
    private static final String PROJECT_NAME = "sample-tits-java";
    private static final String BUILD_CHECK_URL =
        "https://api.github.com/repos/kibonga/sample-tits-java/statuses/818266115d0b1bb9b1fb40e00c9c5e54b41fefe4";

    private final RepositoryInfoParser repositoryInfoParser =
        new RepositoryInfoParserImpl();

    @Test
    void parse_jsonNodeExtractedFromPipelineYaml_shouldParseJsonNode()
        throws IOException {
        final InputStream inputStream =
            getClass().getClassLoader()
                .getResourceAsStream("webhook-payload.json");
        final ObjectMapper objectMapper = new ObjectMapper();

        final JsonNode pipeline = objectMapper.readTree(inputStream);

        final RepositoryInfo repositoryInfo =
            this.repositoryInfoParser.parse(pipeline);

        assertNotNull(repositoryInfo);
        assertEquals(ACTION, repositoryInfo.action());
        assertEquals(REPOSITORY_NAME, repositoryInfo.repositoryName());
        assertEquals(COMMIT_HASH, repositoryInfo.commitHash());
        assertEquals(BRANCH, repositoryInfo.branch());
        assertEquals(REPO_URL, repositoryInfo.repoUrl());
        assertEquals(PROJECT_NAME, repositoryInfo.projectName());
        assertEquals(BUILD_CHECK_URL, repositoryInfo.buildCheckUrl());
    }
}
