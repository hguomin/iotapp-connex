package dev.iotapp.connex.application.model;

public class EdgeDataDriver {
    private String deviceId;
    private String driverType;
    private String driverId;

    public EdgeDataDriver() {
        this.deviceId = "";
        this.driverType = "";
        this.driverId = "";
    }
    
    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDriverType() {
        return this.driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getDriverId() {
        return this.driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
