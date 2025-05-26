package org.example;

import java.util.function.Consumer;

public interface PullRequestHandler {

    Consumer<RepositoryInfo> handle();
}
