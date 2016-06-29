# gradle-concourse-plugin
This plugin takes a project with Concourse plugins and packages them into a
bundle that can be installed with the `concourse plugins install <file>`
command.

In particular, this plugin packages a project into a common bundle structure
with the following structure:
```
[bundle-name]-[bundle-version]
    lib/
        [jars]
    bin/
        [scripts]
    conf/
        plugin.prefs
```
Bundles are produced as a zip file named `[bundle-name]-[project.version].zip`.

## Usage
Apply the plugin using the standard Gradle convention:
```gradle
plugins {
    id 'com.cinchapi.concourse-plugin'
}
```
Set the bundle name
```gradle
bundle {
    bundleName 'my-bundle'
}
```
## License
Copyright © 2016 Cinchapi Inc.

This plugin is released under the Apache License, Version 2.0. For more information see LICENSE, which is included with this package.
