package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ReconLog;

public interface ReconLogService {
	public void insert(ReconLog mdl);

	public void update(ReconLog mdl);

	public List<ReconLog> fetchAll();
}
