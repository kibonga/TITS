package org.example.handlers.github.pullrequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.net.ssl.HttpsURLConnection;
import org.example.buildcheck.BuildCheckService;
import org.example.buildcheck.BuildResponseFactory;
import org.example.common.directory.DirectoryService;
import org.example.handlers.github.GithubEventHandler;
import org.example.exceptions.CloneRepositoryException;
import org.example.exceptions.PipelineStepException;
import org.example.exceptions.YmlLoadException;
import org.example.common.file.FileSystem;
import org.example.pipeline.util.PipelineConfigProvider;
import org.example.handlers.pipeline.PipelineHandler;
import org.example.pipeline.util.PipelineStepExtractor;
import org.example.repository.executor.RepositoryExecutor;
import org.example.repository.info.RepositoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class PullRequestHandlerImpl implements GithubEventHandler {

    private static final String PULL_REQUESTS = "pull-requests";
    private static final String WORKING_DIR_PATH = "/tmp/pipeline";
    private static final String YML_PIPELINE = "pipeline.yml";
    private static final Logger logger =
        LoggerFactory.getLogger(PullRequestHandlerImpl.class);

    private final FileSystem fileSystem;
    private final RepositoryExecutor repositoryExecutor;
    private final PipelineHandler pipelineHandler;
    private final DirectoryService directoryService;
    private final BuildCheckService buildCheckService;
    private final PipelineConfigProvider pipelineConfigProvider;
    private final PipelineStepExtractor pipelineStepExtractor;

    public PullRequestHandlerImpl(
        PipelineHandler pipelineHandler,
        RepositoryExecutor repositoryExecutor,
        FileSystem fileSystem,
        DirectoryService directoryService,
        BuildCheckService buildCheckService,
        PipelineConfigProvider pipelineConfigProvider,
        PipelineStepExtractor pipelineStepExtractor
    ) {
        this.pipelineHandler = pipelineHandler;
        this.repositoryExecutor = repositoryExecutor;
        this.fileSystem = fileSystem;
        this.directoryService = directoryService;
        this.buildCheckService = buildCheckService;
        this.pipelineConfigProvider = pipelineConfigProvider;
        this.pipelineStepExtractor = pipelineStepExtractor;
    }

    @Override
    public Consumer<RepositoryInfo> handle() {
        return repositoryInfo -> {
            logger.info("Running the Pull Request Handler");

            final Path workingDirPath = Paths.get(WORKING_DIR_PATH);

            this.directoryService.create(workingDirPath);

            final Path projectPath = Paths.get(
                WORKING_DIR_PATH,
                repositoryInfo.projectName()
            );

            try {
                this.repositoryExecutor.clone(
                    repositoryInfo,
                    workingDirPath,
                    this.fileSystem.getFile(workingDirPath)
                );
            } catch (CloneRepositoryException e) {
                logger.error("Error occurred while cloning the repository", e);
                this.directoryService.remove(projectPath);

                return;
            } catch (IOException e) {
                logger.error("I/O error occurred while cloning the repository",
                    e);
                this.directoryService.remove(projectPath);

                return;
            }

            try {
                final Path ymlPath = Path.of(
                    WORKING_DIR_PATH,
                    repositoryInfo.projectName(),
                    YML_PIPELINE
                );

                final Map<String, Object> pipelineYml =
                    this.pipelineConfigProvider.load(ymlPath);

                final List<String> steps = this.pipelineStepExtractor.extract(
                    pipelineYml,
                    PULL_REQUESTS,
                    repositoryInfo.branch()
                );

                this.pipelineHandler.run(steps, projectPath);
            } catch (YmlLoadException e) {
                logger.error("Failed to load pipeline YML", e);
            } catch (PipelineStepException e) {
                logger.error("Pipeline step failed. Step: [{}] Project: [{}]",
                    e.getStep(), e.getPath(), e);
            } finally {
                this.directoryService.remove(projectPath);
            }

            try {
                final HttpsURLConnection httpsURLConnection =
                    this.buildCheckService.connection(
                        repositoryInfo.buildCheckUrl());

                final var buildCheckResponse =
                    BuildResponseFactory.createBuildCheckResponse(
                        "success",
                        "Pipeline successfully built."
                    );

                this.buildCheckService.write(httpsURLConnection,
                    buildCheckResponse);

                logger.info(
                    "Remote repository responded with status: [{}], message: [{}]",
                    httpsURLConnection.getResponseCode(),
                    httpsURLConnection.getResponseMessage()
                );
            } catch (URISyntaxException e) {
                logger.error(
                    "URI syntax error occurred establishing connection: [{}]",
                    repositoryInfo.buildCheckUrl(), e
                );
            } catch (IOException e) {
                logger.error(
                    "I/O error occurred while establishing connection to remote repository",
                    e
                );
            }
        };
    }
}
