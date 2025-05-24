package org.example.geminiapi.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.geminiapi.config.GenerationConfig;

import java.net.HttpURLConnection;
import java.net.URL;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GeminiClient {
    private URL url;
    private HttpURLConnection httpURLConnection;
    private GenerationConfig generationConfig;
    private URL streamUrl;

    public GeminiClient(URL url, HttpURLConnection httpURLConnection, GenerationConfig generationConfig) {
        this.url = url;
        this.httpURLConnection = httpURLConnection;
        this.generationConfig = generationConfig;
    }

    public GeminiClient(HttpURLConnection httpURLConnection, GenerationConfig generationConfig, URL streamUrl) {
        this.httpURLConnection = httpURLConnection;
        this.generationConfig = generationConfig;
        this.streamUrl = streamUrl;
    }
}
