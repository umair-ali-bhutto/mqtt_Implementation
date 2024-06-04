package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DevAdvDetail;

public interface DevAdvDetailService {
	public List<DevAdvDetail> fetchAll();

	public boolean insert(DevAdvDetail DevAdvDetail);

	public void update(DevAdvDetail DevAdvDetail);

}
