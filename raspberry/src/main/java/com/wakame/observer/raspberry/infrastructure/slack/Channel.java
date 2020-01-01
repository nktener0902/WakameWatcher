package com.wakame.observer.raspberry.infrastructure.slack;

public class Channel {

    private String channel;

    public static Channel createChannel(String channel) {
        return new Channel(channel);
    }

    private Channel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return channel;
    }
}
