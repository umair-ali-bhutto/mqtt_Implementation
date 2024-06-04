package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ReconLock;

public interface ReconLockService {
	public void insert(ReconLock mdl);

	public void update(ReconLock mdl);

	public void createLock(long configId);

	public void releaseLock(long configId);

	public List<ReconLock> fetchAll();

	public List<Long> fetchConfigIds();

	public ReconLock fetchByConfigId(long configId);
}
