package dev.iotapp.connex.application;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        devList.add(this.iotHubService.getDeviceById("104"));
        return devList;
    }

    @PostMapping("/{deviceId}/modules")
    public EdgeDataSource deployEdgeModule(@PathVariable String deviceId, @RequestBody EdgeDataSource module) {
        return null;
    }
}
