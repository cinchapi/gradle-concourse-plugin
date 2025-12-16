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

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A Gradle plugin for managing Concourse Plugin projects.
 *
 * <p>
 * Apply the plugin using standard Gradle convention:
 * <pre>
 * plugins {
 *     id 'com.cinchapi.concourse-plugin'
 * }
 * </pre>
 *
 * <p>
 * Configure the plugin bundle:
 * <pre>
 * bundle {
 *     bundleName = "my-plugin"
 * }
 * </pre>
 *
 * @author Jeff Nelson
 */
class ConcoursePluginPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // Ensure that the java plugin is applied
        project.plugins.apply('java')

        // Create the bundle extension for configuration
        BundleExtension ext = project.extensions.create(
                'bundle',
                BundleExtension
        )

        // Register the bundleZip task
        project.tasks.register('bundleZip', BundleZipTask) { task ->
            task.bundleName.convention(ext.bundleName)
            task.dependsOn(project.tasks.named('jar'))
        }

        // Configure the task after project evaluation when all properties
        // are available
        project.afterEvaluate {
            project.tasks.named('bundleZip', BundleZipTask).configure { task ->
                task.configure(ext)
            }
        }
    }

}
