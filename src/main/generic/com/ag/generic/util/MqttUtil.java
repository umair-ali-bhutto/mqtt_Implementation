package com.ag.generic.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ag.generic.prop.AppProp;

public class MqttUtil {

	public void AnnouncePayment(String rrn, Float amount, String qrId, String topic) {
		try {
			AgLogger.logInfo("Mqtt Announce Payment Request:" + rrn + "|" + amount + "|" + qrId + "|" + topic);
//172.192.1.154
			// mqtt.ip = 202.143.120.252
			// mqtt.port = 1883
			// mqtt.username = ag-test
			// mqtt.password = ag-test

			String ip = AppProp.getProperty("mqtt.ip");
			String port = AppProp.getProperty("mqtt.port");
			String username = AppProp.getProperty("mqtt.username");
			String password = AppProp.getProperty("mqtt.password");

			// WINDOWS
//			String req = "cd \"C:\\Program Files\\mosquitto\" && mosquitto_pub -h " + ip + " -p " + port + " -u "
//					+ username + " -P " + password + " -t /" + topic + " -m \"{\\\"request_id\\\":\\\"" + rrn
//					+ "\\\",\\\"biz_type\\\":\\\"1\\\",\\\"broadcast_type\\\":\\\"1\\\",\\\"money\\\":\\\"" + amount
//					+ "\\\"}\"";
//
//			AgLogger.logInfo("@@ REQ:" + req);
//
//			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", req);

			// UBUNTU
			String req = "mosquitto_pub -h " + ip + " -p " + port + " -u " + username + " -P " + password + " -t /"
					+ topic + " -m \"{\\\"request_id\\\":\\\"" + rrn
					+ "\\\",\\\"biz_type\\\":\\\"1\\\",\\\"broadcast_type\\\":\\\"1\\\",\\\"money\\\":\\\"" + amount
					+ "\\\"}\"";
			AgLogger.logInfo("@@ REQ:" + req);

			ProcessBuilder builder = new ProcessBuilder("bash", "-c", req);

			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			AgLogger.logInfo("LINE START");
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				AgLogger.logInfo(line);
			}
			AgLogger.logInfo("LINE END");

		} catch (Exception e) {
			AgLogger.logTrace(getClass(), "EXCEPTION MQTT:" + e.getMessage(), e);
		}

	}

	public void changeQR(String rrn, String mid, String merchantName, String topic, String qrText) {
		try {
			AgLogger.logInfo(
					"Mqtt Change Qr Request:" + rrn + "|" + mid + "|" + merchantName + "|" + topic + "|" + qrText);

			// mqtt.ip = 202.143.120.252
			// mqtt.port = 1883
			// mqtt.username = ag-test
			// mqtt.password = ag-test

			// mosquitto_pub -h 202.143.120.252 -p 1883 -u ag-test -P ag-test -t /POS-QR-NEW
			// -m
			// "{\"request_id\":\"000016\",\"biz_type\":\"1\",\"broadcast_type\":\"1\",\"MerchantId\":\"900000000000006\",\"MerchantName\":\"XYZ
			// Store\",\"homeqrcode\":\"0002010102125204411153035865405200.05802PK5913X990
			// EMV Demo604401!AWARDS!08-01-2024
			// 11:25!112423!ANW!0003566249504501150007623900199920208001229310310V1E024707598380016Digital
			// Pass PAN01149876543211234563045CFE\"}"

			String ip = AppProp.getProperty("mqtt.ip");
			String port = AppProp.getProperty("mqtt.port");
			String username = AppProp.getProperty("mqtt.username");
			String password = AppProp.getProperty("mqtt.password");

			// WINDOWS
//			String req = "cd \"C:\\Program Files\\mosquitto\" && mosquitto_pub -h " + ip + " -p " + port + " -u "
//					+ username + " -P " + password + " -t /" + topic + " -m \"{\\\"request_id\\\":\\\"" + rrn
//					+ "\\\",\\\"biz_type\\\":\\\"1\\\",\\\"broadcast_type\\\":\\\"1\\\",\\\"MerchantId\\\":\\\"" + mid
//					+ "\\\",\\\"MerchantName\\\":\\\"" + merchantName + "\\\",\\\"homeqrcode\\\":\\\"" + qrText
//					+ "\\\"}\"";
//
//			AgLogger.logInfo("@@ REQ:" + req);
//
//			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", req);

			// UBUNTU
			String req = "mosquitto_pub -h " + ip + " -p " + port + " -u " + username + " -P " + password + " -t /"
					+ topic + " -m \"{\\\"request_id\\\":\\\"" + rrn
					+ "\\\",\\\"biz_type\\\":\\\"1\\\",\\\"broadcast_type\\\":\\\"1\\\",\\\"MerchantId\\\":\\\"" + mid
					+ "\\\",\\\"MerchantName\\\":\\\"" + merchantName + "\\\",\\\"homeqrcode\\\":\\\"" + qrText
					+ "\\\"}\"";

			AgLogger.logInfo("@@ REQ:" + req);

			ProcessBuilder builder = new ProcessBuilder("bash", "-c", req);

			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			AgLogger.logInfo("LINE START");
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				AgLogger.logInfo(line);
			}
			AgLogger.logInfo("LINE END");

		} catch (Exception e) {
			AgLogger.logTrace(getClass(), "EXCEPTION MQTT:" + e.getMessage(), e);
		}

	}

	public void AnnounceVerifonePayment(String rrn, Float amount, String topic) {
		try {
			AgLogger.logInfo("Mqtt Announce Verifone Payment Request:" + rrn + "|" + amount + "|" + topic);

			String ip = AppProp.getProperty("mqtt.ip");
			String port = AppProp.getProperty("mqtt.port");
			String username = AppProp.getProperty("mqtt.username");
			String password = AppProp.getProperty("mqtt.password");

			// WINDOWS
//			String req = "cd \"C:\\Program Files\\mosquitto\" && mosquitto_pub -h " + ip + " -p " + port + " -u "
//					+ username + " -P " + password + " -t /" + topic + " -m \"{" + "  \\\"is_receive\\\": \\\"1\\\","
//					+ "  \\\"money\\\": \\\"" + amount + "\\\"," + "  \\\"content_type\\\": \\\"\\\","
//					+ "  \\\"biz_type\\\": \\\"1\\\"," + "  \\\"request_id\\\": \\\"" + rrn + "\\\","
//					+ "  \\\"content\\\": \\\"\\\"," + "  \\\"broadcast_type\\\": \\\"3\\\"" + "}\"";
//
//			AgLogger.logInfo("@@ REQ:" + req);
//
//			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", req);

			// UBUNTU
			String req = "mosquitto_pub -h " + ip + " -p " + port + " -u " + username + " -P " + password + " -t /"
					+ topic + " -m \"{" + "  \\\"is_receive\\\": \\\"1\\\"," + "  \\\"money\\\": \\\"" + amount
					+ "\\\"," + "  \\\"content_type\\\": \\\"\\\"," + "  \\\"biz_type\\\": \\\"1\\\","
					+ "  \\\"request_id\\\": \\\"" + rrn + "\\\"," + "  \\\"content\\\": \\\"\\\","
					+ "  \\\"broadcast_type\\\": \\\"3\\\"" + "}\"";
			AgLogger.logInfo("@@ REQ:" + req);

			ProcessBuilder builder = new ProcessBuilder("bash", "-c", req);

			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			AgLogger.logInfo("LINE START");
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				AgLogger.logInfo(line);
			}
			AgLogger.logInfo("LINE END");

		} catch (Exception e) {
			AgLogger.logTrace(getClass(), "EXCEPTION MQTT:" + e.getMessage(), e);
		}

	}

}
