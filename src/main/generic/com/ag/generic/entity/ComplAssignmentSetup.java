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
 * The persistent class for the COMPL_ASSIGNMENT_SETUP database table.
 * 
 */
@Entity
@Table(name = "COMPL_ASSIGNMENT_SETUP")
@NamedQueries({ 
		@NamedQuery(name = "ComplAssignmentSetup.fetchByID", query = "SELECT c from ComplAssignmentSetup c where c.id=:id"),
		@NamedQuery(name = "ComplAssignmentSetup.fetchByOtherParams", query = "SELECT c from ComplAssignmentSetup c where  c.subType=:subType AND c.category=:category AND c.type=:type AND c.levelCompl=:level"),
		@NamedQuery(name = "ComplAssignmentSetup.fetchAll", query = "SELECT c from ComplAssignmentSetup c"),
		@NamedQuery(name = "ComplAssignmentSetup.fetchAllByID", query = "SELECT c from ComplAssignmentSetup c where c.id=:id"),
		@NamedQuery(name = "ComplAssignmentSetup.fetchRecordByOtherParams", query = "SELECT c from ComplAssignmentSetup c where  c.levelCompl=:level AND c.subType=:subType AND c.category=:category AND c.type=:type")
})
public class ComplAssignmentSetup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPL_ASSIGNMENTS_SETUP_SEQ")
	@SequenceGenerator(name = "COMPL_ASSIGNMENTS_SETUP_SEQ", sequenceName = "COMPL_ASSIGNMENTS_SETUP_SEQ", allocationSize = 1)
	private long id;
	
	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "CLASS_NAME")
	private String className;

	@Column(name = "COMPL_CATEGORY_TYPE")
	private String complCategoryType;

	private String description;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "COMPL_LEVEL")
	private int levelCompl;

	private int priority;

	@Column(name = "SCREEN_ID")
	private int screenId;

	@Column(name = "SUB_TYPE")
	private String subType;

	private int tat;


	@Column(name = "TYPE")

	private String type;

	@Column(name = "USER_ID")
	private int userId;

	public ComplAssignmentSetup() {
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

	public String getComplCategoryType() {
		return this.complCategoryType;
	}

	public void setComplCategoryType(String complCategoryType) {
		this.complCategoryType = complCategoryType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
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

	public int getTat() {
		return this.tat;
	}

	public void setTat(int tat) {
		this.tat = tat;
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