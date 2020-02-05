package com.wakame.observer.raspberry.domain.messaging;

import java.io.IOException;
import java.util.Map;

public interface HttpSender {

    void postImage(String uri, Map<String, String> forms) throws IOException;

    void postText(String uri, Map<String, String> forms) throws IOException;

}
