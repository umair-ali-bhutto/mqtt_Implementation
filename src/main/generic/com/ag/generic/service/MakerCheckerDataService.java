package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.MakerCheckerData;

public interface MakerCheckerDataService{
	
	
	public void update(MakerCheckerData upd);
	public void insert(MakerCheckerData upd);
	public MakerCheckerData fetchByID(long id);
	public MakerCheckerData fetchByUniqueIdentifier(Long screenId,String identifier,String identifier2,String identifier3);
	public MakerCheckerData fetchByUniqueIdentifierVerifier(Long id, String uq, String uq2, String uq3);
	public List<MakerCheckerData> fetchAllByViewerID(int viewerId);
	public List<MakerCheckerData> fetchbyGroupCode(String groupCode);
	public List<MakerCheckerData> fetchVerifierbyGroupCode(String groupId);
	public MakerCheckerData fetchByRecID(long id);

}