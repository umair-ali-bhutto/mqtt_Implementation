package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.BannerTicker;
import com.ag.generic.service.BannerTickerService;
import com.ag.generic.util.AgLogger;

@Service
public class BannerTickerServiceImpl implements BannerTickerService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<BannerTicker> getBannerTickerByCorpId(String type, String corpId) {
		AgLogger.logDebug(getClass(), "Fetching Rec From getBannerTicker");
		try {
			Query cb = entityManager.createNamedQuery("BannerTicker.getBannerTickerByCorpID").setParameter("type", type)
					.setParameter("type", type).setParameter("corpId", corpId);
			return (List<BannerTicker>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}

	}

}
