package org.example;

import java.io.File;

public interface PipelineExecutor {

    public int runPipelineStep(String step, File file)
        throws PipelineRunnerException;
}
