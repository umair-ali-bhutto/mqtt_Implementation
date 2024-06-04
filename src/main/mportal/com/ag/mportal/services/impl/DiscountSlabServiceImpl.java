package com.ag.mportal.services.impl;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountSlab;
import com.ag.mportal.services.DiscountSlabService;

@Service
public class DiscountSlabServiceImpl implements DiscountSlabService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DiscountSlab tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountSlab: ", ex);
		}
	}

	@Override
	@Transactional
	public void update(DiscountSlab tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountSlab: ", ex);
		}
	}

	@Override
	@Transactional
	public List<DiscountSlab> getSlabByDiscountId(List<Long> lstDiscountId) {
		List<DiscountSlab> lstSlab = null;
		try {
			Query query = entityManager.createNamedQuery("DiscountSlab.getSlabByDisctId", DiscountSlab.class);
			query.setParameter("discId", lstDiscountId);

			lstSlab = (List<DiscountSlab>) query.getResultList();

			if (!Objects.isNull(lstSlab) && !lstSlab.isEmpty()) {
				return lstSlab;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FETCHING SLAB BY DISCOUNT ID: ", ex);
		}

		return null;

	}

	@Override
	public DiscountSlab fetchByDiscId(long discId) {
		DiscountSlab mdl = new DiscountSlab();
		try {
			Query q = entityManager.createNamedQuery("DiscountSlab.fetchById", DiscountSlab.class)
					.setParameter("discId", discId);
			mdl = (DiscountSlab) q.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
		}
		return mdl;
	}

}
