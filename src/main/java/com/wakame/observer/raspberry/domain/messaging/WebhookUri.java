package com.wakame.observer.raspberry.domain.messaging;

import java.net.URI;

public class WebhookUri {

    private URI uri;

    static public WebhookUri createWebhook(String uriString) throws Exception {
        URI uri = new URI(uriString);
        return new WebhookUri(uri);
    }

    private WebhookUri(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return uri.toString();
    }
}
