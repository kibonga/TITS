package org.example;

import java.io.File;
import java.nio.file.Path;

public class FileSystemImpl implements FileSystem {

    @Override
    public File getFile(Path path) {
        return getFile(path.toString());
    }

    @Override
    public File getFile(String path) {
        return new File(path);
    }
}
