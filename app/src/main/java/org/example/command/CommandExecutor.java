package org.example.command;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CommandExecutor {

    int runCommand(List<String> command, File dir)
        throws IOException, InterruptedException;
}
