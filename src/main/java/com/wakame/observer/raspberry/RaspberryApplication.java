package com.wakame.observer.raspberry;

import com.wakame.observer.raspberry.service.RaspberryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RaspberryApplication implements CommandLineRunner {

    @Autowired
    RaspberryService raspberryService;

    public static void main(String[] args) {
        SpringApplication.run(RaspberryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Start observing");
        raspberryService.init();
        raspberryService.start();
    }
}
