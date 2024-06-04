package com.ag.loy.adm.service;

import com.ag.loy.adm.entity.CustomerMaster;

public interface CustomerMasterService {
	
	public CustomerMaster findAllByCidCorpId(String cid,String corpId);

}
