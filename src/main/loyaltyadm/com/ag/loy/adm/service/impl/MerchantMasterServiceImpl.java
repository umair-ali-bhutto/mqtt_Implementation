package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.loy.adm.entity.CampaignMerchantProduct;
import com.ag.loy.adm.entity.MerchantMaster;
import com.ag.loy.adm.service.MerchantMasterService;

@Service
public class MerchantMasterServiceImpl implements MerchantMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MerchantMaster findAllByMidCorpId(String mid, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("MerchantMaster.findAll");
			cb.setParameter("corpid", corpId);
			cb.setParameter("mid", mid);
			return (MerchantMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void insert(MerchantMaster merchantMaster) {
		try {
			entityManager.persist(merchantMaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void update(MerchantMaster merchantMaster) {
		try {
			entityManager.merge(merchantMaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MerchantMaster> findAllByCorpId(String corpId) {

		List<MerchantMaster> lst = new ArrayList<MerchantMaster>();

		try {
			Query cb = entityManager.createNamedQuery("MerchantMaster.findAllByCorpId");
			cb.setParameter("corpid", corpId);
			lst = (List<MerchantMaster>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}
}
