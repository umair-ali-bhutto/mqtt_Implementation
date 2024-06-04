package com.ag.mportal.model;

public class RetrieveProvidersReqModel {

	public String MID;
	public String SerialNumber;
	public String Model;
	public String PASSWORD;
	public String UserName;
	public String TID;
	private String Type;

	public RetrieveProvidersReqModel() {

	}

	public RetrieveProvidersReqModel(String mID, String serialNumber, String model, String pASSWORD, String userName,
			String tID, String type) {
		super();
		MID = mID;
		SerialNumber = serialNumber;
		Model = model;
		PASSWORD = pASSWORD;
		UserName = userName;
		TID = tID;
		setType(type);
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

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

}
