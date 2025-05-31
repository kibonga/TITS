package org.example.faker;

import lombok.Setter;
import org.example.handlers.github.GithubEventHandler;
import org.example.handlers.github.GithubEventHandlerFactory;
import org.example.pipeline.util.PipelineStepExtractor;
import org.example.handlers.github.pullrequest.PullRequestHandlerImpl;

@Setter
public class FakePullRequestHandlerFactory implements
    GithubEventHandlerFactory {

    private boolean shouldRepositoryExecutorThrowCloningException = false;
    private boolean shouldFileSystemThrowIOException = false;
    private boolean shouldPipelineConfigThrowYamlLoadException = false;
    private boolean shouldPipelineHandlerThrowPipelineStepException = false;
    private boolean shouldBuildCheckServiceThrowURISyntaxException = false;
    private boolean shouldBuildCheckServiceThrowIOException = false;

    @Override
    public String getEventName() {
        return "pull_request";
    }

    @Override
    public GithubEventHandler create() {
        final FakeFileSystem fileSystem = new FakeFileSystem();
        final FakePipelineHandler pipelineHandler = new FakePipelineHandler();
        final FakeDirectoryService directoryService =
            new FakeDirectoryService();
        final FakeConnectionFactory connectionFactory =
            new FakeConnectionFactory();
        final FakeBuildCheckService buildCheckService =
            new FakeBuildCheckService(connectionFactory);
        final FakePipelineConfigProvider pipelineConfigProvider =
            new FakePipelineConfigProvider();
        final PipelineStepExtractor pipelineStepExtractor =
            new FakePipelineStepExctractor();

        final FakeRepositoryExecutor repositoryExecutor =
            new FakeRepositoryExecutor();

        if (this.shouldRepositoryExecutorThrowCloningException) {
            repositoryExecutor.setShouldThrowCloneRepositoryException(true);
        } else if (this.shouldFileSystemThrowIOException) {
            fileSystem.setShouldThrowIOException(true);
        } else if (this.shouldPipelineConfigThrowYamlLoadException) {
            pipelineConfigProvider.setShouldThrowYamlLoadException(true);
        } else if (this.shouldPipelineHandlerThrowPipelineStepException) {
            pipelineHandler.setShouldThrowPipelineStepException(true);
        } else if (this.shouldBuildCheckServiceThrowURISyntaxException) {
            buildCheckService.setShouldThrowURISyntaxException(true);
        } else if (this.shouldBuildCheckServiceThrowIOException) {
            buildCheckService.setShouldThrowIOException(true);
        }

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
