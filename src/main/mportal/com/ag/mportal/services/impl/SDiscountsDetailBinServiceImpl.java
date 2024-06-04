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
import com.ag.mportal.entity.SDiscountsDetailBin;
import com.ag.mportal.services.SDiscountsDetailBinService;

@Service
public class SDiscountsDetailBinServiceImpl implements SDiscountsDetailBinService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(List<SDiscountsDetailBin> lstDetailBin,Integer discId) {
		try {
			short binNo = 0;
			for(SDiscountsDetailBin detailBin : lstDetailBin) {
				binNo++;
				
				Query queryMaxMaster = null;

				if (DBUtil.getDialect() == 1) {
					queryMaxMaster = entityManager.createNamedQuery("SDiscountsDetailBin.getMaxIdMssql");
				} else {
					queryMaxMaster = entityManager.createNamedQuery("SDiscountsDetailBin.getMaxId");
				}
				Integer maxId = (Integer) queryMaxMaster.getSingleResult();
				detailBin.setDiscId(discId.toString());
				detailBin.setBinNo(binNo);
				if(Objects.isNull(maxId)) {
					detailBin.setSourceId(1+"".trim());
				}else {
					detailBin.setSourceId(maxId.toString().trim());
				}
				entityManager.persist(detailBin);
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE INSERTION OF BINS BY DISCOUNT ID: "+discId+" ", ex);
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SDiscountsDetailBin> fetchListBinByDiscountId(List<String> lstDiscountId) {
		List<SDiscountsDetailBin> lstBin = null;
		try {
			Query query = entityManager.createNamedQuery("SDiscountsDetailBin.getLstBinByDiscountId", SDiscountsDetailBin.class);
			query.setParameter("lstDiscountId", lstDiscountId);
			
			lstBin = (List<SDiscountsDetailBin>) query.getResultList();
			
			AgLogger.logInfo(getClass(), "LIST BIN FETCHED SIZE OF "+lstBin.size());
			if(!Objects.isNull(lstBin) && !lstBin.isEmpty()) {
				return lstBin;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FETCHING OF BIN LIST: ", ex);
			ex.printStackTrace();
		}
		return null;	
	}
	
	@Override
	@Transactional
	public boolean deleteRecordByDiscountId(String discountId) {
		List<SDiscountsDetailBin> lstBin = null;
		try {
			Query query = entityManager.createNamedQuery("SDiscountsDetailBin.deleteByDiscountId");
			query.setParameter("discId", discountId);
			
			query.executeUpdate();
			
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;	
	}

}
