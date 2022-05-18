package dev.iotapp.connex.application.model;

import java.util.ArrayList;
import java.util.List;

//The Azure IoT Hub SDK use gson for json serialization
import com.google.gson.annotations.SerializedName;

public class EdgeDataSourceOpcUaConfiguration {
    @SerializedName("EndpointUrl")
    private String endpointUrl;
    @SerializedName("UseSecurity")
    private boolean useSecurity;
    @SerializedName("OpcAuthenticationMode")
    private String opcAuthenticationMode;
    @SerializedName("Username")
    private String username;
    @SerializedName("Password")
    private String password;
    @SerializedName("OpcNodes")
    private List<OpcUaNodesConfiguration> opcNodes;

    public EdgeDataSourceOpcUaConfiguration() {
        this.endpointUrl = "";
        this.useSecurity = false;
        this.opcAuthenticationMode = "UsernamePassword";
        this.username = "";
        this.password = "";
        this.opcNodes = new ArrayList<>();
    }

    public String getEndpointUrl() {
        return this.endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public boolean getUseSecurity() {
        return this.useSecurity;
    }

    public void setUseSecurity(boolean useSecurity) {
        this.useSecurity = useSecurity;
    }

    public String getOpcAuthenticationMode() {
        return this.opcAuthenticationMode;
    }

    public void setOpcAuthenticationMode(String mode) {
        this.opcAuthenticationMode = mode;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<OpcUaNodesConfiguration> getOpcNodes() {
        return this.opcNodes;
    }

    public void setOpcNodes(List<OpcUaNodesConfiguration> opcNodes) {
        this.opcNodes = opcNodes;
    }

    public void addNode(OpcUaNodesConfiguration node) {
        this.opcNodes.add(node);
    }
}
