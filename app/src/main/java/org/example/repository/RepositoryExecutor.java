package org.example.repository;

import java.io.File;
import java.nio.file.Path;
import org.example.exceptions.CloneRepositoryException;

public interface RepositoryExecutor {

    void clone(RepositoryInfo repositoryInfo, Path path, File file)
        throws CloneRepositoryException;
}
