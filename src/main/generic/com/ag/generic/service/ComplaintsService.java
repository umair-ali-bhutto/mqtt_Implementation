package com.ag.generic.service;

import java.util.Date;
import java.util.List;

import com.ag.generic.entity.Complaint;

public interface ComplaintsService {

	public long insertComplaint(Complaint rxn) throws Exception;

	public void updateComplaint(Complaint rxn);

	public Complaint fetchComplaintById(int complaintId);

	public Complaint fetchComplaintById(int complaintId, String mid, String tid);

	public List<Complaint> fetchAllComplaint(String merchant, String terminal, Date from, Date to, String category,
			String region, String city, int assignedTo,int groupCode);

	public List<Complaint> fetchAllComplaintOnlyNew();


	public List<Complaint> fetchAllComplaintNotClosed();
	public List<Complaint> fetchAllComplaints(String merchant,String terminal, Date from, Date to,String category,String assignedTo,String launchedBy);
	
	public List<Complaint> fetchAllFuelComplaints(String userCode,String complType,String status,int complId, Date from, Date to,String category,String assignedTo,String launchedBy);
	
}
