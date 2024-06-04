package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.util.AgLogger;
import com.ag.metro.model.GenericLovModel;
import com.ag.mportal.entity.PreauthMaster;
import com.ag.mportal.services.PreauthMasterService;

@Service
public class PreauthMasterServiceImpl implements PreauthMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<GenericLovModel> FetchAllActiveConfig() {
		List<GenericLovModel> lstMdl = new ArrayList<GenericLovModel>();
		try {
			Query cb = entityManager.createNamedQuery("PreauthMaster.FetchAllActiveConfig");

			List<PreauthMaster> lst = cb.getResultList();
			GenericLovModel m = new GenericLovModel();
			m.setId("-");
			m.setValue("Select");
			lstMdl.add(m);
			if (lst != null) {
				
				for (PreauthMaster p : lst) {
					GenericLovModel g = new GenericLovModel();
					g.setId(p.getPaid() + "");
					g.setValue(p.getName());
					lstMdl.add(g);
				}

			}

			return lstMdl;
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
		return null;
	}
	@Override
	public List<GenericLovModel> FetchAllConfig() {
		List<GenericLovModel> lstMdl = new ArrayList<GenericLovModel>();
		try {
			Query cb = entityManager.createNamedQuery("PreauthMaster.findAll");

			List<PreauthMaster> lst = cb.getResultList();
			GenericLovModel m = new GenericLovModel();
			m.setId("-");
			m.setValue("N/A");
			lstMdl.add(m);
			if (lst != null) {
				
				for (PreauthMaster p : lst) {
					GenericLovModel g = new GenericLovModel();
					g.setId(p.getPaid() + "");
					g.setValue(p.getName());
					lstMdl.add(g);
				}

			}

			return lstMdl;
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Boolean insert(PreauthMaster preauthMaster) {
		try {
			entityManager.persist(preauthMaster);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<PreauthMaster> fetchData(String cardEntry, String status) {
		List<PreauthMaster> lst = new ArrayList<PreauthMaster>();
		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String m = "";
//			if (dateFrom != null) {
//
//				m += " AND (" + DBUtil.getDateQueryParam("ENTRY_DATE") + " between '" + 
//				sdf.format(dateFrom) + "' AND '" + sdf.format(dateTo) + "') ";
//				
//				m += " AND DATE_FROM = '" + sdf.format(dateFrom) + "' ";
//			}
//			if(dateTo != null) {
//				m += " AND DATE_TO = '" + sdf.format(dateTo) + "' ";
//			}
			if (cardEntry != null) {
				m += " AND (SUB_SEG = '" + cardEntry + "') ";
			}
			if (status != null) {
				m += " AND (ACTIVE = '" + status + "') ";
			}
			
			String query = "SELECT * FROM PREAUTH_MASTER WHERE PAID IS NOT NULL " + m + " ";
			Query cb = entityManager.createNativeQuery(query,PreauthMaster.class);
			lst =(List<PreauthMaster>)  cb.getResultList();
			AgLogger.logDebug("", "Query: " + query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@Override
	public PreauthMaster fetchByID(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("PreauthMaster.fetchByID").setParameter("id",
					id);
			return (PreauthMaster) cb.getSingleResult();
		} catch (Exception nre) {
			nre.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	@Transactional
	public boolean update(PreauthMaster preauthMaster) {
		try {
			entityManager.merge(preauthMaster);
			return true;
		} catch (Exception e) {
			
		}
		return false;
		
	}
	
	
	
	@Override
	public List<PreauthMaster> fetchAll() {
		List<PreauthMaster> lst = new ArrayList<PreauthMaster>();
		try {		
			String query = "SELECT * FROM PREAUTH_MASTER WHERE PAID IS NOT NULL AND ACTIVE='Y'  ";
			Query cb = entityManager.createNativeQuery(query,PreauthMaster.class);
			lst =(List<PreauthMaster>)  cb.getResultList();
			AgLogger.logDebug("", "Query: " + query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

}
