package com.ag.mportal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the FILE_UPLOADER database table.
 * 
 */
@Entity
@Table(name = "FILE_UPLOADER_DETAILS")

@NamedQueries({ @NamedQuery(name = "FileUploaderDetails.fetchByFileId", query = "SELECT f FROM FileUploaderDetails f where f.fileId= :fileId order by f.state desc"),
	@NamedQuery(name = "FileUploaderDetails.fetchByFileIdStatus", query = "SELECT f FROM FileUploaderDetails f where f.fileId= :fileId and f.state ='Success'")})
public class FileUploaderDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FILE_UPLOADER_ID_GENERATOR", sequenceName = "FILE_UPLOADER_DETAILS_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_UPLOADER_ID_GENERATOR")
	private long id;

	@Column(name = "FILE_ID")
	private String fileId;

	@Column(name = "FIELD_1")
	private String field1;

	@Column(name = "FIELD_2")
	private String field2;

	@Column(name = "FIELD_3")
	private String field3;
	
	@Column(name = "FIELD_4")
	private String field4;

	@Column(name = "FIELD_5")
	private String field5;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "ENTRY_BY")
	private String entryBy;
	
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "STATE")
	private String state;
	
	@Column(name = "FIELD_6")
	private String field6;
	
	@Column(name = "FIELD_7")
	private String field7;
	
	public String getField7() {
		return field7;
	}

	public void setField7(String field7) {
		this.field7 = field7;
	}

	public String getField8() {
		return field8;
	}

	public void setField8(String field8) {
		this.field8 = field8;
	}

	@Column(name = "FIELD_8")
	private String field8;

	public String getField6() {
		return field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	

}