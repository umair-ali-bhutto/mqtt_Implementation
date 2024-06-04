package com.ag.pos.fuel.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.db.proc.DBProcUtil;
import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.loyalty.model.LovAdvertisment;
import com.ag.generic.loyalty.model.ModulesModel;
import com.ag.generic.loyalty.model.ProductsModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.LovService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;

import oracle.jdbc.internal.OracleTypes;

@RestController
public class FuelConfig {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	LovService lovService;

	@PostMapping({ "/FuelConfig" })
	public ResponseModel doProcess(@RequestBody JSONObject requestService, HttpServletRequest request) {
		AgLogger.logInfo("Fuel Config");
		String userName = requestService.get("userName").toString();
		String password = requestService.get("password").toString();
		return doProcessData(requestService, userName, password, request);
	}

	public ResponseModel doProcessData(JSONObject requestService, String userName, String password,
			HttpServletRequest request) {
		ResponseModel response = new ResponseModel();
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		try {
			String mid = requestService.get("mid").toString();
			String tid = requestService.get("tid").toString();
			String advVersion = requestService.get("advVersion").toString();
			String terminalSerial = requestService.get("terminalSerial").toString();
			UserLogin user = userLoginService.validateUserPassword(userName, password);
			if (user != null) {

				HashMap<Object, Object> dataObject = getDetails(mid, tid, advVersion, terminalSerial, user);

				// HashMap<Object, Object> dataObjectOld = getDetailsold(mid, tid, advVersion,
				// terminalSerial, user);
				if (dataObject.containsKey("code")) {
					response.setCode(dataObject.get("code").toString());
					response.setMessage(dataObject.get("message").toString());
				} else {
					response.setCode("0000");
					response.setMessage("Success");
					// response.setData(dataObjectOld);
					response.setData(dataObject);
				}
				AgLogger.logInfo(new Gson().toJson(response));

			} else {
				response.setCode("9992");
				response.setMessage("Invalid Credentials.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setTid(requestService.get("tid").toString());
			adt.setEntryDate(time);
			adt.setRequest(requestService.toString());
			adt.setResponse(new Gson().toJson(response));
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("Fuel Config");
			adt.setMid(requestService.get("mid").toString());
			adt.setSerialNum(requestService.get("terminalSerial").toString());
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return response;
	}

	public HashMap<Object, Object> getDetails(String mid, String tid, String advVersion, String terminalSerial,
			UserLogin user) {

		HashMap<Object, Object> dataObject = new HashMap<Object, Object>();

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		JSONArray jsonArray = new JSONArray();

		Connection con = null;
		try {
			AgLogger.logInfo("GET_FUEL_PARAM REQUEST " + terminalSerial);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call GET_FUEL_PARAM(?,?,?,?,?,?,?)}");

			cs.setString(1, terminalSerial);

			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.CLOB);
			cs.registerOutParameter(4, OracleTypes.CLOB);
			cs.registerOutParameter(5, OracleTypes.CLOB);
			cs.registerOutParameter(6, OracleTypes.CLOB);
			cs.registerOutParameter(7, OracleTypes.CLOB);

			cs.execute();

			AgLogger.logInfo("GET_FUEL_PARAM RES " + cs.getString(2) + "|" + cs.getString(3) + "|" + cs.getString(4)
					+ "|" + cs.getString(5) + "|" + cs.getString(6) + "|" + cs.getString(7));

			if (cs.getString(2).equals("00")) {

				// Basic Configuration
				String basicConf = cs.getString(3);
				jsonObject = (JSONObject) parser.parse(basicConf);

				String PinEnabled = "Y";
				if (jsonObject.containsKey("PIN_ENABLED")) {
					PinEnabled = jsonObject.get("PIN_ENABLED").toString();
				}

				// PIN_ENABLED Y/N
				dataObject.put("PIN_ENABLED", PinEnabled);

//				// TEMPORARY CHANGED
//				dataObject.put("MODE", "AMOUNT+LITRE");
//				dataObject.put("PORTAL_URL", "http://172.191.1.223:8086/MPORTAL-MW/");
//				dataObject.put("STAN", "700");

				dataObject.put("ADVERTISMENT_VERSION", jsonObject.get("ADVERTISMENT_VERSION"));
				dataObject.put("ACQUIRER", jsonObject.get("ACQUIRER"));
				dataObject.put("AUTO_SETTLE_TIME", jsonObject.get("AUTO_SETTLE_TIME"));
				dataObject.put("BATCH_NO", jsonObject.get("BATCH_NO"));
				dataObject.put("CORP_IMAGE_URL", jsonObject.get("CORP_IMAGE_URL"));
				dataObject.put("CORP_ROC_IMAGE_URL", jsonObject.get("CORP_ROC_IMAGE_URL"));
				dataObject.put("ISSUER", jsonObject.get("ISSUER"));
				dataObject.put("MERCHANT_CITY", jsonObject.get("MERCHANT_CITY"));
				dataObject.put("MERCHANT_NAME", jsonObject.get("MERCHANT_NAME"));
				dataObject.put("MERCHANT_ADDRESS", jsonObject.get("MERCHANT_ADDRESS"));
				dataObject.put("MANAGER_PASSWORD", jsonObject.get("MANAGER_PASSWORD"));
				dataObject.put("MERCHANT_ID", jsonObject.get("MERCHANT_ID"));
				dataObject.put("NII", jsonObject.get("NII"));
				dataObject.put("PROGRAM_ID", jsonObject.get("PROGRAM_ID"));
				dataObject.put("PRI_IP", jsonObject.get("PRI_IP"));
				dataObject.put("PRI_PORT", jsonObject.get("PRI_PORT"));
				dataObject.put("PORTAL_URL", jsonObject.get("PORTAL_URL"));
				dataObject.put("PROGRAM_NAME", jsonObject.get("PROGRAM_NAME"));
				dataObject.put("SEC_IP", jsonObject.get("SEC_IP"));
				dataObject.put("SEC_PORT", jsonObject.get("SEC_PORT"));
				dataObject.put("SUPER_USER_PASSWORD", jsonObject.get("SUPER_USER_PASSWORD"));
				dataObject.put("SYSTEM_PASSWORD", jsonObject.get("SYSTEM_PASSWORD"));
				dataObject.put("TERMINAL_ID", jsonObject.get("TERMINAL_ID"));
				dataObject.put("TPDU", jsonObject.get("TPDU"));
				dataObject.put("USER_PASSWORD", jsonObject.get("USER_PASSWORD"));

				dataObject.put("STAN", jsonObject.get("STAN"));
				dataObject.put("BIN_RANGES", jsonObject.get("BIN_RANGES"));
				dataObject.put("MODE", jsonObject.get("MODE"));

				dataObject.put("ROC_MSG", jsonObject.containsKey("ROC_MSG") ? jsonObject.get("ROC_MSG").toString()
						: "Byco Fuel Cards: Exceptional Fuels for Your Journey!");
				
				dataObject.put("BAFL_APK_DOWNLOAD", jsonObject.containsKey("BAFL_APK_DOWNLOAD") ? jsonObject.get("BAFL_APK_DOWNLOAD").toString()
						: "");


				dataObject.put("SETTLED_RANGE",
						jsonObject.containsKey("SETTLED_RANGE") ? jsonObject.get("SETTLED_RANGE").toString() : "1");

				// Product Configuration
				String productConf = cs.getString(4);

				jsonObject = (JSONObject) parser.parse(productConf);
				jsonArray = (JSONArray) jsonObject.get("PRODUCTS");
				List<ProductsModel> productList = new ArrayList<ProductsModel>();
				for (Object productObj : jsonArray) {
					jsonObject = (JSONObject) productObj;

					ProductsModel product = new ProductsModel();
					product.setProductId(jsonObject.get("productId").toString());
					product.setProductnName(jsonObject.get("productnName").toString());
					product.setProductDescription(jsonObject.get("productDescription").toString());
					product.setProductPercentage(jsonObject.get("productPercentage").toString());
					product.setProductType(jsonObject.get("productType").toString());

					productList.add(product);
				}

				dataObject.put("PRODUCTS", productList);

				// LOV Configuration
				String lovConf = cs.getString(5);

				jsonObject = (JSONObject) parser.parse(lovConf);
				// Master
				jsonArray = (JSONArray) jsonObject.get("LOV_MASTER");
				List<LovMaster> lovMaster = new ArrayList<LovMaster>();
				for (Object lovObject : jsonArray) {
					JSONObject masterobj = (JSONObject) lovObject;

					LovMaster master = new LovMaster();
					master.setId(masterobj.get("id").toString());
					master.setValue(masterobj.get("value").toString());
					master.setCorpId(masterobj.get("corpId").toString());

					lovMaster.add(master);
				}

				dataObject.put("LOV_MASTER", lovMaster);

				// Details
				jsonArray = (JSONArray) jsonObject.get("LOV_DETAILS");
				List<LovDetail> lovDetails = new ArrayList<LovDetail>();
				for (Object lovObject : jsonArray) {
					JSONObject detailObj = (JSONObject) lovObject;

					LovDetail detail = new LovDetail();
					detail.setId(detailObj.get("id").toString());
					detail.setLovId(detailObj.get("lovId").toString());
					detail.setValue(detailObj.get("value").toString());
					detail.setCorpId(detailObj.get("corpId").toString());

					lovDetails.add(detail);
				}

				dataObject.put("LOV_DETAILS", lovDetails);

				// Modules Configuration
				String moduleConfig = cs.getString(6);
				jsonObject = (JSONObject) parser.parse(moduleConfig);
				jsonArray = (JSONArray) jsonObject.get("MODULES");
				List<ModulesModel> moduleList = new ArrayList<ModulesModel>();
				for (Object lovObject : jsonArray) {
					jsonObject = (JSONObject) lovObject;

					ModulesModel m = new ModulesModel();
					m.setModuleId(jsonObject.get("moduleId").toString());
					m.setModuleKeyWord(jsonObject.get("moduleKeyWord").toString());
					m.setModuleName(jsonObject.get("moduleName").toString());
					m.setModuleRocDisplay(jsonObject.get("moduleRocDisplay").toString());
					m.setModuleScreenDisplay(jsonObject.get("moduleScreenDisplay").toString());

					moduleList.add(m);
				}

				dataObject.put("MODULES", moduleList);

				// Advertisement Configuration
				String advertisementConfig = cs.getString(7);
				jsonObject = (JSONObject) parser.parse(advertisementConfig);
				jsonArray = (JSONArray) jsonObject.get("ADVERTISMENT_IMAGES_URL");
				List<LovAdvertisment> advertisementList = new ArrayList<LovAdvertisment>();
				for (Object lovObject : jsonArray) {
					jsonObject = (JSONObject) lovObject;

					LovAdvertisment t = new LovAdvertisment();
					t.setName(jsonObject.get("name").toString());
					t.setTitle(jsonObject.get("title").toString());
					t.setUrl(jsonObject.get("url").toString());

					advertisementList.add(t);
				}

				dataObject.put("ADVERTISMENT_IMAGES_URL", advertisementList);

			} else {
				dataObject.put("code", "0001");
				dataObject.put("message", "No Configuration Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			dataObject.put("code", "9991");
			dataObject.put("message", "Something Went Wrong");
		}

		return dataObject;
	}

}

//// OLD HARD CODED
//public HashMap<Object, Object> getDetailsold(String mid, String tid, String advVersion, String terminalSerial,
//		UserLogin user) {
//
//	HashMap<Object, Object> dataObject = new HashMap<Object, Object>();
//
//	try {
//		boolean recordFound = true;
//		if (recordFound) {
//			List<ModulesModel> mdlList = new ArrayList<>();
//			ModulesModel m = null;
//
//			m = new ModulesModel();
//			m.setModuleId("01");
//			m.setModuleName("SALE");
//			m.setModuleRocDisplay("SALE");
//			m.setModuleScreenDisplay("SALE");
//			m.setModuleKeyWord("sale");
//			mdlList.add(m);
//
//			m = new ModulesModel();
//			m.setModuleId("03");
//			m.setModuleName("BAL ENQUIRY");
//			m.setModuleRocDisplay("BAL ENQUIRY");
//			m.setModuleScreenDisplay("BAL ENQUIRY");
//			m.setModuleKeyWord("balEnquiry");
//			mdlList.add(m);
//
//			m = new ModulesModel();
//			m.setModuleId("04");
//			m.setModuleName("SETTLEMENT");
//			m.setModuleRocDisplay("SETTLEMENT");
//			m.setModuleScreenDisplay("Settlement");
//			m.setModuleKeyWord("settlement");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("05");
//			m.setModuleName("GENERATE QR");
//			m.setModuleRocDisplay("GENERATE QR");
//			m.setModuleScreenDisplay("Generate QR");
//			m.setModuleKeyWord("generateqr");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("06");
//			m.setModuleName("REPORTS");
//			m.setModuleRocDisplay("REPORTS");
//			m.setModuleScreenDisplay("Reports");
//			m.setModuleKeyWord("reports");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("07");
//			m.setModuleName("Details Reports");
//			m.setModuleRocDisplay("Details Reports");
//			m.setModuleScreenDisplay("Details Reports");
//			m.setModuleKeyWord("detailsreports");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("08");
//			m.setModuleName("Summary Report");
//			m.setModuleRocDisplay("Summary Report");
//			m.setModuleScreenDisplay("Summary Report");
//			m.setModuleKeyWord("summaryreport");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("09");
//			m.setModuleName("Any Reciept");
//			m.setModuleRocDisplay("Any Reciept");
//			m.setModuleScreenDisplay("Any Reciept");
//			m.setModuleKeyWord("anyreciept");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("10");
//			m.setModuleName("Last Reciept");
//			m.setModuleRocDisplay("Last Reciept");
//			m.setModuleScreenDisplay("Last Reciept");
//			m.setModuleKeyWord("lastreciept");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("11");
//			m.setModuleName("Last Settle");
//			m.setModuleRocDisplay("Last Settle");
//			m.setModuleScreenDisplay("Last Settle");
//			m.setModuleKeyWord("lastsettle");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("12");
//			m.setModuleName("Setting");
//			m.setModuleRocDisplay("Setting");
//			m.setModuleScreenDisplay("Setting");
//			m.setModuleKeyWord("setting");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("14");
//			m.setModuleName("Complaints");
//			m.setModuleRocDisplay("Complaints");
//			m.setModuleScreenDisplay("Complaints");
//			m.setModuleKeyWord("complaints");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("17");
//			m.setModuleName("System");
//			m.setModuleRocDisplay("System");
//			m.setModuleScreenDisplay("System");
//			m.setModuleKeyWord("system");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("18");
//			m.setModuleName("Super User");
//			m.setModuleRocDisplay("Super User");
//			m.setModuleScreenDisplay("Super User");
//			m.setModuleKeyWord("superuser");
//			mdlList.add(m);
//			m = new ModulesModel();
//			m.setModuleId("19");
//			m.setModuleName("User");
//			m.setModuleRocDisplay("User");
//			m.setModuleScreenDisplay("User");
//			m.setModuleKeyWord("user");
//			mdlList.add(m);
//
//			List<ProductsModel> pdl = new ArrayList<>();
//
//			ProductsModel pm = null;
//
//			pm = new ProductsModel();
//			pm.setProductDescription("MS");
//			pm.setProductId("01");
//			pm.setProductnName("MS");
//			pm.setProductPercentage("1.00%");
//			pm.setProductType("SALE");
//			pdl.add(pm);
//
//			pm = new ProductsModel();
//			pm.setProductDescription("HSD");
//			pm.setProductId("02");
//			pm.setProductnName("HSD");
//			pm.setProductPercentage("1.00%");
//			pm.setProductType("SALE");
//			pdl.add(pm);
//
//			pm = new ProductsModel();
//			pm.setProductDescription("HOBC");
//			pm.setProductId("03");
//			pm.setProductnName("HOBC");
//			pm.setProductPercentage("1.00%");
//			pm.setProductType("SALE");
//			pdl.add(pm);
//
//
//			List<LovMaster> master = new ArrayList<LovMaster>();
//			LovMaster lovmaster = null;
//			lovmaster = new LovMaster();
//			lovmaster.setId("0009");
//			lovmaster.setValue("GENERAL");
//			lovmaster.setCorpId("00004");
//			master.add(lovmaster);
//			lovmaster = new LovMaster();
//			lovmaster.setId("0010");
//			lovmaster.setValue("FEEDBACK");
//			lovmaster.setCorpId("00004");
//			master.add(lovmaster);
//			lovmaster = new LovMaster();
//			lovmaster.setId("0011");
//			lovmaster.setValue("DEALER RELATED");
//			lovmaster.setCorpId("00004");
//			master.add(lovmaster);
//			lovmaster = new LovMaster();
//			lovmaster.setId("0012");
//			lovmaster.setValue("CARD RELATED");
//			lovmaster.setCorpId("00004");
//			master.add(lovmaster);
//
//			List<LovDetail> detail = new ArrayList<LovDetail>();
//			LovDetail lovdetail = null;
//			lovdetail = new LovDetail();
//			lovdetail.setId("00014");
//			lovdetail.setLovId("0009");
//			lovdetail.setValue("GENERAL");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00015");
//			lovdetail.setLovId("0010");
//			lovdetail.setValue("FEEDBACK");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00016");
//			lovdetail.setLovId("0011");
//			lovdetail.setValue("DEALER NOT ACCEPT CARD");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00017");
//			lovdetail.setLovId("0011");
//			lovdetail.setValue("FUEL QUALITY NOT UPTO");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00018");
//			lovdetail.setLovId("0011");
//			lovdetail.setValue("LESS FUEL");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00019");
//			lovdetail.setLovId("0011");
//			lovdetail.setValue("OTHER");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00020");
//			lovdetail.setLovId("0012");
//			lovdetail.setValue("MY CARD IS DAMAGED");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00021");
//			lovdetail.setLovId("0012");
//			lovdetail.setValue("MY CARD IS LOST");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00022");
//			lovdetail.setLovId("0012");
//			lovdetail.setValue("RE-ISSUANCE CARD NOT RECIEVED");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//			lovdetail = new LovDetail();
//			lovdetail.setId("00023");
//			lovdetail.setLovId("0012");
//			lovdetail.setValue("OTHER");
//			lovdetail.setCorpId("00004");
//			detail.add(lovdetail);
//
//			List<LovAdvertisment> lovAdvertisment = new ArrayList<>();
//			LovAdvertisment t = null;
//			t = new LovAdvertisment();
//			t.setName("First Banner");
//			t.setTitle("First Banner");
//			t.setUrl(
//					"https://demo.accessgroup.mobi/DEMO-MPORTAL-MW/imgDownload?url=/home/umair/Desktop/Angular-WS/fuel-banners/Banners1.png&id=1");
//			lovAdvertisment.add(t);
//			t = new LovAdvertisment();
//			t.setName("Second Banner");
//			t.setTitle("Second Banner");
//			t.setUrl(
//					"https://demo.accessgroup.mobi/DEMO-MPORTAL-MW/imgDownload?url=/home/umair/Desktop/Angular-WS/fuel-banners/Banners2.png&id=2");
//			lovAdvertisment.add(t);
//			t = new LovAdvertisment();
//			t.setName("Third Banner");
//			t.setTitle("Third Banner");
//			t.setUrl(
//					"https://demo.accessgroup.mobi/DEMO-MPORTAL-MW/imgDownload?url=/home/umair/Desktop/Angular-WS/fuel-banners/Banners3.png&id=3");
//			lovAdvertisment.add(t);
//
//			t = new LovAdvertisment();
//			t.setName("Fourth Banner");
//			t.setTitle("Fourth Banner");
//			t.setUrl(
//					"https://demo.accessgroup.mobi/DEMO-MPORTAL-MW/imgDownload?url=/home/umair/Desktop/Angular-WS/fuel-banners/Banners4.png&id=4");
//			lovAdvertisment.add(t);
//			t = new LovAdvertisment();
//			t.setName("Fifth Banner");
//			t.setTitle("Fifth Banner");
//			t.setUrl(
//					"https://demo.accessgroup.mobi/DEMO-MPORTAL-MW/imgDownload?url=/home/umair/Desktop/Angular-WS/fuel-banners/Banners5.png&id=5");
//			lovAdvertisment.add(t);
//
//			t = new LovAdvertisment();
//			t.setName("Sixth Banner");
//			t.setTitle("Sixth Banner");
//			t.setUrl(
//					"https://demo.accessgroup.mobi/DEMO-MPORTAL-MW/imgDownload?url=/home/umair/Desktop/Angular-WS/fuel-banners/Banners6.png&id=6");
//			lovAdvertisment.add(t);
//
//			dataObject.put("BIN_RANGES", "8096150000:8096159999,8096180000:8096189999,4025810000:4025819999");
//
//			// dataObject.put("BIN_RANGES", "3799330000:3799339999,4025810000:4025819999");
//
//			dataObject.put("ADVERTISMENT_VERSION", "0.0");
//			dataObject.put("ACQUIRER", "LOYALTY");
//			dataObject.put("AUTO_SETTLE_TIME", "12: 15: 10");
//			dataObject.put("ADVERTISMENT_IMAGES_URL", lovAdvertisment);
//			dataObject.put("BATCH_NO", "000356");
//			dataObject.put("CORP_IMAGE_URL",
//					"http://172.191.1.223:8087/MPORTAL-MW/imgDownload?url=/home/umair/Downloads/graphics/byco/banners/banner2.png&id=1");
//			dataObject.put("CORP_ROC_IMAGE_URL",
//					"http://172.191.1.223:8087/MPORTAL-MW/imgDownload?url=/home/umair/Downloads/graphics/byco/banners/banner2.png&id=1");
//			dataObject.put("ISSUER", "SIF");
//			dataObject.put("LOV_MASTER", master);
//			dataObject.put("LOV_DETAILS", detail);
//			dataObject.put("MERCHANT_CITY", "KARACHI");
//			dataObject.put("MERCHANT_NAME", "X990 EMV Demo");
//			dataObject.put("MERCHANT_ADDRESS", "XYZ");
//			dataObject.put("MANAGER_PASSWORD", "4848");
//			dataObject.put("MERCHANT_ID", "000762390019991");
//			dataObject.put("MODULES", mdlList);
//			dataObject.put("NII", "711");
//			dataObject.put("PROGRAM_NAME", "AWARD");
//			dataObject.put("PROGRAM_ID", "123");
//			dataObject.put("PRI_IP", "202.143.120.251");
//			dataObject.put("PRI_PORT", "7006");
//
////			dataObject.put("PRI_IP", "172.191.1.223");
////			dataObject.put("PRI_PORT", "7809");
//
//			dataObject.put("PORTAL_URL", "https://demo.accessgroup.mobi/DEMO-MPORTAL-MW/");
//			// dataObject.put("PORTAL_URL", "http://172.191.1.223:8087/MPORTAL-MW");
//			dataObject.put("PRODUCTS", pdl);
//			dataObject.put("SUPER_USER_PASSWORD", "4848");
//			dataObject.put("SYSTEM_PASSWORD", "4848");
//
//			dataObject.put("SEC_IP", "202.143.120.251");
//			dataObject.put("SEC_PORT", "7006");
//
////			dataObject.put("SEC_IP", "172.191.1.223");
////			dataObject.put("SEC_PORT", "7808");
//
//			dataObject.put("TERMINAL_ID", "00091889");
//			// dataObject.put("TPDU", "6001158074");
//			dataObject.put("TPDU", "600711800E");
//
//			// OPTIONS FOR MODE SHOULD BE FROM THESE TRHEE ONLY.
//			// AMOUNT, LITRE, AMOUNT+LITRE
//			dataObject.put("MODE", "AMOUNT+LITRE");
//
//			dataObject.put("STAN", "400");
//
//			dataObject.put("USER_PASSWORD", "4848");
//		} else {
//			dataObject.put("code", "0001");
//			dataObject.put("message", "No Configuration Found.");
//		}
//
//	} catch (Exception e) {
//		e.printStackTrace();
//		dataObject.put("code", "9991");
//		dataObject.put("message", "Something Went Wrong");
//	}
//
//	return dataObject;
//}
