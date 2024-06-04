package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.TxnLogsDetail;

public interface TxnLogsDetailService {

	
	public long insertLog(List<TxnLogsDetail> que) throws Exception;
}
