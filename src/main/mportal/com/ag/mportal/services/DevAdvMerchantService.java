package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DevAdvMerchant;

public interface DevAdvMerchantService {
	public List<DevAdvMerchant> fetchAll();

	public boolean insert(DevAdvMerchant DevAdvMerchant);

	public void update(DevAdvMerchant DevAdvMerchant);
}
