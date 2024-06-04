package com.ag.mportal.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.mportal.entity.VwPreauth;
import com.ag.mportal.services.VwPreauthService;

@Service
public class VwPreauthServiceImpl implements VwPreauthService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<VwPreauth> fetchAll() {
		List<VwPreauth> lst = new ArrayList<VwPreauth>();
		try {
			Query cb = entityManager.createNamedQuery("VwPreauth.fetchAll");
			lst = (List<VwPreauth>) cb.getResultList();
			AgLogger.logDebug("", "Query: " + cb);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@Override
	public List<VwPreauth> fetchByParams(List<String> merchant, Date from, Date to, String authId, String status,
			int numberOfRows, int pageNumber) {
		try {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				String whereClause = new String();

				if (from != null && to != null) {
					whereClause += " AND (" + DBUtil.getDateQueryParam("ENTRYDATE") + " between '" + sdf.format(from)
							+ "' AND '" + sdf.format(to) + "') ";
				}

				if (merchant.size() != 0) {
					String jk = "";
					for (String mer : merchant) {
						jk += "'" + mer + "',";
					}

					if (jk.length() != 0) {
						jk = jk.substring(0, jk.length() - 1);
					}

					whereClause += " AND MERCHANTID IN (" + jk + ")";
				}

				if (authId != null) {
					whereClause += " AND AUTHID = '" + authId + "' ";
				}
				if (status != null && !status.equals("ALL")) {
					whereClause += " AND AUTH_STATUS = '" + status + "' ";
				}

				String quer = "SELECT * FROM VW_PREAUTH WHERE TXNDETAILID IS NOT NULL " + whereClause
						+ " ORDER BY TXNDETAILID DESC";

				if (numberOfRows != 0) {
					int startingValue = 0;
					int endingValue = 0;
					quer = quer + " OFFSET @startingValue ROWS FETCH NEXT @endingValue ROWS ONLY ";
					startingValue = numberOfRows * pageNumber;
					endingValue = numberOfRows;
					quer = quer.replace("@startingValue", String.valueOf(startingValue));
					quer = quer.replace("@endingValue", String.valueOf(endingValue));
				}

				AgLogger.logDebug(VwPreauthServiceImpl.class, quer);

				Query cb = entityManager.createNativeQuery(quer, VwPreauth.class);
				@SuppressWarnings("unchecked")
				List<VwPreauth> lst = cb.getResultList();
				return lst;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String fetchByParamsCount(List<String> merchant, Date from, Date to, String authId, String status) {
		try {
			String count = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("ENTRYDATE") + " between '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}

			if (merchant.size() != 0) {
				String jk = "";
				for (String mer : merchant) {
					jk += "'" + mer + "',";
				}

				if (jk.length() != 0) {
					jk = jk.substring(0, jk.length() - 1);
				}

				whereClause += " AND MERCHANTID IN (" + jk + ")";
			}

			if (authId != null) {
				whereClause += " AND AUTHID = '" + authId + "' ";
			}
			if (status != null && !status.equals("ALL")) {
				whereClause += " AND AUTH_STATUS = '" + status + "' ";
			}
			String quer = "SELECT COUNT(*) as 'COUNT' FROM VW_PREAUTH WHERE TXNDETAILID IS NOT NULL " + whereClause;

			AgLogger.logDebug(VwPreauthServiceImpl.class, quer);

			Query cb = entityManager.createNativeQuery(quer);
			int res = (int) cb.getSingleResult();
			count = String.valueOf(res);

			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
