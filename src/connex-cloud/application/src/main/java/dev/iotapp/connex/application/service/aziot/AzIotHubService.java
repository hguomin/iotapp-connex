package dev.iotapp.connex.application.service.aziot;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.microsoft.azure.sdk.iot.service.registry.RegistryClient;
import com.microsoft.azure.sdk.iot.service.twin.Twin;
import com.microsoft.azure.sdk.iot.service.twin.TwinClient;
import com.microsoft.azure.sdk.iot.service.twin.TwinCollection;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.microsoft.azure.sdk.iot.service.auth.IotHubServiceSasToken;
import com.microsoft.azure.sdk.iot.service.configurations.Configuration;
import com.microsoft.azure.sdk.iot.service.configurations.ConfigurationContent;
import com.microsoft.azure.sdk.iot.service.configurations.ConfigurationsClient;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubNotFoundException;
import com.microsoft.azure.sdk.iot.service.methods.DirectMethodRequestOptions;
import com.microsoft.azure.sdk.iot.service.methods.DirectMethodResponse;
import com.microsoft.azure.sdk.iot.service.methods.DirectMethodsClient;
import com.microsoft.azure.sdk.iot.service.registry.Module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dev.iotapp.connex.application.model.EdgeDataDriver;
import dev.iotapp.connex.application.model.EdgeDataSource;
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
    private final HashMap<String, String> dataSrcToAppMap = new HashMap<>() {
        {
            put("opc-ua", "opc-connector");
            put("modbus", "modbus-connector");
            put("ethernet-ip", "eip-connector");
        }
    };

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
    public IotDevice iotHubServiceTestCode(String deviceId) {
        try {

            List<Module> edgeModules = this.registryClient.getModulesOnDevice("gm-dev-iotedge-device");

            for (Module m : edgeModules) {
                Twin twin = this.twinClient.get("gm-dev-iotedge-device", m.getId());
                if(m.getId().contentEquals("devplus-dev")) {
                    this.initOpcModuleTwin(twin);
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
        desiredProperties.clear();
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

    private TwinCollection initOpcModuleTwin(Twin twin) {
        TwinCollection desiredProperties = twin.getDesiredProperties();
        desiredProperties.clear();
        desiredProperties.put("product", "ConnexEdge");
        desiredProperties.put("application", "opc-connector");
        desiredProperties.put("dataSources", new ArrayList<Object>());
        try {
            this.twinClient.replace(twin);
        } catch (IotHubException|IOException e) {
            e.printStackTrace();
        }

        return desiredProperties;
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

    @Override
    public boolean addEdgeDataSource(String deviceId, EdgeDataSource ds) {
        Twin twin = getEdgeModuleTwin(deviceId, ds.getType());
        if(null != twin) {
            ArrayList<Object> dss = (ArrayList<Object>)twin.getDesiredProperties().get("dataSources");
            dss.add(ds);
            try {
                this.twinClient.patch(twin);
                return true;
            }
            catch (IOException | IotHubException e)
            {
                e.printStackTrace();
            }

        }

        return false;
    }

    private Twin getEdgeModuleTwin(String deviceId, String moduleType) {
        try{
            List<Module> edgeModules = this.registryClient.getModulesOnDevice(deviceId);

            for (Module module : edgeModules) {
                Twin twin = this.twinClient.get(deviceId, module.getId());
                TwinCollection desiredProperties = twin.getDesiredProperties();
                if(!desiredProperties.containsKey("product") || !desiredProperties.containsKey("application"))
                    continue;

                String product = (String)desiredProperties.get("product");
                if(!product.contentEquals("ConnexEdge"))
                    continue;

                String app = (String)desiredProperties.get("application");
                if(!app.contentEquals(this.dataSrcToAppMap.get(moduleType)))
                    continue;
                
                return twin;
            }
        } 
        catch (IOException | IotHubException e ) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<EdgeDataSource> getEdgeDataSources(String deviceId) {
        List<EdgeDataSource> dataSources = new ArrayList<>();
        try{
            List<Module> edgeModules = this.registryClient.getModulesOnDevice(deviceId);

            for (Module module : edgeModules) {
                Twin twin = this.twinClient.get(deviceId, module.getId());
                TwinCollection desiredProperties = twin.getDesiredProperties();
                if(!desiredProperties.containsKey("product"))
                    continue;

                String product = (String)desiredProperties.get("product");
                if(!product.contentEquals("ConnexEdge"))
                    continue;

                ArrayList<EdgeDataSource> dss =  (ArrayList<EdgeDataSource>)desiredProperties.get("dataSources");
                if(null == dss || dss.isEmpty())
                    continue;
                dataSources.addAll(dss);
            }
        } 
        catch (IOException | IotHubException e ) {
            e.printStackTrace();
        }
        
        return dataSources;
    }

    @Override
    public void setEdgeDataSourceConfiguration(String deviceId, String dataSrcType, String dataSrcName, Map<String, Object> settings) {
        Twin twin = this.getEdgeModuleTwin(deviceId, dataSrcType);
        if(null != twin) {
            TwinCollection desiredProperties = twin.getDesiredProperties();
            @SuppressWarnings("unchecked")
            ArrayList<Map<String, Object>> dss =  (ArrayList<Map<String, Object>>)desiredProperties.get("dataSources");
            for(Map<String, Object> ds: dss) {
                String name = (String)ds.get("name");
                if(name.contentEquals(dataSrcName)) {

                    @SuppressWarnings("unchecked")
                    Map<String, Object> conf = (Map<String, Object>)ds.get("configuration");
                    settings.forEach((k, v) -> {
                        conf.put(k, v);
                    });
                    break;
                }
            }

            try {
                this.twinClient.patch(twin);
            }
            catch (IOException | IotHubException e)
            {
                e.printStackTrace();
            }
        }   
    }

    @Override
    public EdgeDataSource getDataSource(String deviceId, String dataSrcType, String dataSrcName) {
        Twin twin = this.getEdgeModuleTwin(deviceId, dataSrcType);
        if(null != twin) {
            TwinCollection desiredProperties = twin.getDesiredProperties();
            ArrayList<Map<String, Object>> dss =  (ArrayList<Map<String, Object>>)desiredProperties.get("dataSources");
            for(Map<String, Object> ds: dss) {
                String name = (String)ds.get("name");
                if(name.contentEquals(dataSrcName)) {
                    EdgeDataSource dataSrc = new EdgeDataSource((String)ds.get("name"),
                                                                (String)ds.get("type"), 
                                                                (String)ds.get("tags"), 
                                                                (String)ds.get("description"), 
                                                                (Map<String, Object>)ds.get("configuration"));
                    //we will get modbus configuration from the desired properties "SlaveConfigs"
                    if (dataSrcType.contentEquals("modbus")) {
                        Map<String, Object> slaveConfigs = (Map<String, Object>)desiredProperties.get("SlaveConfigs");
                        Map<String, Object> configuration = (Map<String, Object>)slaveConfigs.get(dataSrcName);
                        configuration.put("PublishInterval", desiredProperties.get("PublishInterval"));
                        configuration.put("Version", desiredProperties.get("Version"));
                        dataSrc.setConfiguration(configuration);
                    }

                    return dataSrc;
                }
            }
        }
        return null;
    }


    @Override
    public void updateEdgeDataSource(String deviceId, EdgeDataSource ds) {        
        Twin twin = this.getEdgeModuleTwin(deviceId, ds.getType());
        if(null != twin) {
            TwinCollection desiredProperties = twin.getDesiredProperties();
            if (ds.getType().contentEquals("opc-ua")) {
                ArrayList<Map<String, Object>> dss = (ArrayList<Map<String, Object>>) desiredProperties.get("dataSources");
                for (Map<String, Object> d : dss) {
                    String name = (String) d.get("name");
                    if (name.contentEquals(ds.getName())) {
                        ds.copyTo(d);
                        try {
                            //save the configuration
                            this.twinClient.patch(twin);

                            //request edge module to update opc ua connection
                            invokeDeviceMethod(twin.getDeviceId(), twin.getModuleId(), "UnpublishAllNodes", new HashMap() {{
                                put("EndpointUrl", ds.getConfiguration().get("EndpointUrl"));
                            }});
                            invokeDeviceMethod(twin.getDeviceId(), twin.getModuleId(), "PublishNodes", ds.getConfiguration());

                            break;
                        } catch (IOException | IotHubException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else if (ds.getType().contentEquals("modbus")) {
                Map<String, Object> slaveConfigs = (Map<String, Object>)desiredProperties.get("SlaveConfigs");
                Map<String, Object> configuration = (Map<String, Object>)slaveConfigs.get(ds.getName());
                desiredProperties.put("PublishInterval", ds.getConfiguration().get("PublishInterval"));
                desiredProperties.put("Version", ds.getConfiguration().get("Version"));
                configuration.forEach((k, v) -> {
                    configuration.put(k, ds.getConfiguration().get(k));
                });
                try {
                    this.twinClient.patch(twin);
                }
                catch (IOException | IotHubException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private DirectMethodResponse invokeDeviceMethod(String deviceId, String moduleId, String methodName, Map<String, Object> payload) throws IotHubException, IOException {
        DirectMethodResponse result = null;
        int responseTimeout = 200;
        int connectTimeout = 5;
        DirectMethodRequestOptions options = DirectMethodRequestOptions.builder()
            .payload(payload)
            .methodConnectTimeoutSeconds(connectTimeout)
            .methodResponseTimeoutSeconds(responseTimeout).build();
        result = this.directMethodsClient.invoke(deviceId, moduleId, methodName, options);
        return result;
    }

    @Override
    public EdgeDataDriver deployEdgeModule(EdgeDataDriver driver) {
        /*
        Map<String, Map<String, Object>> oo = getOpcUaModuleConfiguration(driver.getDriverId());
        ConfigurationContent content = new ConfigurationContent();
        content.setModulesContent(oo);

        Configuration configuration = new Configuration(driver.getDeviceId());
        configuration.setContent(content);

        try
        {
            configurationsClient.applyConfigurationContentOnDevice(driver.getDeviceId(), content);
            return driver;
        }
        catch (IotHubException | IOException iote)
        {
            iote.printStackTrace();
        }

        return null;
        */

        //Module configuration
        String image = "gmdevcr.azurecr.io/iot/opcua-publisher:2.5.6";      
        String createOptions = "{\"HostName\": \"" + driver.getDriverId() + "\", \"Cmd\":[\"--aa\"], \"HostConfig\": {\"Binds\":[\"/opt/opcua-publisher:/appdata\"]}}";
        Map<String, Object> moduleConfiguration = new HashMap<>() {
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
        //Module desired properties
        String application = this.dataSrcToAppMap.get(driver.getDriverType());
        Map<String, Object> moduleDesiredProperties = new HashMap<>() {
            {
                put("properties.desired", new HashMap<String, Object>(){
                    {
                        put("product", "ConnexEdge");
                        put("application", application);
                        put("dataSources", new ArrayList<>());
                        
                    }
                });
            }
        };

        //Get deployment manifest template
        JSONObject deploymentManifest = JSON.parseObject(this.edgeDeploymentManifest);

        //Add module desired properties
        JSONObject modulesContentJson = deploymentManifest.getJSONObject("modulesContent");
        modulesContentJson.put(driver.getDriverId(), moduleDesiredProperties);

        //Put module to edge agent's module list
        JSONObject modulesJson = modulesContentJson.getJSONObject("$edgeAgent")
            .getJSONObject("properties.desired")
            .getJSONObject("modules");
        modulesJson.put(driver.getDriverId(), moduleConfiguration);;

        //Add routes
        modulesContentJson.getJSONObject("$edgeHub")
            .getJSONObject("properties.desired")
            .getJSONObject("routes")
            .put(driver.getDriverId(), "FROM /messages/* INTO $upstream");

        //Get and keep existing modules with its desired properties
        try
        {
            Twin edgeAgentTwin = this.twinClient.get(driver.getDeviceId(), "$edgeAgent");
            TwinCollection modules = (TwinCollection)edgeAgentTwin.getDesiredProperties().get("modules");
            if(null != modules) {
                modules.forEach((moduleName, moduleSettings) -> {
                    modulesJson.put(moduleName, moduleSettings);

                    try {
                        Twin moduleTwin = this.twinClient.get(driver.getDeviceId(), moduleName);
                        Map<String, Object> desiredProperties = new HashMap<>() {
                            {
                                put("properties.desired", moduleTwin.getDesiredProperties());
                            }
                        };
                        modulesContentJson.put(moduleName, desiredProperties);

                    } catch ( IOException | IotHubException e) {
                        e.printStackTrace();
                    }
                });
            }

            Map<String, Map<String, Object>> mco = new HashMap<>();
            ConfigurationContent content = new ConfigurationContent();
            content.setModulesContent(deploymentManifest.getObject("modulesContent", mco.getClass()));

            configurationsClient.applyConfigurationContentOnDevice(driver.getDeviceId(), content);
            return driver;
        }
        catch (IotHubException | IOException iote)
        {
            iote.printStackTrace();
        }

        return null;
    }
    
    private Map<String, Map<String, Object>> getOpcUaModuleConfiguration(String moduleName) {
        String hostName = moduleName;
        String image = "gmdevcr.azurecr.io/iot/opcua-publisher:2.5.6";      
        String createOptions = "{\"HostName\": \"" + hostName + "\", \"Cmd\":[\"--aa\"], \"HostConfig\": {\"Binds\":[\"/opt/opcua-publisher:/appdata\"]}}";
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
        deploymentManifest.getJSONObject("modulesContent")
            .getJSONObject("$edgeAgent")
            .getJSONObject("properties.desired")
            .getJSONObject("modules")
            .put(moduleName, moduleConfiguration);;
        
        Map<String, Map<String, Object>> mco = new HashMap<>();
        return deploymentManifest.getObject("modulesContent", mco.getClass());
    }

    private EdgeDataDriver deployEdgeModuleByModifySystemModuleTwin(EdgeDataDriver driver) {
        String image = "gmdevcr.azurecr.io/iot/opcua-publisher:2.5.6";        
        HashMap<String, Object> createOptions = new HashMap<>() {
            {
                put("HostName", driver.getDriverId());
                put("Cmd", new ArrayList<>(){
                    {
                        //add("--pf=./publishednodes.json");
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

        String registryCredentialName = "gmdevcr";
        Map<String, Object> registryCredential = new HashMap<>() {
            {
                put("address", "gmdevcr.azurecr.io");
                put("username", "gmdevcr");
                put("password", "KEoHXaDOq/37XyJzXg8nqwmFiPONhC34");
            }
        };

        try {

            //Get the module
            Module driverModule = null;
            try {
                driverModule = this.registryClient.getModule(driver.getDeviceId(), driver.getDriverId());
            }
            catch (IotHubNotFoundException e) {
                driverModule = this.registryClient.addModule(new Module(driver.getDeviceId(), driver.getDriverId()));
            }

            Module edgeAgent = this.registryClient.getModule(driver.getDeviceId(), "$edgeAgent");
            
            Twin edgeAgentTwin = this.twinClient.get(driver.getDeviceId(), "$edgeAgent");
            TwinCollection edgeAgentDesiredProperties = edgeAgentTwin.getDesiredProperties(); 
            
            //Add module
            //NOTE: if the edge device is just provisioned, there will be no any desired properties, 
            //we may need to add it manually or do one "Set modules" operation in portal with no any module to deploy to let iot edge generate these defalut desired properties. 
            Map<String, Object> edgeAgentModules = (Map<String, Object>)edgeAgentDesiredProperties.get("modules");
            edgeAgentModules.put(driver.getDriverId(), moduleConfiguration);

            //Update container registry credential
            TwinCollection runtimeSettings = ((TwinCollection)((TwinCollection)edgeAgentDesiredProperties.get("runtime")).get("settings"));
            TwinCollection registryCredentials = (TwinCollection)runtimeSettings.get("registryCredentials");

            if(null == registryCredentials) {
                runtimeSettings.put("registryCredentials", new HashMap<String, Object>());
                registryCredentials = (TwinCollection)runtimeSettings.get("registryCredentials");
            }

            registryCredentials.put(registryCredentialName, registryCredential);


            //Update routes
            Module edgeHub = this.registryClient.getModule(driver.getDeviceId(), "$edgeHub");
            

            Twin edgeHubTwin = this.twinClient.get(driver.getDeviceId(), "$edgeHub");
            Map<String, Object> routes = (Map<String, Object>)edgeHubTwin.getDesiredProperties().get("routes");
            routes.put(driver.getDriverId(), "FROM /messages/* INTO $upstream");

            //Update module twin
            Twin driverModuleTwin = this.twinClient.get(driver.getDeviceId(), driverModule.getId());
            driverModuleTwin.getDesiredProperties().put("product", "ConnexEdge");
            driverModuleTwin.getDesiredProperties().put("application", "opc-connector");

            //Apply deployment
            this.twinClient.patch(edgeAgentTwin);
            this.twinClient.patch(edgeHubTwin);
            this.twinClient.patch(driverModuleTwin);

            return driver;
            
        } catch (IOException | IotHubException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
