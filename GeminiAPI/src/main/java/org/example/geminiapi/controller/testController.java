package org.example.geminiapi.controller;

import jakarta.websocket.server.PathParam;
import org.example.geminiapi.server.impl.sendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @Autowired
    private sendMessage sendMessage;

    @GetMapping("/ai")
    public String Msg(@PathParam("msg") String msg){
        System.out.println(msg);
        return sendMessage.Chat(msg);
    }

}
