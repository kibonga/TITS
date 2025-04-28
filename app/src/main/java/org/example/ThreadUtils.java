package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUtils {

  private static Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

  private ThreadUtils() {}

  public static void logThreadInfo() {
    logger.info("Thread Info: {}", Thread.currentThread().getName());
  }

}
