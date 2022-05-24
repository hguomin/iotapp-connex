package dev.iotapp.connex.application.model;

public class IotEdgeDevice {
    private String id;
    private String displayName;
    private String key;
    private String status;
    private String connectionState;
    public  IotEdgeDevice(String deviceId) {
        this.id = deviceId;
        this.displayName = deviceId;
        this.key = "";
        this.status = "";
        this.connectionState = "";
    }
    public IotEdgeDevice(String id, String name, String key, String status, String connectionState){
        this.id = id;
        this.displayName = name;
        this.key = key;
        this.status = status;
        this.connectionState = connectionState;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId(){ 
        return this.id; 
    } 

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return this.displayName;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(String connectionState) {
        this.connectionState = connectionState;
    }
}
