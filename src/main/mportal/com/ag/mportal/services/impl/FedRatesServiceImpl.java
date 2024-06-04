package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.FedRates;
import com.ag.mportal.services.FedRateService;
@Service
public class FedRatesServiceImpl implements FedRateService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<FedRates> retrieveAll() {

		List<FedRates> lstFedRates = null;
		
		try {
			Query query = entityManager.createNamedQuery("FedRates.retrieveAll");
		
			lstFedRates = (List<FedRates>) query.getResultList();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		} 

		if(!lstFedRates.isEmpty()) {
			return lstFedRates;
		}else {
			return null;
		}
	}

	@Override
	public List<FedRates> retrieveAllProvinces() {
		List<FedRates> lstFedRates = null;
		Session sess = null;
		Transaction tran = null;
//		try {
//
//			SessionFactory sessFact = SessionFactories.getSessionFactory();
//			sess = sessFact.openSession();
//			tran = sess.beginTransaction();
//			Criteria ct = sess.createCriteria(FedRates.class)
//					.setProjection(Projections.projectionList()
//							.add(Projections.property("province"),"province")
//							.add(Projections.property("provinceCode"),"provinceCode")
//							);
//			ct.add(Restrictions.eq("status", 1));
//			lstFedRates = (List<FedRates>) ct.list();
//			tran.commit();
//			// DoPrint.doPrint("RECORD RETRIVED....");
//		} catch (Exception e) {
//			DoPrint.doPrint("EXCEPTION " + e);
//			e.printStackTrace();
//		} finally {
//			if (sess != null && (sess.isOpen() || sess.isConnected()))
//				sess.close();
//		}

		return lstFedRates;
	}

	@Override
	public FedRates retrieveRateByProvince(String provinceName) {
		List<FedRates> lstFedRates = null;
		Session sess = null;
		Transaction tran = null;
		try {
			Query query = entityManager.createNamedQuery("FedRates.retrieveByProvinceName");
			query.setParameter("provinceName", provinceName);
			lstFedRates = (List<FedRates>) query.getResultList();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

		if(!lstFedRates.isEmpty()) {
			return (FedRates)lstFedRates.get(0);
		}else {
			return null;
		}
		
	}
	
	

}
