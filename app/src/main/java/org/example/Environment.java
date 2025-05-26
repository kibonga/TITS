package org.example;

import io.github.cdimascio.dotenv.Dotenv;

public final class Environment {

    private static final Dotenv dotenv = Dotenv.configure()
        .filename(".env")
        .load();

    private Environment() {
    }

    public static String getGithubToken() {
        return dotenv.get("GITHUB_TOKEN");
    }
}
