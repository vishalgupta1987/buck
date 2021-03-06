/*
 * Copyright 2016-present Facebook, Inc.
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

package com.facebook.buck.jvm.kotlin;

import com.facebook.buck.io.file.MoreFiles;
import com.facebook.buck.testutil.ProcessResult;
import com.facebook.buck.testutil.TemporaryPaths;
import com.facebook.buck.testutil.integration.ProjectWorkspace;
import com.facebook.buck.testutil.integration.TestDataHelper;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class KotlinLibraryIntegrationTest {
  @Rule public TemporaryPaths tmp = new TemporaryPaths();

  private ProjectWorkspace workspace;

  @Before
  public void setUp() throws IOException, InterruptedException {
    workspace =
        TestDataHelper.createProjectWorkspaceForScenario(this, "kotlin_library_description", tmp);
    workspace.setUp();

    Path kotlincPath = TestDataHelper.getTestDataScenario(this, "kotlinc");
    MoreFiles.copyRecursively(kotlincPath, tmp.newFolder("kotlinc"));

    KotlinTestAssumptions.assumeCompilerAvailable(workspace.asCell().getBuckConfig());
  }

  @Test
  public void shouldCompileKotlinClass() throws Exception {
    ProcessResult buildResult = workspace.runBuckCommand("build", "//com/example/good:example");
    buildResult.assertSuccess("Build should have succeeded.");
  }

  @Test
  public void shouldCompileLibraryWithDependencyOnAnother() throws Exception {
    ProcessResult buildResult = workspace.runBuckCommand("build", "//com/example/child:child");
    buildResult.assertSuccess("Build should have succeeded.");
  }

  @Test
  public void shouldFailToCompileInvalidKotlinCode() throws Exception {
    ProcessResult buildResult = workspace.runBuckCommand("build", "//com/example/bad:fail");
    buildResult.assertFailure();
  }

  @Test
  public void shouldCompileMixedJavaAndKotlinSources() throws Exception {
    ProcessResult buildResult = workspace.runBuckCommand("build", "//com/example/mixed:example");
    buildResult.assertSuccess("Build should have succeeded.");
  }
}
