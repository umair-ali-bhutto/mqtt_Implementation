package com.mportal.ws.classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.KeyValueModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.entity.DiscountBinProdConfig;
import com.ag.mportal.services.ChainMerchantsService;
import com.ag.mportal.services.DiscountBinProdConfigService;
import com.ag.mportal.services.DiscountMasterService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.mportal.ws.classes.WsCreateDiscount")
public class WsCreateDiscount implements Wisher {

	@Autowired
	UtilAccess utilAccess;

	@Autowired
	UserScreenService screenService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	DiscountBinProdConfigService discountBinProdConfigService;

	@Autowired
	ChainMerchantsService chainMerchantsService;

	@Autowired
	DiscountMasterService discountMasterService;

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
			case "FETCH_MID":
				response = fetchMid(rm);
				break;
			case "FETCH_TID_BY_MID":
				response = fetchTidByMid(rm);
				break;
			case "FETCH_PRODUCTS_BY_BIN":
				response = fetchProductsByBin(rm);
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

			HashMap<Object, Object> o = new HashMap<Object, Object>();
			o.put("screenId", screen.getScreenId());
			response.setData(o);
			response.setCode("0000");
			response.setMessage("Success.");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public ResponseModel fetchMid(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			UserLogin user = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			List<KeyValueModel> lst = fetchMerchantOptions(user);

			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("lst", lst);

				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Merchant Ids Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public List<KeyValueModel> fetchMerchantOptions(UserLogin usl) {
		List<KeyValueModel> skl = new ArrayList<KeyValueModel>();
		KeyValueModel mdl = null;
		if (usl.getMid().equalsIgnoreCase("all")) {
			List<ChainMerchant> cmh = chainMerchantsService.fetchAll();
			for (ChainMerchant c : cmh) {
				mdl = new KeyValueModel();
				mdl.setKey(c.getMid());
				mdl.setValue(c.getName() + " (" + c.getMid() + ")");
				skl.add(mdl);
			}
		} else {
			List<ChainMerchant> cmh = chainMerchantsService.fetchAllByID(usl.getMid());
			for (ChainMerchant c : cmh) {
				mdl = new KeyValueModel();
				mdl.setKey(c.getMid());
				mdl.setValue(c.getName() + " (" + c.getMid() + ")");
				skl.add(mdl);
			}
		}
		List<KeyValueModel> listWithoutDuplicates = skl.stream().distinct().collect(Collectors.toList());
		if (listWithoutDuplicates.size() == 0) {
			mdl = new KeyValueModel();
			mdl.setKey(usl.getUserCode());
			mdl.setValue(usl.getUserName() + " (" + usl.getUserCode() + ")");
			listWithoutDuplicates.add(mdl);
		}
		return listWithoutDuplicates;

	}

	public ResponseModel fetchTidByMid(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String mid = (String) rm.getAdditionalData().get("mid");
			List<KeyValueModel> midList = new ArrayList<KeyValueModel>();
			Type keyValueType = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			midList = new Gson().fromJson(mid, keyValueType);

			List<List<String>> allTids = new ArrayList<>();
			for (KeyValueModel mdl : midList) {
				List<String> tids = discountMasterService.fetchTidsOnMerchantId(mdl.getKey());
				if (tids.size() != 0) {
					allTids.add(tids);
				}
			}

			List<String> finalTids = allTids.stream().flatMap(List::stream).collect(Collectors.toList());

//			tids.add("12345678 (" + midList.get(0).getKey() + ")");
//			tids.add("87654321 (" + midList.get(0).getKey() + ")");
//			tids.add("98000005 (" + midList.get(0).getKey() + ")");

			if (finalTids.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("tids", finalTids);

				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Terminal Ids Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public ResponseModel fetchProductsByBin(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String fromBin = rm.getAdditionalData().get("fromBin").toString();
			String toBin = rm.getAdditionalData().get("toBin").toString();

			List<DiscountBinProdConfig> lst = discountBinProdConfigService.fetchByBin(fromBin, toBin, rm.getCorpId());

			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("lst", lst);

				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Products Found");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}