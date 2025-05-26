package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.example.Faker.FakeFileSystem;
import org.example.Faker.FakePipelineExecutor;
import org.junit.jupiter.api.Test;

class PipelineHandlerImplTest {

    private final FakePipelineExecutor pipelineExecutor =
        new FakePipelineExecutor();
    private final FakeFileSystem fileSystem = new FakeFileSystem();
    private final PipelineHandlerImpl pipelineHandler =
        new PipelineHandlerImpl(this.pipelineExecutor, this.fileSystem);
    private final Path testPath = Paths.get("/test/path");

    @Test
    void run_validPipelineRun_shouldDoNothing() {
        final List<String> steps = new ArrayList<>(List.of("test"));

        this.pipelineExecutor.setStepResult(0);

        assertDoesNotThrow(
            () -> this.pipelineHandler.run(steps, this.testPath));
    }

    @Test
    void run_pipelineHasNoSteps_shouldDoNothing() {
        final List<String> steps = new ArrayList<>();

        assertDoesNotThrow(
            () -> this.pipelineHandler.run(steps, this.testPath));
    }

    @Test
    void run_pipelineStepFailed_shouldThrowPipelineStepException() {
        final List<String> steps = new ArrayList<>(List.of("test"));

        this.pipelineExecutor.setStepResult(-1);

        assertThrows(PipelineStepException.class,
            () -> this.pipelineHandler.run(steps, this.testPath));
    }

    @Test
    void run_pipelineRunnerExceptionCaught_shouldThrowPipelineStepException() {
        final List<String> steps = new ArrayList<>(List.of("test"));

        this.pipelineExecutor.setShouldThrowPipelineRunnerException(true);

        assertThrows(PipelineStepException.class,
            () -> this.pipelineHandler.run(steps, this.testPath));
    }

    @Test
    void run_IOExceptionCaught_shouldThrowPipelineStepException() {
        final List<String> steps = new ArrayList<>(List.of("test"));

        this.fileSystem.setShouldThrowIOException(true);

        assertThrows(PipelineStepException.class,
            () -> this.pipelineHandler.run(steps, this.testPath));

    }
}
