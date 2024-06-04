package com.ag.metro.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.metro.model.VwMetroRepParametersModel;
import com.ag.metro.services.MetroUtilService;
import com.ag.mportal.model.TxnSummaryModel;

@Service
public class MetroUtilServiceImpl implements MetroUtilService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilAccess utilAccess;
	
	@Override
	public List<VwMetroRepParametersModel> getLovsOfMetro() {
		List<VwMetroRepParametersModel> lst = new ArrayList<VwMetroRepParametersModel>();
		String query= "select * from VW_METRO_REP_PARAMETER WHERE A is not null";
		AgLogger.logDebug("0","Fetching Rec From getLovsOfMetro " + query);
		try {
			Query cb = entityManager.createNativeQuery(query);
			List<Object[]> l = (List<Object[]>) cb.getResultList();
			for(Object[] o:l) {
				VwMetroRepParametersModel j = new VwMetroRepParametersModel();
				j.setA(o[0]+"");
				j.setB(o[1]+"");
				j.setTYPE(o[2]+"");
				lst.add(j);
			}
			
		} catch (NoResultException nre) {
			AgLogger.logInfo("No Rec Found From VW METRO RPT VIEW");
		}
		
		return lst;
	}
	
	
	@Override
	public TxnSummaryModel fetchMetroDashboard() {
		
		TxnSummaryModel mdlList = new TxnSummaryModel();
		List<Object[]> objs = null;
		try {
		
			String quer =AppProp.getProperty("txn.summary.rept.orcl.metro");

			Query cb = entityManager.createNativeQuery(quer);
			objs = (List<Object[]>) cb.getResultList();
			
			
			mdlList.setCashOutAmount("0");
			mdlList.setCashOutCount(0);
			mdlList.setRedeemAmount("0");
			mdlList.setRedeemCount(0);
			mdlList.setRefundAmount("0");
			mdlList.setRefundCount(0);
			mdlList.setSaleAmount("0");
			mdlList.setSaleCount(0);
			mdlList.setTotalAmount("0");
			mdlList.setTotalCount(0);
			mdlList.setVoidAmount("0");
			mdlList.setVoidCount(0);
			
			//AgLogger.logInfo(objs.get(0)[0]+"|"+objs.get(0)[1]+"|"+objs.get(0)[2]);
			for(Object[] t:objs) {
				if(t[0].equals("TOPUP")) {
					mdlList.setRefundAmount(objs.get(0)[1].toString());
					mdlList.setRefundCount(Integer.parseInt(objs.get(0)[2].toString()));
					
				}
				if(t[0].equals("REDEEM")) {
					mdlList.setTotalAmount(objs.get(1)[1].toString());
					mdlList.setTotalCount(Integer.parseInt(objs.get(1)[2].toString()));
				}
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return mdlList;
	}

}
