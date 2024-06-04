package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.QrConfig;
import com.ag.mportal.services.QrConfigService;

@Service
public class QrConfigServiceImpl implements QrConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<QrConfig> SelectAll(String corpId) {
		try {
		List<QrConfig> user = new ArrayList<QrConfig>();
		Query cb = entityManager.createNamedQuery("QrConfig.findAll");
		cb.setParameter("corpId", corpId);
		user = (List<QrConfig>) cb.getResultList();
		return user;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QrConfig> SelectAllByMid(String corpId,String mid) {
		try {
		List<QrConfig> user = new ArrayList<QrConfig>();
		Query cb = entityManager.createNamedQuery("QrConfig.findAllByMid");
		cb.setParameter("corpId", corpId);
		cb.setParameter("mid", mid);
		user = (List<QrConfig>) cb.getResultList();
		return user;
		} catch (NoResultException nre) {
			return null;
		}
	}

}
