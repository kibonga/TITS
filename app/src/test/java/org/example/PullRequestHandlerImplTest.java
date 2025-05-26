package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.function.Consumer;
import org.example.Faker.FakePullRequestHandlerFactory;
import org.example.Faker.FakeRepositoryInfoFactory;
import org.junit.jupiter.api.Test;

class PullRequestHandlerImplTest {

    private final FakePullRequestHandlerFactory fakePullRequestHandlerFactory =
        new FakePullRequestHandlerFactory();
    private RepositoryInfo repositoryInfo =
        new FakeRepositoryInfoFactory().create();

    @Test
    void handle_successfulPipelineRun_shouldRespondSuccessfully() {
        final PullRequestHandler pullRequestHandler =
            this.fakePullRequestHandlerFactory.create();

        final Consumer<RepositoryInfo> repositoryInfoConsumer =
            pullRequestHandler.handle();

        assertDoesNotThrow(
            () -> repositoryInfoConsumer.accept(this.repositoryInfo));
    }

    @Test
    void handle_failedCloningRepository_shouldDoNothing() {
        this.fakePullRequestHandlerFactory.setShouldRepositoryExecutorThrowCloningException(
            true);
        final PullRequestHandler pullRequestHandler =
            this.fakePullRequestHandlerFactory.create();

        final Consumer<RepositoryInfo> repositoryInfoConsumer =
            pullRequestHandler.handle();

        assertDoesNotThrow(
            () -> repositoryInfoConsumer.accept(this.repositoryInfo));
    }

    @Test
    void handle_failedCreatingFileWhileCloningRepository_shouldDoNothing() {
        this.fakePullRequestHandlerFactory.setShouldFileSystemThrowIOException(
            true);
        final PullRequestHandler pullRequestHandler =
            this.fakePullRequestHandlerFactory.create();

        final Consumer<RepositoryInfo> repositoryInfoConsumer =
            pullRequestHandler.handle();

        assertDoesNotThrow(
            () -> repositoryInfoConsumer.accept(this.repositoryInfo));
    }

    @Test
    void handle_failedLoadingYamlConfigurationCaughtYamlLoadException_shouldDoNothing() {
        this.fakePullRequestHandlerFactory.setShouldPipelineConfigThrowYamlLoadException(
            true);
        final PullRequestHandler pullRequestHandler =
            this.fakePullRequestHandlerFactory.create();

        final Consumer<RepositoryInfo> repositoryInfoConsumer =
            pullRequestHandler.handle();

        assertDoesNotThrow(
            () -> repositoryInfoConsumer.accept(this.repositoryInfo));
    }

    @Test
    void handle_failedRunningPipelineStepsCaughtPipelineStepException_shouldDoNothing() {
        this.fakePullRequestHandlerFactory.setShouldPipelineHandlerThrowPipelineStepException(
            true);
        final PullRequestHandler pullRequestHandler =
            this.fakePullRequestHandlerFactory.create();

        final Consumer<RepositoryInfo> repositoryInfoConsumer =
            pullRequestHandler.handle();

        assertDoesNotThrow(
            () -> repositoryInfoConsumer.accept(this.repositoryInfo));
    }

    @Test
    void handle_buildCheckServiceConnectionFailedCaughtURISyntaxException_shoulDoNothing() {
        this.fakePullRequestHandlerFactory.setShouldBuildCheckServiceThrowURISyntaxException(
            true);
        final PullRequestHandler pullRequestHandler =
            this.fakePullRequestHandlerFactory.create();

        final Consumer<RepositoryInfo> repositoryInfoConsumer =
            pullRequestHandler.handle();

        assertDoesNotThrow(
            () -> repositoryInfoConsumer.accept(this.repositoryInfo));
    }

    @Test
    void handle_buildCheckServiceConnectionFailedCaughtIOException_shoulDoNothing() {
        this.fakePullRequestHandlerFactory.setShouldBuildCheckServiceThrowIOException(
            true);
        final PullRequestHandler pullRequestHandler =
            this.fakePullRequestHandlerFactory.create();

        final Consumer<RepositoryInfo> repositoryInfoConsumer =
            pullRequestHandler.handle();

        assertDoesNotThrow(
            () -> repositoryInfoConsumer.accept(this.repositoryInfo));
    }
}
