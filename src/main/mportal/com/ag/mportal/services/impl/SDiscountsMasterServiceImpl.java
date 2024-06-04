package com.ag.mportal.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.mportal.entity.SDiscountsDetailBin;
import com.ag.mportal.entity.SDiscountsDetailSlab;
import com.ag.mportal.entity.SDiscountsMaster;
import com.ag.mportal.services.SDiscountsMasterService;

@Service
public class SDiscountsMasterServiceImpl implements SDiscountsMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Integer insert(SDiscountsMaster discountMaster, SDiscountsDetailSlab detailSlab,
			ArrayList<SDiscountsDetailBin> lstDetailBin) {
		try {
			Query queryMaxMaster = null;

			if (DBUtil.getDialect() == 1) {
				queryMaxMaster = entityManager.createNamedQuery("SDiscountsMaster.getMaxIdMssql");
			} else {
				queryMaxMaster = entityManager.createNamedQuery("SDiscountsMaster.getMaxId");
			}
			Integer maxId = (Integer) queryMaxMaster.getSingleResult();
			discountMaster.setDiscId(String.valueOf(maxId));
			entityManager.persist(discountMaster);

			short binNo = 0;
			for (SDiscountsDetailBin detailBin : lstDetailBin) {
				binNo++;
				Query query = null;
				if (DBUtil.getDialect() == 1) {
					query = entityManager.createNamedQuery("SDiscountsDetailBin.getMaxIdMssql");
				} else {
					query = entityManager.createNamedQuery("SDiscountsDetailBin.getMaxId");
				}

				Integer maxBinId = (Integer) query.getSingleResult();
				detailBin.setDiscId(maxId.toString());
				detailBin.setBinNo(binNo);
				if (Objects.isNull(maxBinId)) {
					detailBin.setSourceId(1 + "".trim());
				} else {
					detailBin.setSourceId(maxBinId.toString().trim());
				}
				AgLogger.logDebug("0",detailBin.toString() + "..........");
				entityManager.persist(detailBin);
			}

			Query queryMaxSlab = null;
			if (DBUtil.getDialect() == 1) {
				queryMaxSlab = entityManager.createNamedQuery("SDiscountsDetailSlab.getMaxIdMssql");
			} else {
				queryMaxSlab = entityManager.createNamedQuery("SDiscountsDetailSlab.getMaxId");
			}
			Integer maxSlabId = (Integer) queryMaxSlab.getSingleResult();
			if (Objects.isNull(maxSlabId)) {
				detailSlab.setSourceId(1 + "".trim());
			} else {
				detailSlab.setSourceId(maxSlabId.toString().trim());
			}
			detailSlab.setDiscId(maxId.toString().trim());
			entityManager.persist(detailSlab);

			return maxId;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE CREATING DISCOUNT ENTRIES: ", ex);
		}
		return null;
	}

	@Override
	public List<SDiscountsMaster> fetchLstDiscountMaster(String name, String status, String startDate, String endDate) {

		try {
			String whereClause = "";
			if (!Objects.isNull(name)) {
				whereClause += " NAME='" + name + "' ";
			}

			if (!Objects.isNull(endDate)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " DATE_TO='" + endDate + "' ";
			}

			if (!Objects.isNull(startDate)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " DATE_FROM='" + startDate + "' ";
			}

			if (!Objects.isNull(status) && !status.equalsIgnoreCase("all")) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " ACTIVE= '" + status + "' ";
			}

			String queryString = AppProp.getProperty("select.discount.master");

			if (!whereClause.isEmpty()) {
				String st = " WHERE " + whereClause;
				queryString = queryString.replace("@whereClause", st);
			} else {
				queryString = queryString.replace("@whereClause", "");
			}

			queryString = queryString + " ORDER BY CR_ON DESC";

			AgLogger.logDebug(SDiscountsMasterServiceImpl.class, queryString);

			Query cb = entityManager.createNativeQuery(queryString, SDiscountsMaster.class);

			List<SDiscountsMaster> lst = (List<SDiscountsMaster>) cb.getResultList();

			AgLogger.logDebug(getClass(), "FETCHED LIST DISCOUNT MASTERS SIZE: " + lst.size());
			if (!Objects.isNull(lst) && !lst.isEmpty()) {
				return lst;
			}

		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING DISCOUNT MASTERS: ", ex);
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public SDiscountsMaster fetchByDiscountId(String discountId) {
		try {
			Query query = entityManager.createNamedQuery("SDiscountsMaster.getRecordByDiscountId",
					SDiscountsMaster.class);
			query.setParameter("discId", discountId);
			SDiscountsMaster discountMaster = (SDiscountsMaster) query.getSingleResult();
			return discountMaster;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING DISCOUNT MASTER BY DISCOUNT ID: ", ex);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean update(SDiscountsMaster sDiscountsMaster, SDiscountsDetailSlab sDiscountsDetailSlab,
			ArrayList<SDiscountsDetailBin> lstDetailBin, String oldBinStatus) {
		try {

			if (oldBinStatus.equals("N")) {
				Query queryDelete = entityManager.createNamedQuery("SDiscountsDetailBin.deleteByDiscountId");
				queryDelete.setParameter("discId", sDiscountsMaster.getDiscId());
				queryDelete.setParameter("updBy", sDiscountsMaster.getUpdBy());
				queryDelete.setParameter("updOn", new Timestamp(new java.util.Date().getTime()));
				queryDelete.executeUpdate();
			}

			entityManager.merge(sDiscountsMaster);
			entityManager.merge(sDiscountsDetailSlab);
			short binNo = 0;
			for (SDiscountsDetailBin detailBin : lstDetailBin) {
				binNo++;
				Query query = null;
				if (DBUtil.getDialect() == 1) {
					query = entityManager.createNamedQuery("SDiscountsDetailBin.getMaxIdMssql");
				} else {
					query = entityManager.createNamedQuery("SDiscountsDetailBin.getMaxId");
				}
				Integer maxId = (Integer) query.getSingleResult();
				detailBin.setDiscId(sDiscountsMaster.getDiscId());
				detailBin.setBinNo(binNo);
				if (Objects.isNull(maxId)) {
					detailBin.setSourceId(1 + "".trim());
				} else {
					detailBin.setSourceId(maxId.toString().trim());
				}
				entityManager.persist(detailBin);
			}
			return true;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE UPDATING DISCOUNT ENTRIES: ", ex);
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<SDiscountsMaster> fetchDiscountInDateRange(Timestamp fromDate, Timestamp toDate) {

		try {

			Query query = entityManager.createNamedQuery("SDiscountsMaster.getDiscountInDateRange",
					SDiscountsMaster.class);
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);

			List<SDiscountsMaster> lst = (List<SDiscountsMaster>) query.getResultList();

			AgLogger.logDebug(getClass(), "fetchDiscountInDateRange SIZE: " + lst.size());
			if (!Objects.isNull(lst) && !lst.isEmpty()) {
				return lst;
			}

		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING fetchDiscountInDateRange: ", ex);
			ex.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public List<SDiscountsMaster> fetchDiscountInDateRangeNotInID(Timestamp fromDate, Timestamp toDate,String discId) {

		try {

			Query query = entityManager.createNamedQuery("SDiscountsMaster.getDiscountInDateRangeNotInID",
					SDiscountsMaster.class);
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
			query.setParameter("discId", discId);

			List<SDiscountsMaster> lst = (List<SDiscountsMaster>) query.getResultList();

			AgLogger.logDebug(getClass(), "fetchDiscountInDateRange SIZE: " + lst.size());
			if (!Objects.isNull(lst) && !lst.isEmpty()) {
				return lst;
			}

		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING fetchDiscountInDateRange: ", ex);
			ex.printStackTrace();
		}
		return null;
	}

}
