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

@Entity
@Table(name = "CAPTCHA")
@NamedQueries({

		@NamedQuery(name = "Captcha.retrieveAll", query = "SELECT a FROM Captcha a WHERE a.isActive = 1 and a.id=:id and a.uuid=:uuid"),
		@NamedQuery(name = "Captcha.update", query = "UPDATE Captcha SET isActive = 0 WHERE uuid=:uuid"),

})
public class Captcha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAPTCHA_SEQ")
	@SequenceGenerator(name = "CAPTCHA_SEQ", sequenceName = "CAPTCHA_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "CAPTCHA_ANS")
	private String captchaAns;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "IS_ACTIVE")
	private int isActive;
	
	@Column(name = "UUID")
	private String uuid;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaptchaAns() {
		return captchaAns;
	}

	public void setCaptchaAns(String captchaAns) {
		this.captchaAns = captchaAns;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	
}