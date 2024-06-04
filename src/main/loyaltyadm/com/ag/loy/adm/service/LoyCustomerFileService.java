package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.LoyCustomerFile;

public interface LoyCustomerFileService {
	
	public void insert(LoyCustomerFile lcf);
	public void update(LoyCustomerFile lcf);
	public LoyCustomerFile fetchByName(String fileName);
	public List<LoyCustomerFile> fetchAllByCorpId(String corpId);
	public List<LoyCustomerFile> fetchAll();
	public LoyCustomerFile fetchById(long id);

}
