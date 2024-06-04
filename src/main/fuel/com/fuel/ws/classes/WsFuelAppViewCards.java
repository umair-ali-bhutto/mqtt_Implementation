package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.model.FuelStationModel;
import com.ag.fuel.model.KeyValueModel;
import com.ag.fuel.model.ViewAllCardsModel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.fuel.ws.classes.WsFuelAppViewCards")
public class WsFuelAppViewCards implements Wisher {

	@Autowired
	UserLoginService loginService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		ArrayList<ViewAllCardsModel> cardsList = new ArrayList<ViewAllCardsModel>();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());

		try {

			UserLogin user = loginService.validetUserid(Integer.parseInt(rm.getUserid()));

			if (user != null) {

				String responseProc = DBProceduresFuel.fuelAppProcess(user.getCorpId(), "list_linked_cards",
						user.getUserId(), user.getUserCode(), null, user.getGroupCode(), null);

				HashMap<Object, Object> resp = new HashMap<Object, Object>();

				if (responseProc.equals("0001")) {
					response.setCode("0001");
					response.setMessage("No Cards Found.");

				} else {

					FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);

					if (mdl.getCode().equals("0000")) {

						Type viewAllCardsModelListType = new TypeToken<List<ViewAllCardsModel>>() {
						}.getType();
						cardsList = new Gson().fromJson(new Gson().toJson(mdl.getData().get("lst")),
								viewAllCardsModelListType);

						map.put("lst", cardsList);

						response.setData(map);
						response.setCode("0000");
						response.setMessage("SUCCESS.");

					} else {
						response.setCode("0001");
						response.setMessage("No Cards Found.");

					}

				}

			} else {
				response.setCode("9991");
				response.setMessage("Cannot Validate User.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something went wrong.");
		}

		return response;
	}

}
