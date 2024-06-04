package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ComplChannelConfig;

public interface ComplChannelConfigService {
	
	public long insert(ComplChannelConfig tcn);
	public ComplChannelConfig fetchByID(ComplChannelConfig tcn);
	public List<ComplChannelConfig> fetchByOtherParams(ComplChannelConfig tcn);
	public List<String> fetchAll();
	public List<ComplChannelConfig> fetchAllReq();
	public List<ComplChannelConfig> fetchAllByID(long id);
	public void update(ComplChannelConfig id);

}