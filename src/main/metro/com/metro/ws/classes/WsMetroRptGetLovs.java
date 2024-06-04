package com.metro.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.metro.model.GenericLovModel;
import com.ag.metro.model.VwMetroRepParametersModel;
import com.ag.metro.services.impl.MetroUtilServiceImpl;

@Component("com.metro.ws.classes.WsMetroRptGetLovs")
public class WsMetroRptGetLovs implements Wisher {
	
	@Autowired
	MetroUtilServiceImpl metroUtilService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			List<VwMetroRepParametersModel> lst =metroUtilService.getLovsOfMetro(); 
			
			if(lst.size()!=0) {

				HashMap<Object, Object> mpk = new HashMap<Object, Object>();
				response.setCode("0000");
				response.setMessage("SUCCESS");
				mpk.put("storeNameModel", getStoreName(lst));
				mpk.put("reportTypeModel", getReportType(lst));
				mpk.put("cardTypeModel", getCardType(lst));
				mpk.put("reportNameModel", getReportName(lst));
				mpk.put("batchNumModel", getBacthNumList(lst));
				response.setData(mpk);
			}else {
				response.setCode("0001");
				response.setMessage("NO LOVS FOUND.");
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
	
	
	private List<GenericLovModel> getStoreName(List<VwMetroRepParametersModel> lstm) {
		List<GenericLovModel> lst = new ArrayList<GenericLovModel>();
		for(VwMetroRepParametersModel m:lstm) {
			if(m.getTYPE().equals("StoreName")) {
				GenericLovModel g = new GenericLovModel();
				g.setId(m.getA());
				g.setValue(m.getB());
				lst.add(g);
			}
		}
		return lst;
	}
	
	
	private List<GenericLovModel> getReportType(List<VwMetroRepParametersModel> lstm) {
		List<GenericLovModel> lst = new ArrayList<GenericLovModel>();
		for(VwMetroRepParametersModel m:lstm) {
			if(m.getTYPE().equals("ReportType")) {
				GenericLovModel g = new GenericLovModel();
				g.setId(m.getA());
				g.setValue(m.getB());
				lst.add(g);
			}
		}
		return lst;
	}
	
	
	private List<GenericLovModel> getCardType(List<VwMetroRepParametersModel> lstm) {
		List<GenericLovModel> lst = new ArrayList<GenericLovModel>();
		for(VwMetroRepParametersModel m:lstm) {
			if(m.getTYPE().equals("CardType")) {
				GenericLovModel g = new GenericLovModel();
				g.setId(m.getA());
				g.setValue(m.getB());
				lst.add(g);
			}
		}
		return lst;
	}
	
	
	private List<GenericLovModel> getReportName(List<VwMetroRepParametersModel> lstm) {
		List<GenericLovModel> lst = new ArrayList<GenericLovModel>();
		for(VwMetroRepParametersModel m:lstm) {
			if(m.getTYPE().equals("ReportName")) {
				GenericLovModel g = new GenericLovModel();
				g.setId(m.getA());
				g.setValue(m.getB());
				lst.add(g);
			}
		}
		return lst;
	}
	
	
	private List<GenericLovModel> getBacthNumList(List<VwMetroRepParametersModel> lstm) {
		List<GenericLovModel> lst = new ArrayList<GenericLovModel>();
		for(VwMetroRepParametersModel m:lstm) {
			if(m.getTYPE().equals("BatchNo")) {
				GenericLovModel g = new GenericLovModel();
				g.setId(m.getA());
				g.setValue(m.getB());
				lst.add(g);
			}
		}
		return lst;
	}

}