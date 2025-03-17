package com.ag.mqtt.main;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ag.config.AgLogger;

import io.moquette.broker.Server;
import io.moquette.broker.config.MemoryConfig;

/**
 * @author umair.ali
 * @since November 12 2024 (12-11-2024)
 * @version 1.0
 * 
 * @description Configures and starts the MQTT broker for the application.
 *              <p>
 *              This class is annotated with {@link Component} to be
 *              automatically discovered by Spring's component scanning and
 *              inject dependencies where needed. It uses properties defined in
 *              the {@code application.properties} or other configuration files
 *              to set up the broker's host, port, SSL, and other settings.
 *              </p>
 * 
 *              <h3>Configuration Properties:</h3>
 *              <ul>
 *              <li>{@code mqtt.host} - The host address of the MQTT
 *              broker.</li>
 *              <li>{@code mqtt.port} - The port for the MQTT broker.</li>
 *              <li>{@code allow.anonymous} - Whether anonymous connections are
 *              allowed.</li>
 *              <li>{@code mqtt.password.file} - Path to the password file for
 *              clients.</li>
 *              <li>{@code mqtt.ssl.port} - The port for secure SSL
 *              connections.</li>
 *              <li>{@code mqtt.jks.path} - Path to the JKS (Java KeyStore) file
 *              for SSL configuration.</li>
 *              <li>{@code mqtt.key.store.password} - Password for the JKS
 *              file.</li>
 *              <li>{@code mqtt.key.manager.password} - Password for the key
 *              manager.</li>
 *              </ul>
 * 
 *              <h3>Methods:</h3>
 *              <ul>
 *              <li>{@link startBroker()} - Initializes and starts the MQTT
 *              broker using the configured properties.</li>
 *              <li>{@link stopBroker()} - Stops the MQTT broker when the
 *              application is shutting down.</li>
 *              </ul>
 */
@Component
public class MqttBrokerConfig {

	private Server mqttBroker;

	// The host address of the MQTT broker
	@Value("${mqtt.host}")
	public String mqttHost;

	// The port on which the MQTT broker will listen for connections
	@Value("${mqtt.port}")
	public String mqttPort;

	// Flag to allow anonymous clients to connect to the broker
	@Value("${allow.anonymous}")
	public String allowAnonymous;

	// Path to the password file for MQTT clients
	@Value("${mqtt.password.file}")
	public String mqttPasswordFile;

	// The SSL port for the MQTT broker
	@Value("${mqtt.ssl.port}")
	public String mqttSslPort;

	// Path to the Java KeyStore (JKS) file for SSL configuration
	@Value("${mqtt.jks.path}")
	public String mqttJksPath;

	// Password for the Java KeyStore (JKS) file
	@Value("${mqtt.key.store.password}")
	public String mqttKeyStorePassword;

	// Password for the key manager associated with the JKS
	@Value("${mqtt.key.manager.password}")
	public String mqttKeyManagerPassword;

	/**
	 * Initializes and starts the MQTT broker after the Spring container is
	 * initialized. This method reads the configuration properties and starts the
	 * MQTT broker with the specified settings.
	 * 
	 * @throws IOException if the broker fails to start
	 */
	@PostConstruct
	public void startBroker() throws IOException {

		AgLogger.logInfo("Starting MQTT Server ........................");
		AgLogger.logInfo("mqtt.host: " + mqttHost);
		AgLogger.logInfo("mqtt.port: " + mqttPort);
		AgLogger.logInfo("allow.anonymous: " + allowAnonymous);
		AgLogger.logInfo("mqtt.password.file: " + mqttPasswordFile);
		AgLogger.logInfo("mqtt.ssl.port: " + mqttSslPort);
		AgLogger.logInfo("mqtt.jks.path: " + mqttJksPath);

		mqttBroker = new Server();
		Properties brokerProperties = new Properties();

		brokerProperties.setProperty("host", mqttHost);
		brokerProperties.setProperty("port", mqttPort);
		brokerProperties.setProperty("allow_anonymous", allowAnonymous);
		brokerProperties.setProperty("password_file", mqttPasswordFile);
		brokerProperties.setProperty("ssl_port", mqttSslPort);
		brokerProperties.setProperty("jks_path", mqttJksPath);
		brokerProperties.setProperty("key_store_password", mqttKeyStorePassword);
		brokerProperties.setProperty("key_manager_password", mqttKeyManagerPassword);

		mqttBroker.startServer(new MemoryConfig(brokerProperties));
		AgLogger.logInfo("MQTT Broker started on port: " + brokerProperties.getProperty("port") + " ssl_port: "
				+ brokerProperties.getProperty("ssl_port"));
		
		////temp
		////To Test Subscriber
//		try {
//			new MqttSubscriber().main(null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * Stops the MQTT broker when the Spring container is destroyed. This method is
	 * called before the application shuts down.
	 */
	@PreDestroy
	public void stopBroker() {
		mqttBroker.stopServer();
	}
}
