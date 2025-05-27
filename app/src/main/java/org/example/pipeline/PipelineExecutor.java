package org.example.pipeline;

import java.io.File;
import org.example.exceptions.PipelineRunnerException;

public interface PipelineExecutor {

    public int runPipelineStep(String step, File file)
        throws PipelineRunnerException;
}
