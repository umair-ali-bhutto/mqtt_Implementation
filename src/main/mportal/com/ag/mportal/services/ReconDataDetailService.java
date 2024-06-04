package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ReconDataDetail;

public interface ReconDataDetailService {
	public void insert(ReconDataDetail mdl);

	public void update(ReconDataDetail mdl);

	public List<ReconDataDetail> fetchAll();
}
