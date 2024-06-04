package com.generic.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.MakerCheckerConfigService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsFetchMakerCheckerConfigList")
public class WsFetchMakerCheckerConfigList implements Wisher {
	@Autowired
	MakerCheckerConfigService makerCheckerConfig;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String id = AppProp.getProperty("maker.approver.group.id");

			String checkerId = AppProp.getProperty("checker.screen.id");

			List<MakerCheckerConfig> lst = makerCheckerConfig.fetchByCorpId(rm.getCorpId());
			if (lst.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("lst", lst);
				o.put("id", id);
				o.put("checkerId", checkerId);
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");
			} else {
				response.setCode("0002");
				response.setMessage("No Records Found.");
			}

		} catch (Exception e) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
