package dev.iotapp.connex.application.service;

import java.util.List;
import java.util.Map;

import dev.iotapp.connex.application.model.EdgeDataDriver;
import dev.iotapp.connex.application.model.EdgeDataSource;
import dev.iotapp.connex.application.model.IotDevice;

public interface IotHubService {
    
    IotDevice iotHubServiceTestCode(String deviceId);
    EdgeDataDriver deployEdgeModule(EdgeDataDriver driver);
    List<EdgeDataSource> getEdgeDataSources(String deviceId);
    boolean addEdgeDataSource(String deviceId, EdgeDataSource ds);
    void updateEdgeDataSource(String deviceId, EdgeDataSource ds);
    EdgeDataSource getDataSource(String deviceId, String dataSrcType, String dataSrcName);
    void setEdgeDataSourceConfiguration(String deviceId, String dataSrcType, String dataSrcName, Map<String, Object> settings);
}
