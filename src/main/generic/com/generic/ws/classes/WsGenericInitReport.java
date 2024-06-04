package com.generic.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresGeneric;
import com.ag.db.proc.GenericDbProcModel;
import com.ag.generic.model.ReportLovModel;
import com.ag.generic.model.ReportLovModel.Report;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.generic.ws.classes.WsGenericInitReport")
public class WsGenericInitReport implements Wisher {

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String userId = rm.getUserid();
			String corpId = rm.getCorpId();
			String groupCode = (rm.getAdditionalData().containsKey("groupCode"))
					? rm.getAdditionalData().get("groupCode").toString()
					: null;

			GenericDbProcModel result = DBProceduresGeneric.fetchGenericReportData("L", "0", userId, corpId, groupCode);

			if (result.getVrsp_code().equals("0000")) {
				String json = result.getVmessage();

				TypeToken<List<ReportLovModel>> typeToken = new TypeToken<List<ReportLovModel>>() {};
				List<ReportLovModel> reportItems = new Gson().fromJson(json, typeToken.getType());

				ReportLovModel reportItem = reportItems.get(0); // Assuming there's only one item in the array

				List<Report> reportList = reportItem.getReportList();

				HashMap<Object, Object> obj = new HashMap<Object, Object>();

				obj.put("reportList", reportList);

				response.setData(obj);
				response.setCode("0000");
				response.setMessage("Success.");
			} else {
				response.setCode("0002");
				response.setMessage("No Record Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}
