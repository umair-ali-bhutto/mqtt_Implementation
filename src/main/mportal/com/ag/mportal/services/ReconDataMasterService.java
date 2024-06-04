package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ReconDataMaster;

public interface ReconDataMasterService {
	public long insert(ReconDataMaster mdl);

	public void update(ReconDataMaster mdl);

	public List<ReconDataMaster> fetchAll();

	public ReconDataMaster fetchById(long masterId);
}
