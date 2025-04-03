package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommandRunner {

  private CommandRunner() {}

  public static int runCommand(List<String> command, File workingDir)
      throws IOException, InterruptedException {
    final Process process = new ProcessBuilder(command)
        .directory(workingDir)
        .redirectErrorStream(true)
        .start();

    // Constantly trying to empty the buffer in order not to reach deadlock due to input stream data overflowing the buffer
    try (final BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {
      bufferedReader.lines().forEach(System.out::println);
    }

    return process.waitFor();
  }
}
