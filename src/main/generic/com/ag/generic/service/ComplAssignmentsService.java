package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ComplAssignment;


public interface ComplAssignmentsService  {
	
	public long insert(ComplAssignment id);
	public ComplAssignment fetchByID(int tcn);
	public ComplAssignment fetchByOtherParams(long id);
	public List<ComplAssignment> fetchAll();
	public List<ComplAssignment> fetchAllByID(int id);
	public void update(ComplAssignment id);
	public List<ComplAssignment> fetchAllByIDByCategory(int id, String category, String type, String subType);
	public ComplAssignment fetchByComplID(int compId);
	public ComplAssignment fetchRecordHavingProcByAndProcDateNull(int tcn);

}