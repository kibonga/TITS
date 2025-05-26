package org.example;

import java.io.File;
import java.nio.file.Path;

public interface RepositoryExecutor {

    void clone(RepositoryInfo repositoryInfo, Path path, File file)
        throws CloneRepositoryException;
}
