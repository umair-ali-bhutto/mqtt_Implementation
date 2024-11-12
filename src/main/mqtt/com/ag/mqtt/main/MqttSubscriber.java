package com.ag.mqtt.main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber {
	public static void main(String[] args) throws Exception {
		String brokerUrl = "ssl://172.191.1.223:8886";
		String topic = "test";
		String clientId = "mqtt-client";
		String username = "ag-test";
		String password = "ag-test";

		@SuppressWarnings("resource")
		MqttClient client = new MqttClient(brokerUrl, clientId);
		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(username);
		options.setPassword(password.toCharArray());
		options.setSocketFactory(createSslSocketFactory(
				"/mnt/8EFED7B1FED79037/UBUNTU-BACKUP/VS-GIT/mqtt_Implementation/src/main/resources/certificates/clientkeystore.jks",
				"access123+",
				"/mnt/8EFED7B1FED79037/UBUNTU-BACKUP/VS-GIT/mqtt_Implementation/src/main/resources/certificates/serverkeystore.jks",
				"access123+"));

		client.setCallback(new MqttCallback() {
			@Override
			public void connectionLost(Throwable cause) {
				System.out.println("Connection lost: " + cause.getMessage());
			}

			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				System.out.println(
						"Message arrived. Topic: " + topic + " \nMessage: " + new String(message.getPayload()));
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
			}
		});

		client.connect(options);
		client.subscribe(topic);
		System.out.println("Connected to broker and subscribed to topic: " + topic);

		// Publish a message to the same topic
		String messageContent = "Hello, MQTT!";
		int qos = 2; // Quality of Service level (0, 1, or 2)
		MqttMessage message = new MqttMessage(messageContent.getBytes());
		message.setQos(qos);
		client.publish(topic, message);
		System.out.println("Message published to topic: " + topic);

		// Keep the client connected to receive messages
//		Thread.sleep(5000);

		// Optionally, disconnect the client if no longer needed
//		client.disconnect();
//		System.out.println("Disconnected from broker");

	}

	private static SSLSocketFactory createSslSocketFactory(String clientKeystorePath, String clientKeystorePassword,
			String trustStorePath, String trustStorePassword) throws Exception {
		KeyStore clientKeystore = KeyStore.getInstance("JKS");
		clientKeystore.load(Files.newInputStream(Paths.get(clientKeystorePath)), clientKeystorePassword.toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(clientKeystore, clientKeystorePassword.toCharArray());

		KeyStore trustStore = KeyStore.getInstance("JKS");
		trustStore.load(Files.newInputStream(Paths.get(trustStorePath)), trustStorePassword.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(trustStore);

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return sslContext.getSocketFactory();
	}

}
