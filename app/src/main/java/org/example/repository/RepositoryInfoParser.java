package org.example.repository;

import com.fasterxml.jackson.databind.JsonNode;

public interface RepositoryInfoParser {

    RepositoryInfo parse(JsonNode root);
}
