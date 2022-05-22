package dev.iotapp.connex.application;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.iotapp.connex.application.model.EdgeDataDriver;
import dev.iotapp.connex.application.model.EdgeDataSource;
import dev.iotapp.connex.application.model.IotDevice;
import dev.iotapp.connex.application.service.IotHubService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/devices")
public class DeviceController {

    private final IotHubService iotHubService;

    @Autowired
    public DeviceController(IotHubService iotHubService) {
        this.iotHubService = iotHubService;
    }

    @GetMapping()
    public List<IotDevice> getDeviceList() {
        List<IotDevice> devList = new LinkedList<>();
        devList.add(new IotDevice("100", "MyDevice1", "pwd123456"));
        devList.add(new IotDevice("101", "MyDevice2", "pwd123456"));
        devList.add(new IotDevice("102", "MyDevice3", "pwd123456"));
        devList.add(this.iotHubService.iotHubServiceTestCode("104"));
        return devList;
    }

    @PostMapping("/{deviceId}/modules")
    public EdgeDataDriver deployEdgeModule(@RequestBody EdgeDataDriver driver) {
        return this.iotHubService.deployEdgeModule(driver);
    }

    @GetMapping("/{deviceId}/dataSources")
    public List<EdgeDataSource> getEdgeDataSources(@PathVariable String deviceId) {
        return this.iotHubService.getEdgeDataSources(deviceId);
    }

    @PostMapping("/{deviceId}/dataSources")
    public EdgeDataSource addEdgeDataSource(@PathVariable String deviceId, @RequestBody EdgeDataSource ds) {

        if(this.iotHubService.addEdgeDataSource(deviceId, ds)) {
            return ds;
        }

        return null;
    }

    @PutMapping("/{deviceId}/dataSources")
    public void updateEdgeDataSource(@PathVariable String deviceId, @RequestBody EdgeDataSource ds) {
        this.iotHubService.updateEdgeDataSource(deviceId, ds);
    }

    @GetMapping("/{deviceId}/dataSources/{dataSrcType}/{dataSrcName}")
    public EdgeDataSource getDataSource(@PathVariable String deviceId, @PathVariable String dataSrcType,  @PathVariable String dataSrcName) {
        return this.iotHubService.getDataSource(deviceId, dataSrcType, dataSrcName);
    }

    @PostMapping("/{deviceId}/dataSources/{dataSrcType}/{dataSrcName}/config")
    public void setEdgeDataSourceConfiguration(@PathVariable String deviceId, @PathVariable String dataSrcType,  @PathVariable String dataSrcName, @RequestBody Map<String, Object> settings) {
        this.iotHubService.setEdgeDataSourceConfiguration(deviceId, dataSrcType, dataSrcName, settings);
    }
}
