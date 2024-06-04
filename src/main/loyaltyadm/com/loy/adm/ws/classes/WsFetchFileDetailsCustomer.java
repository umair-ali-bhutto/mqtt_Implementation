package com.loy.adm.ws.classes;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.LoyCustomerFile;
import com.ag.loy.adm.service.LoyCustomerFileService;

@Component("com.loy.adm.ws.classes.WsFetchFileDetailsCustomer")
public class WsFetchFileDetailsCustomer implements Wisher {

	@Autowired
	LoyCustomerFileService customerFileService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			long id = (rm.getAdditionalData().containsKey("id"))
					? Long.parseLong(rm.getAdditionalData().get("id").toString())
					: 0l;

			LoyCustomerFile mdl = new LoyCustomerFile();
			mdl = customerFileService.fetchById(id);

			if (!Objects.isNull(mdl)) {
				if (!mdl.getStatus().equals("NEW")) {
					HashMap<Object, Object> obj = new HashMap<Object, Object>();
					obj.put("mdl", mdl);

					response.setCode("0000");
					response.setMessage("SUCCESS.");
					response.setData(obj);
				} else {
					response.setCode("0002");
					response.setMessage("Record Will be Processed.");
				}

			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}