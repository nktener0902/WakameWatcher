# PetWatcher

## What is

This app takes a photo and sends the photo to Slack.

## How to use

### Prerequisite

* Java (11>=)
* Gradle (5>=)

### Configuration

This app needs following three files.

* `/tmp/pet-watcher/slack/webhook/url`
  - URL of Slack file.upload API (https://slack.com/api/files.upload)
* `/tmp/pet-watcher/slack/webhook/channel`
  - Channel where this app post a photo
* `/tmp/pet-watcher/slack/webhook/token`
  - Your token (xoxp-...)

Pathes of these files are configurable.
You can change the path in `/raspberry/src/main/resources/application.properties`.

This app repeats taking a photo and sending the photo.
By default, the interval time is a hour.
You can configure the interval time.
The setting of the interval time is written in `/raspberry/src/main/resources/application.properties`.

### Run on mac

Before running this application, a user needs to install `imagesnap` that is an image-capture application.

```bash
brew install imagesnap
tccutil reset Camera
```

After that, the user can run this application.

```bash
gradle bootRun
```
