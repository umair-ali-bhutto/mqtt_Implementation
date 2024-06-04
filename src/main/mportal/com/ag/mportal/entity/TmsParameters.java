package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PARAMETER_NEW")
@NamedQueries({
	
	@NamedQuery(name = "TmsParameters.retrieveAllByTID", query = "SELECT a FROM TmsParameters a WHERE a.partId=:partId ")

})
public class TmsParameters implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARAMETER_NEW_ID_SEQ")
	@SequenceGenerator(name = "PARAMETER_NEW_ID_SEQ", sequenceName = "PARAMETER_NEW_ID_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "SEQINFO")
	private int seqInfo;
	
	@Column(name = "FAMNM")
	private String famNm;
	
	@Column(name = "APPNM")
	private String appNm;
	
	
	@Column(name = "PARTID")
	private String partId;
	
	
	@Column(name = "PARNAMELOC")
	private String parNameLoc;
	
	@Column(name = "DLDTYPE")
	private String dldType;
	
	@JsonIgnore
	@Column(name = "PARINFO")
	private String parInfo;
	
	@Column(name = "VALUE")
	private String value;
	
	@JsonIgnore
	@Column(name = "PSIZE")
	private Integer pSize;
	
	@Column(name = "FLAG")
	private String flag;
	
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public int getSeqInfo() {
		return seqInfo;
	}



	public void setSeqInfo(int seqInfo) {
		this.seqInfo = seqInfo;
	}



	



	public String getAppNm() {
		return appNm;
	}



	public void setAppNm(String appNm) {
		this.appNm = appNm;
	}



	public String getPartId() {
		return partId;
	}



	public void setPartId(String partId) {
		this.partId = partId;
	}



	public String getParNameLoc() {
		return parNameLoc;
	}



	public void setParNameLoc(String parNameLoc) {
		this.parNameLoc = parNameLoc;
	}



	public String getDldType() {
		return dldType;
	}



	public void setDldType(String dldType) {
		this.dldType = dldType;
	}



	public String getParInfo() {
		return parInfo;
	}



	public void setParInfo(String parInfo) {
		this.parInfo = parInfo;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public int getpSize() {
		return pSize;
	}



	public void setpSize(int pSize) {
		this.pSize = pSize;
	}



	public String getFlag() {
		return flag;
	}



	public void setFlag(String flag) {
		this.flag = flag;
	}



	public String getFamNm() {
		return famNm;
	}



	public void setFamNm(String famNm) {
		this.famNm = famNm;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	
	

}