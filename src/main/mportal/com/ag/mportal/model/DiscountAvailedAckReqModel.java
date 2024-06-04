package com.ag.mportal.model;

public class DiscountAvailedAckReqModel {
	public String recId;
	public String MID;
	public String SerialNumber;
	public String Model;
	public String PASSWORD;
	public String UserName;
	public String Acquirer;
	public String Bin;
	public String CardScheme;
	public String TxnTime;
	public String TxnDate;
	public String Amount;
	public String TID;
	public String Stan;
	public String RRN;
	public String BatchNo;
	public String InvoiceNo;
	public String Status;
	
	
	public DiscountAvailedAckReqModel() {
	
	}
	
	public DiscountAvailedAckReqModel(String recId, String mID, String serialNumber, String model, String pASSWORD,
			String userName, String acquirer, String bin, String cardScheme, String txnTime, String txnDate,
			String amount, String tID, String stan, String rRN, String batchNo, String invoiceNo) {
		super();
		this.recId = recId;
		MID = mID;
		SerialNumber = serialNumber;
		Model = model;
		PASSWORD = pASSWORD;
		UserName = userName;
		Acquirer = acquirer;
		Bin = bin;
		CardScheme = cardScheme;
		TxnTime = txnTime;
		TxnDate = txnDate;
		Amount = amount;
		TID = tID;
		Stan = stan;
		RRN = rRN;
		BatchNo = batchNo;
		InvoiceNo = invoiceNo;
	}




	public String getRecId() {
		return recId;
	}


	public void setRecId(String recId) {
		this.recId = recId;
	}


	public String getMID() {
		return MID;
	}


	public void setMID(String mID) {
		MID = mID;
	}


	public String getSerialNumber() {
		return SerialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}


	public String getModel() {
		return Model;
	}


	public void setModel(String model) {
		Model = model;
	}


	public String getPASSWORD() {
		return PASSWORD;
	}


	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}


	public String getUserName() {
		return UserName;
	}


	public void setUserName(String userName) {
		UserName = userName;
	}


	public String getAcquirer() {
		return Acquirer;
	}


	public void setAcquirer(String acquirer) {
		Acquirer = acquirer;
	}


	public String getBin() {
		return Bin;
	}


	public void setBin(String bin) {
		Bin = bin;
	}


	public String getCardScheme() {
		return CardScheme;
	}


	public void setCardScheme(String cardScheme) {
		CardScheme = cardScheme;
	}


	public String getTxnTime() {
		return TxnTime;
	}


	public void setTxnTime(String txnTime) {
		TxnTime = txnTime;
	}


	public String getTxnDate() {
		return TxnDate;
	}


	public void setTxnDate(String txnDate) {
		TxnDate = txnDate;
	}


	public String getAmount() {
		return Amount;
	}


	public void setAmount(String amount) {
		Amount = amount;
	}


	public String getTID() {
		return TID;
	}


	public void setTID(String tID) {
		TID = tID;
	}


	public String getStan() {
		return Stan;
	}


	public void setStan(String stan) {
		Stan = stan;
	}


	public String getRRN() {
		return RRN;
	}


	public void setRRN(String rRN) {
		RRN = rRN;
	}


	public String getBatchNo() {
		return BatchNo;
	}


	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		this.Status = status;
	}

	public String getInvoiceNo() {
		return InvoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		InvoiceNo = invoiceNo;
	}
	

}
