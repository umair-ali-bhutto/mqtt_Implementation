package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ReconConfigDetail;

public interface ReconConfigDetailService {
	public void insert(ReconConfigDetail mdl);

	public void update(ReconConfigDetail mdl);

	public List<ReconConfigDetail> fetchAll();
	
	public List<ReconConfigDetail> fetchByConfigId(long reconConfigId);
}
