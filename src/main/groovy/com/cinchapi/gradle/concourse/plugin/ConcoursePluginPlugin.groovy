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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * A Gradle plugin that can be used for managing Concourse Plugin projects.
 *
 * Apply the plugin using standard Gradle convention:
 * plugins {
 * 	id 'com.cinchapi.concourse-plugin'
 * }
 */
class ConcoursePluginPlugin implements Plugin<Project> {

    private static final String GROUP_NAME = "Distribution"

    void apply(Project project) {
        project.plugins.apply('java') //ensure that the java plugin is applied

        BundleExtension ext = project.extensions.create('bundle', BundleExtension)

        BundleZipTask bundleZip = project.tasks.create('bundleZip', BundleZipTask, {
            group = GROUP_NAME
            description = "Creates a compressed zip file that contains required runtime resources for all the plugins in the bundle"
            bundleExtension ext
        })

        project.afterEvaluate {
            bundleZip.configure(ext)
        }

    }
}
