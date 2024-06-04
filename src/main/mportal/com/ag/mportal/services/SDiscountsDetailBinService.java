package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.SDiscountsDetailBin;


public interface SDiscountsDetailBinService
{
	public void insert(List<SDiscountsDetailBin> lstDetailBin,Integer discId);
	
	public List<SDiscountsDetailBin> fetchListBinByDiscountId(List<String> lstDiscountId) ;
	
	public boolean deleteRecordByDiscountId(String discountId) ;
	
}
