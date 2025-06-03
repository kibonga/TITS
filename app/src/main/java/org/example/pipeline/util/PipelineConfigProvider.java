package org.example.pipeline.util;

import java.nio.file.Path;
import java.util.Map;
import org.example.exceptions.YmlLoadException;

public interface PipelineConfigProvider {

    Map<String, Object> load(Path path) throws YmlLoadException;
}
