package com.ag.generic.util;

import java.sql.Timestamp;

import com.ag.generic.entity.ComplCategory;
import com.ag.generic.entity.Complaint;
import com.ag.generic.model.ComplainDescriptionModel;
import com.ag.generic.prop.AppProp;
import com.ag.mportal.entity.QueueLog;
import com.google.gson.Gson;

public class AssignmentUtil {

	public Complaint doInsertAssignment(String desc, String mid, String tid,ComplCategory ctd,String corpId) {
		Complaint c = new Complaint();
		c.setAssignedDate(null);
		c.setAssignedTo(null);
		c.setClosureDate(null);
		c.setDescription(desc);
		c.setEntryBy(getUserIdFromCorpId(corpId));
		c.setEntryDate(new Timestamp(new java.util.Date().getTime()));
		c.setMaker("N/A");
		c.setMid(mid);
		c.setModel("N/A");

		if (ctd != null) {
			c.setCategory(ctd.getCode());
			c.setSubType(ctd.getSubTypeCode());
			c.setType(ctd.getTypeCode());
		}

		c.setSerialNumber("N/A");
		c.setStatus("NEW");
		c.setTid(tid);
		c.setComplaintType("N/A");
		c.setComplaintSubType("N/A");
		
		return c;
	}

	public static String doConvertToJSONUser(QueueLog qs) {
		Gson gson = new Gson();
		String json = gson.toJson(qs);
		return json;
	}

	
	public static String doConvertToJSONComplaint(Complaint qs) {
		Gson gson = new Gson();
		String json = gson.toJson(qs);
		return json;
	}
	public static String doConvertToJSONComplaintDescription(ComplainDescriptionModel qs) {
		Gson gson = new Gson();
		String json = gson.toJson(qs);
		return json;
	}
	
	
	String getUserIdFromCorpId(String corpId) {
		int k = 0;
		AgLogger.logDebug("DEFAULT CORP ID", corpId);
		String prop = AppProp.getProperty("reg.get.entryby.from.corpid");
		String[] br = prop.split(",");
		for(String s:br) {
			String[] m = s.split(":");
			if(m[0].equals(corpId)) {
				return m[1];
			}
		}
		return k+"";
	}

}
