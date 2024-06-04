package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ComplCategory;



public interface ComplCategoryService{
	
	public long insert(ComplCategory tcn);
	public ComplCategory fetchByID(ComplCategory tcn);
	public ComplCategory fetchByOtherParams(ComplCategory tcn);
	public List<ComplCategory> fetchAll();
	public List<ComplCategory> fetchAllByID(int id);
	public void update(ComplCategory id);
	

}