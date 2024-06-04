package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.EcrSaf;

public interface EcrSafService {

	public long insert(EcrSaf tcn);
	public List<EcrSaf> fetchAllSaf();
	public void update(EcrSaf tcn); 
}
