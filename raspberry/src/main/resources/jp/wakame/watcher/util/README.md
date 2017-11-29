# What is this directory

以下のファイルを、任意のディレクトリに用意する。

* AWS IoTへのcertification
* privateキー
* 本アプリケーションの設定ファイル

## AWS IoTへのcertification

AWS IoTのコンソール画面でThingの証明書を作成した際のcertificationをダウンロードし、任意のディレクトリに保存する。

## privateキー

AWS IoTのコンソール画面でThingの証明書を作成した際のprivateキーをダウンロードし、任意のディレクトリに保存する。

## 本アプリケーションの設定ファイル

```
# Client endpoint, e.g. <prefix>.iot.us-east-1.amazonaws.com
clientEndpoint=xxxxx.iot.xxxxx.amazonaws.com

# Client ID, unique client ID per connection
clientId=xxxxx

# Client certificate file
certificateFile=/xxx/xxx/certificate.pem

# Client private key file
privateKeyFile=/xxx/xxx/private.pem

# Thing name
thingName=xxxxx

# Topic name
topicName=wakamesystem/pic

# Directory path where pictures are
pictreLocation=/xxx/xxx/
```