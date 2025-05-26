package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.example.Faker.FakeCommandExecutor;
import org.junit.jupiter.api.Test;

class PipelineExecutorImplTest {

    private final FakeCommandExecutor fakeCommandExecutor =
        new FakeCommandExecutor();
    private final PipelineExecutorImpl pipelineExecutor =
        new PipelineExecutorImpl(this.fakeCommandExecutor);
    private final String testStep = "test_step";
    private final File testFile =
        Files.createTempDirectory(this.testStep).toFile();

    PipelineExecutorImplTest() throws IOException {
    }

    @Test
    void runPipelineStep_executesValidCommand_shouldReturnCorrectResult()
        throws PipelineRunnerException {
        final int result =
            this.pipelineExecutor.runPipelineStep(this.testStep, this.testFile);

        assertEquals(0, result);
    }

    @Test
    void runPipelineStep_interruptExceptionCaught_shouldThrowPipelineRunnerException() {
        this.fakeCommandExecutor.setThrowInterruptedException(true);

        assertThrows(PipelineRunnerException.class,
            () -> this.pipelineExecutor.runPipelineStep(this.testStep,
                this.testFile));
    }

    @Test
    void runPipelineStep_IOExceptionCaught_shouldThrowPipelineRunnerException() {
        this.fakeCommandExecutor.setThrowIOException(true);

        assertThrows(PipelineRunnerException.class,
            () -> this.pipelineExecutor.runPipelineStep(this.testStep,
                this.testFile));
    }
}
