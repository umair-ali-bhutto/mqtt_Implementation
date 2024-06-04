package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.DebugLog;


public interface DebugLogService {

	public List<DebugLog> fetchAllByCorpId(String fromDateTime,String toDateTime,String debugMessage);
	
	

}