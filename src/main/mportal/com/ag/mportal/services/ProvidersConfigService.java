package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ProvidersConfig;

public interface ProvidersConfigService {

	public Long save(ProvidersConfig sdt);

	public ProvidersConfig fetchAllRecordById(long id);

	public List<ProvidersConfig> fetchAllRecordById(String mid, String tid, String serialNumber, String type);
	public List<ProvidersConfig> fetchAllRecordByFilter(String mid, String tid, String donationAccount,String status);
	public List<ProvidersConfig> fetchAllRecord();

	public void update(ProvidersConfig sdt);
}
