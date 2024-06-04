package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.TapNPayResponseCodesConfig;
import com.ag.mportal.services.TapNPayResponseCodesConfigService;

@Service
public class TapNPayResponseCodesConfigServiceImpl implements TapNPayResponseCodesConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TapNPayResponseCodesConfig> fetchAll() {
		try {
			Query cb = entityManager.createNamedQuery("TapNPayResponseCodesConfig.retrieveAll");
			return (List<TapNPayResponseCodesConfig>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
