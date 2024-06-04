package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.CustomerStatusMaster;

public interface CustomerStatusMasterService {
	
	public List<CustomerStatusMaster> fetchAllByCorpIdCusttype(String corpId,String custtype);
}