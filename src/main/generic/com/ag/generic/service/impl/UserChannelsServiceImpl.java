package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.UserChannel;
import com.ag.generic.service.UserChannelsService;
import com.ag.generic.util.AgLogger;

@Service
public class UserChannelsServiceImpl implements UserChannelsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insert(UserChannel tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public UserChannel fetchByID(UserChannel tcn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("UserChannels.fetchByID").setParameter("id", tcn.getId());
			return (UserChannel) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserChannel fetchByOtherParams(UserChannel tcn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("UserChannels.fetchByOtherParams").setParameter("id",
					tcn.getId());
			return (UserChannel) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserChannel> fetchAll() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
			Query cb = entityManager.createNamedQuery("UserChannels.fetchAll");
			return (List<UserChannel>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserChannel> fetchAllByID(int id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllByID");
			Query cb = entityManager.createNamedQuery("UserChannels.fetchAllByID").setParameter("userLoginId", id);
			return (List<UserChannel>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void update(UserChannel id) {
		try {
			entityManager.merge(id);
		} catch (NoResultException nre) {

		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		try {
			AgLogger.logDebug("","Deleting From fetchAllByID");
			entityManager.createNativeQuery("DELETE FROM USER_CHANNELS WHERE USER_LOGIN_ID = " + id).executeUpdate();
		} catch (NoResultException nre) {

		}
	}

}
