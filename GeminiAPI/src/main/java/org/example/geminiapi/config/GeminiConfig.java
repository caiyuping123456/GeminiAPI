package org.example.geminiapi.config;

import lombok.Data;
import org.example.geminiapi.bean.GeminiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@Configuration
@ConfigurationProperties(prefix = "gemini")
@Data
public class GeminiConfig {

    private String apiKey;
    private String baseUrl;

    @Bean
    public GeminiClient geminiClien(){
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL(baseUrl+ "?key=" + apiKey);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new GeminiClient(url,httpURLConnection);
    }
}
