package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.loy.adm.entity.LoyCustomerFile;
import com.ag.loy.adm.service.LoyCustomerFileService;

@Service
public class LoyCustomerFileServiceImpl implements LoyCustomerFileService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(LoyCustomerFile acm) {
		try {
			entityManager.persist(acm);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void update(LoyCustomerFile acm) {
		try {
			entityManager.merge(acm);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public LoyCustomerFile fetchByName(String fileName) {
		LoyCustomerFile mdl = new LoyCustomerFile();
		try {
			Query cb = entityManager.createNamedQuery("LoyCustomerFile.fetchByName", LoyCustomerFile.class);
			cb.setParameter("fileName", fileName);
			mdl = (LoyCustomerFile) cb.getSingleResult();
		} catch (NoResultException nre) {
			mdl = null;
		}

		return mdl;
	}

	@Override
	public List<LoyCustomerFile> fetchAllByCorpId(String corpId) {
		List<LoyCustomerFile> lst = new ArrayList<LoyCustomerFile>();
		try {
			Query cb = entityManager.createNamedQuery("LoyCustomerFile.fetchAllByCorpId", LoyCustomerFile.class);
			cb.setParameter("corpId", corpId);
			lst = (List<LoyCustomerFile>) cb.getResultList();
		} catch (Exception e) {

		}
		return lst;
	}

	@Override
	public LoyCustomerFile fetchById(long id) {
		LoyCustomerFile mdl = new LoyCustomerFile();
		try {
			Query cb = entityManager.createNamedQuery("LoyCustomerFile.fetchById", LoyCustomerFile.class);
			cb.setParameter("id", id);
			mdl = (LoyCustomerFile) cb.getSingleResult();
		} catch (NoResultException nre) {
			mdl = null;
		}

		return mdl;
	}

	@Override
	public List<LoyCustomerFile> fetchAll() {
		List<LoyCustomerFile> lst = new ArrayList<LoyCustomerFile>();
		try {
			Query cb = entityManager.createNamedQuery("LoyCustomerFile.fetchAllNew", LoyCustomerFile.class);
			lst = (List<LoyCustomerFile>) cb.getResultList();
		} catch (Exception e) {

		}
		return lst;
	}

}
