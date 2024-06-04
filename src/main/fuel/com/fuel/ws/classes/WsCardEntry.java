package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProcUtil;
import com.ag.fuel.model.InputFormModel;
import com.ag.fuel.model.KeyValueModel;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import oracle.jdbc.internal.OracleTypes;

@Component("com.fuel.ws.classes.WsCardEntry")
public class WsCardEntry implements Wisher {

	@Autowired
	UserScreenService screenService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "INIT":
				response = initRequest(rm);
				break;
			case "FETCH_CARD_TYPES":
				response = fetchCardtypes(rm);
				break;

			case "FETCH_PRINTING_TYPES":
				response = fetchPrintingtypes(rm);
				break;

			case "FETCH_ACCOUNT_COMPANY":
				response = fetchAccountCompany(rm);
				break;

			case "FETCH_INPUTS":
				response = fetchInputs(rm);
				break;

			case "FETCH_PRODUCTS":
				response = fetchProducts(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String route = (rm.getAdditionalData().containsKey("route"))
					? rm.getAdditionalData().get("route").toString()
					: null;

			String corpid = rm.getCorpId();

			UserScreen screen = screenService.fetchScreenIdByRoute(route, corpid);

			List<KeyValueModel> lst = new ArrayList<KeyValueModel>();

			KeyValueModel keyValue = null;
			keyValue = new KeyValueModel();
			keyValue.setKey("PREPAIDCARD");
			keyValue.setValue("Prepaid");
			lst.add(keyValue);

			keyValue = new KeyValueModel();
			keyValue.setKey("POSTPAIDCARD");
			keyValue.setValue("Postpaid");
			lst.add(keyValue);

			if (lst.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("categories", lst);
				o.put("screenId", screen.getScreenId());
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");
			} else {
				response.setCode("0001");
				response.setMessage("No Category Found.");
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	ResponseModel fetchCardtypes(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String requestData = (rm.getAdditionalData().get("requestData") != null)
					? rm.getAdditionalData().get("requestData").toString()
					: null;

			String selectedCategory = (rm.getAdditionalData().get("selectedCategory") != null)
					? rm.getAdditionalData().get("selectedCategory").toString()
					: null;

			String userCode = (rm.getAdditionalData().get("userCode") != null)
					? rm.getAdditionalData().get("userCode").toString()
					: null;

			String groupCode = (rm.getAdditionalData().get("groupCode") != null)
					? rm.getAdditionalData().get("groupCode").toString()
					: null;

			String res[] = FuelCardEntryProcGetLov(rm.getCorpId(), selectedCategory, "LOV",
					Integer.parseInt(rm.getUserid()), userCode, Integer.parseInt(groupCode), requestData);

			if (res[0].equals("00")) {
				List<KeyValueModel> lst = new ArrayList<KeyValueModel>();
				HashMap<Object, Object> obMap = new HashMap<Object, Object>();
				Type ListType = new TypeToken<List<KeyValueModel>>() {
				}.getType();
				lst = new Gson().fromJson(res[2], ListType);
				obMap.put("cardTypes", lst);
				response.setData(obMap);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
			} else {
				response.setCode(res[0]);
				response.setMessage(res[1]);
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	ResponseModel fetchPrintingtypes(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {

			String requestData = (rm.getAdditionalData().get("requestData") != null)
					? rm.getAdditionalData().get("requestData").toString()
					: null;

			String userCode = (rm.getAdditionalData().get("userCode") != null)
					? rm.getAdditionalData().get("userCode").toString()
					: null;

			String groupCode = (rm.getAdditionalData().get("groupCode") != null)
					? rm.getAdditionalData().get("groupCode").toString()
					: null;

			String res[] = FuelCardEntryProcGetLov(rm.getCorpId(), "PRINTINGTYPE", "LOV",
					Integer.parseInt(rm.getUserid()), userCode, Integer.parseInt(groupCode), requestData);

			if (res[0].equals("00")) {
				List<KeyValueModel> lst = new ArrayList<KeyValueModel>();
				HashMap<Object, Object> obMap = new HashMap<Object, Object>();
				Type ListType = new TypeToken<List<KeyValueModel>>() {
				}.getType();
				lst = new Gson().fromJson(res[2], ListType);
				obMap.put("printingTypes", lst);
				response.setData(obMap);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
			} else {
				response.setCode(res[0]);
				response.setMessage(res[1]);
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	ResponseModel fetchAccountCompany(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String requestData = (rm.getAdditionalData().get("requestData") != null)
					? rm.getAdditionalData().get("requestData").toString()
					: null;

			String userCode = (rm.getAdditionalData().get("userCode") != null)
					? rm.getAdditionalData().get("userCode").toString()
					: null;

			String groupCode = (rm.getAdditionalData().get("groupCode") != null)
					? rm.getAdditionalData().get("groupCode").toString()
					: null;

			String res[] = FuelCardEntryProcGetLov(rm.getCorpId(), "ACCOUNT_COMPANY", "LOV",
					Integer.parseInt(rm.getUserid()), userCode, Integer.parseInt(groupCode), requestData);

			if (res[0].equals("00")) {
				JsonObject jsonObject = new Gson().fromJson(res[2], JsonObject.class);

				List<KeyValueModel> lstAccounts = new ArrayList<KeyValueModel>();
				List<KeyValueModel> lstCompanies = new ArrayList<KeyValueModel>();

				HashMap<Object, Object> obMap = new HashMap<Object, Object>();
				Type ListType = new TypeToken<List<KeyValueModel>>() {
				}.getType();
				lstAccounts = new Gson().fromJson(jsonObject.getAsJsonArray("accounts"), ListType);
				lstCompanies = new Gson().fromJson(jsonObject.getAsJsonArray("companies"), ListType);

				obMap.put("accounts", lstAccounts);
				obMap.put("companies", lstCompanies);
				response.setData(obMap);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
			} else {
				response.setCode(res[0]);
				response.setMessage(res[1]);
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	ResponseModel fetchInputs(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String selectedCategory = (rm.getAdditionalData().get("selectedCategory") != null)
					? rm.getAdditionalData().get("selectedCategory").toString()
					: null;
			String selectedCardType = (rm.getAdditionalData().get("selectedCardType") != null)
					? rm.getAdditionalData().get("selectedCardType").toString()
					: null;
			String selectedPrintingType = (rm.getAdditionalData().get("selectedPrintingType") != null)
					? rm.getAdditionalData().get("selectedPrintingType").toString()
					: null;
			String selectedaccount = (rm.getAdditionalData().get("selectedaccount") != null)
					? rm.getAdditionalData().get("selectedaccount").toString()
					: null;
			String selectedcompany = (rm.getAdditionalData().get("selectedcompany") != null)
					? rm.getAdditionalData().get("selectedcompany").toString()
					: null;

			String groupCode = (rm.getAdditionalData().get("groupCode") != null)
					? rm.getAdditionalData().get("groupCode").toString()
					: null;

			String[] responseProc = ProcGetCardInputField("F", "1", selectedCardType, rm.getUserid(), rm.getCorpId(),
					groupCode);

			if (responseProc[0].equals("00")) {

				TypeToken<List<InputFormModel>> typeToken = new TypeToken<List<InputFormModel>>() {
				};
				List<InputFormModel> filterList = new Gson().fromJson(responseProc[1], typeToken.getType());

				if (filterList.size() != 0) {

					boolean containsDuplicateParams = containsDuplicateParams(filterList);

					if (!containsDuplicateParams) {
						HashMap<Object, Object> obj = new HashMap<Object, Object>();
						obj.put("filterList", filterList);

						response.setData(obj);
						response.setCode("0000");
						response.setMessage("Success.");
					} else {
						response.setCode("0003");
						response.setMessage("Record Contains Duplicate Params.");
					}

				} else {
					response.setCode("0002");
					response.setMessage("No Record Found.");
				}
			} else {
				response.setCode(responseProc[0]);
				response.setMessage(responseProc[1]);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	ResponseModel fetchProducts(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String selectedCategory = (rm.getAdditionalData().get("selectedCategory") != null)
					? rm.getAdditionalData().get("selectedCategory").toString()
					: null;

			String selectedCardType = (rm.getAdditionalData().get("selectedCardType") != null)
					? rm.getAdditionalData().get("selectedCardType").toString()
					: null;

			String userCode = (rm.getAdditionalData().get("userCode") != null)
					? rm.getAdditionalData().get("userCode").toString()
					: null;

			String groupCode = (rm.getAdditionalData().get("groupCode") != null)
					? rm.getAdditionalData().get("groupCode").toString()
					: null;

			String res[] = FuelCardEntryProcGetLov(rm.getCorpId(), "PRODUCTS", "LOV", Integer.parseInt(rm.getUserid()),
					userCode, Integer.parseInt(groupCode), null);

			if (res[0].equals("00")) {
				List<KeyValueModel> lst = new ArrayList<KeyValueModel>();
				HashMap<Object, Object> obMap = new HashMap<Object, Object>();
				Type ListType = new TypeToken<List<KeyValueModel>>() {
				}.getType();
				lst = new Gson().fromJson(res[2], ListType);
				obMap.put("products", lst);
				response.setData(obMap);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
			} else {
				response.setCode(res[0]);
				response.setMessage(res[1]);
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public boolean containsDuplicateParams(List<InputFormModel> filterList) {
		HashSet<String> pageParams = new HashSet<String>();
		for (InputFormModel filter : filterList) {
			String pageParamName = filter.getPageParamName();
			if (pageParams.contains(pageParamName)) {
				return true;
			}
			pageParams.add(pageParamName);
		}
		return false;
	}

	public static String[] FuelCardEntryProcGetLov(String corpId, String message, String messageType, int userId,
			String userCode, int groupCode, String request) {

		String[] response = new String[3];

		response[0] = "9999";
		response[1] = "Failed";
		response[2] = "";

		Connection con = null;
		try {

			AgLogger.logInfo("PROC_GET_LOV REQUEST:" + corpId + "|" + message + "|" + messageType + "|" + userId + "|"
					+ userCode + "|" + groupCode + "|" + request);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_LOV(?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, message);
			cs.setString(3, messageType);
			cs.setInt(4, userId);
			cs.setString(5, userCode);
			cs.setInt(6, groupCode);
			cs.setString(7, request);
			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.registerOutParameter(10, OracleTypes.CLOB);
			cs.execute();

			response[0] = cs.getString(8);
			response[1] = cs.getString(9);
			response[2] = cs.getString(10);

			AgLogger.logInfo("PROC_GET_LOV RESPONSE:" + response[0] + "|" + response[1] + "|" + response[2]);

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "9999";
			response[1] = "Something Went Wrong.";
			response[2] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static String[] ProcGetCardInputField(String type, String pageId, String cardType, String userId,
			String corpId, String groupCode) {

		String[] response = new String[2];

		response[0] = "99";
		response[1] = "";

		Connection con = null;
		try {
			AgLogger.logInfo("PROC_GET_CARD_INPUT_FIELD REQUEST:" + "|" + type + "|" + pageId + "|" + cardType + "|"
					+ userId + "|" + corpId + "|" + groupCode);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_CARD_INPUT_FIELD(?,?,?,?,?,?,?,?)}");
			cs.setString(1, type);
			cs.setString(2, pageId);
			cs.setString(3, cardType);
			cs.setString(4, userId);
			cs.setString(5, corpId);
			cs.setString(6, groupCode);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.registerOutParameter(8, OracleTypes.CLOB);
			cs.execute();

			response[0] = cs.getString(7);
			response[1] = cs.getString(8);

			AgLogger.logInfo("PROC_GET_CARD_INPUT_FIELD RESPONSE:" + response[0] + "|" + response[1]);

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "99";
			response[1] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}
		return response;
	}

}
