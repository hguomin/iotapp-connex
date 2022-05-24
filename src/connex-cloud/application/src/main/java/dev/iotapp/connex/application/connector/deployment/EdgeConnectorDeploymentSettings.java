package dev.iotapp.connex.application.connector.deployment;

import java.util.HashMap;
import java.util.Map;

public class EdgeConnectorDeploymentSettings {
    private Map<String, EdgeConnectorDeploymentSetting> settings = new HashMap<>();

    public Map<String, EdgeConnectorDeploymentSetting> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, EdgeConnectorDeploymentSetting> settings) {
        this.settings = settings;
    }

    public  EdgeConnectorDeploymentSetting getSetting(String connectorType) {
        return this.settings.get(connectorType);
    }
}
