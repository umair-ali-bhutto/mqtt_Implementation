package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DevAdvMaster;

public interface DevAdvMasterService {
	public List<DevAdvMaster> fetchAll();
	
	public long fetchAdvId();

	public boolean insert(DevAdvMaster DevAdvMaster);

	public void update(DevAdvMaster DevAdvMaster);
}
