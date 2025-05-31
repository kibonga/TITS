package org.example.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.example.exceptions.YmlLoadException;
import org.example.pipeline.util.PipelineStepExtractor;
import org.junit.jupiter.api.Test;

class YamlPipelineStepExtractorTest {

    private static final String PULL_REQUESTS = "pull-requests";
    private static final String BRANCHES = "branches";
    private static final String FEATURE_BRANCH = "feature/test-branch";
    private static final String HOTFIX_BRANCH = "hotfix/test-branch";
    private static final String MASTER_BRANCH = "master/test-branch";

    final Path path = Paths.get(
        Objects.requireNonNull(
            getClass().getClassLoader().getResource("pipeline.yml")).toURI());
    final Map<String, Object> yml = YmlParser.load(this.path);
    private final PipelineStepExtractor pipelineStepExtractor =
        new YamlPipelineStepExtractor();

    YamlPipelineStepExtractorTest()
        throws YmlLoadException, URISyntaxException {
    }

    @Test
    void extractSteps_parsePullRequestUsesDefaultOption_shouldReturnCorrectSteps() {
        final List<String> steps = this.pipelineStepExtractor.extract(
            this.yml,
            PULL_REQUESTS, FEATURE_BRANCH
        );

        assertFalse(steps.isEmpty());
        assertEquals(2, steps.size());
    }

    @Test
    void extractSteps_parsePullRequestUsesHotfix_shouldReturnCorrectSteps() {
        final List<String> steps = this.pipelineStepExtractor.extract(
            this.yml,
            PULL_REQUESTS, HOTFIX_BRANCH
        );

        assertFalse(steps.isEmpty());
        assertEquals(1, steps.size());
    }

    @Test
    void extractSteps_parseBranchUsesDefaultOption_shouldReturnCorrectSteps() {
        final List<String> steps = this.pipelineStepExtractor.extract(
            this.yml,
            BRANCHES, FEATURE_BRANCH
        );

        assertFalse(steps.isEmpty());
        assertEquals(2, steps.size());
    }

    @Test
    void extractSteps_parseBranchUsesMaster_shouldReturnCorrectSteps() {
        final List<String> steps = this.pipelineStepExtractor.extract(
            this.yml,
            BRANCHES, MASTER_BRANCH
        );

        assertFalse(steps.isEmpty());
        assertEquals(1, steps.size());
    }
}
