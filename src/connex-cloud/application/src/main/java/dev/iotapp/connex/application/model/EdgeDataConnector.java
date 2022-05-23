package dev.iotapp.connex.application.model;

public class EdgeDataConnector {
    private String deviceId;
    private String type;
    private String id;
    private String status;
    private String image;
    private String version;

    public EdgeDataConnector() {
        this.deviceId = "";
        this.type = "";
        this.id = "";
        this.status = "";
        this.image = "";
        this.version = "";
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
