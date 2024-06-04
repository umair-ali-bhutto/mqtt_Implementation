package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProcUtil;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.model.InputFormModel;
import com.ag.fuel.model.KeyValueModel;
import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.services.BuildControlConfigService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.jdbc.internal.OracleTypes;

@Component("com.fuel.ws.classes.WsEditCardProfile")
public class WsEditCardProfile implements Wisher {

	@Autowired
	UserScreenService screenService;
	
	@Autowired
	UserLoginService userLoginService;

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
			case "FETCH_INPUT":
				response = fetchRequest(rm);
				break;
			case "FETCH":
				response = processRequest(rm);
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
			
			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String[] responseProc = AGFuelUtil.fuelAccountProcess(rm.getCorpId(), "CARD_PROFILE_EDIT", "LOV",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), "");

			FuelProcModelDetails mdl = new Gson().fromJson(responseProc[2], FuelProcModelDetails.class);
			
			ArrayList<KeyValueModel> card = new ArrayList<KeyValueModel>();
			
			Type cardlst = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			card = new Gson().fromJson(new Gson().toJson(mdl.getData().get("cardedit")), cardlst);
			
			

			if (card.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("card", card);
				o.put("screenId", screen.getScreenId());
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");
			} else {
				response.setCode("0001");
				response.setMessage("No Data Found.");
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	ResponseModel fetchRequest(RequestModel rm) {
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

	ResponseModel processRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String data = (rm.getAdditionalData().containsKey("data")) ? rm.getAdditionalData().get("data").toString()
					: null;
			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String[] responseProc = AGFuelUtil.fuelGetData(rm.getCorpId(), "CARD_EDIT", "GET_DATA",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), data);
//			String responseProc = "{\n"
//					+ "    \"data\": \n"
//					+ "    {\n"
//					+ "    \"category\":\"Postpaid\",\n"
//					+ "    \"cardtype\":\"Byco Corporate\",\n"
//					+ "    \"printingtype\":\"Byco Card\",\n"
//					+ "    \"account\":\"4047500-ALI & KHAN (PVT.) LTD-007\",\n"
//					+ "    \"company\":\"71100028-ALI & KHAN (PVT.) LTD-007\",\"profileData\":\"Anwar|Bhutto|Anwar Bhutto|F|13-Jan-2005|M|KHI|KARACHI|8734237583657|03323336707|anwar.bhutto@access.net.pk\"}}";
			String category = "";
			String cardtype = "";
			String printingtype = "";
			String company = "";
			String account = "";
			String profileData = "";

			if (responseProc[2].contains("data")) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					JsonNode jsonNode = objectMapper.readTree(responseProc[2]);
					category = jsonNode.get("data").get("category").asText();
					cardtype = jsonNode.get("data").get("cardtype").asText();
					printingtype = jsonNode.get("data").get("printingtype").asText();
					company = jsonNode.get("data").get("company").asText();
					account = jsonNode.get("data").get("account").asText();
					profileData = jsonNode.get("data").get("profileData").asText();
					response.setCode("0000");
					response.setMessage("Success.");

				} catch (Exception e) {
					e.printStackTrace();
				}
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("category", category);
				o.put("cardtype", cardtype);
				o.put("account", account);
				o.put("printingtype", printingtype);
				o.put("company", company);
				o.put("profileData", profileData);
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");

			} else {
				response.setCode("9999");
				response.setMessage("No Data Found.");
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
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

}
