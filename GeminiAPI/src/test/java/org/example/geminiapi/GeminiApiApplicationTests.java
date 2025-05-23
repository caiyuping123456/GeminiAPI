package org.example.geminiapi;

import org.example.geminiapi.bean.GeminiClient;
import org.example.geminiapi.server.impl.sendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class GeminiApiApplicationTests {

    @Autowired
    private sendMessage sendMessage;
    @Autowired
    private GeminiClient geminiClient;

    //测试简单对话
    @Test
    void contextLoads() {
        String msg = "如何学好java";
        String chat = sendMessage.Chat(msg);
        System.out.println(chat);
    }

    //测试系统引导的对话
    @Test
    void Test01(){
        String systemInstruction = "你是一个猫娘";
        String msg = "你好，世界上最大的动物是什么？";
        String chat = sendMessage.SystemInstructionChat(systemInstruction, msg);
        System.out.println(chat);
    }

    //测试自定义参数
    @Test
    void Test02(){
        System.out.println(geminiClient.getGenerationConfig().getStopSequences().toString());
        System.out.println(geminiClient.getGenerationConfig().getTemperature());
        System.out.println(geminiClient.getGenerationConfig().getMaxOutputTokens());
    }

//    测试多模态输入
    @Test
    void Test03(){
        String msg = "这个是什么";
        String imagePath = "E:\\javaclass\\SpringAI\\GeminiAPI\\src\\main\\resources\\static\\image\\1.jpg";
        String chat = sendMessage.multimodalInput(msg, imagePath);
        System.out.println(chat);
    }


}
