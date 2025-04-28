package org.example;

import lombok.Getter;

@Getter
public class YmlLoadException extends Exception {

  private final String path;

  public YmlLoadException(String message, String path) {
    super(message);
    this.path = path;
  }
}
