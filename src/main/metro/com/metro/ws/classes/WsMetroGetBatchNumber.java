package com.metro.ws.classes;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.metro.services.MetroBatchSummaryService;

@Component("com.metro.ws.classes.WsMetroGetBatchNumber")
public class WsMetroGetBatchNumber implements Wisher {

	@Autowired
	MetroBatchSummaryService metroBatchSummaryService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED -- " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String s = metroBatchSummaryService.getBatchNumber();
			if (!s.equals("N/A")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				response.setCode("0000");
				response.setMessage("SUCCESS");

				obj.put("batchNumber", s);
				obj.put("systdateTime", sdf.format(new java.util.Date()));
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("ERROR IN GETTING BATCH NUMBER.");
			}

		} catch (Exception ex) {

			response.setCode("9999");
			response.setMessage("SERVICE ERROR");
		}

		return response;

	}
}
