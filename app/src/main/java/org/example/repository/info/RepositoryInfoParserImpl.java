package org.example.repository.info;

import com.fasterxml.jackson.databind.JsonNode;

public class RepositoryInfoParserImpl implements RepositoryInfoParser {

    private static final String API_URL_BASE = "https://api.github.com/repos/";
    private static final String STATUSES = "/statuses/";
    private static final String REPOSITORY = "repository";
    private static final String PULL_REQUEST = "pull_request";
    private static final String HEAD = "head";

    private static final String ACTION = "action";
    private static final String[] REPOSITORY_NAME =
        new String[]{REPOSITORY, "full_name"};
    private static final String[] COMMIT_HASH =
        new String[]{PULL_REQUEST, HEAD, "sha"};
    private static final String[] BRANCH =
        new String[]{PULL_REQUEST, HEAD, "ref"};
    private static final String[] REPO_URL =
        new String[]{REPOSITORY, "clone_url"};
    private static final String[] PROJECT_NAME =
        new String[]{REPOSITORY, "name"};

    private static String getText(JsonNode node, String... path) {
        for (final String key : path) {
            node = node.path(key);
        }

        return node.asText();
    }

    @Override
    public RepositoryInfo parse(JsonNode root) {
        System.out.println("Start parsing " + root);

        final String repositoryName = getText(root, REPOSITORY_NAME);
        System.out.println("Repostiory name " + repositoryName);

        final String commitHash = getText(root, COMMIT_HASH);
        System.out.println("Commit hash " + commitHash);

        final String buildCheckUrl =
            API_URL_BASE + repositoryName + STATUSES + commitHash;
        System.out.println("Build check url " + buildCheckUrl);

        final var action = getText(root, ACTION);
        System.out.println("Action " + action);

        final var branch = getText(root, BRANCH);
        System.out.println("Branch " + branch);

        final var repoUrl = getText(root, REPO_URL);
        System.out.println("RepoUrl " + repoUrl);

        final var projectName = getText(root, PROJECT_NAME);
        System.out.println("ProjectName " + projectName);

        return new RepositoryInfo(
            getText(root, ACTION),
            repositoryName,
            commitHash,
            getText(root, BRANCH),
            getText(root, REPO_URL),
            getText(root, PROJECT_NAME),
            buildCheckUrl
        );
    }
}
