Update Reminder for Android
===

[ ![Download](https://api.bintray.com/packages/guitoun3/maven/update-reminder/images/download.svg) ](https://bintray.com/guitoun3/maven/update-reminder/_latestVersion)


![Sample](sample/images/screenshot.png "Sample")


## Installation

### Gradle

```groovy
dependencies {
    compile 'com.github.guitoun3:update-reminder:1.2.0'
}
```

## Usage

```java
new UpdateReminder.Builder(this)
                .setBaseUrl("http://yourdomain.com/")
                .setPath("config.json")
                .build()
                .checkUpdate();
```

This file must be placed on http://yourdomain.com/config.json

```json
{
  "update_reminder": {
    "enabled": true,
    "versionCode": "3",
    "force_update": false,
    "message": "Your custom message for users"
  },
  "your_config_key1": "12",
  "your_other_config_stuff": "value"
}
```