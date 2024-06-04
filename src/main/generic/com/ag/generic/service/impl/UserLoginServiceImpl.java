
package com.ag.generic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ag.generic.entity.UserGroups;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.model.UserComplAssignModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.google.gson.Gson;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public UserLogin validateUserByNumber(String userCode, String corpId, String number) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From validetUser");
			Query cb = entityManager.createNamedQuery("UserLogin.validetUserByNumber");
			cb.setParameter("userCode", userCode);
			cb.setParameter("corpId", corpId);
			cb.setParameter("number", number);
			return (UserLogin) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserLogin validetUser(String userCode, String corpId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From validetUser");
			Query cb = entityManager.createNamedQuery("UserLogin.validetUser").setParameter("userCode", userCode);
			cb.setParameter("corpId", corpId);
			return (UserLogin) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserLogin validetUserWithoutCorpId(String userCode) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From validetUser");
			Query cb = entityManager.createNamedQuery("UserLogin.validetUserWithoutCorpID").setParameter("userCode",
					userCode);
			return (UserLogin) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserLogin validetUserWithoutStatus(String userCode, String corpId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From validetUser");
			Query cb = entityManager.createNamedQuery("UserLogin.validetUserWithouStatus").setParameter("userCode",
					userCode);
			cb.setParameter("corpId", corpId);
			return (UserLogin) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserGroups getUserGroup(int groupID, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserGroups.validetGroup").setParameter("grpId", groupID)
					.setParameter("corpId", corpId);
			return (UserGroups) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Transactional
	@Override
	public int insertUser(UserLogin usr) {
		try {
			entityManager.persist(usr);
			return usr.getUserId();
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Transactional
	@Override
	public void updateUser(UserLogin usr) {
		try {
			entityManager.merge(usr);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public UserLogin validetUserid(int getUserid) {
		try {
			Query cb = entityManager.createNamedQuery("UserLogin.validetUserId").setParameter("userId", getUserid);
			return (UserLogin) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<UserLogin> retrieveUsers(String userId, String mId, String cnic, String msisdn, String merchantName,
			String city, String region,String corpId) {
		List<UserLogin> usersLogin = null;
		try {
			String whereClause = " WHERE ";
			String where = "";
			String sql = AppProp.getProperty("select.user");

			if (userId != null && !userId.equals("")) {
				where += " USER_CODE = '" + userId + "' ";
			}
			if (mId != null && !mId.equals("")) {
				if (!where.isEmpty()) {
					where += " AND ";
				}
				where += " MID = '" + mId + "' ";
			}
			if (cnic != null && !cnic.equals("")) {
				if (!where.isEmpty()) {
					where += " AND ";
				}
				where += " CNIC = '" + cnic + "' ";
			}

			if (msisdn != null && !msisdn.equals("")) {
				if (!where.isEmpty()) {
					where += " AND ";
				}
				where += " MSISDN = '" + msisdn + "' ";
			}
			if (merchantName != null && !merchantName.equals("")) {
				if (!where.isEmpty()) {
					where += " AND ";
				}
				where += " USER_NAME = '" + merchantName + "' ";
			}

			if (!Objects.isNull(region) && !region.isEmpty()) {
				if (!where.isEmpty()) {
					where += " AND ";
				}
				where += " REGION = '" + region + "' ";
			}

			if (!Objects.isNull(city) && !city.isEmpty()) {
				if (!where.isEmpty()) {
					where += " AND ";
				}
				where += " CITY = '" + city + "' ";
			}
			
			
			if (!Objects.isNull(corpId) && !corpId.isEmpty()) {
				if (!where.isEmpty()) {
					where += " AND ";
				}
				where += " CORP_ID = '" + corpId + "' ";
			}

			if (!where.isEmpty()) {
				whereClause += where;
				sql += whereClause;
			}
			
			
			
			
			
			
			sql += " ORDER BY USER_ID ";

			Query cb = entityManager.createNativeQuery(sql, UserLogin.class);
			usersLogin = (List<UserLogin>) cb.getResultList();
			return usersLogin;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String[] fetchMerchantTerminalUpd(String mid, String tid) {
		try {
			String[] sk = new String[4];
			sk[0] = "9999";
			sk[1] = "";
			sk[2] = "";
			sk[3] = "";

			try {
				String query = DBUtil.fetchMerchantTerminalUpdN();
				query = query.replaceAll("#MID", mid);
				query = query.replaceAll("#TID", tid);
				AgLogger.logDebug(getClass(), query);

				Query cb = entityManager.createNativeQuery(query);
				List<Object[]> objs = (List<Object[]>) cb.getResultList();
				if (objs.size() != 0) {
					for (Object[] olm : objs) {
						sk[0] = "0000";
						sk[1] = (String) olm[0];
						sk[2] = (String) olm[1];
						sk[3] = (String) olm[2];
					}
				}
			} catch (Exception e) {
				AgLogger.logInfo("EXCEPTION  :  " + this.getClass().getName() + e);
				e.printStackTrace();
			}
			return sk;
		} catch (NoResultException nre) {
			return null;
		}

	}

	@Override
	public UserLogin validateUserPassword(String userCode, String password) {
		try {
			UserLogin user = null;
				if (!Objects.isNull(userCode) && !userCode.isEmpty() && !Objects.isNull(password)
						&& !password.isEmpty()) {
					Query cb = entityManager.createNamedQuery("UserLogin.validetUserPassword")
							.setParameter("userCode", userCode)
							.setParameter("password", UtilAccess.md5Java(password));
					user = (UserLogin) cb.getSingleResult();
				}
			return user;
		} catch (NoResultException nre) {
			return null;
		}

	}

	@Override
	public List<UserLogin> lstUserMerchants(List<String> lstMerchantId, String corpId) {
		List<UserLogin> list = new ArrayList<UserLogin>();
		Session sess = null;

		try {
			Query query = entityManager.createNamedQuery("UserLogin.lstUserMerchants");
			query.setParameter("mids", lstMerchantId);
			query.setParameter("corpId", corpId);
			List<Object[]> listObj = query.getResultList();

			if (!listObj.isEmpty()) {
				list = new ArrayList<UserLogin>();
				for (Object[] obj : listObj) {
					UserLogin userLogin = new UserLogin();
					userLogin.setUserId(Integer.parseInt(obj[0].toString()));
					userLogin.setMid(obj[1].toString());
					list.add(userLogin);
				}
			}

		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

		return list;
	}

	@Override
	public List<UserLogin> fetchAllAdmins(String corpId) {
		List<UserLogin> usersLogin = null;
		try {
			List<Integer> ids = new ArrayList<Integer>();

			String value = AppProp.getProperty("subadmin.user.group.code");
			if (value.contains(",")) {
				String[] splt = value.split("\\,");
				for (String sm : splt) {
					ids.add(Integer.parseInt(sm));
				}
			} else {
				ids.add(Integer.parseInt(value));
			}

			usersLogin = new ArrayList<UserLogin>();
			Query cb = entityManager.createNamedQuery("UserLogin.fetchAllAdmins").setParameter("groupCode", ids);
			cb.setParameter("corpId", corpId);
			usersLogin = (List<UserLogin>) cb.getResultList();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}

		return usersLogin;
	}

	@Override
	public HashMap<String, Object> fetchAllUsers(String corpId) {
		List<UserLogin> usersLogin = null;
		HashMap<String, Object> usersMap = null;
		try {
			usersLogin = new ArrayList<UserLogin>();
			Query cb = entityManager.createNamedQuery("UserLogin.fetchAll");
			cb.setParameter("corpId", corpId);

			usersLogin = (List<UserLogin>) cb.getResultList();
			usersMap = new HashMap<String, Object>();
			if (usersLogin != null) {
				usersMap = new HashMap<String, Object>();
				for (UserLogin obj : usersLogin) {
					usersMap.put(obj.getUserCode(), obj);
				}
			}
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}

		return usersMap;
	}
	
	
	@Override
	public HashMap<Integer, Object> fetchAllUsersId(String corpId) {
		List<UserLogin> usersLogin = null;
		HashMap<Integer, Object> usersMap = null;
		try {
			usersLogin = new ArrayList<UserLogin>();
			Query cb = entityManager.createNamedQuery("UserLogin.fetchAll");
			cb.setParameter("corpId", corpId);

			usersLogin = (List<UserLogin>) cb.getResultList();
			usersMap = new HashMap<Integer, Object>();
			if (usersLogin != null) {
				usersMap = new HashMap<Integer, Object>();
				for (UserLogin obj : usersLogin) {
					usersMap.put(obj.getUserId(), obj);
				}
			}
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}

		return usersMap;
	}

	@Override
	public UserLogin fetchUserByMid(String mid, String corpId) {
		UserLogin usersLogin = null;
		try {
			Query cb = entityManager.createNamedQuery("UserLogin.fetchAllByMID").setParameter("mid", mid);
			cb.setParameter("corpId", corpId);

			usersLogin = (UserLogin) cb.getSingleResult();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}

		return usersLogin;
	}

	// New Method

	@Override
	public UserLogin validetUserByGroup(String region, String city, String country, String groupCode) {

		try {
			String query = AppProp.getProperty("select.user");

			query += " WHERE IS_ACTIVE=1 ";

			if (!Objects.isNull(groupCode) && !groupCode.isEmpty() && !groupCode.equals("999")) {
				query += " AND  GROUP_CODE = '" + Integer.parseInt(groupCode) + "' ";
			}

			if (!Objects.isNull(city) && !city.isEmpty()) {
				query += " AND  CITY = '" + city + "' ";
			}

			if (!Objects.isNull(region) && !region.isEmpty()) {
				query += " AND  REGION = '" + region + "' ";
			}

			Query cb = entityManager.createNativeQuery(query, UserLogin.class);

			AgLogger.logDebug(getClass(), "Fetching Rec From validetUser");
			return (UserLogin) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

	}

	// New Method

	// @Override
//	public UserLogin validetUserByGroup(String region, String city, String country, String groupCode) {
//
//		try {
//			String query = "SELECT * FROM USERS_LOGIN WHERE IS_ACTIVE = 1 ";
//
//			if (!Objects.isNull(groupCode) && !groupCode.isEmpty() && !groupCode.equals("999")) {
//				query += " AND  GROUP_CODE = '" + Integer.parseInt(groupCode) + "' ";
//			}
//
//			if (!Objects.isNull(city) && !city.isEmpty()) {
//				query += " AND  CITY = '" + city + "' ";
//			}
//
//			if (!Objects.isNull(region) && !region.isEmpty()) {
//				query += " AND  REGION_CODE = '" + region + "' ";
//			}
//
//			if (!Objects.isNull(country) && !country.isEmpty()) {
//				query += " AND  USER_COUNTRY_CODE = '" + country + "' ";
//			}
//
//			Query cb = entityManager.createNativeQuery(query);
//
//			AgLogger.logDebug(getClass(), "Fetching Rec From validetUser");
//			return (UserLogin) cb.getSingleResult();
//		} catch (NoResultException nre) {
//			return null;
//		}
//
//	}

	@Transactional
	@Override
	public void updateAndinsertUserSettings(List<UserSetting> lstUserSettingUpdate,
			List<UserSetting> lstUserSettingInsert, UserLogin user) {
		try {
			for (UserSetting userSetting : lstUserSettingUpdate) {
				entityManager.merge(userSetting);
			}
			for (UserSetting userSetting : lstUserSettingInsert) {
				entityManager.persist(userSetting);
			}
			entityManager.merge(user);
			AgLogger.logInfo("RECORD INSERTED AND UPDATED");
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception ", e);
		}

	}

	@Override
	public List<UserLogin> findUserByGroupAll(UserLogin user) {
		List<UserLogin> list = null;
		List<String> listOfString = new ArrayList<String>();
		try {

			String sql = "Select * from USERS_LOGIN WHERE IS_ACTIVE=1 ";

			// code for getting groups to assign complaints
			String jsonString = AppProp.getProperty("group.code.compl.assign.json");
			List<UserComplAssignModel> temp = new ArrayList<UserComplAssignModel>();
			JSONArray jsonArray = new JSONArray(jsonString);
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject ob = jsonArray.getJSONObject(i);
				UserComplAssignModel comp = new Gson().fromJson(ob.toString(), UserComplAssignModel.class);
				temp.add(comp);
			}

			Integer[] reqGroupCode = null;
			for (UserComplAssignModel c : temp) {
				if (c.getGroupCode().equals(user.getGroupCode())) {
					reqGroupCode = new Integer[c.getGroupList().size()];
					// c.getGroupList().toArray(reqGroupCode);
					listOfString = convertIntListToStringList(c.getGroupList(), s -> String.valueOf(s));
					if (c.getMode().equals("REGION")) {
						if (!Objects.isNull(user.getRegion()) && !user.getRegion().isEmpty()) {
							sql += " AND REGION = '" + user.getRegion().trim() + "' ";
						}
					}
				}
			}
			if (listOfString.size() > 0) {
				String string = String.join(", ", listOfString);
				sql += " AND GROUP_CODE in " + "(" + string + ") ";
			}
			
			sql+=" ORDER BY GROUP_CODE,USER_ID ";
			
			AgLogger.logDebug("", sql);
			Query cb = entityManager.createNativeQuery(sql, UserLogin.class);
			list = (List<UserLogin>) cb.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static <T, U> List<U> convertIntListToStringList(List<T> listOfInteger, Function<T, U> function) {
		return listOfInteger.stream().map(function).collect(Collectors.toList());
	}

	@Override
	public List<UserLogin> fetchAll() {
		List<UserLogin> usersLogin = null;
		try {
			Query cb = entityManager.createNamedQuery("UserLogin.fetchAllUsers");

			usersLogin = (List<UserLogin>) cb.getResultList();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}

		return usersLogin;
	}

}
