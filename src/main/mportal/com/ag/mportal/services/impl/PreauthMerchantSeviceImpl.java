package com.ag.mportal.services.impl;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.PreauthMaster;
import com.ag.mportal.entity.PreauthMerchant;

import com.ag.mportal.services.PreauthMerchantService;

@Service
public class PreauthMerchantSeviceImpl implements PreauthMerchantService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean insert(PreauthMerchant preauthMerchant) {
		try {
			entityManager.persist(preauthMerchant);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<PreauthMerchant> fetchData(String mId, String preauthId) {
		List<PreauthMerchant> lst = new ArrayList<PreauthMerchant>();
		try {
			String m = "";
			if (mId != null && !mId.equals("null")) {
				m += " AND m.mid = '" + mId + "' ";
				AgLogger.logInfo("MID....." + mId);
			}
			if (preauthId != null && !preauthId.equals("-")) {
				m += " AND m.paid = '" + preauthId + "' ";
			}
			String quer = "SELECT m.corpid AS CORPID,m.paid AS PAID,m.mid AS MID,m.active AS ACTIVE,"
					+ "m.SOURCE_REF AS SOURCE_REF,u.USER_CODE AS CR_BY,m.CR_ON AS CR_ON,m.UPD_BY AS UPD_BY"
					+ ",m.UPD_ON AS UPD_ON FROM PREAUTH_MERCHANT m, USERS_LOGIN u "
					+ " WHERE m.CR_BY = u.USER_ID AND m.PAID IS NOT NULL" + m + " ORDER BY m.PAID";
			AgLogger.logInfo(quer);
			Query cb = entityManager.createNativeQuery(quer, PreauthMerchant.class);
			lst = (List<PreauthMerchant>) cb.getResultList();
			AgLogger.logDebug("", "Query: " + quer);
		} catch (Exception e) {

		}
		return lst;
	}

	@Override
	public PreauthMerchant fetchByID(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("PreauthMerchant.fetchByID").setParameter("id", id);
			return (PreauthMerchant) cb.getSingleResult();
		} catch (Exception nre) {
			nre.printStackTrace();
			return null;
		}
	}
	@Override
	public PreauthMerchant fetchByIDActive(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("PreauthMerchant.fetchByIDActive").setParameter("id", id);
			return (PreauthMerchant) cb.getSingleResult();
		} catch (Exception nre) {
			nre.printStackTrace();
			return null;
		}
	}


	@Override
	@Transactional
	public boolean update(PreauthMerchant preauthMerchant) {
		try {
			entityManager.merge(preauthMerchant);
			return true;
		} catch (Exception e) {

		}
		return false;

	}

}
