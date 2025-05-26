package org.example.Faker;

import java.io.File;
import java.nio.file.Path;
import lombok.Setter;
import org.example.CloneRepositoryException;
import org.example.RepositoryExecutor;
import org.example.RepositoryInfo;

@Setter
public class FakeRepositoryExecutor implements RepositoryExecutor {

    private static final String FAILED_CLONING_REPOSITORY =
        "Failed cloning the repository";
    private static final String REPOSITORY_NAME = "test-repository";
    private static final String BRANCH_NAME = "test-branch";

    private boolean shouldThrowCloneRepositoryException;

    @Override
    public void clone(RepositoryInfo repositoryInfo, Path path, File file)
        throws CloneRepositoryException {

        if (this.shouldThrowCloneRepositoryException) {
            throw new CloneRepositoryException(
                FAILED_CLONING_REPOSITORY,
                REPOSITORY_NAME,
                BRANCH_NAME
            );
        }

        return;
    }
}
