package com.example.oslobysykkel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OsloBysykkelApplication {

    private static final Logger log = LoggerFactory.getLogger(OsloBysykkelApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OsloBysykkelApplication.class, args);
    }
}
