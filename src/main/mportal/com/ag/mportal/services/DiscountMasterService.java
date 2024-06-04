package com.ag.mportal.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ag.generic.model.KeyValueModel;
import com.ag.mportal.entity.DiscountMaster;
import com.ag.mportal.model.DMSReportReportModel;
import com.ag.mportal.model.DiscountMasterSlabModel;
import com.ag.mportal.model.ReportModel;

public interface DiscountMasterService {

	public void insert(DiscountMaster tcn);

	public void update(DiscountMaster tcn);

	public List<DiscountMaster> fetchLstDiscountMaster(String name, String status, String startDate, String endDate);

	public DiscountMaster fetchById(long id);

	public List<DiscountMasterSlabModel> fetchAll(String corpId, Timestamp txnDate, String currency, String txnAmount,
			String status);

	public List<DMSReportReportModel> fetchDiscountTxnDetailsReport(List<String> merchant, Date from, Date to,
			String discStatus, List<String> disName, int numberOfRows, int pageNumber);
	

	public String fetchDiscountTxnDetailsCount(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, String reportType);

	public List<String> fetchTidsOnMerchantId(String mid);

	public List<KeyValueModel> fetchLstDiscountNameByCorpId(String corpId);
}
