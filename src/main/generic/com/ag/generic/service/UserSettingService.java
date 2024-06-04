package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.UserSetting;
public interface UserSettingService {
	public List<UserSetting> fetchAllUserSetting();
	public List<UserSetting> fetchSettingById(int Id);
	public UserSetting fetchSettingByIdName(int id,String name);
	public void insertProp(UserSetting prop) ;
	public void updateProp(UserSetting prop);
	public List<UserSetting> fetchSettingByUserLoginId(int Id);
	public List<UserSetting> fetchSettingByLstUserLoginId(List<Integer> lstUserId);
	public void updateAndinsertUserSettings(List<UserSetting> oldLstUserSetting,List<UserSetting> newLstUserSetting);
	public void insertLstUserSettings(List<UserSetting> lstUserSetting);
}
