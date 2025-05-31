package org.example.repository.info;

import com.fasterxml.jackson.databind.JsonNode;

public interface RepositoryInfoParser {

    RepositoryInfo parse(JsonNode root);
}
