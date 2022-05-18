package dev.iotapp.connex.application.model;

import java.util.HashMap;
import java.util.Map;

public class EdgeDataSource {
    private String name;
    private String type;
    private String tags;
    private String description;
    private Map<String, Object> configuration;

    public EdgeDataSource() {
        this.name = "";
        this.type = "";
        this.tags = "";
        this.description = "";
        this.configuration = new HashMap<>();
    }

    public EdgeDataSource(String name, String type, String tags, String description, Map<String, Object> configuration) {
        this.name = name;
        this.type = type;
        this.tags = tags;
        this.description = description;
        this.configuration = configuration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return this.tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }

    public void setConfiguration(Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    public Map<String, Object> getConfiguration() {
        return this.configuration;
    }
}