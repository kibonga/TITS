package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class PullRequestHandler {

  private static final String PULL_REQUESTS = "pull-requests";
  private static final String WORKING_DIR_PATH = "/tmp/pipeline";
  private static final String YML_PIPELINE = "pipeline.yml";
  private static final Logger logger = LoggerFactory.getLogger(PullRequestHandler.class);

  public PullRequestHandler() {
  }

  public static Consumer<RepositoryInfo> handle() {
    return repositoryInfo -> {
      logger.info("Running the Pull Request Handler");

      final Path workingDirPath = Paths.get(WORKING_DIR_PATH);

      DirectoryUtil.create(workingDirPath);

      final Path projectPath = Paths.get(
          WORKING_DIR_PATH,
          repositoryInfo.getProjectName()
      );

      try {
        RepositoryUtil.clone(repositoryInfo, WORKING_DIR_PATH);
      } catch (CloneRepositoryException e) {
        logger.error("Failed to clone repository", e);
        RepositoryUtil.clean(projectPath);
        return;
      } catch (Exception e) {
        RepositoryUtil.clean(projectPath);
        logger.error("Unexpected error occurred while cloning the repository", e);
        return;
      }

      try {
        final Path ymlPath = Path.of(
            WORKING_DIR_PATH,
            repositoryInfo.getProjectName(),
            YML_PIPELINE
        );

        final Map<String, Object> pipelineYml = YmlParser.load(ymlPath);

        final List<String> steps = PipelineUtil.extractSteps(
            pipelineYml,
            PULL_REQUESTS,
            repositoryInfo.getBranch()
        );

        PipelineHandler.run(steps, projectPath);
      } catch (YmlLoadException e) {
        logger.error("Failed to load pipeline YML", e);
      } catch (PipelineStepException e) {
        logger.error("Pipeline step failed. Step: [{}] Project: [{}]", e.getStep(), e.getPath(), e);
      } catch (Exception e) {
        logger.error("Unexpected error occurred while running the pipeline", e);
      } finally {
        RepositoryUtil.clean(projectPath);
      }

      try {
        final var connection = new GithubBuildStatusService.Builder()
            .method("POST")
            .url(repositoryInfo.getBuildCheckUrl())
            .output(true)
            .headers("Authorization", String.format("Bearer %s", Environment.getGithubToken()))
            .headers("Accept", "application/vnd.github.v3+json")
            .headers("Content-Type", "application/json; charset=UTF-8")
            .build()
            .createConnection();

        final var buildCheckResponse = BuildResponseFactory.createBuildCheckResponse(
            "success",
            "Pipeline successfully built."
        );

        try (final var outputStream = connection.getOutputStream()) {
          outputStream.write(buildCheckResponse);
        }

        logger.info("Remote repository responded with status: [{}], message: [{}]",
            connection.getResponseCode(), connection.getResponseMessage());
      } catch (URISyntaxException | IOException e) {
        logger.error("Error occurred while establishing connection to remote repository", e);
        throw new RuntimeException(e);
      }
    };
  }
}
