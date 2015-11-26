Update Reminder for Android
===

[ ![Download](https://api.bintray.com/packages/guitoun3/maven/update-reminder/images/download.svg) ](https://bintray.com/guitoun3/maven/update-reminder/_latestVersion)


![Sample](sample/images/screenshot.png "Sample")


## Installation

### Gradle

```groovy
dependencies {
    compile 'com.github.guitoun3:update-reminder:1.0.0'
}
```

## Usage

```java
new UpdateReminder.Builder(this)
                .setBaseUrl("http://yourdomain.com/")
                .setPath("version.json")
                .build()
                .checkUpdate();
```

This file must be placed on http://yourdomain.com/version.json

```json
{"enabled":true,"version":"1.0","force_update":false}
```