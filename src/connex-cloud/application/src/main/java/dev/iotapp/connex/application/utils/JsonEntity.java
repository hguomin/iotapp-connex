package dev.iotapp.connex.application.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

public class JsonEntity {
    private ObjectMapper mapper;

    @Autowired
    public JsonEntity(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    public <T> T asObject(Class<T> cls) throws JsonProcessingException, IllegalArgumentException {
        return this.mapper.treeToValue(null, cls);
    }
}
