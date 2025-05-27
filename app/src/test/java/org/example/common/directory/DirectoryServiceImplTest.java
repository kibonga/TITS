package org.example.common.directory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DirectoryServiceImplTest {

    private static final Path tmpDirPath = Paths.get("/tmp/testDir");
    private final DirectoryService directoryService =
        new DirectoryServiceImpl();

    @Test
    void create_shouldCreateDirectory() {
        this.directoryService.create(tmpDirPath);

        assertTrue(Files.exists(tmpDirPath));
    }

    @Test
    void create_whenDirectoryAlreadyExists_shouldNotCreateNewOne()
        throws IOException {
        Files.createDirectory(tmpDirPath);

        this.directoryService.create(tmpDirPath);

        assertTrue(Files.exists(tmpDirPath));
    }

    @Test
    void clean_whenDirectoryExists_shouldRemoveDirectory() throws IOException {
        Files.createDirectory(tmpDirPath);
        Files.createFile(Paths.get(tmpDirPath + "/test.txt"));

        this.directoryService.remove(tmpDirPath);

        assertFalse(Files.exists(tmpDirPath));
    }

    @Test
    void clean_whenDirectoryDoesNotExist_shouldSkipRemoval() {
        this.directoryService.remove(tmpDirPath);

        assertFalse(Files.exists(tmpDirPath));
    }

    @AfterEach
    void afterEach() throws IOException {
        Files.deleteIfExists(tmpDirPath);
    }

}
