package org.example.faker;

import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;

public class FakeThreadExecutor implements Executor {

    @Override
    public void execute(@NotNull Runnable command) {
        return;
    }
}
