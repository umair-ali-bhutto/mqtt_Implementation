package com.ag.mqtt.test;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

public class MqttSubscriberTest {

	public static void main(String[] args) throws MqttException, Exception {
		String brokerUrl = "tcp://localhost:1884"; // Unencrypted MQTT connection
		String topic = "test";
		String clientId = "mqtt-client";
		String username = "ag-test";
		String password = "ag-test";

		MqttClient client = new MqttClient(brokerUrl, clientId);
		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(username);
		options.setPassword(password.toCharArray());

		client.setCallback(new MqttCallback() {
			@Override
			public void connectionLost(Throwable cause) {
				System.out.println("Connection lost: " + cause.getMessage());
			}

			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				System.out
						.println("Message arrived. Topic: " + topic + " Message: " + new String(message.getPayload()));
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
			}
		});

		client.connect(options);
		client.subscribe(topic);
		System.out.println("Connected to broker (without SSL) and subscribed to topic: " + topic);

		String messageContent = "Hello, MQTT!";
		int qos = 2;
		MqttMessage message = new MqttMessage(messageContent.getBytes());
		message.setQos(qos);
		client.publish(topic, message);
		System.out.println("Message published to topic: " + topic);
		Thread.sleep(5000);
		client.disconnect();

	}
}
