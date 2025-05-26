package org.example;

import java.util.List;
import java.util.Map;

public interface PipelineStepExtractor {

    List<String> extract(Map<String, Object> pipeline, String type1,
        String type2);
}
