package com.ag.generic.model;

import java.util.List;

public class UserComplAssignModel {

	private Integer groupCode;
	private List<Integer> groupList;
	private String mode;
	
	
	
	public Integer getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public List<Integer> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Integer> groupList) {
		this.groupList = groupList;
	}
	
}
