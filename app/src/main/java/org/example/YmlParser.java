package org.example;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class YmlParser {

    private static final Logger logger =
        LoggerFactory.getLogger(YmlParser.class);

    private YmlParser() {
    }

    public static Map<String, Object> load(@Nonnull Path path)
        throws YmlLoadException {
        Objects.requireNonNull(path, "Path cannot be null");
        logger.info("Loading YML file from path: [{}]", path);

        try (final InputStream inputStream = Files.newInputStream(path)) {
            return parseYaml(inputStream, path);
        } catch (YmlLoadException | IOException | RuntimeException e) {
            logger.error("Failed to load YML on path: [{}]", path, e);

            throw new YmlLoadException("Failed to load YML on path: [{}]",
                path.toString());
        }
    }

    private static Map<String, Object> parseYaml(InputStream inputStream,
        Path path)
        throws YmlLoadException {
        final Map<String, Object> yaml = new Yaml().load(inputStream);

        if (isNull(yaml)) {
            logger.error("Empty YML on path: [{}]", path);

            throw new YmlLoadException("Loaded YML is empty on path: [{}]",
                path.toString());
        }

        return yaml;
    }
}
