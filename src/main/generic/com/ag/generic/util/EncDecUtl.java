package com.ag.generic.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ag.generic.prop.AppProp;

public class EncDecUtl {

	public static void main(String args[]) {
		// String s = doEncrypt("tem");
		// System.out.println(s);

		// String k =
		// doDecrypt(Base64.getDecoder().decode("elmvP6hjHtHzUf1Ora6cPGnNdeqbelY+oJPR5UH1ahtLe35Ovt5/YG1TeoQucn1HJcZ9EUjxXjiDkkE47+6NlQMxAsuwyqZjKrZOtpVcD4Azpj4EsLw3u8cwlaEZZZ1eGC9vgeq5Y2sVJgykdaBoL9eOFOJWSYSnt8sfUDWpVMHU2cBT325R0FmH+jDafjhPohAB/Xbb16QFBUvqlkRlRQ=="));
		// System.out.println(k);
	}

	public static String doEncrypt(String data, String userAgent) {
		String result = null;
		try {

			if (userAgent.contains("okhttp")) {
				AgLogger.logInfo("@@@@@@@@@@@@ AES ENCRYPTION USED @@@@@@@@@@@@");

				byte[] encryptKey = AppProp.getProperty("app.mw.enc.key").getBytes();
				SecretKey theKey = new SecretKeySpec(encryptKey, "AES");
				Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
				byte[] bts = AppProp.getProperty("app.mw.enc.iv").getBytes();
				GCMParameterSpec ivSpec = new GCMParameterSpec(128, bts);
				cipher.init(Cipher.ENCRYPT_MODE, theKey, ivSpec);
				byte[] plaintext = data.getBytes();
				byte[] encrypted = cipher.doFinal(plaintext);
				result = new String(Base64.getEncoder().encode(encrypted));

			} else {
				AgLogger.logInfo("@@@@@@@@@@@@ DESede ENCRYPTION USED @@@@@@@@@@@@");

				byte[] encryptKey = AppProp.getProperty("mw.enc.key").getBytes();
				DESedeKeySpec spec = new DESedeKeySpec(encryptKey);
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
				SecretKey theKey = keyFactory.generateSecret(spec);
				Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
				byte[] bts = AppProp.getProperty("mw.enc.iv").getBytes();
				IvParameterSpec IvParameters = new IvParameterSpec(bts);
				cipher.init(Cipher.ENCRYPT_MODE, theKey, IvParameters);
				byte[] plaintext = data.getBytes();
				byte[] encrypted = cipher.doFinal(plaintext);
				result = new String(Base64.getEncoder().encode(encrypted));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String doDecrypt(byte[] data, String userAgent) {
		String result = null;
		try {

			if (userAgent.contains("okhttp")) {
				AgLogger.logInfo("@@@@@@@@@@@@ AES DECRYPTION USED @@@@@@@@@@@@");

				byte[] encryptKey = AppProp.getProperty("app.mw.enc.key").getBytes();
				SecretKey theKey = new SecretKeySpec(encryptKey, "AES");
				Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
				byte[] bts = AppProp.getProperty("app.mw.enc.iv").getBytes();
				GCMParameterSpec ivSpec = new GCMParameterSpec(128, bts);
				cipher.init(Cipher.DECRYPT_MODE, theKey, ivSpec);
				byte[] encrypted = cipher.doFinal(data);
				result = new String(encrypted);

			} else {
				AgLogger.logInfo("@@@@@@@@@@@@ DESede DECRYPTION USED @@@@@@@@@@@@");

				byte[] encryptKey = AppProp.getProperty("mw.enc.key").getBytes();
				DESedeKeySpec spec = new DESedeKeySpec(encryptKey);
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
				SecretKey theKey = keyFactory.generateSecret(spec);
				Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
				byte[] bts = AppProp.getProperty("mw.enc.iv").getBytes();
				IvParameterSpec IvParameters = new IvParameterSpec(bts);
				cipher.init(Cipher.DECRYPT_MODE, theKey, IvParameters);
				byte[] encrypted = cipher.doFinal(data);
				result = new String(encrypted);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
