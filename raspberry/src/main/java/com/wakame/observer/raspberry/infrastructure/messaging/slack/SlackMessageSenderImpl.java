package com.wakame.observer.raspberry.infrastructure.messaging.slack;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class SlackMessageSenderImpl implements SlackMessageSender {

    private WebhookUrl webhookUrl;

    @Override
    public void init() {

    }

    @Override
    public void post() {

        HttpURLConnection uc;
        try {
            URL url = new URL("https://hooks.slack.com/services/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");
            uc = (HttpURLConnection) url.openConnection();
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
