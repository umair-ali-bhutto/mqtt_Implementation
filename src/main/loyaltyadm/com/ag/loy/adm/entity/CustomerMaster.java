package com.ag.loy.adm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
  * The persistent class for the CUSTOMER_MASTER database table.
  *
  */
@Entity
@Table(name="CUSTOMER_MASTER")
@NamedQuery(name="CustomerMaster.findAll", query="SELECT c FROM CustomerMaster c WHERE c.id.cid=:cid and c.id.corpid=:corpid")
public class CustomerMaster implements Serializable {
     private static final long serialVersionUID = 1L;

     @Id
     private String cid;

     @Column(name="ACC_GL")
     private String accGl;

     private String address1;

     private String address2;

     private String address3;

     private String address4;

     private BigDecimal balance;

     @Column(name="CITY_ID")
     private String cityId;

     private String cnic;

     private String corpid;

     private String country;

     @Column(name="CR_BY")
     private String crBy;

     @Column(name="CR_ON")
     private Timestamp crOn;

     private String currency;

     private String custcategory;

     private String custtype;

     @Temporal(TemporalType.DATE)
     private Date dob;

     private String email;

     private String fname;

     @Column(name="FULL_NAME")
     private String fullName;

     private String gender;

     @Column(name="GL_SOURCE")
     private String glSource;

     @Column(name="IS_INDV")
     private String isIndv;

     private String lname;

     private String mname;

     private String msisdn;

     private String nationality;

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

     @Column(name="REC_REF")
     private BigDecimal recRef;

     private BigDecimal resbalance;

     @Column(name="SOURCE_REF")
     private String sourceRef;

     private String status;

     private String tname;

     @Column(name="UPD_BY")
     private String updBy;

     @Column(name="UPD_ON")
     private Timestamp updOn;

     public CustomerMaster() {
     }

     public String getCid() {
         return this.cid;
     }

     public void setCid(String cid) {
         this.cid = cid;
     }

     public String getAccGl() {
         return this.accGl;
     }

     public void setAccGl(String accGl) {
         this.accGl = accGl;
     }

     public String getAddress1() {
         return this.address1;
     }

     public void setAddress1(String address1) {
         this.address1 = address1;
     }

     public String getAddress2() {
         return this.address2;
     }

     public void setAddress2(String address2) {
         this.address2 = address2;
     }

     public String getAddress3() {
         return this.address3;
     }

     public void setAddress3(String address3) {
         this.address3 = address3;
     }

     public String getAddress4() {
         return this.address4;
     }

     public void setAddress4(String address4) {
         this.address4 = address4;
     }

     public BigDecimal getBalance() {
         return this.balance;
     }

     public void setBalance(BigDecimal balance) {
         this.balance = balance;
     }

     public String getCityId() {
         return this.cityId;
     }

     public void setCityId(String cityId) {
         this.cityId = cityId;
     }

     public String getCnic() {
         return this.cnic;
     }

     public void setCnic(String cnic) {
         this.cnic = cnic;
     }

     public String getCorpid() {
         return this.corpid;
     }

     public void setCorpid(String corpid) {
         this.corpid = corpid;
     }

     public String getCountry() {
         return this.country;
     }

     public void setCountry(String country) {
         this.country = country;
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

     public String getCustcategory() {
         return this.custcategory;
     }

     public void setCustcategory(String custcategory) {
         this.custcategory = custcategory;
     }

     public String getCusttype() {
         return this.custtype;
     }

     public void setCusttype(String custtype) {
         this.custtype = custtype;
     }

     public Date getDob() {
         return this.dob;
     }

     public void setDob(Date dob) {
         this.dob = dob;
     }

     public String getEmail() {
         return this.email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getFname() {
         return this.fname;
     }

     public void setFname(String fname) {
         this.fname = fname;
     }

     public String getFullName() {
         return this.fullName;
     }

     public void setFullName(String fullName) {
         this.fullName = fullName;
     }

     public String getGender() {
         return this.gender;
     }

     public void setGender(String gender) {
         this.gender = gender;
     }

     public String getGlSource() {
         return this.glSource;
     }

     public void setGlSource(String glSource) {
         this.glSource = glSource;
     }

     public String getIsIndv() {
         return this.isIndv;
     }

     public void setIsIndv(String isIndv) {
         this.isIndv = isIndv;
     }

     public String getLname() {
         return this.lname;
     }

     public void setLname(String lname) {
         this.lname = lname;
     }

     public String getMname() {
         return this.mname;
     }

     public void setMname(String mname) {
         this.mname = mname;
     }

     public String getMsisdn() {
         return this.msisdn;
     }

     public void setMsisdn(String msisdn) {
         this.msisdn = msisdn;
     }

     public String getNationality() {
         return this.nationality;
     }

     public void setNationality(String nationality) {
         this.nationality = nationality;
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

     public String getTname() {
         return this.tname;
     }

     public void setTname(String tname) {
         this.tname = tname;
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

}
