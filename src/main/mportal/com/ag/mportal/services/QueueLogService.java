package com.ag.mportal.services;

import com.ag.mportal.entity.QueueLog;

public interface QueueLogService {

	
	public long insertLog(QueueLog que) throws Exception;
	public QueueLog fetchQueueLogDetails(String mId);
	public void update(QueueLog que);
}
