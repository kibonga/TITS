package org.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class TestGradleBuild {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws IOException, InterruptedException {
    final String branchName = "feature/sample-tits/parsing-yaml";

    final Yaml yaml = new Yaml();

    try (final InputStream inputStream = Files.newInputStream(Paths.get("pipeline.yml"))) {
      final Map<String, Object> yml = yaml.load(inputStream);

      final var root =
          (Map<String, Map<String, Map<String, Map<String, ArrayList<String>>>>>) yml.get("root");

      final var pipelines = getPipelines(root);

      final var branches = (Map<String, Map<String, ArrayList<String>>>) pipelines.get("branches");
      final var pullRequests = pipelines.get("pull-requests");

      final File workingDir = new File("/tmp/pipeline");

      for (final var branchWrapper : branches.entrySet()) {
        if (branchName.contains(branchWrapper.getKey()) || branchWrapper.getKey().equals("**")) {
          final var stepsWrapper = branchWrapper.getValue();
          for (final var steps : stepsWrapper.entrySet()) {
            for (final var command : steps.getValue()) {
              CommandRunner.runCommand(new ArrayList<>(List.of("bash", "-c", command)), workingDir);
            }
          }
        }
      }

    }
  }

  private static Map<String, Map<String, Map<String, ArrayList<String>>>> getPipelines(
      Map<String, Map<String, Map<String, Map<String, ArrayList<String>>>>> root) {
    return root.entrySet().stream().filter(e -> e.getKey().equals("pipelines")).findFirst().get()
        .getValue();
  }

  private static void testGradleBuild() throws IOException, InterruptedException {
    final File repoDir = new File("/tmp/pipeline");

    System.out.println("Running gradle wrapper");
    final List<String> wrapperCmd = new ArrayList<>(List.of("./gradlew", "build"));

    if (CommandRunner.runCommand(wrapperCmd, repoDir) != 0) {
      System.out.println("ERROR:: Failed to do a perform gradle wrapper.");
      return;
    }
    System.out.println("Successfully ran gradle wrapper");
  }

  private static InputStream getResourceAsStream(String path) {
    return TestGradleBuild.class.getClassLoader().getResourceAsStream(path);
  }
}
