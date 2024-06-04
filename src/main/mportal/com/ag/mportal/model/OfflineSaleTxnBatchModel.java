package com.ag.mportal.model;

import java.util.HashMap;
import java.util.List;

public class OfflineSaleTxnBatchModel {
	
	private TxnInfo TxnInfo;
	private Summary Summary;
	private List<String> TxnData;
	private AdditionalData additionalData;
	
	public class TxnInfo{
		private String userName;
		private String password;
		private String MID;
		private String TID;
		private String Model;
		private String SerialNumber;
		private String acquirer;
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getAcquirer() {
			return acquirer;
		}
		public void setAcquirer(String acquirer) {
			this.acquirer = acquirer;
		}
		public String getMID() {
			return MID;
		}
		public void setMID(String mID) {
			MID = mID;
		}
		public String getTID() {
			return TID;
		}
		public void setTID(String tID) {
			TID = tID;
		}
		public String getModel() {
			return Model;
		}
		public void setModel(String model) {
			Model = model;
		}
		public String getSerialNumber() {
			return SerialNumber;
		}
		public void setSerialNumber(String serialNumber) {
			SerialNumber = serialNumber;
		}
		
	}
	
	public class Summary{
		private String SaleCount;
		private String SaleAmount;
		private String RedeemCount;
		private String RedeemAmount;
		private String VoidCount;
		private String VoidAmount;
		private String CashOutCount;
		private String CashOutAmount;
		private String RefundCount;
		private String RefundAmount;
		public String getSaleCount() {
			return SaleCount;
		}
		public void setSaleCount(String saleCount) {
			SaleCount = saleCount;
		}
		public String getSaleAmount() {
			return SaleAmount;
		}
		public void setSaleAmount(String saleAmount) {
			SaleAmount = saleAmount;
		}
		public String getRedeemCount() {
			return RedeemCount;
		}
		public void setRedeemCount(String redeemCount) {
			RedeemCount = redeemCount;
		}
		public String getRedeemAmount() {
			return RedeemAmount;
		}
		public void setRedeemAmount(String redeemAmount) {
			RedeemAmount = redeemAmount;
		}
		public String getVoidCount() {
			return VoidCount;
		}
		public void setVoidCount(String voidCount) {
			VoidCount = voidCount;
		}
		public String getVoidAmount() {
			return VoidAmount;
		}
		public void setVoidAmount(String voidAmount) {
			VoidAmount = voidAmount;
		}
		public String getCashOutCount() {
			return CashOutCount;
		}
		public void setCashOutCount(String cashOutCount) {
			CashOutCount = cashOutCount;
		}
		public String getCashOutAmount() {
			return CashOutAmount;
		}
		public void setCashOutAmount(String cashOutAmount) {
			CashOutAmount = cashOutAmount;
		}
		public String getRefundCount() {
			return RefundCount;
		}
		public void setRefundCount(String refundCount) {
			RefundCount = refundCount;
		}
		public String getRefundAmount() {
			return RefundAmount;
		}
		public void setRefundAmount(String refundAmount) {
			RefundAmount = refundAmount;
		}
		
	}
	
	public class AdditionalData{
		private HashMap<String, String> wlanInfo;
		private HashMap<String, String> ethernetInfo;
		private HashMap<String, String> gsmInfo;
		private HashMap<String, String> additionalinfo;
		public HashMap<String, String> getWlanInfo() {
			return wlanInfo;
		}
		public void setWlanInfo(HashMap<String, String> wlanInfo) {
			this.wlanInfo = wlanInfo;
		}
		public HashMap<String, String> getEthernetInfo() {
			return ethernetInfo;
		}
		public void setEthernetInfo(HashMap<String, String> ethernetInfo) {
			this.ethernetInfo = ethernetInfo;
		}
		public HashMap<String, String> getGsmInfo() {
			return gsmInfo;
		}
		public void setGsmInfo(HashMap<String, String> gsmInfo) {
			this.gsmInfo = gsmInfo;
		}
		public HashMap<String, String> getAdditionalinfo() {
			return additionalinfo;
		}
		public void setAdditionalinfo(HashMap<String, String> additionalinfo) {
			this.additionalinfo = additionalinfo;
		}
	}


	public TxnInfo getTxnInfo() {
		return TxnInfo;
	}


	public void setTxnInfo(TxnInfo txnInfo) {
		TxnInfo = txnInfo;
	}


	public Summary getSummary() {
		return Summary;
	}


	public void setSummary(Summary summary) {
		Summary = summary;
	}


	public List<String> getTxnData() {
		return TxnData;
	}


	public void setTxnData( List<String> txnData) {
		TxnData = txnData;
	}


	public AdditionalData getAdditionalData() {
		return additionalData;
	}


	public void setAdditionalData(AdditionalData additionalData) {
		this.additionalData = additionalData;
	}
	
	

}
