package org.example.faker;

import io.github.cdimascio.dotenv.Dotenv;

public class FakerTestEnv {

    public static void main(String[] args) {
        final var dotenv = Dotenv.configure().filename(".env").load();
        final String token = dotenv.get("GITHUB_TOKEN");
        System.out.println(token);
    }

}
