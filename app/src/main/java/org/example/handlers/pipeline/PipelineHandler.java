package org.example.handlers.pipeline;

import java.nio.file.Path;
import java.util.List;
import org.example.exceptions.PipelineStepException;

public interface PipelineHandler {

    void run(List<String> steps, Path path) throws PipelineStepException;
}
