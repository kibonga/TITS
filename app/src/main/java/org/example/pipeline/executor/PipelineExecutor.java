package org.example.pipeline.executor;

import java.io.File;
import org.example.exceptions.PipelineRunnerException;

public interface PipelineExecutor {

    int runPipelineStep(String step, File file) throws PipelineRunnerException;
}
