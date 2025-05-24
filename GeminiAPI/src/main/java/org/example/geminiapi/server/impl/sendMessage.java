package org.example.geminiapi.server.impl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.example.geminiapi.bean.GeminiClient;
import org.example.geminiapi.content.JsonInputLasting;
import org.example.geminiapi.function.Base64Decode;
import org.example.geminiapi.function.JsonFormat;
import org.example.geminiapi.function.SendAndRead;
import org.example.geminiapi.server.Gemini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;

@Service
public class sendMessage implements Gemini {

    @Autowired
    private GeminiClient geminiClient;

    //基本文本回复
    public String Chat(String msg){
        String jsonInput = JsonInputLasting.chat(msg,geminiClient.getGenerationConfig());
        HttpURLConnection conn = geminiClient.getHttpURLConnection();
        System.out.println(jsonInput);
        // Send request（将要问的交给ai）
        SendAndRead.Send(conn,jsonInput);
        // Read response（读取AI返回的回复字节流）
        StringBuilder response = SendAndRead.Read(conn);
        //返回一个json（已经处理了，提取的text）
        return JsonFormat.json(response);
    }

    //系统指令引导回复
    public String SystemInstructionChat(String systemInstruction,String msg){
        String jsonInput = JsonInputLasting.systemInstructionChatJsonInput(systemInstruction, msg,geminiClient.getGenerationConfig());
        HttpURLConnection conn = geminiClient.getHttpURLConnection();
        // Send request（将要问的交给ai）
        SendAndRead.Send(conn,jsonInput);
        // Read response（读取AI返回的回复字节流）
        StringBuilder response = SendAndRead.Read(conn);
        //返回一个json（已经处理了，提取的text）
        return JsonFormat.json(response);
    }

    //多模态输入
    public String multimodalInput(String msg ,String imagePath){
        //读取图片并 Base64 编码图片
        String base64Image = Base64Decode.Base64(imagePath);
        String jsonInput = JsonInputLasting.multimodalInputJson(msg, geminiClient.getGenerationConfig(), base64Image);
        HttpURLConnection conn = geminiClient.getHttpURLConnection();
        // Send request（将要问的交给ai）
        SendAndRead.Send(conn,jsonInput);
        // Read response（读取AI返回的回复字节流）
        StringBuilder response = SendAndRead.Read(conn);
        //返回一个json（已经处理了，提取的text）
        return JsonFormat.json(response);
    }

    //流式请求响应实时交互
    public void streamInput(String msg){
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLSv1.3");
            sslContext.init(null, null, null); // 使用系统默认的信任管理器和密钥管理器
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        HttpClient client = null;
        client = HttpClient.newBuilder()
                .sslContext(sslContext) // 强制使用TLS 1.3
                .connectTimeout(Duration.ofSeconds(30))
                .build();

        String jsonInput = JsonInputLasting.streamInputJson(msg, geminiClient.getGenerationConfig());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.valueOf(geminiClient.getStreamUrl())))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInput))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                .thenApply(HttpResponse::body)
                .thenAccept(lines -> lines.forEach(line -> {
                    //line是流式json（多条）
                    //System.out.println(line.toString());
                    if (line.startsWith("data: ")) {
                        String data = line.substring(6); // 移除 "data: " 前缀
                        if (!"[DONE]".equals(data)) {
                            String ans = JsonFormat.json(new StringBuilder(data));
                            System.out.print(ans);
                            // 解析 JSON 并提取文本内容
                        }
                    }
                }))
                .join();
    }


}
