package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class YamlPipelineStepExtractor implements PipelineStepExtractor {

    private static final String ROOT = "root";
    private static final String PIPELINES = "pipelines";
    private static final String DEFAULT_PIPELINE_TYPE = "**";
    private static final String STEPS = "steps";

    @SuppressWarnings("unchecked")
    private static Map<String, Object> extractMap(Map<String, Object> pipeline,
        String... nestedTypes) {
        return Optional.ofNullable(
                (Map<String, Object>) nestedStep(pipeline, nestedTypes)
            )
            .orElse(new LinkedHashMap<>());
    }

    @SuppressWarnings("unchecked")
    private static List<String> extractList(Map<String, Object> pipeline,
        String... nestedTypes) {
        return Optional.ofNullable(
                (ArrayList<String>) nestedStep(pipeline, nestedTypes))
            .orElse(new ArrayList<>());
    }

    private static Object nestedStep(Map<String, Object> pipeline,
        String... nestedTypes) {
        Object object = pipeline;

        for (final String nestedType : nestedTypes) {
            if (!(object instanceof Map)) {
                return null;
            }
            object = ((Map<?, ?>) object).get(nestedType);
        }

        return object;
    }

    private static String extractStep(String stepWrapper) {
        return Arrays.stream(stepWrapper.split("/"))
            .findFirst()
            .orElse(DEFAULT_PIPELINE_TYPE);
    }

    @Override
    public List<String> extract(Map<String, Object> pipeline, String type1,
        String type2) {

        final Map<String, Object> stepMap = extractMap(
            pipeline,
            ROOT, PIPELINES, type1
        );

        final String potentialStep2 = extractStep(type2);
        final String step2 = stepMap.containsKey(potentialStep2) ?
            potentialStep2 : DEFAULT_PIPELINE_TYPE;

        return extractList(stepMap, step2, STEPS);
    }
}
