package org.example.buildcheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

public class BuildResponseFactory {

    private BuildResponseFactory() {
    }

    public static byte[] createBuildCheckResponse(String state,
        String description)
        throws JsonProcessingException {
        final var mapper = new ObjectMapper();
        final var response = new HashMap<String, String>();

        response.put("state", state);
        response.put("context", "remote-build-check");
        response.put("description", description);

        return mapper.writeValueAsBytes(response);
    }
}
