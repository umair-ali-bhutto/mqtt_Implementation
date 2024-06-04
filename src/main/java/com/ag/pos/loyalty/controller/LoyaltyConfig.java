package com.ag.pos.loyalty.controller;

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
public class LoyaltyConfig {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	LovService lovService;

	@PostMapping({ "/LoyaltyConfig" })
	public ResponseModel doProcessFileAuth(@RequestBody JSONObject requestService, HttpServletRequest request) {
		String userName = requestService.get("userName").toString();
		String password = requestService.get("password").toString();
		return doProcessData(requestService, userName, password, request);
	}

	@SuppressWarnings("null")
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
				if (dataObject != null || dataObject.size() != 0) {
					response.setCode("0000");
					response.setMessage("Success");
					response.setData(dataObject);
				} else {
					response.setCode("0001");
					response.setMessage("No Configuration Found.");
				}

			} else {
				response.setCode("9991");
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
			adt.setTid("N/A");
			adt.setEntryDate(time);
			adt.setRequest(userName + "|" + password);
			adt.setResponse(new Gson().toJson(response));
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("Loyalty Config");
			adt.setMid("N/A");
			adt.setSerialNum("N/A");
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
			AgLogger.logInfo("REQUEST  " + terminalSerial);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call GET_LOY_PARAM(?,?,?,?,?,?,?)}");

			cs.setString(1, terminalSerial);

			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.CLOB);
			cs.registerOutParameter(4, OracleTypes.CLOB);
			cs.registerOutParameter(5, OracleTypes.CLOB);
			cs.registerOutParameter(6, OracleTypes.CLOB);
			cs.registerOutParameter(7, OracleTypes.CLOB);

			cs.execute();

			AgLogger.logInfo("GET_LOY_PARAM RES " + cs.getString(2) + "|" + cs.getString(3) + "|" + cs.getString(4)
					+ "|" + cs.getString(5) + "|" + cs.getString(6) + "|" + cs.getString(7));

			if (cs.getString(2).equals("00")) {

				// Basic Configuration
				String basicConf = cs.getString(3);
				jsonObject = (JSONObject) parser.parse(basicConf);

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

				// Product Configuration
				String productConf = cs.getString(4);

				jsonObject = (JSONObject) parser.parse(productConf);
				jsonArray = (JSONArray) jsonObject.get("PRODUCTS");
				List<ProductsModel> productList = new ArrayList<>();
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
				// Master
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
				System.out.println("NO RECORD");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

//		List<LovAdvertisment> lovAdvertisment = new ArrayList<>();
//		LovAdvertisment t = new LovAdvertisment();
//		t.setName("abx");
//		t.setTitle("xyz");
//		t.setUrl("https://demo.accessgroup.mobi/down/opt/test");
//		lovAdvertisment.add(t);
//
//		List<ProductsModel> pdl = new ArrayList<>();
//		ProductsModel pd = new ProductsModel();
//		pd.setProductDescription("REGULAR");
//		pd.setProductId("02");
//		pd.setProductnName("REGULAR");
//		pd.setProductPercentage("1.00%");
//		pd.setProductType("AWARD");
//		pdl.add(pd);
//
//		ProductsModel pdRedeem = new ProductsModel();
//		pdRedeem.setProductDescription("REDEEM");
//		pdRedeem.setProductId("01");
//		pdRedeem.setProductnName("REDEEM");
//		pdRedeem.setProductPercentage("1.00%");
//		pdRedeem.setProductType("REDEEM");
//		pdl.add(pdRedeem);
//
//		List<ModulesModel> mdlList = new ArrayList<>();
//		ModulesModel m = null;
//		m = new ModulesModel();
//		m.setModuleId("01");
//		m.setModuleKeyWord("award");
//		m.setModuleName("AWARDS");
//		m.setModuleRocDisplay("AWARDS");
//		m.setModuleScreenDisplay("AWARDS");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("02");
//		m.setModuleKeyWord("redeem");
//		m.setModuleName("REDEEM");
//		m.setModuleRocDisplay("REDEEM");
//		m.setModuleScreenDisplay("REDEEM");
//		mdlList.add(m);
//
////		m = new ModulesModel();
////		m.setModuleId("15");
////		m.setModuleKeyWord("discount");
////		m.setModuleName("DISCOUNT");
////		m.setModuleRocDisplay("DISCOUNT");
////		m.setModuleScreenDisplay("DISCOUNT");
////		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("03");
//		m.setModuleKeyWord("balEnquiry");
//		m.setModuleName("BAL ENQUIRY");
//		m.setModuleRocDisplay("BAL ENQUIRY");
//		m.setModuleScreenDisplay("BAL ENQUIRY");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("04");
//		m.setModuleKeyWord("settlement");
//		m.setModuleName("SETTLEMENT");
//		m.setModuleRocDisplay("SETTLEMENT");
//		m.setModuleScreenDisplay("Settlement");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("05");
//		m.setModuleKeyWord("generateqr");
//		m.setModuleName("GENERATE QR");
//		m.setModuleRocDisplay("GENERATE QR");
//		m.setModuleScreenDisplay("Generate QR");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("06");
//		m.setModuleKeyWord("reports");
//		m.setModuleName("REPORTS");
//		m.setModuleRocDisplay("REPORTS");
//		m.setModuleScreenDisplay("Reports");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("07");
//		m.setModuleKeyWord("detailsreports");
//		m.setModuleName("Details Reports");
//		m.setModuleRocDisplay("Details Reports");
//		m.setModuleScreenDisplay("Details Reports");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("08");
//		m.setModuleKeyWord("summaryreport");
//		m.setModuleName("Summary Report");
//		m.setModuleRocDisplay("Summary Report");
//		m.setModuleScreenDisplay("Summary Report");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("09");
//		m.setModuleKeyWord("anyreciept");
//		m.setModuleName("Any Reciept");
//		m.setModuleRocDisplay("Any Reciept");
//		m.setModuleScreenDisplay("Any Reciept");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("10");
//		m.setModuleKeyWord("lastreciept");
//		m.setModuleName("Last Reciept");
//		m.setModuleRocDisplay("Last Reciept");
//		m.setModuleScreenDisplay("Last Reciept");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("11");
//		m.setModuleKeyWord("lastsettle");
//		m.setModuleName("Last Settle");
//		m.setModuleRocDisplay("Last Settle");
//		m.setModuleScreenDisplay("Last Settle");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("12");
//		m.setModuleKeyWord("setting");
//		m.setModuleName("Setting");
//		m.setModuleRocDisplay("Setting");
//		m.setModuleScreenDisplay("Setting");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("13");
//		m.setModuleKeyWord("updatekyc");
//		m.setModuleName("Update KYC");
//		m.setModuleRocDisplay("Update KYC");
//		m.setModuleScreenDisplay("Update KYC");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("14");
//		m.setModuleKeyWord("complaints");
//		m.setModuleName("Complaints");
//		m.setModuleRocDisplay("Complaints");
//		m.setModuleScreenDisplay("Complaints");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("16");
//		m.setModuleKeyWord("keyedintxn");
//		m.setModuleName("Keyed In TXN");
//		m.setModuleRocDisplay("Keyed In TXN");
//		m.setModuleScreenDisplay("Keyed In TXN");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("17");
//		m.setModuleKeyWord("system");
//		m.setModuleName("System");
//		m.setModuleRocDisplay("System");
//		m.setModuleScreenDisplay("System");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("18");
//		m.setModuleKeyWord("superuser");
//		m.setModuleName("Super User");
//		m.setModuleRocDisplay("Super User");
//		m.setModuleScreenDisplay("Super User");
//		mdlList.add(m);
//
//		m = new ModulesModel();
//		m.setModuleId("19");
//		m.setModuleKeyWord("user");
//		m.setModuleName("User");
//		m.setModuleRocDisplay("User");
//		m.setModuleScreenDisplay("User");
//		mdlList.add(m);

//		dataObject.put("ACQUIRER", "LOYALTY");
//		dataObject.put("ADVERTISMENT_IMAGES_URL", lovAdvertisment);
//		dataObject.put("ADVERTISMENT_VERSION", "0.0");
//		dataObject.put("AUTO_SETTLE_TIME", "12:15:10");
//		dataObject.put("BATCH_NO", "000001");
//		dataObject.put("CORP_IMAGE_URL", "https://demo.accessgroup.mobi/MPORTAL-MW/downloads?abc");
//		dataObject.put("CORP_ROC_IMAGE_URL", "https://demo.accessgroup.mobi/MPORTAL-MW/downloads?abc");
//		dataObject.put("ISSUER", "SIF");
//		dataObject.put("MANAGER_PASSWORD", "9876");
//		dataObject.put("MERCHANT_ADDRESS", "XYZ");
//		dataObject.put("MERCHANT_CITY", "KARACHI");
//		dataObject.put("MERCHANT_ID", "999999999999999");
//		dataObject.put("MERCHANT_NAME", "X990 EMV Demo");
//		dataObject.put("NII", "115");
//		dataObject.put("PORTAL_URL", "https://demo.accessgroup.mobi/MPORTAL-MW");
//		dataObject.put("PRI_IP", "172.191.1.223");
//		dataObject.put("PRI_PORT", "7808");
//		dataObject.put("PRODUCTS", pdl);
//		dataObject.put("MODULES", mdlList);
//		dataObject.put("SEC_IP", "172.191.1.223");
//		dataObject.put("SEC_PORT", "7809");
//		dataObject.put("SUPER_USER_PASSWORD", "1234");
//		dataObject.put("SYSTEM_PASSWORD", "4321");
//		dataObject.put("TERMINAL_ID", "99999999");
//		dataObject.put("TPDU", "6001158074");
//		dataObject.put("USER_PASSWORD", "4845");
//		dataObject.put("PROGRAM_ID", "123");
//		dataObject.put("PROGRAM_NAME", "AWARD");
//		dataObject.put("LOV_MASTER", lovService.fetchLovs(user.getCorpId()));
//		dataObject.put("LOV_DETAILS", lovService.fetchLovsDetailsAll(user.getCorpId()));

		return dataObject;
	}

}
