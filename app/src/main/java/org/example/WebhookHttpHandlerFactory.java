package org.example;

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
