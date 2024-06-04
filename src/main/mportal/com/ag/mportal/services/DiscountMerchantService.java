package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DiscountMerchant;

public interface DiscountMerchantService {

	public void insert(DiscountMerchant tcn);

	public void update(DiscountMerchant tcn);

	public List<DiscountMerchant> getMerchantByDiscountId(List<Long> lstDiscountId);

	public List<DiscountMerchant> fetchAllByDiscId(long discId);

	public DiscountMerchant fetchByDiscIdAndMid(long discId, String mid);

	public void deleteByDiscId(long discId);
}
