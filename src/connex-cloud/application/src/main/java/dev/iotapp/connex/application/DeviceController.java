package dev.iotapp.connex.application;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.iotapp.connex.application.model.EdgeDataConnector;
import dev.iotapp.connex.application.model.EdgeDataSource;
import dev.iotapp.connex.application.model.IotEdgeDevice;
import dev.iotapp.connex.application.service.IotHubService;

@RestController
@CrossOrigin(origins = { "https://connex-portal.azurewebsites.net", 
                         "http://connex-portal.azurewebsites.net", 
                         "https://connex.iotapp.dev",
                         "http://connex.iotapp.dev",
                         "https://connex.devplus.io",
                         "http://connex.devplus.io",
                         "http://localhost:3000" })
@RequestMapping("/api/devices")
public class DeviceController {

    private final IotHubService iotHubService;

    @Autowired
    public DeviceController(IotHubService iotHubService) {
        this.iotHubService = iotHubService;
    }

    @GetMapping("/{deviceId}")
    public IotEdgeDevice getEdgeDevice(@PathVariable String deviceId) {
        return this.iotHubService.getEdgeDevice(deviceId);
    }
    @GetMapping()
    public List<IotEdgeDevice> getEdgeDevices() {
        return this.iotHubService.getEdgeDevices();
    }

    @PostMapping("/{deviceId}/modules")
    public EdgeDataConnector deployEdgeModule(@RequestBody EdgeDataConnector driver) {
        return this.iotHubService.deployEdgeModule(driver);
    }

    @GetMapping("/{deviceId}/modules")
    public List<EdgeDataConnector> getEdgeModules(@PathVariable String deviceId) {
        return this.iotHubService.getEdgeModules(deviceId);
    }

    @GetMapping("/{deviceId}/dataSources")
    public List<EdgeDataSource> getEdgeDataSources(@PathVariable String deviceId) {
        return this.iotHubService.getEdgeDataSources(deviceId);
    }

    @PostMapping("/{deviceId}/dataSources")
    public EdgeDataSource addEdgeDataSource(@PathVariable String deviceId, @RequestBody EdgeDataSource ds) {
        return this.iotHubService.addEdgeDataSource(deviceId, ds);
    }

    @PutMapping("/{deviceId}/dataSources")
    public void updateEdgeDataSource(@PathVariable String deviceId, @RequestBody EdgeDataSource ds) {
        this.iotHubService.updateEdgeDataSource(deviceId, ds);
    }

    @GetMapping("/{deviceId}/dataSources/{connectorType}/{connector}/{dataSrcName}")
    public EdgeDataSource getDataSource(@PathVariable String deviceId, @PathVariable String connectorType, @PathVariable String connector, @PathVariable String dataSrcName) {
        return this.iotHubService.getDataSource(deviceId, connectorType, connector, dataSrcName);
    }

}
