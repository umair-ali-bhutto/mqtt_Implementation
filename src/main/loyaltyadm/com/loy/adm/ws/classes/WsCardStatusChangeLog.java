package com.loy.adm.ws.classes;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.UserStatusLog;
import com.ag.loy.adm.service.UserStatusLogService;

@Component("com.loy.adm.ws.classes.WsCardStatusChangeLog")
public class WsCardStatusChangeLog implements Wisher {

	@Autowired
	UserStatusLogService logService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String corpId = (rm.getAdditionalData().containsKey("corpid"))
					? rm.getAdditionalData().get("corpid").toString()
					: null;

			String cardNumber = (rm.getAdditionalData().containsKey("cardNumber"))
					? rm.getAdditionalData().get("cardNumber").toString()
					: null;

			UserStatusLog usl = logService.fetchByCardNo(corpId, cardNumber);

			if (usl != null) {
				HashMap<Object, Object> okj = new HashMap<Object, Object>();

				okj.put("record", usl);

				response.setCode("0000");
				response.setMessage("Success.");
				response.setData(okj);
			} else {
				response.setCode("0003");
				response.setMessage("No Record Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
