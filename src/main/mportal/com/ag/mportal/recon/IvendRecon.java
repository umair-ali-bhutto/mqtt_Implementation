package com.ag.mportal.recon;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.ReconConfig;
import com.ag.mportal.entity.ReconConfigDetail;
import com.ag.mportal.entity.ReconDataDetail;
import com.ag.mportal.entity.ReconDataMaster;
import com.ag.mportal.services.ReconConfigDetailService;
import com.ag.mportal.services.ReconDataDetailService;
import com.ag.mportal.services.ReconDataMasterService;
import com.ag.mportal.services.ReconLockService;
import com.ag.mportal.util.ReconUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.ag.mportal.recon.IvendRecon")
public class IvendRecon {

	@Autowired
	ReconLockService reconLockService;

	@Autowired
	ReconDataDetailService reconDataDetailService;

	@Autowired
	ReconDataMasterService reconDataMasterService;

	@Autowired
	ReconConfigDetailService configDetailService;

	public void doProcess(ReconConfig cfg) {

		try {
			AgLogger.logInfo("IVEND RECON CALLED " + cfg.getId());
			reconLockService.createLock(cfg.getId());

			List<ReconConfigDetail> configDetails = configDetailService.fetchByConfigId(cfg.getId());

			if (configDetails.size() != 0) {

				for (ReconConfigDetail detail : configDetails) {

					String data = fetchData(cfg, detail.getDataOff());

					AgLogger.logInfo("IVEND RESPONSE: " + data.replaceAll("\\n", ""));

					if (data.trim().length() != 0) {
						List<ReconModel> lst = new ArrayList<ReconModel>();
						Type ListType = new TypeToken<List<ReconModel>>() {
						}.getType();
						lst = new Gson().fromJson(data, ListType);

						long masterId = insertMaster(cfg, lst.size());
						int syncedRecords = insertDetail(masterId, lst);

						detail.setReconDataMasterId(masterId);
						detail.setIsActive(0);
						detail.setState("");
						detail.setRemarks("");

						updateMaster(masterId, syncedRecords);

					} else {
						AgLogger.logInfo("No Data Found.");

						detail.setState("");
						detail.setRemarks("");
						detail.setRetries(detail.getRetries() + 1);

					}

					detail.setUpdatedOn(new Timestamp(new Date().getTime()));
					configDetailService.update(detail);
				}

			} else {
				AgLogger.logInfo("No Config Details Found.");
			}

			AgLogger.logInfo("IVEND THREAD ENDED " + cfg.getId());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reconLockService.releaseLock(cfg.getId());
		}

	}

