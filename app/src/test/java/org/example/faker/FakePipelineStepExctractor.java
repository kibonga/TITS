package org.example.faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.pipeline.util.PipelineStepExtractor;

public class FakePipelineStepExctractor implements PipelineStepExtractor {

    @Override
    public List<String> extract(Map<String, Object> pipeline, String type1,
        String type2) {
        return new ArrayList<>();
    }
}
