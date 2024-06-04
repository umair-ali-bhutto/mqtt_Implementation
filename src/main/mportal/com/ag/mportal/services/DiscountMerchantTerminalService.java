package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DiscountMerchantTerminal;

public interface DiscountMerchantTerminalService {

	public void insert(DiscountMerchantTerminal tcn);

	public void update(DiscountMerchantTerminal tcn);

	public DiscountMerchantTerminal fetchByDiscountIdAndTid(long discId, long discMidId, String terminalId);

	public List<DiscountMerchantTerminal> fetchByDiscId(long discId);

	public void deleteByDiscId(long discId);
}
