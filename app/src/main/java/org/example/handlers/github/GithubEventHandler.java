package org.example.handlers.github;

import java.util.function.Consumer;
import org.example.repository.info.RepositoryInfo;

public interface GithubEventHandler {

    Consumer<RepositoryInfo> handle();
}
