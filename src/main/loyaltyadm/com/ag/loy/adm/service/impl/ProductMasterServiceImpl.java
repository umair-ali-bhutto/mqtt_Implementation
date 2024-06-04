package com.ag.loy.adm.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.ProductMaster;
import com.ag.loy.adm.service.ProductMasterService;

@Service
public class ProductMasterServiceImpl implements ProductMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveProductMaster(ProductMaster sdt) {
		try {
			entityManager.persist(sdt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void updateProductMaster(ProductMaster sdt) {
		try {
			entityManager.merge(sdt);
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}

	@Override
	public ProductMaster fetchAllRecordById(String id, String corpId) {
		try {
			Query query = entityManager.createNamedQuery("ProductMaster.retrieveById");
			query.setParameter("corpId", corpId);
			query.setParameter("productId", id);
			return (ProductMaster) query.getSingleResult();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING PRD MASTER: ", ex);
		}
		return null;
	}

	@Override
	public HashMap<Object, Object> fetchAllRecordMap(String corpId) {
		HashMap<Object, Object> mp = new HashMap<Object, Object>();
		try {
			Query query = entityManager.createNamedQuery("ProductMaster.retrieveAll");
			query.setParameter("corpId", corpId);
			List<ProductMaster> lst = (List<ProductMaster>) query.getResultList();
			for (ProductMaster m : lst) {
				mp.put(m.getProductId(), m.getMappedId());
			}

			return mp;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING PRD MASTER: ", ex);
		}
		return null;
	}

	@Override
	public List<ProductMaster> fetchAllRecord(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("ProductMaster.retrieveAll");
			query.setParameter("corpId", corpId);
			List<ProductMaster> lst = (List<ProductMaster>) query.getResultList();

			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING PRD MASTER: ", ex);
		}
		return null;
	}

	@Override
	public String fetchMaxId() {
		String res = "";
		try {
			String q = "SELECT MAX(PRODUCT_ID) FROM PRODUCT_MASTER";
			Query query = entityManager.createNativeQuery(q);
			res = String.valueOf(query.getSingleResult());
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION : ", ex);
		}
		return res;
	}

}