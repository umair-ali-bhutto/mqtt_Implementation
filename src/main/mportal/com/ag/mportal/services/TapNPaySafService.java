package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.TapNPaySaf;

public interface TapNPaySafService {
	
	public void insert(TapNPaySaf tcn);
	
	public void update(TapNPaySaf tcn);
	
	public List<TapNPaySaf> fetchAll();

}