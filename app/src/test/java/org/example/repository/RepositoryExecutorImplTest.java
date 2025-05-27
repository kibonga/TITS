package org.example.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.example.exceptions.CloneRepositoryException;
import org.example.faker.FakeCommandExecutor;
import org.example.faker.FakeDirectoryService;
import org.example.faker.FakeRepositoryInfoParser;
import org.junit.jupiter.api.Test;

class RepositoryExecutorImplTest {

    private final FakeDirectoryService directoryService =
        new FakeDirectoryService();
    private final FakeCommandExecutor commandExecutor =
        new FakeCommandExecutor();
    private final RepositoryExecutor repositoryExecutor =
        new RepositoryExecutorImpl(this.commandExecutor, this.directoryService);
    private final FakeRepositoryInfoParser repositoryInfoParser =
        new FakeRepositoryInfoParser();
    private final Path path = Paths.get("/test/path");
    private final File file = Files.createTempFile("test", "json").toFile();

    RepositoryExecutorImplTest() throws IOException {
    }

    @Test
    void clone_validCommandPassed_shouldNotThrowException() {
        final JsonNode jsonNode = JsonNodeFactory.instance.nullNode();
        final RepositoryInfo repositoryInfo =
            this.repositoryInfoParser.parse(jsonNode);

        assertDoesNotThrow(
            () -> this.repositoryExecutor.clone(repositoryInfo, this.path,
                this.file));
    }

    @Test
    void clone_invalidCommandPassed_shouldThrowCloneRepositoryException() {
        final JsonNode jsonNode = JsonNodeFactory.instance.nullNode();
        final RepositoryInfo repositoryInfo =
            this.repositoryInfoParser.parse(jsonNode);

        this.commandExecutor.setReturnCode(-1);

        final CloneRepositoryException exception =
            assertThrows(CloneRepositoryException.class,
                () -> this.repositoryExecutor.clone(repositoryInfo, this.path,
                    this.file));
        assertEquals("Failed cloning the repository", exception.getMessage());
    }

    @Test
    void clone_IOExceptionCaught_shouldThrowCloneRepositoryException() {
        final JsonNode jsonNode = JsonNodeFactory.instance.nullNode();
        final RepositoryInfo repositoryInfo =
            this.repositoryInfoParser.parse(jsonNode);

        this.commandExecutor.setThrowIOException(true);

        final CloneRepositoryException exception =
            assertThrows(CloneRepositoryException.class,
                () -> this.repositoryExecutor.clone(repositoryInfo, this.path,
                    this.file));
        assertEquals("I/O error occurred while cloning the repository",
            exception.getMessage());
    }

    @Test
    void clone_InterruptExceptionCaught_shouldThrowCloneRepositoryException() {
        final JsonNode jsonNode = JsonNodeFactory.instance.nullNode();
        final RepositoryInfo repositoryInfo =
            this.repositoryInfoParser.parse(jsonNode);

        this.commandExecutor.setThrowInterruptedException(true);

        final CloneRepositoryException exception =
            assertThrows(CloneRepositoryException.class,
                () -> this.repositoryExecutor.clone(repositoryInfo, this.path,
                    this.file));
        assertEquals("Interrupt error occurred while cloning the repository",
            exception.getMessage());
    }
}
