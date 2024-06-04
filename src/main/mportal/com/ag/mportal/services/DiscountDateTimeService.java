package com.ag.mportal.services;

import java.sql.Timestamp;
import java.util.List;

import com.ag.mportal.entity.DiscountDateTime;

public interface DiscountDateTimeService {

	public void insert(DiscountDateTime tcn);

	public void update(DiscountDateTime tcn);

	public List<DiscountDateTime> fetchByDisc(long discId);

	public DiscountDateTime fetchByTimestamp(long discId, Timestamp transactionTime);

	public void deleteByDiscId(long discId);
}
