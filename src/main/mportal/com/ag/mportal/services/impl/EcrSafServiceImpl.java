package com.ag.mportal.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.EcrSaf;
import com.ag.mportal.services.EcrSafService;

@Service
public class EcrSafServiceImpl implements EcrSafService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insert(EcrSaf tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return 0l;
		}
	}

	@Override
	@Transactional
	public void update(EcrSaf tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public List<EcrSaf> fetchAllSaf() {
		List<EcrSaf> lstFedRates = null;
		Session sess = null;
		try {
			// For SQL SERVER
//	        LocalDateTime currentTimestamp = LocalDateTime.now();	        
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");	        
//	        String formattedTimestamp = currentTimestamp.format(formatter);	        
//			String sqlQuery = AppProp.getProperty("fetch.all.saf.query");	 
//			String replacedQuery = sqlQuery.replace("@PARAMDATE", formattedTimestamp);	 
//			AgLogger.logInfo("ECR SAF FETCH ALL QUERY: "+replacedQuery);
			
			// FOR ORACLE
	        LocalDateTime currentTimestamp = LocalDateTime.now();	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");	        
	        String formattedTimestamp = currentTimestamp.format(formatter);	        
			String sqlQuery = AppProp.getProperty("fetch.all.saf.query");	 
			String replacedQuery = sqlQuery.replace("@PARAMDATE", formattedTimestamp);	 
			AgLogger.logInfo("ECR SAF FETCH ALL QUERY: "+replacedQuery);
			
			
			
			
			
			
			Query query = entityManager.createNativeQuery(replacedQuery,EcrSaf.class);
			lstFedRates = (List<EcrSaf>) query.getResultList();
			return lstFedRates;
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}
	

	
//	public String convertToCronExpression(LocalDateTime dateTime) {
//        int second = dateTime.getSecond();
//        int minute = dateTime.getMinute();
//        int hour = dateTime.getHour();
//        int day = dateTime.getDayOfMonth();
//        int month = dateTime.getMonthValue();
//
//        // Convert to cron expression format: second minute hour day month ?
//        String cronExpression = String.format("%d %d %d %d %d ?", second, minute, hour, day, month);
//
//        return cronExpression;
//    }

}
