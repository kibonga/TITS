package org.example.webhook;

import org.example.buildcheck.BuildCheckService;
import org.example.command.CommandExecutor;
import org.example.command.CommandRunner;
import org.example.http.ConnectionFactory;
import org.example.http.DefaultConnectionFactory;
import org.example.common.directory.DirectoryService;
import org.example.common.directory.DirectoryServiceImpl;
import org.example.common.file.FileSystem;
import org.example.common.file.FileSystemImpl;
import org.example.buildcheck.GithubBuildCheckService;
import org.example.http.HttpExchangeResponder;
import org.example.http.HttpExchangeResponderImpl;
import org.example.http.HttpHandlerFactory;
import org.example.http.HttpResponderImpl;
import org.example.pipeline.PipelineConfigProvider;
import org.example.pipeline.PipelineExecutor;
import org.example.pipeline.PipelineExecutorImpl;
import org.example.pipeline.PipelineHandler;
import org.example.pipeline.PipelineHandlerImpl;
import org.example.pipeline.PipelineStepExtractor;
import org.example.pullrequest.PullRequestHandlerImpl;
import org.example.repository.RepositoryExecutor;
import org.example.repository.RepositoryExecutorImpl;
import org.example.repository.RepositoryInfoParserImpl;
import org.example.thread.ThreadExecutor;
import org.example.yaml.YamlPipelineConfigProvider;
import org.example.yaml.YamlPipelineStepExtractor;
import org.jetbrains.annotations.NotNull;

public class WebhookHttpHandlerFactory implements
    HttpHandlerFactory<WebhookHandler> {

    private static @NotNull PullRequestHandlerImpl getPullRequestHandler() {
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

    private static @NotNull HttpExchangeResponder getHttpExchangeResponder() {
        return new HttpExchangeResponderImpl(new HttpResponderImpl());
    }

    @Override
    public WebhookHandler create() {
        return new WebhookHandler(
            getPullRequestHandler(),
            new RepositoryInfoParserImpl(),
            new WebhookHeaderExtractor(),
            getHttpExchangeResponder(),
            new ThreadExecutor()
        );
    }
}
