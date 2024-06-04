package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ReconConfig;

public interface ReconConfigService {
	public void insert(ReconConfig mdl);

	public void update(ReconConfig mdl);

	public List<ReconConfig> fetchAll();
}
