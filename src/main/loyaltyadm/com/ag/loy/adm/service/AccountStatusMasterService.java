package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.AccountStatusMaster;


public interface AccountStatusMasterService {

	public List<AccountStatusMaster> fetchAllByCorpIdAcctype(String corpId,String acctype);
}
