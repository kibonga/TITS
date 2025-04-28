package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PipelineRunner {

  private static final Logger logger = LoggerFactory.getLogger(PipelineRunner.class);

  private PipelineRunner() {
  }

  public static int runPipelineStep(String step, String path)
      throws RuntimeException, IOException, InterruptedException {
    return CommandRunner.runCommand(
        wrapPipelineStep(step),
        new File(path)
    );
  }

  // In order to return stderr status code we need to provide "set -e;"
  private static List<String> wrapPipelineStep(String step) {
    return new ArrayList<>(List.of("bash", "-c", "(set -e; " + step + ")"));
  }
}
