package com.wakame.observer.raspberry.infrastructure.messaging.slack;

import java.net.MalformedURLException;
import java.net.URL;

public class WebhookUrl {

    private URL url;

    static public WebhookUrl createWebhook(String urlString) throws Exception {
        try {
            URL url = new URL(urlString);
            return new WebhookUrl(url);
        }catch (MalformedURLException e) {
            e.printStackTrace();
            throw new Exception("");
        }
    }

    private WebhookUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
}
