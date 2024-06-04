package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.AwardMasters;

public interface AwardMastersService {

	public Long saveAwardMasters(AwardMasters awardMasters);

	public boolean updateAwardMasters(AwardMasters awardMasters);

	public ArrayList<AwardMasters> fetchAllRecords(String corpId);
	
	public ArrayList<AwardMasters> fetchAllActiveRecords(String corpId);

	public ArrayList<AwardMasters> fetchAllRecordById(Long id,String corpId);

	public AwardMasters fetchByAwardName(String awardName,String corpId);
	
	public String fetchMaxAwardId();
	
	

}