package org.example.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandRunner implements CommandExecutor {

    private static final Logger logger =
        LoggerFactory.getLogger(CommandRunner.class);

    @Override
    public int runCommand(List<String> command, File dir)
        throws IOException, InterruptedException {
        final Process process = new ProcessBuilder(command)
            .directory(dir)
            .redirectErrorStream(true)
            .start();

        // Constantly trying to empty the buffer in order not to reach deadlock due to input stream data overflowing the buffer
        try (final BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
            bufferedReader.lines().forEach(logger::info);
        }

        return process.waitFor();
    }
}
