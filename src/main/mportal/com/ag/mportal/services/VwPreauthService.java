package com.ag.mportal.services;

import java.util.Date;
import java.util.List;

import com.ag.mportal.entity.VwPreauth;

public interface VwPreauthService {
	
	public List<VwPreauth> fetchAll();
	
	public List<VwPreauth> fetchByParams(List<String> merchant,Date from, Date to,String auth, String status, int numberOfRows, int pageNumber);
	
	public String fetchByParamsCount(List<String> merchant,Date from, Date to,String auth, String status);
	
	

}
