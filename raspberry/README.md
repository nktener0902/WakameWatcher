# PetWatcher

## What is

This app takes a photo and send the photo to Slack once an hour.

## How to use

### Prerequisite

* Java (11>=)
* Gradle (5>=)

### Configuration

Following three files are required for file upload to slack.

* `/var/pet-watcher/slack/fileupload/url`
  - URL of Slack file.upload API (e.g., https://slack.com/api/files.upload)
* `/var/pet-watcher/slack/fileupload/channel`
  - Channel where this app post a photo
* `/var/pet-watcher/slack/fileupload/token`
  - Your token (e.g., xoxp-...)

Pathes of these files is configurable.
You can change the path in `/raspberry/src/main/resources/application.properties`.

To access to AWS SQS, a few configurations are required.
At first, you have to write name and region of AWS SQS queue in `/raspberry/src/main/resources/application.properties`.

```
aws.sqs.queuename=<name of your queue (e.g., pet-watcher)>
aws.sqs.region=<region (e.g., ap-northeast-1)>
```

Secondly, you have to get access key and security access token from your AWS console.
Finally, you configure the access key and security access token in your terminal (see [here](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html)).

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
