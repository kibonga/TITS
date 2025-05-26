package org.example;

import java.nio.file.Path;
import java.util.List;

public interface PipelineHandler {

    void run(List<String> steps, Path path) throws PipelineStepException;
}
