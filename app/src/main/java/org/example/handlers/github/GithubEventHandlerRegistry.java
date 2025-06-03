package org.example.handlers.github;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.example.repository.info.RepositoryInfo;

public class GithubEventHandlerRegistry {

    private Map<String, Consumer<RepositoryInfo>> handlers = new HashMap<>();

    public GithubEventHandlerRegistry(
        List<GithubEventHandlerFactory> factories) {
        this.handlers = factories.stream()
            .collect(Collectors.toMap(
                GithubEventHandlerFactory::getEventName,
                factory -> factory.create().handle()
            ));
    }


    public Consumer<RepositoryInfo> getHandler(String event) {
        return this.handlers.getOrDefault(event, null);
    }
}
