package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.BroadcastMsgDetail;
import com.ag.generic.model.BroadCastMessageModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.BroadCastMessageDetailService;
import com.ag.generic.util.AgLogger;

@Service
public class BroadCastMessageDetailServiceImpl implements BroadCastMessageDetailService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(BroadcastMsgDetail sdt) {
		
		try {
		entityManager.persist(sdt);
	} catch (NoResultException nre) {
		
	}
	}

	@Override
	public BroadcastMsgDetail searBroadCastMessageDetail() {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From searBroadCastMessageDetail");
		// Add Order By Clause Order BY Id asc
		Query cb = entityManager.createNamedQuery("BroadCastMessageDetail.searBroadCastMessageDetail");
		return (BroadcastMsgDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<BroadCastMessageModel> searchBrdMsgByReadDateAndUserId(int userLoginId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From searchBrdMsgByReadDateAndUserId");
		// NEE TO CREATE BRD MESSAGE QUERY 
		String query = AppProp.getProperty("brd.msg.query");
		query = query.replaceAll("@PARAMUID", userLoginId + "");
		AgLogger.logDebug(getClass(), query);
		Query cb = entityManager.createNativeQuery(query,"BroadCastMessageModels");
		return (List<BroadCastMessageModel>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
