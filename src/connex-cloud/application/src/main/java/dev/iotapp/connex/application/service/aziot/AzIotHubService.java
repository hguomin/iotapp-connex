package dev.iotapp.connex.application.service.aziot;

import java.io.Console;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.microsoft.azure.sdk.iot.service.registry.RegistryClient;
import com.microsoft.azure.sdk.iot.service.twin.Twin;
import com.microsoft.azure.sdk.iot.service.twin.TwinClient;
import com.microsoft.azure.sdk.iot.service.twin.TwinClientOptions;
import com.microsoft.azure.sdk.iot.service.twin.TwinCollection;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.microsoft.azure.sdk.iot.service.auth.IotHubServiceSasToken;
import com.microsoft.azure.sdk.iot.service.configurations.Configuration;
import com.microsoft.azure.sdk.iot.service.configurations.ConfigurationContent;
import com.microsoft.azure.sdk.iot.service.configurations.ConfigurationsClient;
import com.microsoft.azure.sdk.iot.service.configurations.serializers.ConfigurationContentParser;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import com.microsoft.azure.sdk.iot.service.methods.DirectMethodsClient;
import com.microsoft.azure.sdk.iot.service.registry.Module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dev.iotapp.connex.application.model.EdgeDataSourceOpcUaConfiguration;
import dev.iotapp.connex.application.model.IotDevice;
import dev.iotapp.connex.application.model.OpcUaNodesConfiguration;
import dev.iotapp.connex.application.service.IotHubService;

@Service
public class AzIotHubService implements IotHubService {
    
    private final RegistryClient registryClient;
    private final ConfigurationsClient configurationsClient;
    private final TwinClient twinClient;
    private final DirectMethodsClient directMethodsClient;

    private final String edgeDeploymentManifest;

    @Autowired
    public AzIotHubService(RegistryClient registryClient, 
                        ConfigurationsClient configurationsClient, 
                        TwinClient twinClient,
                        DirectMethodsClient directMethodsClient,
                        AzSasToken iotHubSasToken, 
                        IotHubServiceSasToken iotHubSasToken2, 
                        @Qualifier("iotHubDefaultEdgeDeploymentManifest")
                        String edgeDeploymentManifet) {

        this.registryClient = registryClient;
        this.configurationsClient = configurationsClient;
        this.twinClient = twinClient;
        this.directMethodsClient = directMethodsClient;
        this.edgeDeploymentManifest = edgeDeploymentManifet;
    }