	public String fetchData(ReconConfig cfg, Date dataOff) {
		String response = "";
		try {
			String url = cfg.getUri();
			JSONObject obj = new JSONObject();
			obj.put("token", cfg.getUriToken());
			obj.put("user", cfg.getCredUser());
			obj.put("password", cfg.getCredPass());
			obj.put("dataOff", dataOff);

			AgLogger.logInfo("IVEND URL: " + url);

			response = new ReconUtil().doPost(url, obj.toString(), cfg.getTimeout());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public long insertMaster(ReconConfig cfg, int listSize) {
		ReconDataMaster master = new ReconDataMaster();
		master.setReconConfigId(cfg.getId());
		master.setType("AUTO");
		master.setEntryDate(new Timestamp(new Date().getTime()));
		master.setEntryBy("Recon Schedular");
		master.setUpdDate(null);
		master.setUpdBy("");
		master.setTotalRecords(listSize);
		master.setTotalSyncedRecords(0);
		master.setFileName("");
		master.setFilePath("");
		master.setUriName(cfg.getUri());
		master.setStatus("PROCESSING");
		master.setRemarks("");
		long masterId = reconDataMasterService.insert(master);
		return masterId;
	}

	public int insertDetail(long masterId, List<ReconModel> lst) {
		int i = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			for (ReconModel mdl : lst) {
				ReconDataDetail detail = new ReconDataDetail();
				detail.setReconDataMasterId(masterId);
				detail.setEcrUniqueId(Long.parseLong(mdl.getTransactionKey()));

				try {
					detail.setTransactionDate(new Timestamp(dateFormat.parse(mdl.getBusinessDate()).getTime()));
				} catch (Exception e) {
				}
				try {
					detail.setActualDate(new Timestamp(dateFormat.parse(mdl.getActualDate()).getTime()));
				} catch (Exception e) {
				}
				try {
					detail.setEntryDate(new Timestamp(dateFormat.parse(mdl.getDatTime()).getTime()));
				} catch (Exception e) {
				}

				detail.setDescription(mdl.getDescription());
				detail.setTill(mdl.getTill());
				detail.setCustomerCode(mdl.getCustomerCode());
				detail.setTransactionAmount(mdl.getAmount());
				detail.setNetAmount(mdl.getNet());
				detail.setTenderType(mdl.getTendertype());
				detail.setCardNumber(mdl.getCardNum());
				detail.setTransactionId(Long.parseLong(mdl.getTransactionID()));
				detail.setIvendUser(mdl.getUser());
				detail.setAuthId(mdl.getAuthCode());
				detail.setBatchNo(mdl.getBatchNum());
				detail.setTid(mdl.getTID());
				detail.setInvoiceNo(mdl.getInvNum());
				detail.setManualTransaction(mdl.getManualTrx());
				detail.setAppVersion(mdl.getAppVersion());
				detail.setRrn(mdl.getRRN());
				detail.setIdentifier(mdl.getIdentifier());
				detail.setTenderTypeKey(mdl.getTenderTypeKey());
				detail.setStoreKey(mdl.getStoreKey());
				reconDataDetailService.insert(detail);
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public void updateMaster(long masterId, int syncedRecords) {
		ReconDataMaster mdl = reconDataMasterService.fetchById(masterId);
		mdl.setUpdBy("Recon Schedular");
		mdl.setUpdDate(new Timestamp(new Date().getTime()));
		mdl.setTotalSyncedRecords(syncedRecords);
		mdl.setStatus("PROCESSED");
		reconDataMasterService.update(mdl);
	}

}

class ReconModel {
	private String TransactionKey;
	private String BusinessDate;
	private String Description;
	private String Till;
	private String CustomerCode;
	private String Amount;
	private String Net;
	private String Tendertype;
	private String CardNum;
	private String TransactionID;
	private String ActualDate;
	private String User;
	private String AuthCode;
	private String BatchNum;
	private String TID;
	private String InvNum;
	private String ManualTrx;
	private String AppVersion;
	private String RRN;
	private String Identifier;
	private String DatTime;
	private String TenderTypeKey;
	private String StoreKey;

	public String getTransactionKey() {
		return TransactionKey;
	}

	public void setTransactionKey(String transactionKey) {
		TransactionKey = transactionKey;
	}

	public String getBusinessDate() {
		return BusinessDate;
	}

	public void setBusinessDate(String businessDate) {
		BusinessDate = businessDate;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getTill() {
		return Till;
	}

	public void setTill(String till) {
		Till = till;
	}

	public String getCustomerCode() {
		return CustomerCode;
	}

	public void setCustomerCode(String customerCode) {
		CustomerCode = customerCode;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getNet() {
		return Net;
	}

	public void setNet(String net) {
		Net = net;
	}

	public String getTendertype() {
		return Tendertype;
	}

	public void setTendertype(String tendertype) {
		Tendertype = tendertype;
	}

	public String getCardNum() {
		return CardNum;
	}

	public void setCardNum(String cardNum) {
		CardNum = cardNum;
	}

	public String getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}

	public String getActualDate() {
		return ActualDate;
	}

	public void setActualDate(String actualDate) {
		ActualDate = actualDate;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getAuthCode() {
		return AuthCode;
	}

	public void setAuthCode(String authCode) {
		AuthCode = authCode;
	}

	public String getBatchNum() {
		return BatchNum;
	}

	public void setBatchNum(String batchNum) {
		BatchNum = batchNum;
	}

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}

	public String getInvNum() {
		return InvNum;
	}

	public void setInvNum(String invNum) {
		InvNum = invNum;
	}

	public String getManualTrx() {
		return ManualTrx;
	}

	public void setManualTrx(String manualTrx) {
		ManualTrx = manualTrx;
	}

	public String getAppVersion() {
		return AppVersion;
	}

	public void setAppVersion(String appVersion) {
		AppVersion = appVersion;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	public String getIdentifier() {
		return Identifier;
	}

	public void setIdentifier(String identifier) {
		Identifier = identifier;
	}

	public String getDatTime() {
		return DatTime;
	}

	public void setDatTime(String datTime) {
		DatTime = datTime;
	}

	public String getTenderTypeKey() {
		return TenderTypeKey;
	}

	public void setTenderTypeKey(String tenderTypeKey) {
		TenderTypeKey = tenderTypeKey;
	}

	public String getStoreKey() {
		return StoreKey;
	}

	public void setStoreKey(String storeKey) {
		StoreKey = storeKey;
	}

}
