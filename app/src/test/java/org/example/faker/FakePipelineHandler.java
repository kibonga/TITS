package org.example.faker;

import java.nio.file.Path;
import java.util.List;
import lombok.Setter;
import org.example.pipeline.PipelineHandler;
import org.example.exceptions.PipelineStepException;

@Setter
public class FakePipelineHandler implements PipelineHandler {

    private static final String PIPELINE_STEP_EXCEPTION_MESSAGE =
        "Pipeline step exception";
    private static final String STEP = "test-step";
    private static final String PATH = "test-path";

    private boolean shouldThrowPipelineStepException;

    @Override
    public void run(List<String> steps, Path path)
        throws PipelineStepException {

        if (this.shouldThrowPipelineStepException) {
            throw new PipelineStepException(
                PIPELINE_STEP_EXCEPTION_MESSAGE,
                STEP,
                PATH
            );
        }

        return;
    }
}
