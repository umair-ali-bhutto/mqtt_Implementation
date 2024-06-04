package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.loy.adm.entity.LoyFileUploadProc;
import com.ag.loy.adm.service.LoyFileUploadProcService;

@Service
public class LoyFileUploadProcServiceImpl implements LoyFileUploadProcService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<LoyFileUploadProc> FetchAllByCorpId(String corpId,String type) {
		List<LoyFileUploadProc> lst = new ArrayList<LoyFileUploadProc>();
		try {
			Query query = entityManager.createNamedQuery("LoyFileUploadProc.fetchAllByCorpId", LoyFileUploadProc.class);
			query.setParameter("corpId", corpId);
			query.setParameter("type", type);
			lst = (List<LoyFileUploadProc>) query.getResultList();
		} catch (Exception e) {

		}
		return lst;
	}

}
