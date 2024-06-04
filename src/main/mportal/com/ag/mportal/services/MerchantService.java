package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.Merchant;

public interface MerchantService {
	

	public long insert(Merchant tcn);
	public Merchant fetchByID(Merchant tcn);
	public Merchant fetchByOtherParams(Merchant tcn);
	public List<Merchant> fetchAll();
	public List<Merchant> fetchAllByID(long id);
	public void update(Merchant id);

}