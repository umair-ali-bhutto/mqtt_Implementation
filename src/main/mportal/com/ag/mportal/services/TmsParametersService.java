package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.TmsParameters;
import com.ag.mportal.entity.TmsParametersDefaultValues;


public interface TmsParametersService
{
	public List<TmsParameters> fetchByTid(String tid);
	public void insert(List<TmsParameters> tms);
	public List<TmsParametersDefaultValues> fetchAllDefaultParameters();
	public void update(List<TmsParameters> tms);
	
}
