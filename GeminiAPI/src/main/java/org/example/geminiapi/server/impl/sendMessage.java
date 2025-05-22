package org.example.geminiapi.server.impl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.example.geminiapi.bean.GeminiClient;
import org.example.geminiapi.server.Gemini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;

@Service
public class sendMessage implements Gemini {

    @Autowired
    private GeminiClient geminiClient;

    public String Chat(String msg){
        String jsonInput = String.format(
                "{"
                        + "\"contents\":["
                        + "  {"
                        + "    \"parts\":["
                        + "      {\"text\":\"%s\"}"
                        + "    ]"
                        + "  }"
                        + "]"
                        + "}", msg);
        HttpURLConnection conn = geminiClient.getHttpURLConnection();
        // Send request
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getResponseCode() >= 400 ? conn.getErrorStream() : conn.getInputStream(), "utf-8"))) {

            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONArray candidatesArray = null;
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            candidatesArray = jsonObject.getJSONArray("candidates");
            JSONObject contentObject = candidatesArray.getJSONObject(0).getJSONObject("content");
            JSONArray partsArray = contentObject.getJSONArray("parts");
            String textContent = partsArray.getJSONObject(0).getString("text");
            return textContent;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
