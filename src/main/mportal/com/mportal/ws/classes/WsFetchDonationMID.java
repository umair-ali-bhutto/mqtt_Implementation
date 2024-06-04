package com.mportal.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.KeyValueModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.ProvidersConfig;
import com.ag.mportal.services.OfflineSaleService;
import com.ag.mportal.services.impl.ProvidersConfigServiceImpl;

@Component("com.mportal.ws.classes.WsFetchDonationMID")
public class WsFetchDonationMID implements Wisher {

	@Autowired
	ProvidersConfigServiceImpl providersConfigServiceImpl;

	@Autowired
	UtilService utilService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());

		ResponseModel response = new ResponseModel();

		try {
			
			List<ProvidersConfig> data = providersConfigServiceImpl.fetchAllRecord();
			List<KeyValueModel> strList = new ArrayList<KeyValueModel>();
			for(ProvidersConfig p:data) {
				KeyValueModel m = new KeyValueModel();
				m.setKey(p.getTxnMid());
				m.setValue(p.getAccountTitle());
				strList.add(m);
			}
			
			if (strList.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("lst", strList);
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");
			} else {
				response.setCode("0001");
				response.setMessage("No Data Found.");
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}