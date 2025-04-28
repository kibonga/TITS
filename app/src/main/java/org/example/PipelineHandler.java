package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class PipelineHandler {

  private static final Logger logger = LoggerFactory.getLogger(PipelineHandler.class);

  private PipelineHandler() {
  }

  public static void run(List<String> steps, Path path) {
    if (steps.isEmpty()) {
      logger.info("Pipeline has no steps.");

      return;
    }

    logger.info("Running the pipeline for the following steps: [{}]", String.join(", ", steps));

    for (final String step : steps) {
      try {
        if (PipelineRunner.runPipelineStep(step, path.toString()) != 0) {
          logger.error("Pipeline step failed: [{}]", step);

          throw new PipelineStepException("Pipeline step failed", step,
              path.toString());
        }

        logger.info("Pipeline run was successful");
      } catch (IOException | InterruptedException e) {
        logger.error("Exception caught while running the pipeline step: [{}]", step, e);

        throw new PipelineStepException("Exception occurred while running the pipeline step", step,
            path.toString());
      }
    }
  }
}
