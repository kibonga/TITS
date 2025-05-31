package org.example.faker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.Setter;
import org.example.common.command.CommandExecutor;

@Setter
public class FakeCommandExecutor implements CommandExecutor {

    private int returnCode = 0;
    private boolean throwIOException;
    private boolean throwInterruptedException;

    @Override
    public int runCommand(List<String> command, File dir)
        throws IOException, InterruptedException {
        if (this.throwIOException) {
            throw new IOException();
        }

        if (this.throwInterruptedException) {
            throw new InterruptedException();
        }

        return this.returnCode;
    }
}
