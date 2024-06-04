package com.ag.mportal.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.MerConfDetails;
import com.ag.mportal.entity.MerConfMaster;
import com.ag.mportal.model.ViewOfflineSaleModel;
import com.ag.mportal.model.ViewR1R2MarketSegmebtThresholdModel;
import com.ag.mportal.services.MerConfMasterService;

@Service
public class MerConfMasterServiceImpl implements MerConfMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insert(MerConfMaster tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return 0l;
		}
	}

	@Override
	@Transactional
	public void update(MerConfMaster tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public MerConfMaster fetchByMidTid(String mid, String tid, String type) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From MerMasterConfig");
			Query cb = entityManager.createNamedQuery("MerConfMaster.fetchByMidTid");
			cb.setParameter("mid", mid);
			cb.setParameter("tid", tid);
			cb.setParameter("type", type);
			AgLogger.logInfo(cb + "");
			return (MerConfMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<ViewOfflineSaleModel> fetchData(String mid, String tid) {
		List<ViewOfflineSaleModel> lst = new ArrayList<ViewOfflineSaleModel>();
		try {
			String query = AppProp.getProperty("view.offline.sale.query");

			if (mid != null && !mid.equals("null")) {
				query += " AND MID = '" + mid + "'";
			}

			if (tid != null && !tid.equals("null")) {
				query += " AND TID = '" + tid + "'";
			}
			AgLogger.logInfo(query);

			Query cb = entityManager.createNativeQuery(query);

			List<Object[]> temp = (List<Object[]>) cb.getResultList();
			for (Object[] obj : temp) {
				ViewOfflineSaleModel mdl = new ViewOfflineSaleModel();

				mdl.setId(Long.parseLong(obj[0].toString()));
				mdl.setMid(obj[1].toString());
				mdl.setTid(obj[2].toString());
				mdl.setEntryDate((Timestamp) obj[3]);
				mdl.setEntryBy(obj[4].toString());
				mdl.setUpdateDate(obj[5] == null ? null : (Timestamp) obj[5]);
				mdl.setUpdatedBy(obj[6] == null ? null : (String) obj[6]);
				mdl.setMinAmountThreshold(obj[7].toString());
				mdl.setMinAmountThresholdId(Integer.parseInt(obj[8].toString()));
				mdl.setMaxAmountThreshold(obj[9].toString());
				mdl.setMaxAmountThresholdId(Integer.parseInt(obj[10].toString()));
				mdl.setThresholdCount(obj[11].toString());
				mdl.setThresholdCountId(Integer.parseInt(obj[12].toString()));
				mdl.setIsActive(Integer.parseInt(obj[13].toString()));

				lst.add(mdl);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}
	
	public List<ViewR1R2MarketSegmebtThresholdModel> fetchR1R2Data(String mid, String tid) {
		List<ViewR1R2MarketSegmebtThresholdModel> lst = new ArrayList<ViewR1R2MarketSegmebtThresholdModel>();
		try {
			String query = AppProp.getProperty("view.r1.r2.market.segment.threshold.query");

			if (mid != null && !mid.equals("null")) {
				query += " AND MID = '" + mid + "'";
			}

			if (tid != null && !tid.equals("null")) {
				query += " AND TID = '" + tid + "'";
			}
			AgLogger.logInfo(query);

			Query cb = entityManager.createNativeQuery(query);

			List<Object[]> temp = (List<Object[]>) cb.getResultList();
			for (Object[] obj : temp) {
				ViewR1R2MarketSegmebtThresholdModel mdl = new ViewR1R2MarketSegmebtThresholdModel();

				mdl.setId(Long.parseLong(obj[0].toString()));
				mdl.setMid(obj[1].toString());
				mdl.setTid(obj[2].toString());
				mdl.setEntryDate((Timestamp) obj[3]);
				mdl.setEntryBy(obj[4].toString());
				mdl.setUpdateDate(obj[5] == null ? null : (Timestamp) obj[5]);
				mdl.setUpdatedBy(obj[6] == null ? null : (String) obj[6]);
				mdl.setR1CreditThreshold(obj[7].toString());
				mdl.setR1CreditThresholdId(Integer.parseInt(obj[8].toString()));
				mdl.setR1DebitThreshold(obj[9].toString());
				mdl.setR1DebitThresholdId(Integer.parseInt(obj[10].toString()));
				mdl.setR2CreditThreshold(obj[11].toString());
				mdl.setR2CreditThresholdId(Integer.parseInt(obj[12].toString()));
				mdl.setR2DebitThreshold(obj[13].toString());
				mdl.setR2DebitThresholdId(Integer.parseInt(obj[14].toString()));
				mdl.setIsActive(Integer.parseInt(obj[15].toString()));

				lst.add(mdl);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@Override
	public List<MerConfMaster> fetchAllByMidTid(String mid, String tid) {
		List<MerConfMaster> lstFedRates = null;
		Session sess = null;
		try {
			String query = AppProp.getProperty("view.build.control.query");

			if (mid != null && !mid.equals("null")) {
				query += " AND MID = '" + mid + "'";
			}

			if (tid != null && !tid.equals("null")) {
				query += " AND TID = '" + tid + "'";
			}
			AgLogger.logInfo(query);

			Query cb = entityManager.createNativeQuery(query, MerConfMaster.class);
			lstFedRates = (List<MerConfMaster>) cb.getResultList();
			return lstFedRates;
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}

	@Override
	public MerConfMaster fetchAllById(Long ids) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From MerMasterConfig");
			Query cb = entityManager.createNamedQuery("MerConfMaster.fetchById");
			cb.setParameter("ids", ids);
			AgLogger.logInfo(cb + "");
			return (MerConfMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<MerConfMaster> fetchDataByMidTid(String mid, String tid) {
		List<MerConfMaster> lst = new ArrayList<MerConfMaster>();
		try {
			String query = "SELECT * FROM MER_CONF_MASTER WHERE TYPE = 'DONATION' ";

			if (mid != null && !mid.equals("null")) {
				query += " AND MID = '" + mid + "'";
			}

			if (tid != null && !tid.equals("null")) {
				query += " AND TID = '" + tid + "'";
			}
			query += " ORDER BY ID DESC ";
			AgLogger.logInfo(query);

			Query cb = entityManager.createNativeQuery(query, MerConfMaster.class);

			lst = (List<MerConfMaster>) cb.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@Override
	public HashMap<String,HashMap<String, String>> fetchRecordForHeartBeat(String mid, String tid, List<String> types) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From MerMasterConfig");
			Query cb = entityManager.createNamedQuery("MerConfMaster.fetchheartBeatData");
			cb.setParameter("mid", mid);
			cb.setParameter("tid", tid);
			cb.setParameter("type", types);
			@SuppressWarnings("unchecked")
			List<Object[]> dataObjects = (List<Object[]>) cb.getResultList();
			AgLogger.logInfo(dataObjects.size()+"");
			HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String,String>>();
			for(Object[] o:dataObjects) {
				if(data.containsKey(o[0].toString())) {
					HashMap<String, String> temp = data.get(o[0].toString());
					data.remove(o[0].toString());
					temp.put(o[1].toString(), o[2].toString());
					data.put(o[0].toString(), temp);
				}else {
					HashMap<String, String> d = new HashMap<String, String>();
					d.put(o[1].toString(), o[2].toString());
					data.put(o[0].toString(), d);
				}
			}
 			
			return data;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String fetchRecordForHeartBeatBatch(String mid, String tid) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From MerMasterConfig");
			AgLogger.logDebug(getClass(), "Fetching Rec For HeatrBeat");			
			
			String query1 = AppProp.getProperty("hb.query1").replaceAll("@PARAM1", mid).replaceAll("@PARAM2", tid);
			String query2 = AppProp.getProperty("hb.query2").replaceAll("@PARAM1", mid).replaceAll("@PARAM2", tid);
			AgLogger.logInfo("QUERY 1: "+query1);
			AgLogger.logInfo("QUERY 2: "+query2);

			Query cb1 = entityManager.createNativeQuery(query1);
			Query cb2 = entityManager.createNativeQuery(query2);
			String myTxnBatchNo = (String) cb1.getSingleResult();
			String mySetBatchNo = (String) cb2.getSingleResult();

			int mySetBatchNoFinal = Integer.parseInt(mySetBatchNo) + 1;
			int myTxnBatchNoFinal = Integer.parseInt(myTxnBatchNo);
			if (mySetBatchNoFinal > myTxnBatchNoFinal) {
				return mySetBatchNoFinal + "";
			} else {
				return myTxnBatchNoFinal + "";
			}

		} catch (NoResultException nre) {
			return null;
		} catch (Exception nre) {
			nre.printStackTrace();
			return null;
		}
	}

}
