package org.example;

import java.nio.file.Path;
import java.util.Map;

public class YamlPipelineConfigProvider implements PipelineConfigProvider {

    @Override
    public Map<String, Object> load(Path path) throws YmlLoadException {
        return YmlParser.load(path);
    }
}
