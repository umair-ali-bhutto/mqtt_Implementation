package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.MakerCheckerConfig;

public interface MakerCheckerConfigService {

	public MakerCheckerConfig fetchByID(long id);

	public MakerCheckerConfig fetchByScreenID(int screnId);

	public List<MakerCheckerConfig> fetchAll();

	public List<MakerCheckerConfig> fetchByApproverGroupId(int GroupId);

	public List<MakerCheckerConfig> fetchByVerifierGroupId(int GroupId);

	public void updateGroupAssignedApprover(List<Long> str, int groupID);

	public void updateGroupAssignedVerifier(List<Long> str, int groupID);

	public List<MakerCheckerConfig> fetchByCorpId(String corpId);

}