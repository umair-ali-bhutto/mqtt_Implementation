package com.ag.test;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.ag.db.proc.DBProcUtil;
import com.ag.fuel.util.ISO8583ErrorMap;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.EncDecUtl;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.metro.services.impl.MetroTxnDetailServiceImpl;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.MerchantTerminalDetailsService;
import com.ag.mportal.services.PosEntryModeConfigService;
import com.ag.mportal.services.ReconLockService;
import com.ag.mportal.services.RocConfigurationService;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.services.impl.TxnDetailsServiceImpl;
import com.ag.mportal.util.String_to_bcd;
import com.ag.pos.controller.ProcessAdvertisment.Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;

import oracle.jdbc.internal.OracleTypes;

@SpringBootTest
class AGMWTest {

	@Autowired
	ReconLockService reconLockService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	MetroTxnDetailServiceImpl tapNPayTransactionService;

	@Autowired
	TxnDetailsServiceImpl txnDetailsServiceImpl;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	RocConfigurationService rocConfigurationService;

	@Autowired
	PosEntryModeConfigService posEntryModeConfigService;

	@Autowired
	MerchantTerminalDetailsService merchantTerminalDetailsService;

	@Test
	void contextLoads() throws Exception {
		
//		System.out.println( new java.text.SimpleDateFormat("yyyyMMdd").format(
//	            new java.text.SimpleDateFormat("dd-MM-yyyy").parse("01-05-2024")));
		

		
		
		//System.out.println(calculatePinBlock("5160620167004845", "4265"));

//MerchantTerminalDetail mtd = new MerchantTerminalDetail();
//mtd.setDataName("LONGITUDE");
//mtd.setType("TRANSACTION-WLANINFO");		
//merchantTerminalDetailsService.save(mtd);

//		String headers = "default:939388,00004:123,00005:1234556";
//		String corpId = "00005"; // corpid to search for
//
//		String header = headers.contains(",")
//				? Arrays.stream(headers.split(",")).filter(part -> part.split(":")[0].equals(corpId)).findFirst()
//						.map(part -> part.split(":")[1]).orElse(headers.split(",")[0].split(":")[1])
//				: headers.split(":")[0].equals("default") ? headers.split(":")[1] : null;
//
//		System.out.println(header);
//		System.out.println("@@@@@@@@@@@@");
//		
//		
//		
//
//		String a2 = EncDecUtl.doEncrypt("HELLO", "okhttp");
//
//		System.out.println(a2);
//
//		String k = EncDecUtl.doDecrypt(Base64.getDecoder().decode(a2),"okhttp");
//		System.out.println(k);
//		
//		
//
//		ReportModel txnDetail = txnDetailsService.fetchTxnDetailByIdForRoc("2305730");
//
//		String reportPath = rocConfigurationService.fetch(txnDetail.getType(), txnDetail.getPosEntryMode(),
//				txnDetail.getModel(), "00004");
//
//		PosEntryModeConfig pc = posEntryModeConfigService.fetchByMode(txnDetail.getPosEntryMode());

//		try {
//
//			Timestamp timestamp = Timestamp.from(YearMonth
//					.of(Integer.parseInt("06/27".split("/")[1]) + 2000, Integer.parseInt("06/27".split("/")[0]))
//					.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//			System.out.println(timestamp);
//
//			System.out.println(new Timestamp(System.currentTimeMillis()));
//
//			ResponseModel response = UtilAccess.downloadRoc(txnDetail, reportPath, pc);
////			System.out.println(response.getData().get("rocBlob"));
//			System.out.println(response.getData().get("imagePath"));
//			String originalUrl = "https://demo.accessgroup.mobi/fuelrevampdev/imgDownload?url="
//					+ response.getData().get("imagePath") + "&id=1";
//			String encodedUrl = URLEncoder.encode(originalUrl, "UTF-8");
//
//			String a = response.getData().get("imagePath").toString()
//					.substring(response.getData().get("imagePath").toString().indexOf(".") - 25);
//			System.out.println(response.getData().get("imagePath").toString());
//			System.out.println(a);
//
//			System.out.println(new HttpUtil().doGet("https://ulvis.net/api.php?url=" + encodedUrl + "&private=1"));
//
//			System.out.println(new HttpUtil().doGet("https://ulvis.net/api.php?url=" + encodedUrl + "&custom=AG"
//					+ System.currentTimeMillis() + "&private=1"));
//
//			System.out.println(new HttpUtil()
//					.doGet("https://ulvis.net/api.php?url=" + encodedUrl + "&custom=" + a + "&private=1"));
//		} catch (Exception ex) {
//
//		}

//		Float pTxnAmount = 20f;
//		
//		String signed = calculateHmacSHA256(
//				"appkey=8EAD7367D75E1B577844&timestamp="
//						+ new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())
//						+ "&nonce=" + UtilAccess.rrn() + "&amount=" + pTxnAmount
//						+ "&template=739&device_id=M10-10141DP1560",
//				"531F4345D643D89BC434C1F9BCD83896EE7ED373");
//
//		System.out.println(signed);
//
//		JSONObject obj = new JSONObject();
//		obj.put("appkey", "8EAD7367D75E1B577844");
//		obj.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
//		obj.put("nonce", UtilAccess.rrn());
//		obj.put("amount", pTxnAmount);
//		obj.put("template", 739);
//		obj.put("device_id", "M10-10141DP1560");
//		obj.put("sign", signed);
//
//		String res = doPost("https://api.cloudentify.com/v1/push/message", obj.toString());
//
//		System.out.println(res);

//		System.out.println(calculateHmacSHA256(
//				"appkey=8EAD7367D75E1B577844&timestamp=20240319140400&nonce=710839160697&amount=20&template=739&device_id=M10-10141DP1560",
//				"531F4345D643D89BC434C1F9BCD83896EE7ED373"));
//		
//
//		JSONObject obj = new JSONObject();
//		obj.put("appkey", "8EAD7367D75E1B577844");
//		obj.put("timestamp", "20240319140400");
//		obj.put("nonce", "710839160698");
//		obj.put("amount", 20);
//		obj.put("template", 739);
//		obj.put("device_id", "M10-10141DP1560");
//		obj.put("sign", "XClTVf2Tivo9RZIOV+oxLMb/VrCw4dIBTP6nMFcHom8=");
//		
//		String res = doPost("https://api.cloudentify.com/v1/push/message", obj.toString());
//
//		System.out.println(res);
//		
//		appkey: 8EAD7367D75E1B577844
//		secret: 531F4345D643D89BC434C1F9BCD83896EE7ED373
//		https://api.cloudentify.com/v1/push/message

//		 String s = EncDecUtl.doEncrypt("{\"channel\":\"android\",\"corpId\":\"00\",\"imei\":\"4014a2b4-ef1d-4dfc-ab66-a36d2d500d43\",\"lang\":\"en\",\"message\":\"CHK_CONFIG\"}","okhttp");
//		 System.out.println(s);
//		String[] beanNames = applicationContext.getBeanDefinitionNames();
//	    System.out.println("List of beans:");
//	    for (String beanName : beanNames) {
//	        System.out.println(beanName);
//	    }

//	process("200000005000508","42093687");

		// System.out.println("StringEscapeUtils.unescapeJava(sJava):\n" +
		// StringEscapeUtils.unescapeJava(response.getData().get("rocBlob").toString()));
		// doProcess();
	}

