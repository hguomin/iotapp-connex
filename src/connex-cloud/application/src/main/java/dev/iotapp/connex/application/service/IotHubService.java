package dev.iotapp.connex.application.service;

import java.util.List;
import java.util.Map;

import dev.iotapp.connex.application.model.EdgeDataConnector;
import dev.iotapp.connex.application.model.EdgeDataSource;
import dev.iotapp.connex.application.model.IotDevice;

public interface IotHubService {
    
    IotDevice iotHubServiceTestCode(String deviceId);
    List<EdgeDataConnector> getEdgeModules(String deviceId);
    EdgeDataConnector deployEdgeModule(EdgeDataConnector driver);
    List<EdgeDataSource> getEdgeDataSources(String deviceId);
    EdgeDataSource addEdgeDataSource(String deviceId, EdgeDataSource ds);
    void updateEdgeDataSource(String deviceId, EdgeDataSource ds);
    EdgeDataSource getDataSource(String deviceId, String connectorType, String connector, String dataSrcName);

}
