package org.example;

import java.nio.file.Path;
import java.util.Map;

public interface PipelineConfigProvider {

    Map<String, Object> load(Path path) throws YmlLoadException;
}
