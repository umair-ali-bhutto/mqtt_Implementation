package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.CalenderMaster;

public interface CalenderMasterService {
	public CalenderMaster fetchAllRecordById(String id, String corpId);

	public List<CalenderMaster> fetchAllRecord(String corpId);

}