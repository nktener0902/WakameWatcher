package com.wakame.observer.raspberry.adapter;

import lombok.Value;

@Value
public class PhotoOrderMessage {

    private final String body;

    public PhotoOrderMessage(String body) {
        this.body = body;
    }
}
