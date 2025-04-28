package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryUtil {

  private static final Logger logger = LoggerFactory.getLogger(RepositoryUtil.class);

  private RepositoryUtil() {
  }

  public static void clone(RepositoryInfo repositoryInfo, String workingDirPath)
      throws IOException, InterruptedException {
    logger.info("Cloning repository: [{}] from branch [{}]", repositoryInfo.getRepoUrl(), repositoryInfo.getBranch());

    final Path path = Paths.get(workingDirPath, repositoryInfo.getProjectName());

    DirectoryUtil.clean(path);

    final List<String> cloneCommand = cloneCommand(
        repositoryInfo.getBranch(),
        repositoryInfo.getRepoUrl()
    );

    if (CommandRunner.runCommand(cloneCommand, new File(workingDirPath)) != 0) {
      logger.info("Failed to clone repository: [{}] for branch: [{}]", repositoryInfo.getRepoUrl(),
          repositoryInfo.getBranch());

      throw new CloneRepositoryException("Failed cloning the repository",
          repositoryInfo.getRepoUrl(), repositoryInfo.getBranch());
    }

    logger.info("Successfully cloned the repository: {} for branch: {}",
        repositoryInfo.getRepoUrl(), repositoryInfo.getBranch());
  }

  public static void clean(Path path) {
    DirectoryUtil.clean(path);
  }

  private static List<String> cloneCommand(String branch, String url) {
    return new ArrayList<>(
        List.of(
            "git",
            "clone",
            "--depth=1",
            "--single-branch",
            "--branch=" + branch,
            url
        )
    );
  }
}
