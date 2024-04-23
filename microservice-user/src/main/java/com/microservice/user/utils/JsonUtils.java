package com.microservice.user.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json,clazz);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
