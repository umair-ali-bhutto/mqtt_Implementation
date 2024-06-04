package com.loy.adm.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.DebugLog;
import com.ag.loy.adm.service.DebugLogService;

@Component("com.loy.adm.ws.classes.WsDebugLog")
public class WsDebugLog implements Wisher {

	@Autowired
	DebugLogService debugLogService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String frmDate = (rm.getAdditionalData().containsKey("frmDate"))
					? rm.getAdditionalData().get("frmDate").toString()
					: null;

			String toDate = (rm.getAdditionalData().containsKey("toDate"))
					? rm.getAdditionalData().get("toDate").toString()
					: null;

			String debugMessage = (rm.getAdditionalData().containsKey("debugMessage"))
					? rm.getAdditionalData().get("debugMessage").toString()
					: null;

			List<DebugLog> mstr = debugLogService.fetchAllByCorpId(frmDate, toDate, debugMessage);
			if (mstr != null) {

				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("list", mstr);
				response.setCode("0000");
				response.setMessage("SUCCESS");
			} else {
				response.setCode("0001");
				response.setMessage("No Awards List Found.");
			}
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}
