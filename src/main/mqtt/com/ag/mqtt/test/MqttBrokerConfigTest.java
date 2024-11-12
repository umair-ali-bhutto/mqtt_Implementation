package com.ag.mqtt.test;
import io.moquette.broker.Server;
import io.moquette.broker.config.MemoryConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

@Component
public class MqttBrokerConfigTest {
    private Server mqttBroker;

//    @PostConstruct
    public void startBroker() throws IOException {
        mqttBroker = new Server();
        Properties brokerProperties = new Properties();
        brokerProperties.setProperty("port", "1884"); // MQTT standard port without SSL
        brokerProperties.setProperty("allow_anonymous", "false"); // Require authentication
        brokerProperties.setProperty("password_file", "src/main/resources/password.conf"); // Path to password file for user auth

        mqttBroker.startServer(new MemoryConfig(brokerProperties));
        System.out.println("MQTT Broker started on port 1884 (no SSL)");
    }

    public void stopBroker() {
        mqttBroker.stopServer();
    }
}
