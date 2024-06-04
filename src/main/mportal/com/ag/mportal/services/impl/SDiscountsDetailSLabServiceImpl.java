package com.ag.mportal.services.impl;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.mportal.entity.SDiscountsDetailSlab;
import com.ag.mportal.services.SDiscountsDetailSlabService;

@Service
public class SDiscountsDetailSLabServiceImpl implements SDiscountsDetailSlabService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(SDiscountsDetailSlab detailSlab, Integer discountId) {
		try {
			Query queryMaxMaster = null;

			if (DBUtil.getDialect() == 1) {
				queryMaxMaster = entityManager.createNamedQuery("SDiscountsDetailSlab.getMaxIdMssql");
			} else {
				queryMaxMaster = entityManager.createNamedQuery("SDiscountsDetailSlab.getMaxId");
			}
			
			
			Integer maxId = (Integer) queryMaxMaster.getSingleResult();
			if (Objects.isNull(maxId)) {
				detailSlab.setSourceId(1 + "".trim());
			} else {
				detailSlab.setSourceId(maxId.toString().trim());
			}
			detailSlab.setDiscId(discountId.toString());
			entityManager.persist(detailSlab);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE INSERTION OF SLAB", ex);
		}

	}

	@Override
	@Transactional
	public List<SDiscountsDetailSlab> getSlabByDiscountId(List<String> lstDiscountId) {
		List<SDiscountsDetailSlab> lstSlab = null;
		try {
			Query query = entityManager.createNamedQuery("SDiscountsDetailSlab.getSlabByDiscountId",
					SDiscountsDetailSlab.class);
			query.setParameter("discId", lstDiscountId);

			lstSlab = (List<SDiscountsDetailSlab>) query.getResultList();

			if (!Objects.isNull(lstSlab) && !lstSlab.isEmpty()) {
				return lstSlab;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FETCHING SLAB BY DISCOUNT ID: ", ex);
		}

		return null;

	}

	@Override
	@Transactional
	public boolean update(SDiscountsDetailSlab sDiscountsDetailSlab) {

		try {
			entityManager.merge(sDiscountsDetailSlab);
			return true;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE UPDATING SLAB: ", ex);
		}
		return false;
	}

}
