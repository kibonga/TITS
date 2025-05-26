package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class PipelineHandlerImpl implements PipelineHandler {

    private static final Logger logger =
        LoggerFactory.getLogger(PipelineHandlerImpl.class);

    private final PipelineExecutor pipelineExecutor;
    private final FileSystem fileSystem;

    public PipelineHandlerImpl(PipelineExecutor pipelineExecutor,
        FileSystem fileSystem) {
        this.pipelineExecutor = pipelineExecutor;
        this.fileSystem = fileSystem;
    }

    @Override
    public void run(List<String> steps, Path path)
        throws PipelineStepException {
        if (steps.isEmpty()) {
            logger.info("Pipeline has no steps.");

            return;
        }

        final String stepsString = String.join(" ", steps);
        logger.info("Running the pipeline for the following steps: [{}]",
            stepsString);

        for (final String step : steps) {
            try {
                final int stepResult = this.pipelineExecutor.runPipelineStep(
                    step,
                    this.fileSystem.getFile(path)
                );

                if (stepResult != 0) {
                    logger.error(
                        "Pipeline step failed with a non-zero status for step: [{}]",
                        step);

                    throw new PipelineStepException(
                        "Pipeline step returned a non-zero status: "
                            + stepResult,
                        step,
                        path.toString()
                    );
                }

            } catch (PipelineRunnerException e) {
                logger.error(
                    "Pipeline runner exception occurred running the pipeline step: [{}]",
                    step, e);

                throw new PipelineStepException(
                    "Pipeline runner exception occurred running the pipeline step",
                    step,
                    path.toString()
                );
            } catch (IOException e) {
                logger.error(
                    "I/O error occurred while running the pipeline step: [{}]",
                    step, e);

                throw new PipelineStepException(
                    "I/O error occurred while running the pipeline step: [{}]",
                    step,
                    path.toString()
                );
            }
        }
    }
}
