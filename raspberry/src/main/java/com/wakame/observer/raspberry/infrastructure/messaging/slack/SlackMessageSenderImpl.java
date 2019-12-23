package com.wakame.observer.raspberry.infrastructure.messaging.slack;

import com.wakame.observer.raspberry.domain.config.AppConfig;
import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
@Component
public class SlackMessageSenderImpl implements SlackMessageSender {

    @Autowired
    private AppConfig appConfig;

    private WebhookUri webhookUri;
    private Token token;
    private Channel channel;

    @Override
    public void post(Photograph photograph) throws Exception {

        webhookUri = WebhookUri.createWebhook(appConfig.getSlackWebhookUrl());
        token = Token.createToken(appConfig.getSlackWebhookToken());
        channel = Channel.createChannel(appConfig.getSlackWebhookChannel());

        log.info("webhookUri=" + webhookUri.toString());
        log.info("token=" + token.toString());
        log.info("channel=" + channel.toString());
        log.info("imagePath=" + photograph.getImage().getAbsolutePath());

//        try (FileInputStream fIStream= new FileInputStream(photograph.getImage().getAbsoluteFile())) {
//            InputStreamReader iSReader = new InputStreamReader(fIStream, "UTF-8");
//
//            int data;
//            while ((data = iSReader.read()) != -1) {
//                System.out.println(data);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        HttpResponse<String> response = Unirest.post(webhookUri.toString())
//                .header("Content-Type", "multipart/form-data")
//                //.header("Content-Type", "application/x-www-form-urlencoded")
//                .header("Authorization", "Bearer " + token.toString())
//                .field("token", token.toString())
//                .field("channels", channel.toString())
//                .field("file", new FileInputStream(photograph.getImage().getAbsoluteFile()), MULTIPART_FORM_DATA)
//                .asString();
//        log.info(response.getBody());

        String curl = "curl -Ss --location --request POST '" + webhookUri.toString() + "' " +
                "--header 'Content-Type: multipart/form-data' " +
                "--form 'token=" + token.toString() + "' " +
                "--form 'channels=" + channel.toString() + "' " +
                "--form 'file=@" + photograph.getImage().getAbsolutePath() + "'";
        ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", curl);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        int linenum = 0;
        while (true) {
            linenum++;
            line = r.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
        }
        System.out.println(sb);
    }

}
