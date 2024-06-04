package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.service.LoyaltyAdminService;
import com.ag.metro.model.GenericLovModel;

@Service
public class LoyaltyAdminServiceImpl implements LoyaltyAdminService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String getCidFromAccountUser(String corpId, String cardNumber) {
		try {
			String sql = "select userid  from account_users d where corpid ='" + corpId + "' and cid = '" + cardNumber
					+ "' and cr_on = (select max(j.cr_on) from account_users j where j.cid=d.cid)";
			AgLogger.logInfo(sql);
			Query cb = entityManager.createNativeQuery(sql);
			return (String) cb.getSingleResult();
		} catch (NoResultException nre) {
			return "N/A";
		}
	}

	@Override
	public List<GenericLovModel> fetchCustomerTypes(String corpId) {
		List<GenericLovModel> mdlList = new ArrayList<GenericLovModel>();
		try {
			String q = AppProp.getProperty("loy.adm.fetch.cust.type");
			String sql = q.replace("@corpid", "'" + corpId + "'");
			AgLogger.logInfo(sql);
			Query cb = entityManager.createNativeQuery(sql);
			List<Object[]> objList = (List<Object[]>) cb.getResultList();
			if (objList != null) {
				for (Object[] o : objList) {
					AgLogger.logInfo(o[0].toString());
					AgLogger.logInfo(o[1].toString());
					GenericLovModel gModel = new GenericLovModel();
					gModel.setId(o[0].toString());
					gModel.setValue(o[1].toString());
					mdlList.add(gModel);
				}
			}
			return mdlList;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String[] isRecordExist(String cardNumber, String name, String cnic, String corpId) {
		String[] result = new String[5];
		try {

			String queryString = AppProp.getProperty("cust.prof.valid.query");
			queryString += " @whereClause";
			String whereClause = "";

			if (!Objects.isNull(corpId)) {
				whereClause += " m.CORPID = '" + corpId + "' ";
			}

			if (!Objects.isNull(cardNumber)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += "d.USERID = '" + cardNumber + "' ";
			}
			if (!Objects.isNull(name)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += "d.USERDISPNAME LIKE '%" + name + "%' ";
			}
			if (!Objects.isNull(cnic)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += "m.CNIC ='" + cnic + "' ";
			}

			queryString = queryString.replace("@whereClause", whereClause);

			AgLogger.logInfo(queryString);

			Query query = entityManager.createNativeQuery(queryString);

			Object[] obj = (Object[]) query.setMaxResults(1).getSingleResult();

			if (obj != null) {
				result[0] = "0000";
				// cid
				result[1] = obj[0].toString();
				// nameoncard
				result[2] = obj[2].toString();
				// cardnumber
				result[3] = obj[3].toString();
				// accid
				result[4] = obj[4].toString();

			} else {
				result[0] = "0002";
			}
		} catch (NoResultException e) {
			result[0] = "9992";
		}

		return result;
	}
	
	
	@Override
	public String fetchCid(String userId,String corpId) {
		String cid = null;
		try {
			String q = AppProp.getProperty("fetch.cid");
			String sql = q.replace("@corpid", "'" + corpId + "'");
			sql = sql.replace("@userid", "'" + userId + "'");
			
			AgLogger.logInfo(sql);
			Query cb = entityManager.createNativeQuery(sql);
			String obj = (String) cb.setMaxResults(1).getSingleResult();
			if (obj != null) {
				cid = obj;
			}
			return cid;
		} catch (NoResultException nre) {
			return null;
		}
	}
	
}
