package org.example.geminiapi;

import org.example.geminiapi.server.impl.sendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class GeminiApiApplicationTests {

    @Autowired
    private sendMessage sendMessage;

    @Test
    void contextLoads() {
        String msg = "如何学好java";
        String chat = sendMessage.Chat(msg);
        System.out.println(chat);
    }

}
