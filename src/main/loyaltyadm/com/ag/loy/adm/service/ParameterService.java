package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.Parameter;


public interface ParameterService {

	public void insert(Parameter acm);
	public void update(Parameter acm);
	public List<Parameter> fetchAllByCorpId(String corpId);	

}