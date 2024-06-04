package com.generic.ws.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresGeneric;
import com.ag.db.proc.GenericDbProcModel;
import com.ag.generic.model.ReportFilterModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.generic.ws.classes.WsGenericFetchFiltersReport")
public class WsGenericFetchFiltersReport implements Wisher {

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
			String reportId = (rm.getAdditionalData().containsKey("reportId"))
					? rm.getAdditionalData().get("reportId").toString()
					: null;

			GenericDbProcModel result = DBProceduresGeneric.fetchGenericReportData("F", reportId, userId, corpId, groupCode);

			if (result.getVrsp_code().equals("0000")) {

				String json = result.getVmessage();

				TypeToken<List<ReportFilterModel>> typeToken = new TypeToken<List<ReportFilterModel>>() {
				};
				List<ReportFilterModel> filterList = new Gson().fromJson(json, typeToken.getType());

				if (filterList.size() != 0) {

					boolean containsDuplicateParams = containsDuplicateParams(filterList);
 
					if (!containsDuplicateParams) {
						HashMap<Object, Object> obj = new HashMap<Object, Object>();
						obj.put("filterList", filterList);

						response.setData(obj);
						response.setCode("0000");
						response.setMessage("Success.");
					} else {
						response.setCode("0003");
						response.setMessage("Record Contains Duplicate Params.");
					}

				} else {
					response.setCode("0002");
					response.setMessage("No Record Found.");
				}

			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public boolean containsDuplicateParams(List<ReportFilterModel> filterList) {
		HashSet<String> reportParams = new HashSet<String>();
		for (ReportFilterModel filter : filterList) {
			String reportParamName = filter.getReportParamName();
			if (reportParams.contains(reportParamName)) {
				return true;
			}
			reportParams.add(reportParamName);
		}
		return false;
	}
}
