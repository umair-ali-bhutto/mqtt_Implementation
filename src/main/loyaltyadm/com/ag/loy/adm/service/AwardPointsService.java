package com.ag.loy.adm.service;

import java.util.ArrayList;
import java.util.Date;

import com.ag.loy.adm.entity.AwardPoints;

public interface AwardPointsService {
	public void saveAwardPoints(AwardPoints lst);

	public void updateAwardPoints(Long mawardId, double amountSlab, double amountFrom, double amountTo, double prec,
			double min, double max, double expDays, String accountDr, String updatedBy, Date updateOn,
			Long amountSlabOld);

	public ArrayList<AwardPoints> fetchRecordById(String id,String corpId);

	public int deleteAwardPoints(Long mAwardId);
}