package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CUSTOMER_USER_MASTER database table.
 * 
 */
@Entity
@Table(name="CUSTOMER_USER_MASTER")
@NamedQueries({@NamedQuery(name="CustomerUserMaster.findAll", query="SELECT c FROM CustomerUserMaster c where c.id.cid=:cid and c.id.corpid=:corpid order by c.crOn desc"),
	           @NamedQuery(name="CustomerUserMaster.findAllByUid", query="SELECT c FROM CustomerUserMaster c where c.id.userid=:userid and c.id.corpid=:corpid"),
	           @NamedQuery(name="CustomerUserMaster.findAllByCardNumber", query="SELECT c FROM CustomerUserMaster c where c.id.userid=:cardNumber and c.id.corpid=:corpid")})
public class CustomerUserMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CustomerUserMasterPK id;

	@Column(name="ACC_GL")
	private String accGl;

	private String authfactor0;

	private String authfactor1;

	private BigDecimal authfactor2;

	private BigDecimal authfactor3;

	private BigDecimal balance;

	@Column(name="CR_BY")
	private String crBy;

	@Column(name="CR_ON")
	private Timestamp crOn;

	private String currency;

	@Temporal(TemporalType.DATE)
	private Date expirydate;

	@Column(name="GL_SOURCE")
	private String glSource;

	@Temporal(TemporalType.DATE)
	private Date issuedate;

	private String other0;

	private String other1;

	private String other2;

	private String other3;

	private String other4;

	private String other5;

	private String other6;

	private BigDecimal other7;

	private BigDecimal other8;

	private BigDecimal other9;

	private BigDecimal otherbalance;

	private String password;

	@Column(name="REC_REF")
	private BigDecimal recRef;

	private BigDecimal resbalance;

	@Column(name="SOURCE_REF")
	private String sourceRef;

	private String status;

	@Column(name="UPD_BY")
	private String updBy;

	@Column(name="UPD_ON")
	private Timestamp updOn;

	private String userdispname;

	private String username;

	private String usertype;

	private String usertypea;

	private String usertypeb;

	private String usertypec;

	private String usertyped;

	public CustomerUserMaster() {
	}

	public CustomerUserMasterPK getId() {
		return this.id;
	}

	public void setId(CustomerUserMasterPK id) {
		this.id = id;
	}

	public String getAccGl() {
		return this.accGl;
	}

	public void setAccGl(String accGl) {
		this.accGl = accGl;
	}

	public String getAuthfactor0() {
		return this.authfactor0;
	}

	public void setAuthfactor0(String authfactor0) {
		this.authfactor0 = authfactor0;
	}

	public String getAuthfactor1() {
		return this.authfactor1;
	}

	public void setAuthfactor1(String authfactor1) {
		this.authfactor1 = authfactor1;
	}

	public BigDecimal getAuthfactor2() {
		return this.authfactor2;
	}

	public void setAuthfactor2(BigDecimal authfactor2) {
		this.authfactor2 = authfactor2;
	}

	public BigDecimal getAuthfactor3() {
		return this.authfactor3;
	}

	public void setAuthfactor3(BigDecimal authfactor3) {
		this.authfactor3 = authfactor3;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCrBy() {
		return this.crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public Timestamp getCrOn() {
		return this.crOn;
	}

	public void setCrOn(Timestamp crOn) {
		this.crOn = crOn;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getExpirydate() {
		return this.expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	public String getGlSource() {
		return this.glSource;
	}

	public void setGlSource(String glSource) {
		this.glSource = glSource;
	}

	public Date getIssuedate() {
		return this.issuedate;
	}

	public void setIssuedate(Date issuedate) {
		this.issuedate = issuedate;
	}

	public String getOther0() {
		return this.other0;
	}

	public void setOther0(String other0) {
		this.other0 = other0;
	}

	public String getOther1() {
		return this.other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return this.other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getOther3() {
		return this.other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}

	public String getOther4() {
		return this.other4;
	}

	public void setOther4(String other4) {
		this.other4 = other4;
	}

	public String getOther5() {
		return this.other5;
	}

	public void setOther5(String other5) {
		this.other5 = other5;
	}

	public String getOther6() {
		return this.other6;
	}

	public void setOther6(String other6) {
		this.other6 = other6;
	}

	public BigDecimal getOther7() {
		return this.other7;
	}

	public void setOther7(BigDecimal other7) {
		this.other7 = other7;
	}

	public BigDecimal getOther8() {
		return this.other8;
	}

	public void setOther8(BigDecimal other8) {
		this.other8 = other8;
	}

	public BigDecimal getOther9() {
		return this.other9;
	}

	public void setOther9(BigDecimal other9) {
		this.other9 = other9;
	}

	public BigDecimal getOtherbalance() {
		return this.otherbalance;
	}

	public void setOtherbalance(BigDecimal otherbalance) {
		this.otherbalance = otherbalance;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getRecRef() {
		return this.recRef;
	}

	public void setRecRef(BigDecimal recRef) {
		this.recRef = recRef;
	}

	public BigDecimal getResbalance() {
		return this.resbalance;
	}

	public void setResbalance(BigDecimal resbalance) {
		this.resbalance = resbalance;
	}

	public String getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Timestamp getUpdOn() {
		return this.updOn;
	}

	public void setUpdOn(Timestamp updOn) {
		this.updOn = updOn;
	}

	public String getUserdispname() {
		return this.userdispname;
	}

	public void setUserdispname(String userdispname) {
		this.userdispname = userdispname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUsertypea() {
		return this.usertypea;
	}

	public void setUsertypea(String usertypea) {
		this.usertypea = usertypea;
	}

	public String getUsertypeb() {
		return this.usertypeb;
	}

	public void setUsertypeb(String usertypeb) {
		this.usertypeb = usertypeb;
	}

	public String getUsertypec() {
		return this.usertypec;
	}

	public void setUsertypec(String usertypec) {
		this.usertypec = usertypec;
	}

	public String getUsertyped() {
		return this.usertyped;
	}

	public void setUsertyped(String usertyped) {
		this.usertyped = usertyped;
	}

}
