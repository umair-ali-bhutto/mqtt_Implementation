package com.ag.generic.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.ag.generic.entity.Complaint;
import com.ag.generic.prop.AppProp;
import com.ag.mportal.entity.QueueLog;
import com.google.gson.Gson;

public class DataWrapper {

	private String mid;
	private String userName;
	private Timestamp requestDate;
	private String msdisn;
	private String email;
	private String model;
	private String serialNum;
	private String dashBoardText;

	public DataWrapper(String descriptionText, String category, String type, String subType, String adm,String dbText) {
		String concant = category + type + subType;

		if (category.equals("002")) {
			doProcessComplaint(descriptionText, adm,dbText);
		} else {

			switch (concant) {
			case "001000100001":
				doProcessUser(descriptionText, adm);
				break;
//			case "002000100001":
//				doProcessComplaint(descriptionText, adm);
//				break;
			case "001000200002":
				doProcessLinkRequest(descriptionText, adm);
				break;
			default:
				break;
			}
		}
	}

	void doProcessUser(String desc, String admName) {
		System.out.println(desc);
		QueueLog obj = new Gson().fromJson(desc, QueueLog.class);
		mid = obj.getMid();
		userName = obj.getMid();
		msdisn = obj.getMsdisn();
		email = obj.getEmail();
		requestDate = obj.getEntryDate();
		model = obj.getModel();
		serialNum = obj.getSerialNum();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
		String text = AppProp.getProperty("user.dashboard.text");
		text = text.replaceAll("@Admin", admName);
		text = text.replaceAll("@MID", mid);
		text = text.replaceAll("@REQDATE", sdf.format(requestDate));
		dashBoardText = text;
	}

	void doProcessComplaint(String desc, String adm,String dbText) {
		System.out.println(desc);
		Complaint obj = new Gson().fromJson(desc, Complaint.class);
		mid = obj.getMid();
//		UsersLogin usl = new UserDAOImpl().validetUser(obj.getEntryBy());
//		if (usl != null) {
//			userName = usl.getUserName();
//			msdisn = usl.getMsisdn();
//			email = usl.getEmail();
//		}
		requestDate = obj.getEntryDate();
		model = obj.getModel();
		serialNum = obj.getSerialNumber();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
		String text = dbText;
		text = text.replaceAll("@Admin", adm);
		text = text.replaceAll("@MID", mid);
		text = text.replaceAll("@REQDATE", sdf.format(requestDate));
		dashBoardText = text;

	}

	void doProcessLinkRequest(String desc, String adm) {
		System.out.println(desc);
		ComplainDescriptionModel obj = new Gson().fromJson(desc, ComplainDescriptionModel.class);
		mid = obj.getChainMID();
//		UsersLogin usl = new UserDAOImpl().validetUser(obj.getEntryBy());
//		if (usl != null) {
//			userName = usl.getUserName();
//			msdisn = usl.getMsisdn();
//			email = usl.getEmail();
//		}
		requestDate = obj.getEntryDate();
		model = obj.getModel();
		serialNum = obj.getSerialNumber();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
		String text = AppProp.getProperty("user.dashboard.link.req.text");
		text = text.replaceAll("@Admin", adm);
		text = text.replaceAll("@MID", mid);
		text = text.replaceAll("@REQDATE", sdf.format(requestDate));
		dashBoardText = text;

	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Timestamp requestDate) {
		this.requestDate = requestDate;
	}

	public String getMsdisn() {
		return msdisn;
	}

	public void setMsdisn(String msdisn) {
		this.msdisn = msdisn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getDashBoardText() {
		return dashBoardText;
	}

	public void setDashBoardText(String dashBoardText) {
		this.dashBoardText = dashBoardText;
	}

}
