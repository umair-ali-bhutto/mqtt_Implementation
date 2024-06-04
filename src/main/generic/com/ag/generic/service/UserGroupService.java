package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.UserGroups;
import com.ag.generic.model.GroupDeclarModel;



public interface UserGroupService {
	public void addNewGroup(UserGroups userGroups );
	public List<UserGroups> allGroups(String corpId);
	List<UserGroups> getUserGroups(String groupName,String corpId);
	public UserGroups viewGroup(int groupId,String corpId);
	public UserGroups updateGroup(UserGroups UserGroups);
	public int getGroupcode();
	public List<UserGroups> getUserGroupsByScreen(int screenId,String corpId);
	public List<GroupDeclarModel> getUserGroupsAllConverted(String corpId);
	public List<GroupDeclarModel> getNonMerchantUserGroups(String corpId);



}