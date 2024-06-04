package com.generic.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.entity.DocumentConfig;
import com.ag.generic.service.DocumentConfigService;

@Component("com.generic.ws.classes.WsDocumentation")
public class WsDocumentation implements Wisher {

	@Autowired
	DocumentConfigService configService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String corpId = (rm.getAdditionalData().containsKey("corpId"))
					? rm.getAdditionalData().get("corpId").toString()
					: null;

			String userGroup = (rm.getAdditionalData().containsKey("userGroup"))
					? rm.getAdditionalData().get("userGroup").toString()
					: null;

			List<DocumentConfig> lst = configService.fetchAllByGroupCode(corpId, Integer.parseInt(userGroup));

			HashMap<Object, Object> obj = new HashMap<Object, Object>();

			obj.put("lst", lst);

			response.setData(obj);
			response.setCode("0000");
			response.setMessage("Success.");

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}
