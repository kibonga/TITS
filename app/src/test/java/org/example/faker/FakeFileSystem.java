package org.example.faker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Setter;
import org.example.common.file.FileSystem;

@Setter
public class FakeFileSystem implements FileSystem {

    private static final String IO_EXCEPTION_MESSAGE = "I/O exception";

    private boolean shouldThrowIOException;

    @Override
    public File getFile(Path path) throws IOException {

        if (this.shouldThrowIOException) {
            throw new IOException(IO_EXCEPTION_MESSAGE);
        }

        return Files.createTempFile("test", "txt").toFile();
    }

    @Override
    public File getFile(String path) throws IOException {
        return Files.createTempFile("test", "txt").toFile();
    }
}
