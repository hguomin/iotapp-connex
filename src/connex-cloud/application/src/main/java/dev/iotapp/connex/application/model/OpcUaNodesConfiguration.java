package dev.iotapp.connex.application.model;

import com.google.gson.annotations.SerializedName;

public class OpcUaNodesConfiguration {
    @SerializedName("Id")
    private String id;
    @SerializedName("OpcSamplingInterval")
    private int opcSamplingInterval;
    @SerializedName("OpcPublishingInterval")
    private int opcPublishingInterval;

    public OpcUaNodesConfiguration() {
        this.id = "";
        this.opcSamplingInterval = 1000;
        this.opcPublishingInterval = 100;
    }

    public OpcUaNodesConfiguration(String id) {
        this.id = id;
        this.opcSamplingInterval = 1000;
        this.opcPublishingInterval = 1000;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOpcSamplingInterval() {
        return this.opcSamplingInterval;
    }

    public void setOpcSamplingInterval(int opcSamplingInterval) {
        this.opcSamplingInterval = opcSamplingInterval;
    }

    public int getOpcPublishingInterval() {
        return this.opcPublishingInterval;
    }

    public void setOpcPublishingInterval(int opcPublishingInterval) {
        this.opcPublishingInterval = opcPublishingInterval;
    }
}
