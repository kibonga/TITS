package org.example.repository.executor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.example.common.command.CommandExecutor;
import org.example.common.directory.DirectoryService;
import org.example.exceptions.CloneRepositoryException;
import org.example.repository.info.RepositoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryExecutorImpl implements RepositoryExecutor {

    private static final Logger logger =
        LoggerFactory.getLogger(RepositoryExecutorImpl.class);

    private final CommandExecutor commandExecutor;
    private final DirectoryService directoryService;

    public RepositoryExecutorImpl(CommandExecutor commandExecutor,
        DirectoryService directoryService) {
        this.commandExecutor = commandExecutor;
        this.directoryService = directoryService;
    }

    private static List<String> cloneCommand(String branch, String url) {
        return new ArrayList<>(
            List.of("git", "clone", "--depth=1", "--single-branch",
                "--branch=" + branch, "--verbose", url));
    }

    @Override
    public void clone(RepositoryInfo repositoryInfo, Path path, File file)
        throws CloneRepositoryException {
        logger.info("Cloning repository: [{}] from branch [{}]",
            repositoryInfo.repoUrl(), repositoryInfo.branch());

//        this.directoryService.remove(path);

        final List<String> cloneCommand =
            cloneCommand(repositoryInfo.branch(), repositoryInfo.repoUrl());

        try {
            logger.info("Running the command: {} for file: {}", cloneCommand,
                file);
            final int commandResult =
                this.commandExecutor.runCommand(cloneCommand, file);
            if (commandResult != 0) {
                logger.error(
                    "Failed cloning the repository: [{}] for branch: [{}]",
                    repositoryInfo.repoUrl(), repositoryInfo.branch());

                throw new CloneRepositoryException(
                    "Failed cloning the repository", repositoryInfo.repoUrl(),
                    repositoryInfo.branch());
            }
        } catch (IOException e) {
            throw new CloneRepositoryException(
                "I/O error occurred while cloning the repository",
                repositoryInfo.repoUrl(), repositoryInfo.branch());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CloneRepositoryException(
                "Interrupt error occurred while cloning the repository",
                repositoryInfo.repoUrl(), repositoryInfo.branch());
        }

        logger.info("Successfully cloned the repository: {} for branch: {}",
            repositoryInfo.repoUrl(), repositoryInfo.branch());
    }
}
