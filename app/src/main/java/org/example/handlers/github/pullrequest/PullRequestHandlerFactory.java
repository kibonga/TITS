package org.example.handlers.github.pullrequest;

import org.example.buildcheck.BuildCheckService;
import org.example.buildcheck.GithubBuildCheckService;
import org.example.common.command.CommandExecutor;
import org.example.common.command.CommandRunner;
import org.example.common.directory.DirectoryService;
import org.example.common.directory.DirectoryServiceImpl;
import org.example.common.file.FileSystem;
import org.example.common.file.FileSystemImpl;
import org.example.handlers.github.GithubEventHandler;
import org.example.handlers.github.GithubEventHandlerFactory;
import org.example.common.http.connection.ConnectionFactory;
import org.example.common.http.connection.DefaultConnectionFactory;
import org.example.pipeline.util.PipelineConfigProvider;
import org.example.pipeline.executor.PipelineExecutor;
import org.example.pipeline.executor.PipelineExecutorImpl;
import org.example.handlers.pipeline.PipelineHandler;
import org.example.handlers.pipeline.PipelineHandlerImpl;
import org.example.pipeline.util.PipelineStepExtractor;
import org.example.repository.executor.RepositoryExecutor;
import org.example.repository.executor.RepositoryExecutorImpl;
import org.example.yaml.YamlPipelineConfigProvider;
import org.example.yaml.YamlPipelineStepExtractor;
import org.jetbrains.annotations.NotNull;

public class PullRequestHandlerFactory implements GithubEventHandlerFactory {

    @Override
    public String getEventName() {
        return "pull_request";
    }

    @Override
    public @NotNull GithubEventHandler create() {
        final FileSystem fileSystem = new FileSystemImpl();
        final CommandExecutor commandRunner = new CommandRunner();
        final PipelineExecutor pipelineExecutor =
            new PipelineExecutorImpl(commandRunner);
        final PipelineHandler pipelineHandler =
            new PipelineHandlerImpl(pipelineExecutor, fileSystem);
        final DirectoryService directoryService = new DirectoryServiceImpl();
        final ConnectionFactory connectionFactory =
            new DefaultConnectionFactory();
        final BuildCheckService buildCheckService =
            new GithubBuildCheckService(connectionFactory);
        final PipelineConfigProvider pipelineConfigProvider =
            new YamlPipelineConfigProvider();
        final PipelineStepExtractor pipelineStepExtractor =
            new YamlPipelineStepExtractor();

        final RepositoryExecutor repositoryExecutor =
            new RepositoryExecutorImpl(commandRunner, directoryService);

        return new PullRequestHandlerImpl(
            pipelineHandler,
            repositoryExecutor,
            fileSystem,
            directoryService,
            buildCheckService,
            pipelineConfigProvider,
            pipelineStepExtractor
        );
    }
}
