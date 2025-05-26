package org.example.Faker;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.RepositoryInfo;
import org.example.RepositoryInfoParser;

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
