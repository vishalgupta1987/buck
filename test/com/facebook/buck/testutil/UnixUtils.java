/*
 * Copyright 2018-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.testutil;

import com.facebook.buck.util.Escaper.Quoter;
import com.google.common.collect.ImmutableList;
import java.nio.file.FileSystems;

/** An implementation of {@link PlatformUtils} for Unix platforms (Mac OS, Linux) */
public class UnixUtils extends PlatformUtils {
  private static final String BUCK_EXE =
      FileSystems.getDefault()
          .getPath("buck-out", "gen", "programs", "buck.pex")
          .toAbsolutePath()
          .toString();

  public UnixUtils() {
    super(Quoter.DOUBLE);
  }

  /** Returns a buck command builder for a unix platform, which runs buck through its .pex file */
  @Override
  public ImmutableList.Builder<String> getCommandBuilder() {
    ImmutableList.Builder<String> commandBuilder = ImmutableList.<String>builder();
    commandBuilder.add(BUCK_EXE);
    return commandBuilder;
  }
}
