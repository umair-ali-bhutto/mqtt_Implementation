package com.ag.mportal.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ag.mportal.entity.SDiscountsDetailBin;
import com.ag.mportal.entity.SDiscountsDetailSlab;
import com.ag.mportal.entity.SDiscountsMaster;

public interface SDiscountsMasterService {
	public Integer insert(SDiscountsMaster discountMaster, SDiscountsDetailSlab sDiscountsDetailSlab,
			ArrayList<SDiscountsDetailBin> lstBin);

	public List<SDiscountsMaster> fetchLstDiscountMaster(String name, String status, String startDate, String endDate);

	public SDiscountsMaster fetchByDiscountId(String discountId);

	public boolean update(SDiscountsMaster sDiscountsMaster, SDiscountsDetailSlab sDiscountsDetailSlab,
			ArrayList<SDiscountsDetailBin> lstDetailBin, String oldBinStatus);

	public List<SDiscountsMaster> fetchDiscountInDateRange(Timestamp fromDate, Timestamp toDate);
	
	public List<SDiscountsMaster> fetchDiscountInDateRangeNotInID(Timestamp fromDate, Timestamp toDate,String discId);

}
