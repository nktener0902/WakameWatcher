package com.wakame.observer.raspberry;

import com.wakame.observer.raspberry.service.RaspberryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RaspberryApplication implements CommandLineRunner {

    @Autowired
    RaspberryService raspberryService;

    public static void main(String[] args) {
        SpringApplication.run(RaspberryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start observing");
        raspberryService.init();
        raspberryService.start();
    }
}
