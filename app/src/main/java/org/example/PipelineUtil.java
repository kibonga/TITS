package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class PipelineUtil {

  private static final Logger logger = LoggerFactory.getLogger(PipelineUtil.class);
  private static final String DEFAULT_PIPELINE_TYPE = "**";

  private PipelineUtil() {
  }

  public static List<String> extractSteps(Map<String, Object> pipelineYml, String type1,
      String type2) {
    final Map<String, Object> pullRequestMap = PipelineUtil.extractMap(
        pipelineYml,
        "root",
        "pipelines",
        type1
    );

    final String extractedType2 = extractType(type2);
    final String key = pullRequestMap.containsKey(extractedType2) ?
        extractedType2 :
        DEFAULT_PIPELINE_TYPE;

    return PipelineUtil.extractList(pullRequestMap, key, "steps");
  }

  private static List<String> extractList(Map<String, Object> pipeline, String... tree) {
    return Optional.ofNullable((ArrayList<String>) nestedStep(pipeline, tree))
        .orElse(new ArrayList<>());
  }

  private static Map<String, Object> extractMap(Map<String, Object> pipeline, String... tree) {
    return Optional.ofNullable(
        (LinkedHashMap<String, Object>) nestedStep(pipeline, tree)
    ).orElse(new LinkedHashMap<>());
  }

  private static Object nestedStep(Map<String, Object> pipeline, String... paths) {
    Object obj = pipeline;

    for (final String path : paths) {
      if (!(obj instanceof Map)) {
        return null;
      }
      obj = ((Map<?, ?>) obj).get(path);
    }

    return obj;
  }


  private static String extractType(String wrapper) {
    return Arrays.stream(wrapper.split("/"))
        .findFirst()
        .orElse(DEFAULT_PIPELINE_TYPE);
  }
}
