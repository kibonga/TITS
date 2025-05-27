package org.example.exceptions;

import lombok.Getter;

@Getter
public class PipelineRunnerException extends Exception {

    private final String step;
    private final String path;

    public PipelineRunnerException(String message, String step, String path) {
        super(message);
        this.step = step;
        this.path = path;
    }
}
