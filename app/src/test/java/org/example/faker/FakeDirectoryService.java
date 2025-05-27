package org.example.faker;

import java.nio.file.Path;
import lombok.Setter;
import org.example.common.directory.DirectoryService;

@Setter
public class FakeDirectoryService implements DirectoryService {

    private boolean exists;
    private boolean removeDoNothing;

    @Override
    public void create(Path path) {
        return;
    }

    @Override
    public void remove(Path path) {
        if (this.removeDoNothing) {
            return;
        }
    }

    @Override
    public boolean exists(Path path) {
        return this.exists;
    }
}
