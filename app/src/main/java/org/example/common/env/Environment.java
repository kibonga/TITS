package org.example.common.env;

import io.github.cdimascio.dotenv.Dotenv;

public final class Environment {

    private static final Dotenv dotenv = Dotenv.configure()
        .filename(".env")
        .load();

    private Environment() {
    }

    public static String get(String key) {
        return dotenv.get(key);
    }
}
