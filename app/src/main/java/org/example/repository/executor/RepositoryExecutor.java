package org.example.repository.executor;

import java.io.File;
import java.nio.file.Path;
import org.example.exceptions.CloneRepositoryException;
import org.example.repository.info.RepositoryInfo;

public interface RepositoryExecutor {

    void clone(RepositoryInfo repositoryInfo, Path path, File file)
        throws CloneRepositoryException;
}
