package com.ag.generic.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.UserGroups;
import com.ag.generic.model.GroupDeclarModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserGroupService;
import com.ag.generic.util.AgLogger;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addNewGroup(UserGroups userGroups) {
		try {
			entityManager.persist(userGroups);
		} catch (NoResultException nre) {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserGroups> allGroups(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserGroups.allGroup");
			cb.setParameter("corpId",corpId );
			return (List<UserGroups>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserGroups> getUserGroups(String groupName,String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserGroups.getUserGroups").setParameter("grpName", groupName);
			cb.setParameter("corpId",corpId );
			return (List<UserGroups>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public UserGroups viewGroup(int groupId,String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserGroups.validetGroup").setParameter("grpId", groupId);
			cb.setParameter("corpId",corpId );
			return (UserGroups) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Transactional
	public UserGroups updateGroup(UserGroups userGroups) {
		try {
			return entityManager.merge(userGroups);
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public int getGroupcode() {
		try {
			Query cb = entityManager.createNamedQuery("UserGroups.getMaxGroupCode");
		
			return (int) cb.getSingleResult();
		} catch (NoResultException nre) {
			return (Integer) null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserGroups> getUserGroupsByScreen(int screenid, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserGroups.getGroupsByScreen").setParameter("screenId",
					screenid);
			cb.setParameter("corpId",corpId );
			return (List<UserGroups>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<GroupDeclarModel> getUserGroupsAllConverted(String corpId) {
		try {
			List<UserGroups> userGroup = new ArrayList<UserGroups>();
			Map<String, GroupDeclarModel> grp = null;
			grp = new LinkedHashMap<String, GroupDeclarModel>();
			Query cb = entityManager.createNamedQuery("UserGroups.allGroup");
			cb.setParameter("corpId",corpId );
			userGroup = (List<UserGroups>) cb.getResultList();
			for (UserGroups userG : userGroup) {
				grp.put(String.valueOf(userG.getGrpId()),
						new GroupDeclarModel(String.valueOf(userG.getGrpId()), userG.getGrpName()));
			}
			return new ArrayList<GroupDeclarModel>(grp.values());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<GroupDeclarModel> getNonMerchantUserGroups(String corpId) {
		Map<String, GroupDeclarModel> grp = null;
		List<UserGroups> lstUserGroups = null;

		try {
			List<Integer> ids = new ArrayList<Integer>();
			String value = AppProp.getProperty("non.merchant.user.group.code");
			if (value.contains(",")) {
				String[] splt = value.split("\\,");
				for (String sm : splt) {
					ids.add(Integer.parseInt(sm));
				}
			} else {
				ids.add(Integer.parseInt(value));
			}

			lstUserGroups = new ArrayList<UserGroups>();
			Query cb = entityManager.createNamedQuery("UserGroups.getNonMerchantUserGroups").setParameter("grpId", ids);
			cb.setParameter("corpId",corpId );
			try {
				lstUserGroups = (List<UserGroups>) cb.getResultList();
				grp = new LinkedHashMap<String, GroupDeclarModel>();
				for (UserGroups u : lstUserGroups) {
					grp.put(String.valueOf(u.getGrpId()),
							new GroupDeclarModel(String.valueOf(u.getGrpId()), u.getGrpName()));
				}
			} catch (NoResultException nre) {
				return null;
			}

		} catch (Exception e) {
			AgLogger.logerror(getClass(), "EXCEPTION IN USER GROUP SERVICE NON MERCHANT FETCH ", e);
			return null;
		}

		return new ArrayList<GroupDeclarModel>(grp.values());
	}

	

}