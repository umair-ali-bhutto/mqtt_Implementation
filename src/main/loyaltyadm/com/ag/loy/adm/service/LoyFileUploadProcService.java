package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.LoyFileUploadProc;

public interface LoyFileUploadProcService {
	
	public List<LoyFileUploadProc> FetchAllByCorpId(String corpId,String type);

}
