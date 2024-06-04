package com.ag.mportal.services;

import com.ag.mportal.entity.ConfigEcrRouting;

public interface ConfigEcrRoutingService {

	public void insertRouting(ConfigEcrRouting tcn);
	
	public ConfigEcrRouting fetchByMidTid(String mid, String tid,String callType);
	public ConfigEcrRouting fetchByID(long id);
}
