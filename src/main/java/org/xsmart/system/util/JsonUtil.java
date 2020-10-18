package org.xsmart.system.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

    private static final ObjectMapper Objectmaper = new ObjectMapper();

    public static <T> String objectToJsonString(T object){
        String jsonString = "";
        try {
            jsonString = Objectmaper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static <T> T jsonStringToObject(String jsonString,Class<T> valueType){
        T resultObject = null;
        try {
            resultObject = Objectmaper.readValue(jsonString,valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultObject;
    }

}
