package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.PlayersIdsPushNotification;
import com.ag.generic.service.PlayerIdsPushNotificationService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;

@Service
public class PlayerIdsPushNotificationServiceImpl implements PlayerIdsPushNotificationService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public int insert(PlayersIdsPushNotification playerIdsPushNotification) {
		try {
			entityManager.persist(playerIdsPushNotification);
			return playerIdsPushNotification.getId();
		} catch (NoResultException nre) {
			return (Integer) null;
		}

	}

	@Override
	@Transactional
	public void update(PlayersIdsPushNotification playerIdsPushNotification) {
		try {
			entityManager.merge(playerIdsPushNotification);
		} catch (NoResultException nre) {

		}
	}

	@Override
	@Transactional
	public void updateByUserLoginId(int userId, String playerId, String channel) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From updateByUserLoginId");
			String sql = "";
			if(DBUtil.getDialect() == 1) {
				sql = "UPDATE PLAYERS_IDS_PUSH_NOTIFICATIONS SET PLAYER_ID = '" + playerId + "', CHANNEL = '"
						+ channel + "', UPDATED_DATE = (GETDATE()) WHERE USER_LOGIN_ID = " + userId + "";
			}
			else {
				sql = "UPDATE PLAYERS_IDS_PUSH_NOTIFICATIONS SET PLAYER_ID = '" + playerId + "', CHANNEL = '"
						+ channel + "', UPDATED_DATE = sysdate WHERE USER_LOGIN_ID = " + userId + "";	
			}
			AgLogger.logDebug(getClass(), sql + "..............");
			entityManager.createNativeQuery(sql).executeUpdate();
		} catch (NoResultException nre) {

		} catch (Exception e) {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayersIdsPushNotification> searchAll() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From searchAll");
			// ADD WHERE CLAUSE playerId not equal to 00
			// ORDER BY id desc
			Query cb = entityManager.createNamedQuery("PlayersIdsPushNotification.searchAll");
			return (List<PlayersIdsPushNotification>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public PlayersIdsPushNotification searchByUserLoginId(int userId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From searchByUserLoginId");
			Query cb = entityManager.createNamedQuery("PlayersIdsPushNotification.searchByUserLoginId")
					.setParameter("userLoginId", userId);
			return (PlayersIdsPushNotification) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
