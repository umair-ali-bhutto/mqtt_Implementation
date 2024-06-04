package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DiscountLog;

public interface DiscountLogService {
	public long insert(DiscountLog discountLog);

	public void update(DiscountLog discountLog);

	public List<DiscountLog> fetchAll();

	public List<DiscountLog> fetchAllByCid(String cid);

	public DiscountLog getById(long recId);

	public int fetchTxnCountPerDay(String cid, long discId);

	public int fetchTxnCountPerWeek(String cid, long discId, String startDate, String endDate);

	public int fetchTxnCountPerMonth(String cid, long discId);

	public int fetchTxnCountPerYear(String cid, long discId);

}
