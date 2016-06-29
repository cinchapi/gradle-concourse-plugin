/*
 * Copyright (c) 2016 Cinchapi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cinchapi.gradle.concourse.plugin

import org.gradle.api.tasks.bundling.Zip

/**
 * A Gradle task that creates a plugin bundle in zip format.
 * Invokable using ./gradlew bundleZip
 *
 * @author Jeff Nelson
 */
class BundleZipTask extends Zip {

    public BundleZipTask() {

    }

    /**
     * A pointer to the {@link BundleExtension} that configures the task.
     */
    private BundleExtension bundleExtension

    /**
     * Set the {@link #bundleExtension}.
     * @param ext the {@link BundleExtension} to set
     */
    public void bundleExtension(BundleExtension ext) {
        this.bundleExtension = ext
    }

    /**
     * Return the {@link #bundleExtension}.
     * @return the {@link BundleExtension} that configures this task
     */
    public BundleExtension getBundleExtension() {
        return this.bundleExtension;
    }

    /**
     * Return the proper base name for the task.
     * @return the base name
     */
    public String getBaseName() {
        setBaseName(bundleExtension.bundleName)
        return super.getBaseName()
    }

    /**
     * Execute the task logic.
     * @param ext the extension that configures this task with preferences
     */
    public void configure(BundleExtension ext){
        String bundleRootDir = ext.bundleName + '-' + String.valueOf(project.version)

        // Copy all dependencies into the "lib" directory of the bundle
        into("${bundleRootDir}/lib") {
            from(project.tasks.jar.outputs.files)
            from(project.configurations.runtime)
        }

        // Copy all the scripts into the "bin" directory of the bundle
        into("${bundleRootDir}/bin") {
            from("${project.projectDir}/scripts")
        }

        // TODO Copy over the plugin.prefs file
    }


}
