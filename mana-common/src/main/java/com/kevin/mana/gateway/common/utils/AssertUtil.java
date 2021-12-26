package com.kevin.mana.gateway.common.utils;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * @author kevin
 */
public final class AssertUtil {

  private AssertUtil() {
  }

  public static void notEmpty(String string, String message) {
    if (StringUtils.isEmpty(string)) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void assertNotBlank(String string, String message) {
    if (StringUtils.isBlank(string)) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notNull(Object object, String message) {
    if (Objects.isNull(object)) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void isTrue(boolean value, String message) {
    if (!value) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void assertState(boolean condition, String message) {
    if (!condition) {
      throw new IllegalStateException(message);
    }
  }
}
