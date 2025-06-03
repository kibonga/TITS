package org.example.pipeline.executor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.example.common.command.CommandExecutor;
import org.example.exceptions.PipelineRunnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PipelineExecutorImpl implements PipelineExecutor {

    private static final Logger logger =
        LoggerFactory.getLogger(PipelineExecutorImpl.class);
    private final CommandExecutor commandExecutor;

    public PipelineExecutorImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    // In order to return stderr status code we need to provide "set -e;"
    private static List<String> wrapPipelineStep(String step) {
        return new ArrayList<>(List.of("bash", "-c", "(set -e; " + step + ")"));
    }

    @Override
    public int runPipelineStep(String step, File file)
        throws PipelineRunnerException {
        try {
            return this.commandExecutor.runCommand(
                wrapPipelineStep(step),
                file
            );
        } catch (IOException | InterruptedException e) {
            logger.error(
                "Pipeline runner failed for step: [{}] on path: [{}]",
                step,
                file.toPath(), e
            );

            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            throw new PipelineRunnerException(
                "Pipeline runner failed for step: [{}] on path: [{}]",
                step,
                file.toPath().toString()
            );
        }
    }
}
