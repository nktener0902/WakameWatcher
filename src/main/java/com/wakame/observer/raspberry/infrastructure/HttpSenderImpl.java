package com.wakame.observer.raspberry.infrastructure;

import com.wakame.observer.raspberry.domain.messaging.HttpSender;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

@Component
public class HttpSenderImpl implements HttpSender {

    @Override
    public void post(String uri, Map<String, String> forms) throws IOException {

        String curl = "curl -Ss --location --request POST '" + uri + "' " +
                "--header 'Content-Type: multipart/form-data' ";
        for (Iterator<Map.Entry<String, String>> iterator = forms.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> form = iterator.next();
            curl += " --form '" + form.getKey() + "=" + form.getValue() + "'";
        }
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
