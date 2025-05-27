package org.example.common.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FileSystem {

    File getFile(Path path) throws IOException;

    File getFile(String path) throws IOException;
}
