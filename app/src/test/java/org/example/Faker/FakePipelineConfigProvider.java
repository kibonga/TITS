package org.example.Faker;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Setter;
import org.example.PipelineConfigProvider;
import org.example.YmlLoadException;

@Setter
public class FakePipelineConfigProvider implements PipelineConfigProvider {

    private static final String YAML_LOAD_EXCEPTION_MESSAGE =
        "Failed to load YML";

    private boolean shouldThrowYamlLoadException;

    @Override
    public Map<String, Object> load(Path path) throws YmlLoadException {

        if (this.shouldThrowYamlLoadException) {
            throw new YmlLoadException(YAML_LOAD_EXCEPTION_MESSAGE,
                path.toString());
        }

        return new LinkedHashMap<>();
    }
}
