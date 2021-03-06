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

/**
 * Hold the default configuration for the "bundle" extension that can be
 * delcared in build.gradle in the following way:
 *
 * bundle {
 * 	bundleName = "real-time-notifier"
 * }
 */
class BundleExtension {

    /**
     * The bundle name. Used when naming the resulting artificats.
     */
    private String bundleName;

    /**
     * Set the {@link #bundleName}
     * @param bundleName the bundle name to use
     */
    public void bundleName(String bundleName){
        this.bundleName = bundleName;
    }

    /**
     * Return the {@link #bundleName}.
     * @return the bundle name
     */
    public String getBundleName(){
        return bundleName;
    }
}
