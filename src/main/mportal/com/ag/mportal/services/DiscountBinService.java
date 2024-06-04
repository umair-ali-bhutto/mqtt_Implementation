package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DiscountBin;

public interface DiscountBinService {

	public void insert(DiscountBin tcn);

	public void update(DiscountBin tcn);

	public List<DiscountBin> getBinByDiscountId(List<Long> lstDiscountId);

	public List<DiscountBin> fetchAllByDiscId(long discId);

	public DiscountBin fetchAllByBinAndProductId(String bin, String productId, long discId);
	
	public void deleteByDiscId(long discId);
}
