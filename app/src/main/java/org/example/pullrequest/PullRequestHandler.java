package org.example.pullrequest;

import java.util.function.Consumer;
import org.example.repository.RepositoryInfo;

public interface PullRequestHandler {

    Consumer<RepositoryInfo> handle();
}
