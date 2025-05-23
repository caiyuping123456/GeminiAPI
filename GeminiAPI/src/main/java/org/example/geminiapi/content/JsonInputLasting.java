package org.example.geminiapi.content;


import org.example.geminiapi.config.GenerationConfig;

public class JsonInputLasting {

    public static String systemInstructionChatJsonInput(String systemInstruction,String msg,GenerationConfig generationConfig){
        return String.format(
                "{\n" +
                        "    \"system_instruction\": {\n" +
                        "      \"parts\": [\n" +
                        "        {\n" +
                        "          \"text\": \"%s\"\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    \"contents\": [\n" +
                        "      {\n" +
                        "        \"parts\": [\n" +
                        "          {\n" +
                        "            \"text\": \"%s\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    ]\n"
                        + ","+generationConfigJson(generationConfig) +
                        "  }", systemInstruction,msg);
    }
    public static String chat(String msg,GenerationConfig generationConfig){
        return String.format(
                "{"
                        + "\"contents\":["
                        + "  {"
                        + "    \"parts\":["
                        + "      {\"text\":\"%s\"}"
                        + "    ]"
                        + "  }"
                        + "]"
                        + ","+generationConfigJson(generationConfig)
                        + "}", msg);
    }
    public  static String generationConfigJson(GenerationConfig generationConfig){
        String string =String.join("\",\"", generationConfig.getStopSequences());
        return String.format("\"generationConfig\": {\n" +
                "      \"stopSequences\": [\n" +
                "        \"%s\"\n" +
                "      ],\n" +
                "      \"temperature\": %f,\n" +
                "      \"maxOutputTokens\": %d,\n" +
                "      \"topP\": %f,\n" +
                "      \"topK\": %d\n" +
                "    }", string,generationConfig.getTemperature(),generationConfig.getMaxOutputTokens()
        ,generationConfig.getTopP(),generationConfig.getTopK());
    }

}
