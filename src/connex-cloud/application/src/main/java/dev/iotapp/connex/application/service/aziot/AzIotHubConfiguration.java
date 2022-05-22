package dev.iotapp.connex.application.service.aziot;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.microsoft.azure.sdk.iot.service.auth.IotHubConnectionString;
import com.microsoft.azure.sdk.iot.service.auth.IotHubConnectionStringBuilder;
import com.microsoft.azure.sdk.iot.service.auth.IotHubServiceSasToken;
import com.microsoft.azure.sdk.iot.service.configurations.ConfigurationsClient;
import com.microsoft.azure.sdk.iot.service.methods.DirectMethodsClient;
import com.microsoft.azure.sdk.iot.service.registry.RegistryClient;
import com.microsoft.azure.sdk.iot.service.twin.TwinClient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.FileCopyUtils;

@Configuration
@ConfigurationProperties(prefix = "azure.iothub")
public class AzIotHubConfiguration {
    private String connectionString;
    private String hostName;
    private String sasKey;

    public String getConnectionString() {
        return this.connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSasKey() {
        return this.sasKey;
    }

    public void setSasKey(String sasKey) {
        this.sasKey = sasKey;
    }

    @Bean
    public IotHubConnectionString iotHubConnectionString() {
        return IotHubConnectionStringBuilder.createIotHubConnectionString(this.connectionString);
    }

    @Bean
    public RegistryClient iotHubRegistryClient() {
        return new RegistryClient(this.connectionString);
    }

    @Bean
    public ConfigurationsClient iotHubConfigurationsClient() {
        return new ConfigurationsClient(this.connectionString);
    }

    @Bean
    public TwinClient iotHubTwinClient() {
        return new TwinClient(this.connectionString);
    }

    @Bean DirectMethodsClient iotHubDirectMethodClient() {
        return new DirectMethodsClient(this.connectionString);
    }
    
    @Bean
    public AzSasToken iotHubSasToken() throws Exception {
        String resourceUri = this.hostName;
        // Token will expire in one hour
        long expiry = Instant.now().getEpochSecond() + 3600;

        String stringToSign = URLEncoder.encode(resourceUri, StandardCharsets.UTF_8.toString()) + "\n" + expiry;
        byte[] decodedKey = Base64.getDecoder().decode(this.sasKey);

        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");
        sha256HMAC.init(secretKey);
        Base64.Encoder encoder = Base64.getEncoder();

        String signature = new String(encoder.encode(
                sha256HMAC.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);

        return new AzSasToken("SharedAccessSignature sr=" + URLEncoder.encode(resourceUri, StandardCharsets.UTF_8.toString())
                              + "&sig=" + URLEncoder.encode(signature, StandardCharsets.UTF_8.name()) + "&se=" + expiry);
    }

    @Bean
    public IotHubServiceSasToken iotHubServiceSasToken() {
        return new IotHubServiceSasToken(iotHubConnectionString());
    }

    @Bean
    public String iotHubDefaultEdgeDeploymentManifest() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("aziot/default-edge-deployment-manifest.json");

        return FileCopyUtils.copyToString(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

}
