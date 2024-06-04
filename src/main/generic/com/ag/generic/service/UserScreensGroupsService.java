package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.UserScreensGroup;

public interface UserScreensGroupsService {
	public void insertScreenGroup(UserScreensGroup userScreensGroup);
	public List<UserScreensGroup> getAllScreensGroups(String corpId);
	public List<UserScreensGroup>  viewScrnGroup(int groupId,String corpId);
	public UserScreensGroup updateScreenGroup(UserScreensGroup UserScreensGroup);
	public String deleteRecord(int groupId, String corpId);
	
	public List<UserScreensGroup> viewScreenGroupByScreen (int screenId,String corpId);
}