    @Override
    public IotDevice getDeviceById(String deviceId) {
        try {

            List<Module> edgeModules = this.registryClient.getModulesOnDevice("gm-dev-iotedge-device");

            for (Module m : edgeModules) {
                Twin twin = this.twinClient.get("gm-dev-iotedge-device", m.getId());
                if(m.getId().contentEquals("devplus-dev")) {
                    //this.initModuleTwin(twin);
                    TwinCollection desiredProperties = twin.getDesiredProperties();
                    Map<String, Object> ds = (HashMap<String, Object>)desiredProperties.get("dataSources");
                    List<EdgeDataSourceOpcUaConfiguration> opcua = (List<EdgeDataSourceOpcUaConfiguration>)ds.get("opcUa");

                    EdgeDataSourceOpcUaConfiguration opcUaConfiguration = new EdgeDataSourceOpcUaConfiguration();

                    opcUaConfiguration.setEndpointUrl("opc.tcp://10.2.0.4:48400/UA/ComServerWrapper");
                    opcUaConfiguration.setUsername("opcda");
                    opcUaConfiguration.setPassword("Password01!");
                    opcUaConfiguration.addNode(new OpcUaNodesConfiguration("ns=2;s=0:Random.Real8"));

                    opcua.add(opcUaConfiguration);

                    this.twinClient.patch(twin);

                }
                TwinCollection desiredProperties = twin.getDesiredProperties();
                TwinCollection reportedProperties = twin.getReportedProperties();
                TwinCollection tags = twin.getTags();
                System.out.println("hello twin");
            }
            

            Map<String, Map<String, Object>> oo = getOpcUaModuleConfiguration("my-opc-ua-publisher");
            ConfigurationContent content = new ConfigurationContent();
            content.setModulesContent(oo);
    
            Configuration configuration = new Configuration(deviceId);
            configuration.setContent(content);

            try
            {
                //configuration = configurationsClient.create(configuration);
                //System.out.println("Add configuration " + configuration.getId() + " succeeded.");
                configurationsClient.applyConfigurationContentOnDevice("gm-dev-iotedge-device", content);
                System.out.println("IoT edge deployment ok.");
            }
            catch (IotHubException | IOException iote)
            {
                iote.printStackTrace();
            }

            List<Configuration> configurations = this.configurationsClient.get(20);
            /*
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(this.iotHubSasToken2.toString());
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            RestTemplate rt =  new RestTemplate();
            
            ResponseEntity<List<Configuration>> c = rt.exchange("https://gm-dev-iothub.azure-devices.net/configurations?api-version=2020-05-31-preview", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Configuration>>(){});
            int i = 0;
            i++;
            */
            ConfigurationsTest();
        } catch (IotHubException | IOException e) {
            e.printStackTrace();
        }

        return new IotDevice(deviceId, "name", "key");
    }
    private TwinCollection initModuleTwin(Twin twin) {
        TwinCollection desiredProperties = twin.getDesiredProperties();
        desiredProperties.put("product", "ConnexEdge");
        desiredProperties.put("application", "ProtocolGateway");
        
        HashMap<String, Object> dataSources = new HashMap<>() {
            {
                put("opcUa", new ArrayList<HashMap<String,Object>>());
                put("modbus", new ArrayList<HashMap<String,Object>>());
                put("ethernetIp", new ArrayList<HashMap<String,Object>>());
            }
        };

        desiredProperties.put("dataSources", dataSources);

        try {
            this.twinClient.replace(twin);
        } catch (IotHubException|IOException e) {
            e.printStackTrace();
        }

        return desiredProperties;
    }
    private Map<String, Map<String, Object>> getOpcUaModuleConfiguration(String moduleName) {
        String hostName = moduleName;
        String image = "gmdevcr.azurecr.io/iot/opcua-publisher:2.5.6";
        
        HashMap<String, Object> createOptions = new HashMap<>() {
            {
                put("HostName", hostName);
                put("Cmd", new ArrayList<>(){
                    {
                        add("--pf=./publishednodes.json");
                        add("--aa");
                    }
                });
                put("HostConfig", new HashMap<String, Object>(){
                    {
                        put("Binds", new ArrayList<>(){
                            {
                                add("/opt/opcua-publisher:/appdata");
                            }
                        });
                    }
                });
            }
        };

        HashMap<String, Object> moduleConfiguration = new HashMap<>() {
            {
                put("version", 1.0);
                put("type", "docker");
                put("status", "running");
                put("restartPolicy", "always");
                put("settings", new HashMap<String, Object>(){
                    { 
                        put("image", image); 
                        put("createOptions", createOptions);
                    }
                });
            }
        };
        
        JSONObject deploymentManifest = JSON.parseObject(this.edgeDeploymentManifest);
        //deploymentManifest.getJSONObject("modulesContent")
        //    .getJSONObject("$edgeAgent")
        //    .getJSONObject("properties.desired")
        //    .getJSONObject("modules")
        //    .put(moduleName, moduleConfiguration);;
        
        Map<String, Map<String, Object>> mco = new HashMap<>();
        return deploymentManifest.getObject("modulesContent", mco.getClass());
    }

    private void ConfigurationsTest() {
        HashMap<String, Object> DEVICE_CONTENT_SAMPLE = new HashMap<String, Object>()
        {
            {
                put("properties.desired.chiller-water", new HashMap<String, Object>()
                        {
                            {
                                put("temperature", 66);
                                put("pressure", 28);
                            }
                        }
                );
            }
        };

        ConfigurationContent content = new ConfigurationContent();
        content.setDeviceContent(DEVICE_CONTENT_SAMPLE);

        Configuration config = new Configuration("sample-conf-id");
        config.setContent(content);

        try
        {
            config = configurationsClient.create(config);
            System.out.println("Add configuration " + config.getId() + " succeeded.");
        }
        catch (IotHubException | IOException iote)
        {
            iote.printStackTrace();
        }
    }
}
