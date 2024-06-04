package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ComplAssignmentSetup;

public interface ComplAssignmentsSetupService {
	

	public long insert(ComplAssignmentSetup tcn);
	public ComplAssignmentSetup fetchByID(long tcn);
	public ComplAssignmentSetup fetchByOtherParams(String category,String type,String subType, int level);
	public List<ComplAssignmentSetup> fetchAll();
	public List<ComplAssignmentSetup> fetchAllByID(long id);
	public void update(ComplAssignmentSetup id);
	public ComplAssignmentSetup fetchRecordByOtherParams(String category,String type,String subType,int level);


}