package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ComplState;



public interface ComplStateService{
	
	
	public long insert(ComplState tcn);
	public ComplState fetchByID(ComplState tcn);
	public ComplState fetchByOtherParams(ComplState tcn);
	public List<ComplState> fetchAll();
	public List<ComplState> fetchAllByID(long id);
	public void update(ComplState id);

}