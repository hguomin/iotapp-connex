//By Guomin Huang @16/10/2021

package io.devplus.springcloudstreambinderwebsocket.provisioning;

import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

public class WebSocketChannelProvisioner implements ProvisioningProvider<ConsumerProperties, ProducerProperties> {
    @Override
    public ProducerDestination provisionProducerDestination(String name, ProducerProperties properties) throws ProvisioningException {
        return new WebSocketProducerDestination(name);
    }

    @Override
    public ConsumerDestination provisionConsumerDestination(String name, String group, ConsumerProperties properties) throws ProvisioningException {
        return new WebSocketConsumerDestination(name);
    }
}
