package dev.iotapp.connex.application.model;

import java.util.HashMap;
import java.util.Map;

public class EdgeDataSource {
    private String name;
    private String connector;
    private String type;
    private String tags;
    private String description;
    private Map<String, Object> configuration = new HashMap<>();

    public EdgeDataSource() {
        this.name = "";
        this.connector = "";
        this.type = "";
        this.tags = "";
        this.description = "";
    }

    public EdgeDataSource(String name, String connector, String type, String tags, String description, Map<String, Object> configuration) {
        this.name = name;
        this.connector = connector;
        this.type = type;
        this.tags = tags;
        this.description = description;

        configuration.forEach((k, v) -> {
            this.configuration.put(k, v);
        });
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
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

    public void copyTo(Map<String, Object> target) {
        target.put("name", this.name);
        target.put("connector", this.connector);
        target.put("type", this.type);
        target.put("tags", this.tags);
        target.put("description", this.description);
        this.configuration.forEach((k, v) -> ((Map<String, Object>)target.get("configuration")).put(k, v));
    }
}
