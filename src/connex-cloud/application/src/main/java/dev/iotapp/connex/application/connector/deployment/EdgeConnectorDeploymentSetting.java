package dev.iotapp.connex.application.connector.deployment;


import java.util.HashMap;
import java.util.Map;

public class EdgeConnectorDeploymentSetting {
    private Map<String, Object> desiredProperties = new HashMap<>();
    private Map<String, String> containerSettings = new HashMap<>();

    public Map<String, Object> getDesiredProperties() {
        return desiredProperties;
    }

    public void setDesiredProperties(Map<String, Object> desiredProperties) {
        this.desiredProperties = desiredProperties;
    }

    public Map<String, String> getContainerSettings() {
        return containerSettings;
    }

    public void setContainerSettings(Map<String, String> containerSettings) {
        this.containerSettings = containerSettings;
    }
}
