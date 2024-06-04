package com.ag.generic.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ag.generic.entity.ReqCall;
import com.ag.generic.entity.ReqCallsParam;
import com.ag.mportal.entity.PosEntryModeConfig;

public class UtilAG {

	public static HashMap<String, Object> requestCallsMap = new HashMap<String, Object>();

	public static HashMap<Long, List<ReqCallsParam>> requestCallParametersMap = new HashMap<Long, List<ReqCallsParam>>();
	
	public static HashMap<String, PosEntryModeConfig> pemConfigMap = new HashMap<String, PosEntryModeConfig>();

	public static HashMap<String, Object> getRequestCallsMap() {
		return requestCallsMap;
	}

	public static void setRequestCallsMap(HashMap<String, Object> requestCallsMap) {
		UtilAG.requestCallsMap = requestCallsMap;
	}

	public static HashMap<Long, List<ReqCallsParam>> getRequestCallParametersMap() {
		return requestCallParametersMap;
	}

	public static void setRequestCallParametersMap(HashMap<Long, List<ReqCallsParam>> requestCallParametersMap) {
		UtilAG.requestCallParametersMap = requestCallParametersMap;
	}

	public String decrypt(byte[] message) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest("HG58YZ3CR9".getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, key, iv);

		final byte[] plainText = decipher.doFinal(message);

		return new String(plainText, "UTF-8");
	}

	public static String getMesssageType(String messsageType) {
		try {
			ReqCall rq = (ReqCall) getRequestCallsMap().get(messsageType);
			//System.out.println("Request CLass : " + rq.getRequestClass());
			return rq.getRequestClass();
		} catch (Exception e) {
			return "com.mportal.ws.classes.WsInvalidRequest";
		}
	
	}

	

	public static ReqCall fetchRequestCalls(String messageType) {
		ReqCall requestCalls = null;
		AgLogger.logDebug(""," fetchRequestCalls MESSAGE TYPE " + messageType);
		try {
			requestCalls = (ReqCall) requestCallsMap.get(messageType);
		} catch (Exception ex) {
			requestCalls = null;
			AgLogger.logerror(null,"@@@EXCEPTIN : fetchRequestCalls@@@ ", ex);
			ex.printStackTrace();
		}

		return requestCalls;
	}

	public static List<ReqCallsParam> fetchRequestParametersByCallId(long callId) {
		List<ReqCallsParam> requestCallsParameters = null;
		try {
			requestCallsParameters = new ArrayList<ReqCallsParam>();
			requestCallsParameters = requestCallParametersMap.get(callId);
		} catch (Exception ex) {
			requestCallsParameters = null;
			ex.printStackTrace();
		}
		return requestCallsParameters;

	}

	public static HashMap<String, PosEntryModeConfig> getPemConfigMap() {
		return pemConfigMap;
	}

	public static void setPemConfigMap(HashMap<String, PosEntryModeConfig> pemConfigMap) {
		UtilAG.pemConfigMap = pemConfigMap;
	}
	
	



}
