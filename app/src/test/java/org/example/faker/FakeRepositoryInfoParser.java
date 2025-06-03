package org.example.faker;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.repository.info.RepositoryInfo;
import org.example.repository.info.RepositoryInfoParser;

public class FakeRepositoryInfoParser implements RepositoryInfoParser {

    @Override
    public RepositoryInfo parse(JsonNode root) {
        return new RepositoryInfo(
            "test-action",
            "test-respository-name",
            "test-branch",
            "test-repo-url",
            "test-project-url",
            "test-project-name",
            "test-check-url"
        );
    }
}
