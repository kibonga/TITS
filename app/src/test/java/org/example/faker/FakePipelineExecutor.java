package org.example.faker;

import java.io.File;
import lombok.Setter;
import org.example.pipeline.PipelineExecutor;
import org.example.exceptions.PipelineRunnerException;

@Setter
public class FakePipelineExecutor implements PipelineExecutor {

    private int stepResult = 0;
    private boolean shouldThrowPipelineRunnerException = false;

    @Override
    public int runPipelineStep(String step, File file)
        throws PipelineRunnerException {
        if (this.shouldThrowPipelineRunnerException) {
            throw new PipelineRunnerException(
                "Pipeline runner exception occurred running the pipeline step",
                step, file.getPath());
        }

        return this.stepResult;
    }
}
