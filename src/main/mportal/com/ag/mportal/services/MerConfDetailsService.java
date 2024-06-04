package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.BuildControlConfig;
import com.ag.mportal.entity.MerConfDetails;

public interface MerConfDetailsService {

	public long insert(MerConfDetails tcn);
	public void update(MerConfDetails tcn);
	public List<MerConfDetails> fetchAllById(Long ids);
	public List<BuildControlConfig> fetchRecById(Long ids);
	public void delete(long ids);
	public List<MerConfDetails> fetchAllByRecId(long recId);
}
