/*
 * Copyright (c) 2020 Weasis Team and other contributors.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.weasis.core.util;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/** @author Nicolas Roduit */
public class LangUtil {

  private LangUtil() {}

  public static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
    return iterable == null ? Collections.emptyList() : iterable;
  }

  public static <T, C extends Collection<T>> C convertCollectionType(
      Iterable<?> from, C newCollection, Class<T> listClass) {
    for (Object item : from) {
      newCollection.add(listClass.cast(item));
    }
    return newCollection;
  }

  public static boolean getNULLtoFalse(Boolean val) {
    if (val != null) {
      return val;
    }
    return false;
  }

  public static boolean getNULLtoTrue(Boolean val) {
    if (val != null) {
      return val;
    }
    return true;
  }

  public static boolean getEmptytoFalse(String val) {
    if (StringUtil.hasText(val)) {
      return getBoolean(val);
    }
    return false;
  }

  public static boolean geEmptytoTrue(String val) {
    if (StringUtil.hasText(val)) {
      return getBoolean(val);
    }
    return true;
  }

  private static boolean getBoolean(String val) {
    return Boolean.TRUE.toString().equalsIgnoreCase(val);
  }

  public static OptionalDouble getOptionalDouble(Double val) {
    return val == null ? OptionalDouble.empty() : OptionalDouble.of(val);
  }

  public static OptionalInt getOptionalInteger(Integer val) {
    return val == null ? OptionalInt.empty() : OptionalInt.of(val);
  }

  /**
   * Java 9 introduces overridden methods with covariant return types for the following methods in
   * java.nio.ByteBuffer:
   *
   * @see Buffer clear​()
   * @see Buffer flip​()
   * @see Buffer limit​(int newLimit)
   * @see Buffer mark​()
   * @see Buffer position​(int newPosition)
   * @see Buffer reset​()
   * @see Buffer rewind​()
   *     <p>In Java 9 they all now return ByteBuffer, whereas the methods they override return
   *     Buffer, resulting in exceptions like this when executing on Java 8 and lower:
   *     java.lang.NoSuchMethodError: java.nio.ByteBuffer.limit(I)Ljava/nio/ByteBuffer This is
   *     because the generated byte code includes the static return type of the method, which is not
   *     found on Java 8 and lower because the overloaded methods with covariant return types don't
   *     exist. The solution is to cast ByteBuffer instances to Buffer before calling the method.
   * @param buf is a ByteBuffer
   * @return Buffer
   */
  public static Buffer safeBufferType(ByteBuffer buf) {
    // Explicit cast for compatibility with covariant return type on JDK 9's ByteBuffer
    return buf;
  }
}
