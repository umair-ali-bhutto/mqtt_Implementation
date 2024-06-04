package com.ag.generic.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ag.generic.prop.AppProp;
import com.sun.org.apache.xerces.internal.impl.PropertyManager;

public class HSMUtil {
	static final String head = "0000";
	static final String command = "DE";
	static final String pvk = "5C165DFDEA309D25";
	static final String len = "04";
	static final String dec_table = "0123456789012345";
	static final String pin_vd = "4532630000N3";

	public static String send(final String pCommand) throws UnknownHostException, IOException {
		AgLogger.logInfo("HSM IP : " + AppProp.getProperty("hsm_ip"));
		String ip =  AppProp.getProperty("hsm_ip");

		AgLogger.logInfo("HSM PORT : " +  AppProp.getProperty("hsm_port" ));
		int port = Integer.parseInt( AppProp.getProperty("hsm_port"));
		AgLogger.logInfo(" " + pCommand + " Connecting to HSM ");
		AgLogger.logInfo("Connecting to HSM..");

		String resp = null;
		Socket sc = null;
		int i = Integer.parseInt(AppProp.getProperty("hsm_retry"));
		do {
			AgLogger.logInfo(""+i);
			try {

				sc = new Socket(ip, port);
				DataInputStream din = new DataInputStream(sc.getInputStream());
				DataOutputStream dos = new DataOutputStream(sc.getOutputStream());

				AgLogger.logInfo(" " + pCommand + " Connected ");
				sc.setSoTimeout(Integer.parseInt( AppProp.getProperty("hsm_timeout")));
				dos.writeUTF(pCommand);
				dos.flush();

				resp = din.readUTF();
				AgLogger.logInfo("Response from HSM: " + resp);
				sc.close();
				return resp;
			} catch (Exception e) {
				resp = null;
				AgLogger.logInfo(" HSM EXCEPTION RETRYING " + i);
			}
			i--;
		} while (i > 0);
		AgLogger.logInfo("Response from HSM: " + resp);
		if (sc != null) {
			sc.close();
		}
		return resp;

	}

	public static String doSend(String cardPan, String pin) throws UnknownHostException, IOException {
		String pinM = pin;
		String pan = cardPan.substring(3, cardPan.length() - 1);
		String encap = head + command + pvk + pinM + len + pan + dec_table + pin_vd;
		String s = send(encap);
		return s;
	}

}