	String calculatePinBlock(String cardNumber, String pin) {
		try {
			String clearCardNumber = StringUtils.leftPad(cardNumber.substring(3, cardNumber.length() - 1), 16, "0");
			String clearPin = StringUtils.rightPad("0" + pin.length() + pin, 16, "F");

			long num1 = Long.parseLong(clearCardNumber, 16);
			long num2 = Long.parseLong(clearPin, 16);
			long result = num1 ^ num2;
			String clearPinBlock = "0" + Long.toHexString(result).toUpperCase();
			// C27F127C238C3B64
			
			// 1234567890123456
			// 1334577991133457
			DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex("1234567890123456"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] plainTextBytes = Hex.decodeHex(clearPinBlock);
			byte[] encryptedBytes = cipher.doFinal(plainTextBytes);

			return Hex.encodeHexString(encryptedBytes).toUpperCase();
			// return "057DE1B9B0BC9388";
		} catch (Exception e) {
			e.printStackTrace();
			return "FFFFFFFFFFFFFFFF";
		}
	}

	public ISO8583Model doProcess(ISO8583Model mdlRequest) {
		try {
			mdlRequest.setCode("0000");
			mdlRequest.setMessage("SUCCESS");
			Logger logger = new Logger();
			if (mdlRequest.isLoggerEnable()) {
				logger.addListener(new SimpleLogListener(System.out));
			}
			byte[] fHeader = new byte[5];
			fHeader = String_to_bcd.convert(mdlRequest.getHeader());
			ISOPackager newspecpackager = new GenericPackager(mdlRequest.getPeckager());
			AgLogger.logDebug("NAC", "DIALING TO " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			PostChannel channel = new PostChannel(mdlRequest.getNacIp(), mdlRequest.getNacPort(), newspecpackager);
			channel.setHeader(fHeader);
			channel.setTimeout(50000);

			if (mdlRequest.isLoggerEnable()) {
				((LogSource) channel).setLogger(logger, "test-channel");
			}
			channel.connect();
			AgLogger.logDebug("NAC", "CONNECTED TO " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			channel.send(mdlRequest.getIsoMessageRequest());
			mdlRequest.setIsoMessageResponse(channel.receive());
			AgLogger.logDebug("NAC", "DISCONNECTED FROM " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			mdlRequest.setCode(mdlRequest.getIsoMessageResponse().getString(39));
			channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			mdlRequest.setCode("0001");
			mdlRequest.setMessage("Something Went Wrong.");
		}
		return mdlRequest;
	}

	private static String calculateHmacSHA256(String data, String key)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		hmacSHA256.init(secretKey);
		byte[] hmacData = hmacSHA256.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(hmacData);
	}

	public static String doPost(String url, String text) throws Exception {
		String msg = "";
		try {

			HttpResponse httpResponse = null;
			HttpPost httpPost = new HttpPost(url);
			Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
			httpPost.addHeader(contentType);
			StringEntity entity = new StringEntity(text, "UTF-8");
			entity.setContentType(contentType);
			httpPost.setEntity(entity);
			HttpClient httpClient = HttpClientBuilder.create().build();
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception();
			}
			msg = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {

			throw new Exception();
		}
		return msg;
	}

	public static List<Model> process(String mid, String tid) {
		List<Model> response = new ArrayList<Model>();

		String pRespCode = "00001";
		String Message = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST  00000 | MID:" + mid + "|TID:" + tid + "|DATE:"
					+ new java.sql.Date(new java.util.Date().getTime()) + "|Y|1234678");
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_ADV(?,?,?,?,?,?,?,?)}");
			cs.setString(1, "00000");
			cs.setString(2, mid);
			cs.setString(3, tid);
			cs.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
			cs.setString(5, "Y");
			cs.setString(6, "1234678");

			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.registerOutParameter(8, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("RESPONESE " + cs.getString(7) + "|" + cs.getString(8));

			pRespCode = cs.getString(8);
			Message = cs.getString(7);

			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
				String tMessage = cs.getString(7);
				Type userListType = new TypeToken<ArrayList<Model>>() {
				}.getType();
				response = new Gson().fromJson(tMessage, userListType);

			} else {
				pRespCode = "9998";
				Message = "Unable to Perform Transaction.";
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
			Message = "Exception " + e.getMessage();
		}
		return response;
	}

