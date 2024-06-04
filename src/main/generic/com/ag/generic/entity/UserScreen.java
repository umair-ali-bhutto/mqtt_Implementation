package com.ag.generic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the USER_SCREENS database table.
 * 
 */
@Entity
@Table(name = "USER_SCREENS")
@NamedQuery(name = "UserScreen.getMaxScreenCode", query = " SELECT max(screenCode) as screenCode FROM UserScreen")
@NamedQuery(name = "UserScreen.viewUserScreens", query = "SELECT a FROM UserScreen a WHERE a.screenId=:screenId AND a.corpId=:corpId  AND a.isActive = 1 order by a.position asc ")
@NamedQuery(name = "UserScreen.getAllScreens", query = "SELECT a FROM UserScreen a WHERE a.isActive = 1 AND a.corpId=:corpId AND parentId != 0 order by a.screenDesc asc")
@NamedQuery(name = "UserScreen.getUserScreens", query = "SELECT a FROM UserScreen a WHERE a.screenDesc=:screenDesc and a.corpId=:corpId order by a.screenDesc asc")
@NamedQuery(name = "UserScreen.screenRights", query = "select u from UserScreen u,UserScreensGroup ug where u.screenId = ug.screenId and ug.grpId =:grpId and u.parentId not in (0) and u.parentId is not null AND u.isActive = 1 AND u.corpId=:corpId AND u.subSegment=:subSegment AND ug.isActive =1 order by u.position")
@NamedQuery(name = "UserScreen.screenRightsParent", query = "select u from UserScreen u where  u.parentId =0 AND u.isActive = 1 AND u.corpId=:corpId AND u.subSegment=:subSegment  order by u.position")
@NamedQuery(name = "UserScreen.getParentMenuId", query = "select u from UserScreen u where  u.screenId =:screenId AND u.isActive = 1 AND u.corpId=:corpId") 
@NamedQuery(name = "UserScreen.fetchByRoute", query = "select u from UserScreen u where u.routerLink =:routerLink AND u.isActive = 1 AND u.corpId=:corpId") 
public class UserScreen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "AUDIT_LOG_SEQ")
	@SequenceGenerator(sequenceName = "USER_SCREEN_SEQ", allocationSize = 1, name = "USER_SCREEN_SEQ")
	@Column(name = "SCREEN_ID")
	private int screenId;
	
	
	@Column(name = "SCREEN_CODE")
	private String screenCode;
	
	@Column(name = "SCREEN_DESC")
	private String screenDesc;
	
	@Column(name = "FILE_NAME")
	private String fileName;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	private int isActive;
	
	
	@JsonIgnore
	@Column(name = "INSERT_BY")
	private String insertBy;

	@JsonIgnore
	@Column(name = "INSERT_ON")
	private Timestamp insertOn;


	@JsonIgnore
	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;

	@JsonIgnore
	@Column(name = "LAST_UPDATE_ON")
	private Timestamp lastUpdateOn;

	@Column(name = "ICON")
	private String icon;
	
	@Column(name = "ROUTER_LINK")
	private String routerLink;
	
	@Column(name = "CORP_ID")
	private String corpId;
	
	@Column(name = "PARENT_ID")
	private Integer parentId;
	
	@Column(name = "POSITION")
	private int position;
	
	@Column(name="SUB_SEGMENT")
	private int subSegment;

	

	public UserScreen() {
	}
	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getInsertBy() {
		return this.insertBy;
	}

	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}

	public Timestamp getInsertOn() {
		return this.insertOn;
	}

	public void setInsertOn(Timestamp insertOn) {
		this.insertOn = insertOn;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Timestamp getLastUpdateOn() {
		return this.lastUpdateOn;
	}

	public void setLastUpdateOn(Timestamp lastUpdateOn) {
		this.lastUpdateOn = lastUpdateOn;
	}


	public String getScreenCode() {
		return this.screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getScreenDesc() {
		return this.screenDesc;
	}

	public void setScreenDesc(String screenDesc) {
		this.screenDesc = screenDesc;
	}

	public int getScreenId() {
		return this.screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRouterLink() {
		return routerLink;
	}

	public void setRouterLink(String routerLink) {
		this.routerLink = routerLink;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getSubSegment() {
		return subSegment;
	}
	public void setSubSegment(int subSegment) {
		this.subSegment = subSegment;
	}
	
	

}