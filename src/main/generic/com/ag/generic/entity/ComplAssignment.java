package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the COMPL_ASSIGNMENTS database table.
 * 
 */
@Entity
@Table(name = "COMPL_ASSIGNMENTS")
@NamedQueries({
		@NamedQuery(name = "ComplAssignment.fetchByID", query = "SELECT c from ComplAssignment c where c.id=:id"),
		@NamedQuery(name = "ComplAssignment.fetchByOtherParams", query = "SELECT c from ComplAssignment c where c.id=:id"),
		@NamedQuery(name = "ComplAssignment.fetchAll", query = "SELECT c from ComplAssignment c order by c.id asc"),
		@NamedQuery(name = "ComplAssignment.fetchAllByID", query = "SELECT c from ComplAssignment c where c.userId=:userId AND c.procBy is null AND c.procDate is  null order by c.id asc"),
		@NamedQuery(name = "ComplAssignment.fetchAllByIDByCategory", query = "SELECT c from ComplAssignment c where c.userId=:userId AND c.subType=:subType AND c.category=:category AND type=:type AND c.procBy is null AND c.procDate is  null order by c.id desc"),   
		@NamedQuery(name = "ComplAssignment.fetchByComplID", query = "SELECT c from ComplAssignment c where c.compId=:compId order by c.id asc"),
		@NamedQuery(name = "ComplAssignment.fetchRecordHavingProcByAndProcDateNull", query = "SELECT c from ComplAssignment c where c.compId=:compId AND c.procBy is null AND c.procDate is  null order by c.id asc") 
		})
public class ComplAssignment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPL_ASSIGNMENTS_SEQ")
	@SequenceGenerator(name = "COMPL_ASSIGNMENTS_SEQ", sequenceName = "COMPL_ASSIGNMENTS_SEQ", allocationSize = 1)
	private long id;
	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "CLASS_NAME")
	private String className;

	@Column(name = "COMP_ID")
	private int compId;

	private String description;

	@Column(name = "ENTRY_BY")
	private String entryBy;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "COMPL_LEVEL")
	private int levelCompl;

	private String mid;

	private String priority;

	@Column(name = "PROC_BY")
	private String procBy;

	@Column(name = "PROC_DATE")
	private Timestamp procDate;

	@Column(name = "SCREEN_ID")
	private int screenId;

	@Column(name = "SUB_TYPE")
	private String subType;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "USER_ID")
	private int userId;

	public ComplAssignment() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getCompId() {
		return this.compId;
	}

	public void setCompId(int compId) {
		this.compId = compId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntryBy() {
		return this.entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public Timestamp getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getLevelCompl() {
		return this.levelCompl;
	}

	public void setLevelCompl(int levelCompl) {
		this.levelCompl = levelCompl;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProcBy() {
		return this.procBy;
	}

	public void setProcBy(String procBy) {
		this.procBy = procBy;
	}

	public Timestamp getProcDate() {
		return this.procDate;
	}

	public void setProcDate(Timestamp procDate) {
		this.procDate = procDate;
	}

	public int getScreenId() {
		return this.screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public String getSubType() {
		return this.subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}