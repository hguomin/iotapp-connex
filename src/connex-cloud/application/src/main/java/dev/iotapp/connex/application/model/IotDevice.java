package dev.iotapp.connex.application.model;

public class IotDevice {
    private final String id;
    private final String name;
    private final String key;

    public IotDevice(String id, String name, String key){
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public String getId(){ 
        return this.id; 
    } 

    public String getName() {
        return this.name;
    }

    public String getKey() {
        return this.key;
    }
}
