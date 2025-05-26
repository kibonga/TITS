package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryServiceImpl implements DirectoryService {

    private static final Logger logger =
        LoggerFactory.getLogger(DirectoryServiceImpl.class);

    @Override
    public void create(Path path) {
        logger.info("Creating directory on path: [{}]", path);

        if (exists(path)) {
            logger.info("Directory: [{}] already exists, skipping...", path);

            return;
        }

        if (new File(path.toString()).mkdirs()) {
            logger.info("Successfully created directory on path: [{}]", path);
        }
    }

    @Override
    public void remove(Path path) {
        logger.info("Trying to remove directory from path: [{}]", path);

        if (!exists(path)) {
            logger.info(
                "Directory does not exist at path: [{}]. Skipping removal.",
                path);

            return;
        }

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                    IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });

            logger.info("Successfully removed directory from path: [{}]", path);
        } catch (IOException e) {
            logger.error(
                "Failed to remove directory from path: {}. Reason: {}",
                path,
                e.getMessage()
            );
        }
    }

    @Override
    public boolean exists(Path path) {
        return Files.exists(path);
    }
}
