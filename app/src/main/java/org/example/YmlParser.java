package org.example;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class YmlParser {

  private static final Logger logger = LoggerFactory.getLogger(YmlParser.class);

  private YmlParser() {
  }

  public static Map<String, Object> load(Path path)
      throws YmlLoadException {

    logger.info("Loading YML file from path: [{}]", path);

    try (final InputStream inputStream = Files.newInputStream(path)) {
      final Map<String, Object> yml = new Yaml().load(inputStream);

      if (isNull(yml)) {
        logger.error("Loaded YML is empty on path: [{}]", path);

        throw new YmlLoadException("Loaded YML is empty on path: [{}]", path.toString());
      }

      return yml;
    } catch (IOException | RuntimeException e) {
      logger.error("Failed to load YML on path: [{}]", path, e);

      throw new YmlLoadException("Failed to load YML on path: [{}]", path.toString());
    }
  }
}
