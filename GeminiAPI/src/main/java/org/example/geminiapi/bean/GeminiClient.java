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
}
