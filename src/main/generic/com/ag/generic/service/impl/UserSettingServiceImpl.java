package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.ag.generic.entity.UserSetting;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.util.AgLogger;

@Service
public class UserSettingServiceImpl implements UserSettingService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserSetting> fetchAllUserSetting() {
		try {
			Query query = entityManager.createNamedQuery("UserSetting.fetchAllUserSetting");
			return (List<UserSetting>) query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserSetting> fetchSettingById(int Id) {
		try {
			Query query = entityManager.createNamedQuery("UserSetting.fetchSettingById");
			query.setParameter("id", Id);
			return (List<UserSetting>) query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<UserSetting> fetchSettingByUserLoginId(int Id) {
		try {
			Query query = entityManager.createNamedQuery("UserSetting.fetchSettingByUserLoginId");
			query.setParameter("id", Id);
			return (List<UserSetting>) query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserSetting fetchSettingByIdName(int id, String name) {
		try {
			Query query = entityManager.createNamedQuery("UserSetting.fetchSettingByIdName");
			query.setParameter("id", id);
			query.setParameter("name", name);
			return (UserSetting) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void insertProp(UserSetting prop) {
		try {
			entityManager.persist(prop);
		} catch (NoResultException nre) {

		}
	}

	@Override
	@Transactional
	public void updateProp(UserSetting prop) {
		try {
			entityManager.merge(prop);
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception in Update UserSetting: ", e);
		}

	}

	@Override
	public List<UserSetting> fetchSettingByLstUserLoginId(List<Integer> lstUserId) {

		List<UserSetting> list = null;
		Session sess = null;

		try {
			Query query = entityManager.createNamedQuery("UserSetting.fetchSettingByLstUserLoginId");
			query.setParameter("userIds", lstUserId);
			list = (List<UserSetting>) query.getResultList();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

		return list;
	}

	@Transactional
	@Override
	public void updateAndinsertUserSettings(List<UserSetting> oldLstUserSetting, List<UserSetting> newLstUserSetting) {
		try {
			for (UserSetting userSetting : oldLstUserSetting) {
				userSetting.setIsActive(0);
				entityManager.merge(userSetting);
			}
			for (UserSetting userSetting : newLstUserSetting) {
				entityManager.persist(userSetting);
			}
			AgLogger.logInfo("RECORD INSERTED AND UPDATED");
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception ", e);
		}

	}

	@Override
	@Transactional
	public void insertLstUserSettings(List<UserSetting> lstUserSetting) {
		try {
			for (UserSetting userSetting : lstUserSetting) {
				entityManager.persist(userSetting);
			}
			AgLogger.logInfo("RECORD INSERTED in USER SETTING");
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception in insertLstUserSettings", e);
		}
	}

}
