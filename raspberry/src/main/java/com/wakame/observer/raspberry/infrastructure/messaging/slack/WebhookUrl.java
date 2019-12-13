package com.wakame.observer.raspberry.infrastructure.messaging.slack;

import com.wakame.observer.raspberry.service.AppConfig;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class WebhookUrl {

    private URL url;

    static public WebhookUrl createWebhook() throws Exception {
        return new WebhookUrl((new AppConfig()).getSlackWebhookUrl());
    }

    private WebhookUrl(String url) throws Exception {
        try {
            this.url = new URL(url);
        }catch (MalformedURLException e) {
            e.printStackTrace();
            throw new Exception("");
        }
    }

    public URL getUrl() {
        return url;
    }
}
