package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountBinProdConfig;
import com.ag.mportal.services.DiscountBinProdConfigService;

@Service
public class DiscountBinProdConfigServiceImpl implements DiscountBinProdConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DiscountBinProdConfig tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountBinProdConfig: ", ex);
		}
	}

	@Override
	@Transactional
	public void update(DiscountBinProdConfig tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountBinProdConfig: ", ex);
		}
	}

	@Override
	public List<DiscountBinProdConfig> fetchByCorpId(String corpId) {
		Query cb = null;
		List<DiscountBinProdConfig> lstFiles = null;
		try {
			cb = entityManager.createNamedQuery("DiscountBinProdConfig.fetchByCorpId");
			cb.setParameter("corpId", corpId);
			lstFiles = (List<DiscountBinProdConfig>) cb.getResultList();

			if (!Objects.isNull(lstFiles) && lstFiles.size() > 0) {
				return lstFiles;
			} else {
				return null;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING DiscountBinProdConfig fetchByCorpId: ", ex);
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountBinProdConfig> fetchByBin(String fromBin, String toBin, String corpId) {
		List<DiscountBinProdConfig> lst = new ArrayList<DiscountBinProdConfig>();
		try {
			String query = "SELECT * FROM DISC_BIN_PROD_CONFIG WHERE CORP_ID = '" + corpId
					+ "' AND (CAST(BIN AS INTEGER) BETWEEN " + fromBin + " AND " + toBin + " ) AND IS_ACTIVE = 1";
			AgLogger.logInfo("Disc Bin Product Query: " + query);
			Query q = entityManager.createNativeQuery(query, DiscountBinProdConfig.class);
			lst = (List<DiscountBinProdConfig>) q.getResultList();
		} catch (NoResultException nre) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

}