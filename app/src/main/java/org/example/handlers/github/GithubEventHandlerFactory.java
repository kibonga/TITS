package org.example.handlers.github;

public interface GithubEventHandlerFactory {

    String getEventName();

    GithubEventHandler create();
}
