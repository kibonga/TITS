package org.example.common.thread;

import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;

public class ThreadExecutor implements Executor {

    @Override
    public void execute(@NotNull Runnable command) {
        new Thread(command).start();
    }
}
