/*
 * Copyright (c) 2016-2024 Cinchapi Inc.
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

import groovy.json.JsonBuilder
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.bundling.Zip

import javax.inject.Inject

/**
 * A Gradle task that creates a plugin bundle in zip format.
 *
 * <p>
 * Invokable using {@code ./gradlew bundleZip}
 *
 * @author Jeff Nelson
 */
abstract class BundleZipTask extends Zip {

    /**
     * The name of the bundle.
     *
     * @return the bundle name property
     */
    @Input
    @Optional
    abstract Property<String> getBundleName()

    @Inject
    BundleZipTask() {
        group = 'Distribution'
        description = 'Creates a compressed zip file containing required ' +
                'runtime resources for all the plugins in the bundle'
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    /**
     * Configure the task with the bundle extension settings.
     *
     * @param ext the {@link BundleExtension} containing configuration
     */
    void configure(BundleExtension ext) {
        String name = ext.bundleName.getOrElse(project.name)
        String version = String.valueOf(project.version)
        String bundleRootDir = "${name}-${version}"

        archiveBaseName.set(name)

        // Create manifest.json which contains metadata about the bundle
        JsonBuilder json = new JsonBuilder()
        json {
            bundleName name
            bundleVersion version
        }
        doFirst {
            new File(temporaryDir, 'manifest.json').text = json.toString()
        }

        // Copy manifest.json to the root of the bundle
        into(bundleRootDir) {
            from(temporaryDir)
        }

        // Copy all dependencies into the "lib" directory of the bundle
        into("${bundleRootDir}/lib") {
            from(project.tasks.named('jar'))
            from(project.configurations.named('runtimeClasspath'))
        }

        // Copy all the scripts into the "bin" directory of the bundle
        into("${bundleRootDir}/bin") {
            from("${project.projectDir}/scripts")
        }

        // Copy all the files from the "conf" directory of the project
        into("${bundleRootDir}/conf") {
            from("${project.projectDir}/conf")
        }

        // Copy all the files from the "data" directory of the project
        into("${bundleRootDir}/data") {
            from("${project.projectDir}/data")
        }
    }

}
