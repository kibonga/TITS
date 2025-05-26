package org.example;

public record RepositoryInfo(
    String action,
    String repositoryName,
    String commitHash,
    String branch,
    String repoUrl,
    String projectName,
    String buildCheckUrl
) {

}
