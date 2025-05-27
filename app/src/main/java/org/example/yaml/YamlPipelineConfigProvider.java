package org.example.yaml;

import java.nio.file.Path;
import java.util.Map;
import org.example.exceptions.YmlLoadException;
import org.example.pipeline.PipelineConfigProvider;

public class YamlPipelineConfigProvider implements PipelineConfigProvider {

    @Override
    public Map<String, Object> load(Path path) throws YmlLoadException {
        return YmlParser.load(path);
    }
}
