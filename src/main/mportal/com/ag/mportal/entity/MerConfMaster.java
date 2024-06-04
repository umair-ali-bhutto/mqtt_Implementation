package com.ag.mportal.entity;

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

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * The persistent class for the ECR_SAF database table.
 * 
 */
@Entity
@Table(name = "MER_CONF_MASTER")
@NamedQueries({ @NamedQuery(name = "MerConfMaster.fetchByMidTid", query = "SELECT a FROM MerConfMaster a WHERE a.mid=:mid AND a.tid=:tid AND a.type=:type AND a.isActive = 1"),
				@NamedQuery(name = "MerConfMaster.fetchData", query = "SELECT a FROM MerConfMaster a WHERE a.mid=:mid AND a.tid=:tid AND a.isActive = 1"),
				@NamedQuery(name = "MerConfMaster.fetchheartBeatData", query = "SELECT m.type,d.name,d.value FROM MerConfMaster m,MerConfDetails d WHERE m.id = d.recId and m.type in :type and  m.mid=:mid AND m.tid=:tid"),
				@NamedQuery(name = "MerConfMaster.fetchById", query = "SELECT f FROM MerConfMaster f where f.id IN :ids")
})
public class MerConfMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MER_CONF_MASTER_ID_GENERATOR", sequenceName = "MER_CONF_MASTER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MER_CONF_MASTER_ID_GENERATOR")
	@Column(name = "ID")
	private long id;

	@Column(name = "MID")
	private String mid;

	@Column(name = "TID")
	private String tid;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "IS_ACTIVE")
	private int isActive;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "ENTRY_BY")
	private String entryBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Karachi")
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
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

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	
}