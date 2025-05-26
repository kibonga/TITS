package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;

class YmlParserTest {

    @Test
    void load_shouldParseValidYaml() throws IOException {
        final Path tmpFile = Files.createTempFile("test", "yml");
        Files.write(tmpFile, "key: value".getBytes());

        try {
            final Map<String, Object> result = YmlParser.load(tmpFile);
            assertEquals("value", result.get("key"));
        } catch (YmlLoadException e) {
            throw new AssertionError(e);
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    @Test
    void load_shouldYmlLoadException() throws IOException {
        final Path tmpFile = Files.createTempFile("test", "yml");

        assertThrows(YmlLoadException.class, () -> YmlParser.load(tmpFile));
    }
}
