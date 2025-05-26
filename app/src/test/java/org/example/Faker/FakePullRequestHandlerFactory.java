package org.example.Faker;

import lombok.Setter;
import org.example.HandlerFactory;
import org.example.PipelineStepExtractor;
import org.example.PullRequestHandler;
import org.example.PullRequestHandlerImpl;

@Setter
public class FakePullRequestHandlerFactory implements
    HandlerFactory<PullRequestHandler> {

    private boolean shouldRepositoryExecutorThrowCloningException = false;
    private boolean shouldFileSystemThrowIOException = false;
    private boolean shouldPipelineConfigThrowYamlLoadException = false;
    private boolean shouldPipelineHandlerThrowPipelineStepException = false;
    private boolean shouldBuildCheckServiceThrowURISyntaxException = false;
    private boolean shouldBuildCheckServiceThrowIOException = false;

    @Override
    public PullRequestHandler create() {
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
