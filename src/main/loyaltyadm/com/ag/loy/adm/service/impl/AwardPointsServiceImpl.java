package com.ag.loy.adm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.loy.adm.entity.AwardPoints;
import com.ag.loy.adm.service.AwardPointsService;

@Service
public class AwardPointsServiceImpl implements AwardPointsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveAwardPoints(AwardPoints lst) {
		try {
			entityManager.flush();
			entityManager.clear();
			entityManager.persist(lst);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void updateAwardPoints(Long mawardId, double amountSlab, double amountFrom, double amountTo, double prec,
			double min, double max, double expDays, String accountDr, String updatedBy, Date updateOn,
			Long amountSlabOld) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String query = "update awards_points set  amount_slab =" + amountSlab + "" + " ,amount_from = " + amountFrom
				+ ", amount_to = " + amountTo + " , perc = " + prec + ", min = " + min + ",  max= " + max
				+ ", account_dr='" + accountDr + "', upd_by='" + updatedBy + "'" + " ,upd_on = '"
				+ formatter.format(new Date()) + "' " + " where maward_id = '" + mawardId.toString()
				+ "'  and amount_slab=" + amountSlabOld + " ";
		Query cb = entityManager.createNativeQuery(query);
		cb.executeUpdate();

	}

	@Override
	public ArrayList<AwardPoints> fetchRecordById(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("AwardPoints.fetchRecordById");
			cb.setParameter("corpId", corpId);
			cb.setParameter("mawardId", id);
			return (ArrayList<AwardPoints>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public int deleteAwardPoints(Long mAwardId) {

		String sqlss = "DELETE FROM AWARDS_POINTS WHERE MAWARD_ID='" + mAwardId + "'";
		Query cb = entityManager.createNativeQuery(sqlss);
		return cb.executeUpdate();
	}

}