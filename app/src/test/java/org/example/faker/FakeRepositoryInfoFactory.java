package org.example.faker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.example.repository.info.RepositoryInfo;

public class FakeRepositoryInfoFactory {

    public RepositoryInfo create() {
        final JsonNode root = JsonNodeFactory.instance.nullNode();
        return new FakeRepositoryInfoParser().parse(root);
    }
}
