package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class RepositoryInfo {

  private static final String API_URL_BASE = "https://api.github.com/repos/";
  private static final String STATUSES = "/statuses/";

  private final JsonNode root;
  private final String action;
  private final String repositoryName;
  private final String commitHash;
  private final String branch;
  private final String repoUrl;
  private final String projectName;
  private final String buildCheckUrl;

  public RepositoryInfo(JsonNode root) {
    this.root = root;
    this.action = root.get("action").asText();
    this.repositoryName = getText(root, "repository", "full_name");
    this.commitHash = getText(root, "pull_request", "head", "sha");
    this.branch = getText(root, "pull_request", "head", "ref");
    this.repoUrl = getText(root, "repository", "clone_url");
    this.projectName = getText(root, "repository", "name");
    this.buildCheckUrl = API_URL_BASE + this.repositoryName + STATUSES + this.commitHash;
  }

  private static String getText(JsonNode node, String... path) {
    for (final String key : path) {
      node = node.path(key);
    }

    return node.asText();
  }

}
