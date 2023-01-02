package dev.iotapp.connex.application.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.ast.TypeReference;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode read(String json) {
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toObject(String json, Class<T> cls) {
        T obj = null;
        try {
            obj = mapper.readValue(json, cls);
        } catch (IllegalArgumentException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static <T> T toObject(JsonNode node, Class<T> cls) {
        T obj = null;
        try {
            obj = mapper.treeToValue(node, cls);
        } catch (IllegalArgumentException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static Map<String, Object> toMap(JsonNode node) {
        Map<String, Object> map = null;
        try {
            Map<String, Object> m = new HashMap<>();
            map = mapper.treeToValue(node, m.getClass());
        } catch (IllegalArgumentException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return map;         
    }
}
