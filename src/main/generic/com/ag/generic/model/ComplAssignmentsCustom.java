package com.ag.generic.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComplAssignmentsCustom implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int compId;
	private String category;
	private String type;
	private String subType;
	private String mid;
	private int userId;
	private int screenId;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	private Timestamp entryDate;
	private String entryBy;
	private String priority;
	private String msdisn;
	private String email;
	private String serialNum;
	private String model;
	private String mname;
	private String province;
	private String mdrOnUs;
	private String mdrOffUs;
	
	
	
	public ComplAssignmentsCustom() {
		
	}
	
	
	public ComplAssignmentsCustom(int id, int compId, String category, String type, String subType, String mid,
			int userId, int screenId, String description, Timestamp entryDate, String entryBy, String priority,
			String msdisn, String email, String serialNum, String model, String mname) {
		super();
		this.id = id;
		this.compId = compId;
		this.category = category;
		this.type = type;
		this.subType = subType;
		this.mid = mid;
		this.userId = userId;
		this.screenId = screenId;
		this.description = description;
		this.entryDate = entryDate;
		this.entryBy = entryBy;
		this.priority = priority;
		this.msdisn = msdisn;
		this.email = email;
		this.serialNum = serialNum;
		this.model = model;
		this.mname = mname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompId() {
		return compId;
	}
	public void setCompId(int compId) {
		this.compId = compId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getScreenId() {
		return screenId;
	}
	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}
	public String getEntryBy() {
		return entryBy;
	}
	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}


	


	

	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getMdrOnUs() {
		return mdrOnUs;
	}


	public void setMdrOnUs(String mdrOnUs) {
		this.mdrOnUs = mdrOnUs;
	}


	public String getMdrOffUs() {
		return mdrOffUs;
	}


	public void setMdrOffUs(String mdrOffUs) {
		this.mdrOffUs = mdrOffUs;
	}
	


}