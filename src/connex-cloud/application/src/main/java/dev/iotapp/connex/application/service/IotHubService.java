package dev.iotapp.connex.application.service;

import dev.iotapp.connex.application.model.IotDevice;

public interface IotHubService {
    
    IotDevice getDeviceById(String deviceId);

}
