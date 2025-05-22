package org.example.geminiapi.server.impl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.example.geminiapi.bean.GeminiClient;
import org.example.geminiapi.content.JsonInputLasting;
import org.example.geminiapi.function.JsonFormat;
import org.example.geminiapi.function.SendAndRead;
import org.example.geminiapi.server.Gemini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;

@Service
public class sendMessage implements Gemini {

    @Autowired
    private GeminiClient geminiClient;

    //基本文本回复
    public String Chat(String msg){
        String jsonInput = JsonInputLasting.chat(msg);
        HttpURLConnection conn = geminiClient.getHttpURLConnection();
        // Send request（将要问的交给ai）
        SendAndRead.Send(conn,jsonInput);
        // Read response（读取AI返回的回复字节流）
        StringBuilder response = SendAndRead.Read(conn);
        //返回一个json（已经处理了，提取的text）
        return JsonFormat.json(response);
    }

    //系统指令引导回复
    public String SystemInstructionChat(String systemInstruction,String msg){
        String jsonInput = JsonInputLasting.systemInstructionChatJsonInput(systemInstruction, msg);
        HttpURLConnection conn = geminiClient.getHttpURLConnection();
        // Send request（将要问的交给ai）
        SendAndRead.Send(conn,jsonInput);
        // Read response（读取AI返回的回复字节流）
        StringBuilder response = SendAndRead.Read(conn);
        //返回一个json（已经处理了，提取的text）
        return JsonFormat.json(response);
    }

}
