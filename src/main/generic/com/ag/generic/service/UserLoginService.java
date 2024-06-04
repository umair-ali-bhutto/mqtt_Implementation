package com.ag.generic.service;

import java.util.HashMap;
import java.util.List;

import com.ag.generic.entity.UserGroups;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;

public interface UserLoginService {

	public UserLogin validetUser(String userCode, String corpId);
	public UserLogin validateUserByNumber(String userCode, String corpId, String number);
	
	public UserLogin validetUserWithoutCorpId(String userCode);
	
	public UserLogin validetUserWithoutStatus(String userCode, String corpId);

	public UserGroups getUserGroup(int groupID, String corpId);

	public int insertUser(UserLogin usr);

	public void updateUser(UserLogin usr);

	public UserLogin validetUserid(int getUserid);

	public List<UserLogin> retrieveUsers(String userId, String mId, String cnic, String msisdn, String merchantName,
			String cityCode, String regionCode,String corpId);
	
	public String[] fetchMerchantTerminalUpd(String mid, String tid);
	
	public UserLogin validateUserPassword(String userCode, String password);

	
	public List<UserLogin> lstUserMerchants(List<String> lstMerchantId, String corpId) ;

	public List<UserLogin> findUserByGroupAll(UserLogin user);
	
	public List<UserLogin> fetchAllAdmins( String corpId);
	
	
	public HashMap<String, Object> fetchAllUsers( String corpId);
	
	public HashMap<Integer, Object> fetchAllUsersId(String corpId);
	
	public List<UserLogin> fetchAll();
	
	public UserLogin fetchUserByMid(String mid, String corpId);
	
	public UserLogin validetUserByGroup(String region, String city, String country, String groupCode);
	
	public void updateAndinsertUserSettings(List<UserSetting> lstUserSettingUpdate, List<UserSetting> lstUserSettingInsert, UserLogin user);
	


}
