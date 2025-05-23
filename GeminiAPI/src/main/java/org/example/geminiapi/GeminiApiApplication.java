package org.example.geminiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class GeminiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeminiApiApplication.class, args);
    }

}
