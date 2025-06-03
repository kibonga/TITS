package org.example.pullrequest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.function.Consumer;
import org.example.handlers.github.GithubEventHandler;
import org.example.faker.FakePullRequestHandlerFactory;
import org.example.faker.FakeRepositoryInfoFactory;
import org.example.repository.info.RepositoryInfo;
import org.junit.jupiter.api.Test;

class PullRequestHandlerImplTest {

    private final FakePullRequestHandlerFactory fakePullRequestHandlerFactory =
        new FakePullRequestHandlerFactory();
    private final RepositoryInfo repositoryInfo =
        new FakeRepositoryInfoFactory().create();

    @Test
    void handle_successfulPipelineRun_shouldRespondSuccessfully() {
        final GithubEventHandler pullRequestHandler =
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
        final GithubEventHandler pullRequestHandler =
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
        final GithubEventHandler pullRequestHandler =
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
        final GithubEventHandler pullRequestHandler =
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
        final GithubEventHandler pullRequestHandler =
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
        final GithubEventHandler pullRequestHandler =
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
        final GithubEventHandler pullRequestHandler =
            this.fakePullRequestHandlerFactory.create();

        final Consumer<RepositoryInfo> repositoryInfoConsumer =
            pullRequestHandler.handle();

        assertDoesNotThrow(
            () -> repositoryInfoConsumer.accept(this.repositoryInfo));
    }
}
