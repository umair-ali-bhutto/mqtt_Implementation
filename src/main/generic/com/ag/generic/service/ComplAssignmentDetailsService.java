package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ComplAssignmentDetail;


public interface ComplAssignmentDetailsService{

	public long insert(ComplAssignmentDetail tcn);
	public ComplAssignmentDetail fetchByID(ComplAssignmentDetail tcn);
	public ComplAssignmentDetail fetchByOtherParams(ComplAssignmentDetail tcn);
	public List<ComplAssignmentDetail> fetchAll();
	public List<ComplAssignmentDetail> fetchAllByID(long id);
	public void update(ComplAssignmentDetail id);

}