package com.ag.loy.adm.service;

import java.util.HashMap;
import java.util.List;

import com.ag.loy.adm.entity.TransactionTypeMaster;

public interface TransactionTypeMasterService {

	public void saveTxnTypeMaster(TransactionTypeMaster sdt);

	public TransactionTypeMaster fetchAllRecordById(String id,String corpId);

	public HashMap<Object, Object> fetchAllRecordMap(String corpId);
	
	public List<TransactionTypeMaster> fetchAllRecord(String corpId);

}