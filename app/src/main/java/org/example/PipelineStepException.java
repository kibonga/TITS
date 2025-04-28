package org.example;

import lombok.Getter;

@Getter
public class PipelineStepException extends RuntimeException {

  private final String step;
  private final String path;

  public PipelineStepException(String message, String step, String path) {
    super(message);
    this.step = step;
    this.path = path;
  }

}
