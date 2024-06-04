package com.ag.mportal.services;

import com.ag.mportal.entity.TapNPayBatchConfig;

public interface TapNPayBatchConfigService {

	public TapNPayBatchConfig fetchBatchConfig(String mid,String tid);
	
	public void updateBatch(TapNPayBatchConfig tcf);
}
