package com.ag.fuel.entity;

import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//@Entity
@Table(name = "VW_FC_ACCTBL")
@NamedQueries({ @NamedQuery(name = "FcAccTbl.searchFcAccTblByAccNo", query = "select x from VW_FC_ACCTBL x where x.accNo=:accNo ORDER BY x.accNo DESC"),
		@NamedQuery(name = "FcAccTbl.searchFcAccTblAll", query = "select x from VW_FC_ACCTBL ORDER BY x.accNo DESC") })
public class FcAccTbl {

	@Column(name = "ACC_NO")
	private String accNo;
	@Column(name = "ACC_NAME")
	private String accName;
	@Column(name = "ACC_STATUS")
	private String accStatus;
	@Column(name = "ID_NUMBER")
	private String idNumber;
	@Column(name = "CRD_LMT")
	private Float crdLmt;
	@Column(name = "OVRLMT_PCNT")
	private Long ovrLmtPcnt;
	@Column(name = "CUST_PRD_ID")
	private String custProdId;
	@Column(name = "DPS_VAL")
	private Float dpsVal;
	@Column(name = "ADDR1")
	private String addr1;
	@Column(name = "ADDR2")
	private String addr2;
	@Column(name = "ADDR3")
	private String addr3;
	@Column(name = "ADDR4")
	private String addr4;
	@Column(name = "DEL_DATE")
	private String delDate;
	@Column(name = "REC_TYPE")
	private String recType;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "LAST_MDATE")
	private String lastMDate;
	@Column(name = "LAST_MTIME")
	private String lastMTime;
	@Column(name = "ROC_PR_OPT")
	private String rocPrOpt;
	@Column(name = "FT_DAY_LIMIT")
	private Float ftDayLimit;

}
