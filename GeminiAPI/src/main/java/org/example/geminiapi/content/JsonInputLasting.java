package org.example.geminiapi.content;


public class JsonInputLasting {

    public static String systemInstructionChatJsonInput(String systemInstruction,String msg){
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
                        "    ]\n" +
                        "  }", systemInstruction,msg);
    }
    public static String chat(String msg){
        return String.format(
                "{"
                        + "\"contents\":["
                        + "  {"
                        + "    \"parts\":["
                        + "      {\"text\":\"%s\"}"
                        + "    ]"
                        + "  }"
                        + "]"
                        + "}", msg);
    }

}
