package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ComplStatus;

public interface ComplStatusService {
	

	public long insert(ComplStatus tcn);
	public ComplStatus fetchByID(ComplStatus tcn);
	public ComplStatus fetchByOtherParams(ComplStatus tcn);
	public List<ComplStatus> fetchAll();
	public List<ComplStatus> fetchAllByID(long id);
	public void update(ComplStatus id);
	

}