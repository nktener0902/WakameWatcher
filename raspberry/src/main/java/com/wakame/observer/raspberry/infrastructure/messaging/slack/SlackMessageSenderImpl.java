package com.wakame.observer.raspberry.infrastructure.messaging.slack;

import com.wakame.observer.raspberry.domain.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;

@Slf4j
@Component
public class SlackMessageSenderImpl implements SlackMessageSender {

    @Autowired
    private AppConfig appConfig;

    // TODO: AppConfigのslackWebhookUrlで指定されたファイルに書かれた文字列をWebhookUrl型にして初期化する
    private WebhookUrl webhookUrl;

    @Override
    public void post() throws Exception {

        webhookUrl = WebhookUrl.createWebhook(appConfig.getSlackWebhookUrl());

        HttpURLConnection uc;
        try {
            uc = (HttpURLConnection) webhookUrl.getUrl().openConnection();
            uc.setRequestMethod("POST");
            uc.setUseCaches(false);
            uc.setDoOutput(true);
            uc.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            OutputStreamWriter out = new OutputStreamWriter(
                    new BufferedOutputStream(uc.getOutputStream()));
            out.write("{'text': 'This is a line of text in a channel.\\nAnd this is another line of text.'}");
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line = in.readLine();
            String body = "";
            while (line != null) {
                body = body + line;
                line = in.readLine();
            }
            uc.disconnect();
            //return body;
        } catch (IOException e) {
            e.printStackTrace();
            //return "client - IOException : " + e.getMessage();
        }

    }
}
