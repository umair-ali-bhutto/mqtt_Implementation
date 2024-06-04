package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AccountPayments;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.service.CorporateMasterService;

@Service
public class CorporateMasterServiceImpl implements CorporateMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	UtilService service;

	@Override
	@Transactional
	public void insert(CorporateMaster acm) {
		try {
			entityManager.persist(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void update(CorporateMaster acm) {
		try {
			entityManager.merge(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	public List<CorporateMaster> fetchByCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("CorporateMaster.findAllByCorpID", CorporateMaster.class);
			query.setParameter("corpid", corpId);
			List<CorporateMaster> lst = (List<CorporateMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public List<CorporateMaster> fetchAllList(int groupCode, String corpId) {
		try {
			Query query = null;
			String groups = AppProp.getProperty("corporate.list.view");

			boolean rights = service.isEditRights(groups, groupCode);
			if (rights) {
				query = entityManager.createNamedQuery("CorporateMaster.findAll", CorporateMaster.class);
			} else {
				query = entityManager.createNamedQuery("CorporateMaster.findAllByCorpID", CorporateMaster.class);
				query.setParameter("corpid", corpId);
			}

			List<CorporateMaster> lst = (List<CorporateMaster>) query.getResultList();

			if (lst.size() == 0) {
				CorporateMaster temp = new CorporateMaster();
				temp.setName("N-A");
				temp.setCorpid("0");
				lst.add(temp);
			}
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING Corporates: ", ex);
		}
		return null;
	}

	@Override
	public CorporateMaster fetchCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("CorporateMaster.findAllByCorpID", CorporateMaster.class);
			query.setParameter("corpid", corpId);
			return (CorporateMaster) query.getSingleResult();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public String fetchMaxCorpid() {
		int maxId = 0;
		try {
			String q = AppProp.getProperty("fetch.max.corporate.id");
			Query query = entityManager.createNativeQuery(q);
			String res = (String) query.getSingleResult();
			maxId = Integer.parseInt(res);
		} catch (NoResultException nre) {
			maxId = 0;
		}
		maxId = maxId + 1;
		String max = String.valueOf(maxId);
		String Id = StringUtils.leftPad(max, 5, "0");

		return Id;
	}

	@Override
	public List<CorporateMaster> fetchAll() {
		List<CorporateMaster> lst = new ArrayList<CorporateMaster>();
		try {
			Query query = entityManager.createNamedQuery("CorporateMaster.findAll", CorporateMaster.class);
			lst = (List<CorporateMaster>) query.getResultList();
		} catch (NoResultException e) {
		}

		return lst;
	}

	@Override
	public List<CorporateMaster> fetchByFilter(String corpid, String status) {
		
		try {
			String whereclause = "";
			if (corpid != null) {
				whereclause += "CORPID = '" + corpid + "' ";
			}

			if (status != null) {
				if (!whereclause.isEmpty()) {
					whereclause += " AND ";
				}
				whereclause += "( ACTIVE = '" + status + "') ";
			}

			String q = "SELECT * FROM CORPORATE_MASTER WHERE " + whereclause + " ORDER BY CORPID DESC";
			AgLogger.logDebug(getClass(), q);
			Query query = entityManager.createNativeQuery(q,CorporateMaster.class);
			return (List<CorporateMaster>) query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
		
		
	}

}