	public void doProcess() {
		Connection con = null;
		try {
			con = DBProcUtil.getConnection();
			SQLServerDataTable sourceDataTable = new SQLServerDataTable();
			sourceDataTable.addColumnMetadata("PName", java.sql.Types.VARCHAR);
			sourceDataTable.addColumnMetadata("PType", java.sql.Types.VARCHAR);
			sourceDataTable.addColumnMetadata("PVType", java.sql.Types.VARCHAR);
			sourceDataTable.addColumnMetadata("PValue", java.sql.Types.VARCHAR);

			sourceDataTable.addRow("BATCHNO", "300", "300", "TEST01_Value01");
			sourceDataTable.addRow("ADVVER", "300", "300", "TEST01_Value02");
			sourceDataTable.addRow("ADVDWN", "300", "300", "TEST01_Value03");

			SQLServerCallableStatement cs = (SQLServerCallableStatement) con
					.prepareCall("{call Proc_Device_HeartBeat(?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, "00000");
			cs.setString(2, "200000005010234");
			cs.setString(3, "21038505");
			cs.setString(4, "000");
			// cs.setDate(5, new Date(new java.util.Date().getTime()));
			cs.setStructured(6, "parameterTableType", sourceDataTable);
			cs.setString(7, "909");

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.execute();
			AgLogger.logInfo(cs.getString(9));
			AgLogger.logInfo(cs.getString(8));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			DBProcUtil.closeConnection(con);
		}
	}

}
