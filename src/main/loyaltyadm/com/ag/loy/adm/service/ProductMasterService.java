package com.ag.loy.adm.service;

import java.util.HashMap;
import java.util.List;

import com.ag.loy.adm.entity.ProductMaster;

public interface ProductMasterService {

	public void saveProductMaster(ProductMaster sdt);
	
	public void updateProductMaster(ProductMaster sdt);

	public ProductMaster fetchAllRecordById(String id,String corpId);

	public HashMap<Object, Object> fetchAllRecordMap(String corpId);
	
	public List<ProductMaster> fetchAllRecord(String corpId);
	
	public String fetchMaxId();

}