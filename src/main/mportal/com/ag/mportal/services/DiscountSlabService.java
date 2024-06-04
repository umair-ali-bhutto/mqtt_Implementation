package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DiscountSlab;

public interface DiscountSlabService {

	public void insert(DiscountSlab tcn);
	public void update(DiscountSlab tcn);
	public List<DiscountSlab> getSlabByDiscountId(List<Long> lstDiscountId);
	public DiscountSlab fetchByDiscId(long discId);
}
