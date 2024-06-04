package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.SDiscountsDetailSlab;


public interface SDiscountsDetailSlabService
{
	public void insert(SDiscountsDetailSlab detailSlab, Integer discountId);
	public List<SDiscountsDetailSlab> getSlabByDiscountId(List<String> lstDiscountId);
	public boolean update(SDiscountsDetailSlab sDiscountsDetailSlab);
	
}
