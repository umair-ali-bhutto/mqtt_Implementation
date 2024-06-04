package com.ag.loy.adm.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.loy.adm.entity.CalenderMaster;
import com.ag.loy.adm.service.CalenderMasterService;

@Service
public class CalenderMasterServiceImpl implements CalenderMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public CalenderMaster fetchAllRecordById(String id,String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CalenderMaster.fetchAllRecordById");
			cb.setParameter("callId", id);
			return (CalenderMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<CalenderMaster> fetchAllRecord(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CalenderMaster.fetchAllRecord");
			return (List<CalenderMaster>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}