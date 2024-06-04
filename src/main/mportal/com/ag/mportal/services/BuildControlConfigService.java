package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.BuildControlConfig;

public interface BuildControlConfigService {

	public long insert(BuildControlConfig tcn);
	public void update(BuildControlConfig tcn);
	public List<BuildControlConfig> fetchAllByModel(String controlModel);
	public List<BuildControlConfig> getBuildControlConfigsByUserIds(List<Long> ids);
	
	}
