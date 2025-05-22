package org.example.geminiapi.function;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JsonFormat {
    //用于解析json
    public static String json( StringBuilder response){
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
