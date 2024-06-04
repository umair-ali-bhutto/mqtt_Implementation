package com.ag.fuel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the EFTLOGHISTTBL_CPY database table.
 * 
 */
//@Entity
@Table(name="EFTLOGHISTTBL")
@NamedQueries({
	@NamedQuery(name="Eftloghisttbl.findAll", query="SELECT e FROM Eftloghisttbl e"),
	@NamedQuery(name="Eftloghisttbl.findById", query="SELECT e FROM Eftloghisttbl e where m.acqId = :id")
})
public class Eftloghisttbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACC_NO")
	private String accNo;

	@Column(name="ACQ_ID")
	private String acqId;

	@Column(name="ADD_POS_INFO")
	private String addPosInfo;

	@Column(name="ADDR_VERF_CD")
	private String addrVerfCd;

	@Column(name="AUTH_ID")
	private String authId;

	private Double balance;

	@Column(name="BATCH_NO")
	private String batchNo;

	@Column(name="BILL_AMT")
	private Double billAmt;

	@Column(name="BILL_CURR")
	private String billCurr;

	@Column(name="BILL_RATE")
	private Double billRate;

	@Column(name="CARD_NO")
	private String cardNo;

	@Column(name="CARD_STATUS")
	private String cardStatus;

	private String category;

	@Column(name="COMM_AMT")
	private Double commAmt;

	@Column(name="COMM_FLAG")
	private String commFlag;

	@Column(name="COMM_LEVEL")
	private String commLevel;

	@Column(name="COMM_RATE")
	private Double commRate;

	@Column(name="COMM_SID")
	private String commSid;

	@Column(name="COMM_TYPE")
	private String commType;

	@Column(name="COND_CODE")
	private String condCode;

	@Column(name="CRD_LMT")
	private Double crdLmt;

	@Column(name="CUTF_DATE")
	private String cutfDate;

	@Column(name="CUTF_TIME")
	private String cutfTime;

	@Column(name="CVV_RESULT")
	private String cvvResult;

	@Column(name="DATE_TIME")
	private String dateTime;

	@Column(name="ENT_MODE")
	private String entMode;

	@Column(name="EST_ID")
	private String estId;

	@Column(name="EXP_DATE")
	private String expDate;

	@Column(name="FC_REJ_INFO1")
	private Long fcRejInfo1;

	@Column(name="FC_REJ_INFO2")
	private Long fcRejInfo2;

	@Column(name="FC_SERV_ST_ID")
	private String fcServStId;

	@Column(name="FINISH_TIME")
	private String finishTime;

	@Column(name="FLR_LMT")
	private Long flrLmt;

	@Column(name="FOR_ID")
	private String forId;

	@Column(name="HOST_AUTH")
	private String hostAuth;

	@Column(name="HOST_MSG")
	private String hostMsg;

	@Column(name="HOST_NWK_ID")
	private String hostNwkId;

	@Column(name="HOST_PROC_CODE")
	private String hostProcCode;

	@Column(name="HOST_RSP")
	private String hostRsp;

	@Column(name="HOST_TRACE")
	private String hostTrace;

	@Column(name="INCOME_SRC")
	private String incomeSrc;

	@Column(name="INV_NO")
	private String invNo;

	@Column(name="LAST_MDATE")
	private String lastMdate;

	@Column(name="MC_BANKNET_DATA")
	private String mcBanknetData;

	@Column(name="MER_NAME")
	private String merName;

	private String nii;

	@Column(name="NUM_COUNTRY")
	private String numCountry;

	private String odometer;

	@Column(name="ORG_TXNTYPE")
	private String orgTxntype;

	@Column(name="ORI_AMT")
	private Double oriAmt;

	@Column(name="PREV_RET_REF")
	private String prevRetRef;

	@Column(name="PRI_CARD_NO")
	private String priCardNo;

	@Column(name="PROCESS_CODE")
	private String processCode;

	@Column(name="PROD_AMT1")
	private Double prodAmt1;

	@Column(name="PROD_AMT2")
	private Double prodAmt2;

	@Column(name="PROD_AMT3")
	private Double prodAmt3;

	@Column(name="PROD_AMT4")
	private Double prodAmt4;

	@Column(name="PROD_AMT5")
	private Double prodAmt5;

	@Column(name="PROD_AMT6")
	private Double prodAmt6;

	@Column(name="PROD_AMT7")
	private Double prodAmt7;

	@Column(name="PROD_AMT8")
	private Double prodAmt8;

	@Column(name="PROD_CD1")
	private String prodCd1;

	@Column(name="PROD_CD2")
	private String prodCd2;

	@Column(name="PROD_CD3")
	private String prodCd3;

	@Column(name="PROD_CD4")
	private String prodCd4;

	@Column(name="PROD_CD5")
	private String prodCd5;

	@Column(name="PROD_CD6")
	private String prodCd6;

	@Column(name="PROD_CD7")
	private String prodCd7;

	@Column(name="PROD_CD8")
	private String prodCd8;

	@Column(name="PROD_ORG_AMT1")
	private Double prodOrgAmt1;

	@Column(name="PROD_ORG_AMT2")
	private Double prodOrgAmt2;

	@Column(name="PROD_ORG_AMT3")
	private Double prodOrgAmt3;

	@Column(name="PROD_ORG_AMT4")
	private Double prodOrgAmt4;

	@Column(name="PROD_ORG_AMT5")
	private Double prodOrgAmt5;

	@Column(name="PROD_ORG_AMT6")
	private Double prodOrgAmt6;

	@Column(name="PROD_ORG_AMT7")
	private BigDecimal prodOrgAmt7;

	@Column(name="PROD_ORG_AMT8")
	private Double prodOrgAmt8;

	@Column(name="PROD_QTY1")
	private Long prodQty1;

	@Column(name="PROD_QTY2")
	private Long prodQty2;

	@Column(name="PROD_QTY3")
	private Long prodQty3;

	@Column(name="PROD_QTY4")
	private Long prodQty4;

	@Column(name="PROD_QTY5")
	private Long prodQty5;

	@Column(name="PROD_QTY6")
	private Long prodQty6;

	@Column(name="PROD_QTY7")
	private Long prodQty7;

	@Column(name="PROD_QTY8")
	private Long prodQty8;

	@Column(name="PRODUCT_CODE")
	private String productCode;

	@Column(name="PUMP_NUM")
	private String pumpNum;

	@Column(name="REASON_CODE")
	private String reasonCode;

	@Column(name="RECV_DATE")
	private String recvDate;

	@Column(name="RECV_TIME")
	private String recvTime;

	@Column(name="REJ_REASON")
	private String rejReason;

	private String remark;

	@Column(name="RET_REF")
	private String retRef;

	@Column(name="RSP_CODE")
	private String rspCode;

	@Column(name="RSP_DATA")
	private String rspData;

	@Column(name="SE_AMT")
	private Double seAmt;

	@Column(name="SE_CURR")
	private String seCurr;

	@Column(name="SE_RATE")
	private Double seRate;

	@Column(name="SET_REF_NUM")
	private String setRefNum;

	@Column(name="SETTLE_CITY")
	private String settleCity;

	@Column(name="SETTLE_DATE")
	private String settleDate;

	@Column(name="SETTLE_FLG")
	private String settleFlg;

	@Column(name="SETTLE_PREFIX")
	private String settlePrefix;

	@Column(name="SRV_MID")
	private String srvMid;

	private String standin;

	@Column(name="START_TIME")
	private String startTime;

	@Column(name="SYS_BATCH")
	private String sysBatch;

	@Column(name="TAGIN_TIME")
	private Double taginTime;

	@Column(name="TAGSEQ_NO")
	private Long tagseqNo;

	private Double tax;

	@Column(name="TCC_CODE")
	private String tccCode;

	@Column(name="TERM_ID")
	private String termId;

	@Column(name="TERM_MSG")
	private String termMsg;

	@Column(name="TERM_TYPE")
	private String termType;

	@Column(name="TICKET_NO")
	private String ticketNo;

	private Double tips;

	private String tpdu;

	@Column(name="TRACE_NO")
	private String traceNo;

	@Column(name="TTS_EXP_FLAG")
	private String ttsExpFlag;

	@Column(name="TXN_AMT")
	private Double txnAmt;

	@Column(name="TXN_CURR")
	private String txnCurr;

	@Column(name="TXN_DATE")
	private String txnDate;

	@Column(name="TXN_TYPE")
	private String txnType;

	@Column(name="UT_ACC_NAME")
	private String utAccName;

	@Column(name="UT_ACC_NUM")
	private String utAccNum;

	@Column(name="UT_AMT_AF_DUE")
	private String utAmtAfDue;

	@Column(name="UT_AMT_DUE")
	private String utAmtDue;

	@Column(name="UT_AMT_PAID")
	private String utAmtPaid;

	@Column(name="UT_BILL_DATE")
	private String utBillDate;

	@Column(name="UT_BILL_STATUS")
	private String utBillStatus;

	@Column(name="UT_CMPY_STN")
	private String utCmpyStn;

	@Column(name="UT_DUE_DATE")
	private String utDueDate;

	@Column(name="UT_FILE_PREFIX")
	private String utFilePrefix;

	@Column(name="UT_HOST_RSP")
	private String utHostRsp;

	@Column(name="UT_MIN_PCENT")
	private String utMinPcent;

	@Column(name="UT_NET_CED")
	private String utNetCed;

	@Column(name="UT_NET_WHT")
	private String utNetWht;

	@Column(name="UT_NWK_ID")
	private String utNwkId;

	@Column(name="UT_PARTIAL_IND")
	private String utPartialInd;

	@Column(name="UT_PAY_TYPE")
	private String utPayType;

	@Column(name="UT_TAX_AMT")
	private String utTaxAmt;

	@Column(name="UTIL_INFO")
	private String utilInfo;

	@Column(name="UTIL_TYPE")
	private String utilType;

	@Column(name="UTP_BAL")
	private String utpBal;

	@Column(name="UTP_BAL_BEFORE")
	private String utpBalBefore;

	@Column(name="UTP_V_EXPD")
	private String utpVExpd;

	@Column(name="UTP_V_PERIOD")
	private String utpVPeriod;

	@Column(name="UTP_V_SERIAL")
	private String utpVSerial;

	@Column(name="VEHICLE_NUM")
	private String vehicleNum;

	public Eftloghisttbl() {
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAcqId() {
		return acqId;
	}

	public void setAcqId(String acqId) {
		this.acqId = acqId;
	}

	public String getAddPosInfo() {
		return addPosInfo;
	}

	public void setAddPosInfo(String addPosInfo) {
		this.addPosInfo = addPosInfo;
	}

	public String getAddrVerfCd() {
		return addrVerfCd;
	}

	public void setAddrVerfCd(String addrVerfCd) {
		this.addrVerfCd = addrVerfCd;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Double getBillAmt() {
		return billAmt;
	}

	public void setBillAmt(Double billAmt) {
		this.billAmt = billAmt;
	}

	public String getBillCurr() {
		return billCurr;
	}

	public void setBillCurr(String billCurr) {
		this.billCurr = billCurr;
	}

	public Double getBillRate() {
		return billRate;
	}

	public void setBillRate(Double billRate) {
		this.billRate = billRate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getCommAmt() {
		return commAmt;
	}

	public void setCommAmt(Double commAmt) {
		this.commAmt = commAmt;
	}

	public String getCommFlag() {
		return commFlag;
	}

	public void setCommFlag(String commFlag) {
		this.commFlag = commFlag;
	}

	public String getCommLevel() {
		return commLevel;
	}

	public void setCommLevel(String commLevel) {
		this.commLevel = commLevel;
	}

	public Double getCommRate() {
		return commRate;
	}

	public void setCommRate(Double commRate) {
		this.commRate = commRate;
	}

	public String getCommSid() {
		return commSid;
	}

	public void setCommSid(String commSid) {
		this.commSid = commSid;
	}

	public String getCommType() {
		return commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
	}

	public String getCondCode() {
		return condCode;
	}

	public void setCondCode(String condCode) {
		this.condCode = condCode;
	}

	public Double getCrdLmt() {
		return crdLmt;
	}

	public void setCrdLmt(Double crdLmt) {
		this.crdLmt = crdLmt;
	}

	public String getCutfDate() {
		return cutfDate;
	}

	public void setCutfDate(String cutfDate) {
		this.cutfDate = cutfDate;
	}

	public String getCutfTime() {
		return cutfTime;
	}

	public void setCutfTime(String cutfTime) {
		this.cutfTime = cutfTime;
	}

	public String getCvvResult() {
		return cvvResult;
	}

	public void setCvvResult(String cvvResult) {
		this.cvvResult = cvvResult;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getEntMode() {
		return entMode;
	}

	public void setEntMode(String entMode) {
		this.entMode = entMode;
	}

	public String getEstId() {
		return estId;
	}

	public void setEstId(String estId) {
		this.estId = estId;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public Long getFcRejInfo1() {
		return fcRejInfo1;
	}

	public void setFcRejInfo1(Long fcRejInfo1) {
		this.fcRejInfo1 = fcRejInfo1;
	}

	public Long getFcRejInfo2() {
		return fcRejInfo2;
	}

	public void setFcRejInfo2(Long fcRejInfo2) {
		this.fcRejInfo2 = fcRejInfo2;
	}

	public String getFcServStId() {
		return fcServStId;
	}

	public void setFcServStId(String fcServStId) {
		this.fcServStId = fcServStId;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Long getFlrLmt() {
		return flrLmt;
	}

	public void setFlrLmt(Long flrLmt) {
		this.flrLmt = flrLmt;
	}

	public String getForId() {
		return forId;
	}

	public void setForId(String forId) {
		this.forId = forId;
	}

	public String getHostAuth() {
		return hostAuth;
	}

	public void setHostAuth(String hostAuth) {
		this.hostAuth = hostAuth;
	}

	public String getHostMsg() {
		return hostMsg;
	}

	public void setHostMsg(String hostMsg) {
		this.hostMsg = hostMsg;
	}

	public String getHostNwkId() {
		return hostNwkId;
	}

	public void setHostNwkId(String hostNwkId) {
		this.hostNwkId = hostNwkId;
	}

	public String getHostProcCode() {
		return hostProcCode;
	}

	public void setHostProcCode(String hostProcCode) {
		this.hostProcCode = hostProcCode;
	}

	public String getHostRsp() {
		return hostRsp;
	}

	public void setHostRsp(String hostRsp) {
		this.hostRsp = hostRsp;
	}

	public String getHostTrace() {
		return hostTrace;
	}

	public void setHostTrace(String hostTrace) {
		this.hostTrace = hostTrace;
	}

	public String getIncomeSrc() {
		return incomeSrc;
	}

	public void setIncomeSrc(String incomeSrc) {
		this.incomeSrc = incomeSrc;
	}

	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	public String getLastMdate() {
		return lastMdate;
	}

	public void setLastMdate(String lastMdate) {
		this.lastMdate = lastMdate;
	}

	public String getMcBanknetData() {
		return mcBanknetData;
	}

	public void setMcBanknetData(String mcBanknetData) {
		this.mcBanknetData = mcBanknetData;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getNii() {
		return nii;
	}

	public void setNii(String nii) {
		this.nii = nii;
	}

	public String getNumCountry() {
		return numCountry;
	}

	public void setNumCountry(String numCountry) {
		this.numCountry = numCountry;
	}

	public String getOdometer() {
		return odometer;
	}

	public void setOdometer(String odometer) {
		this.odometer = odometer;
	}

	public String getOrgTxntype() {
		return orgTxntype;
	}

	public void setOrgTxntype(String orgTxntype) {
		this.orgTxntype = orgTxntype;
	}

	public Double getOriAmt() {
		return oriAmt;
	}

	public void setOriAmt(Double oriAmt) {
		this.oriAmt = oriAmt;
	}

	public String getPrevRetRef() {
		return prevRetRef;
	}

	public void setPrevRetRef(String prevRetRef) {
		this.prevRetRef = prevRetRef;
	}

	public String getPriCardNo() {
		return priCardNo;
	}

	public void setPriCardNo(String priCardNo) {
		this.priCardNo = priCardNo;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public Double getProdAmt1() {
		return prodAmt1;
	}

	public void setProdAmt1(Double prodAmt1) {
		this.prodAmt1 = prodAmt1;
	}

	public Double getProdAmt2() {
		return prodAmt2;
	}

	public void setProdAmt2(Double prodAmt2) {
		this.prodAmt2 = prodAmt2;
	}

	public Double getProdAmt3() {
		return prodAmt3;
	}

	public void setProdAmt3(Double prodAmt3) {
		this.prodAmt3 = prodAmt3;
	}

	public Double getProdAmt4() {
		return prodAmt4;
	}

	public void setProdAmt4(Double prodAmt4) {
		this.prodAmt4 = prodAmt4;
	}

	public Double getProdAmt5() {
		return prodAmt5;
	}

	public void setProdAmt5(Double prodAmt5) {
		this.prodAmt5 = prodAmt5;
	}

	public Double getProdAmt6() {
		return prodAmt6;
	}

	public void setProdAmt6(Double prodAmt6) {
		this.prodAmt6 = prodAmt6;
	}

	public Double getProdAmt7() {
		return prodAmt7;
	}

	public void setProdAmt7(Double prodAmt7) {
		this.prodAmt7 = prodAmt7;
	}

	public Double getProdAmt8() {
		return prodAmt8;
	}

	public void setProdAmt8(Double prodAmt8) {
		this.prodAmt8 = prodAmt8;
	}

	public String getProdCd1() {
		return prodCd1;
	}

	public void setProdCd1(String prodCd1) {
		this.prodCd1 = prodCd1;
	}

	public String getProdCd2() {
		return prodCd2;
	}

	public void setProdCd2(String prodCd2) {
		this.prodCd2 = prodCd2;
	}

	public String getProdCd3() {
		return prodCd3;
	}

	public void setProdCd3(String prodCd3) {
		this.prodCd3 = prodCd3;
	}

	public String getProdCd4() {
		return prodCd4;
	}

	public void setProdCd4(String prodCd4) {
		this.prodCd4 = prodCd4;
	}

	public String getProdCd5() {
		return prodCd5;
	}

	public void setProdCd5(String prodCd5) {
		this.prodCd5 = prodCd5;
	}

	public String getProdCd6() {
		return prodCd6;
	}

	public void setProdCd6(String prodCd6) {
		this.prodCd6 = prodCd6;
	}

	public String getProdCd7() {
		return prodCd7;
	}

	public void setProdCd7(String prodCd7) {
		this.prodCd7 = prodCd7;
	}

	public String getProdCd8() {
		return prodCd8;
	}

	public void setProdCd8(String prodCd8) {
		this.prodCd8 = prodCd8;
	}

	public Double getProdOrgAmt1() {
		return prodOrgAmt1;
	}

	public void setProdOrgAmt1(Double prodOrgAmt1) {
		this.prodOrgAmt1 = prodOrgAmt1;
	}

	public Double getProdOrgAmt2() {
		return prodOrgAmt2;
	}

	public void setProdOrgAmt2(Double prodOrgAmt2) {
		this.prodOrgAmt2 = prodOrgAmt2;
	}

	public Double getProdOrgAmt3() {
		return prodOrgAmt3;
	}

	public void setProdOrgAmt3(Double prodOrgAmt3) {
		this.prodOrgAmt3 = prodOrgAmt3;
	}

	public Double getProdOrgAmt4() {
		return prodOrgAmt4;
	}

	public void setProdOrgAmt4(Double prodOrgAmt4) {
		this.prodOrgAmt4 = prodOrgAmt4;
	}

	public Double getProdOrgAmt5() {
		return prodOrgAmt5;
	}

	public void setProdOrgAmt5(Double prodOrgAmt5) {
		this.prodOrgAmt5 = prodOrgAmt5;
	}

	public Double getProdOrgAmt6() {
		return prodOrgAmt6;
	}

	public void setProdOrgAmt6(Double prodOrgAmt6) {
		this.prodOrgAmt6 = prodOrgAmt6;
	}

	public BigDecimal getProdOrgAmt7() {
		return prodOrgAmt7;
	}

	public void setProdOrgAmt7(BigDecimal prodOrgAmt7) {
		this.prodOrgAmt7 = prodOrgAmt7;
	}

	public Double getProdOrgAmt8() {
		return prodOrgAmt8;
	}

	public void setProdOrgAmt8(Double prodOrgAmt8) {
		this.prodOrgAmt8 = prodOrgAmt8;
	}

	public Long getProdQty1() {
		return prodQty1;
	}

	public void setProdQty1(Long prodQty1) {
		this.prodQty1 = prodQty1;
	}

	public Long getProdQty2() {
		return prodQty2;
	}

	public void setProdQty2(Long prodQty2) {
		this.prodQty2 = prodQty2;
	}

	public Long getProdQty3() {
		return prodQty3;
	}

	public void setProdQty3(Long prodQty3) {
		this.prodQty3 = prodQty3;
	}

	public Long getProdQty4() {
		return prodQty4;
	}

	public void setProdQty4(Long prodQty4) {
		this.prodQty4 = prodQty4;
	}

	public Long getProdQty5() {
		return prodQty5;
	}

	public void setProdQty5(Long prodQty5) {
		this.prodQty5 = prodQty5;
	}

	public Long getProdQty6() {
		return prodQty6;
	}

	public void setProdQty6(Long prodQty6) {
		this.prodQty6 = prodQty6;
	}

	public Long getProdQty7() {
		return prodQty7;
	}

	public void setProdQty7(Long prodQty7) {
		this.prodQty7 = prodQty7;
	}

	public Long getProdQty8() {
		return prodQty8;
	}

	public void setProdQty8(Long prodQty8) {
		this.prodQty8 = prodQty8;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPumpNum() {
		return pumpNum;
	}

	public void setPumpNum(String pumpNum) {
		this.pumpNum = pumpNum;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getRecvDate() {
		return recvDate;
	}

	public void setRecvDate(String recvDate) {
		this.recvDate = recvDate;
	}

	public String getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}

	public String getRejReason() {
		return rejReason;
	}

	public void setRejReason(String rejReason) {
		this.rejReason = rejReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRetRef() {
		return retRef;
	}

	public void setRetRef(String retRef) {
		this.retRef = retRef;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspData() {
		return rspData;
	}

	public void setRspData(String rspData) {
		this.rspData = rspData;
	}

	public Double getSeAmt() {
		return seAmt;
	}

	public void setSeAmt(Double seAmt) {
		this.seAmt = seAmt;
	}

	public String getSeCurr() {
		return seCurr;
	}

	public void setSeCurr(String seCurr) {
		this.seCurr = seCurr;
	}

	public Double getSeRate() {
		return seRate;
	}

	public void setSeRate(Double seRate) {
		this.seRate = seRate;
	}

	public String getSetRefNum() {
		return setRefNum;
	}

	public void setSetRefNum(String setRefNum) {
		this.setRefNum = setRefNum;
	}

	public String getSettleCity() {
		return settleCity;
	}

	public void setSettleCity(String settleCity) {
		this.settleCity = settleCity;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getSettleFlg() {
		return settleFlg;
	}

	public void setSettleFlg(String settleFlg) {
		this.settleFlg = settleFlg;
	}

	public String getSettlePrefix() {
		return settlePrefix;
	}

	public void setSettlePrefix(String settlePrefix) {
		this.settlePrefix = settlePrefix;
	}

	public String getSrvMid() {
		return srvMid;
	}

	public void setSrvMid(String srvMid) {
		this.srvMid = srvMid;
	}

	public String getStandin() {
		return standin;
	}

	public void setStandin(String standin) {
		this.standin = standin;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getSysBatch() {
		return sysBatch;
	}

	public void setSysBatch(String sysBatch) {
		this.sysBatch = sysBatch;
	}

	public Double getTaginTime() {
		return taginTime;
	}

	public void setTaginTime(Double taginTime) {
		this.taginTime = taginTime;
	}

	public Long getTagseqNo() {
		return tagseqNo;
	}

	public void setTagseqNo(Long tagseqNo) {
		this.tagseqNo = tagseqNo;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getTccCode() {
		return tccCode;
	}

	public void setTccCode(String tccCode) {
		this.tccCode = tccCode;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermMsg() {
		return termMsg;
	}

	public void setTermMsg(String termMsg) {
		this.termMsg = termMsg;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Double getTips() {
		return tips;
	}

	public void setTips(Double tips) {
		this.tips = tips;
	}

	public String getTpdu() {
		return tpdu;
	}

	public void setTpdu(String tpdu) {
		this.tpdu = tpdu;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getTtsExpFlag() {
		return ttsExpFlag;
	}

	public void setTtsExpFlag(String ttsExpFlag) {
		this.ttsExpFlag = ttsExpFlag;
	}

	public Double getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(Double txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTxnCurr() {
		return txnCurr;
	}

	public void setTxnCurr(String txnCurr) {
		this.txnCurr = txnCurr;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getUtAccName() {
		return utAccName;
	}

	public void setUtAccName(String utAccName) {
		this.utAccName = utAccName;
	}

	public String getUtAccNum() {
		return utAccNum;
	}

	public void setUtAccNum(String utAccNum) {
		this.utAccNum = utAccNum;
	}

	public String getUtAmtAfDue() {
		return utAmtAfDue;
	}

	public void setUtAmtAfDue(String utAmtAfDue) {
		this.utAmtAfDue = utAmtAfDue;
	}

	public String getUtAmtDue() {
		return utAmtDue;
	}

	public void setUtAmtDue(String utAmtDue) {
		this.utAmtDue = utAmtDue;
	}

	public String getUtAmtPaid() {
		return utAmtPaid;
	}

	public void setUtAmtPaid(String utAmtPaid) {
		this.utAmtPaid = utAmtPaid;
	}

	public String getUtBillDate() {
		return utBillDate;
	}

	public void setUtBillDate(String utBillDate) {
		this.utBillDate = utBillDate;
	}

	public String getUtBillStatus() {
		return utBillStatus;
	}

	public void setUtBillStatus(String utBillStatus) {
		this.utBillStatus = utBillStatus;
	}

	public String getUtCmpyStn() {
		return utCmpyStn;
	}

	public void setUtCmpyStn(String utCmpyStn) {
		this.utCmpyStn = utCmpyStn;
	}

	public String getUtDueDate() {
		return utDueDate;
	}

	public void setUtDueDate(String utDueDate) {
		this.utDueDate = utDueDate;
	}

	public String getUtFilePrefix() {
		return utFilePrefix;
	}

	public void setUtFilePrefix(String utFilePrefix) {
		this.utFilePrefix = utFilePrefix;
	}

	public String getUtHostRsp() {
		return utHostRsp;
	}

	public void setUtHostRsp(String utHostRsp) {
		this.utHostRsp = utHostRsp;
	}

	public String getUtMinPcent() {
		return utMinPcent;
	}

	public void setUtMinPcent(String utMinPcent) {
		this.utMinPcent = utMinPcent;
	}

	public String getUtNetCed() {
		return utNetCed;
	}

	public void setUtNetCed(String utNetCed) {
		this.utNetCed = utNetCed;
	}

	public String getUtNetWht() {
		return utNetWht;
	}

	public void setUtNetWht(String utNetWht) {
		this.utNetWht = utNetWht;
	}

	public String getUtNwkId() {
		return utNwkId;
	}

	public void setUtNwkId(String utNwkId) {
		this.utNwkId = utNwkId;
	}

	public String getUtPartialInd() {
		return utPartialInd;
	}

	public void setUtPartialInd(String utPartialInd) {
		this.utPartialInd = utPartialInd;
	}

	public String getUtPayType() {
		return utPayType;
	}

	public void setUtPayType(String utPayType) {
		this.utPayType = utPayType;
	}

	public String getUtTaxAmt() {
		return utTaxAmt;
	}

	public void setUtTaxAmt(String utTaxAmt) {
		this.utTaxAmt = utTaxAmt;
	}

	public String getUtilInfo() {
		return utilInfo;
	}

	public void setUtilInfo(String utilInfo) {
		this.utilInfo = utilInfo;
	}

	public String getUtilType() {
		return utilType;
	}

	public void setUtilType(String utilType) {
		this.utilType = utilType;
	}

	public String getUtpBal() {
		return utpBal;
	}

	public void setUtpBal(String utpBal) {
		this.utpBal = utpBal;
	}

	public String getUtpBalBefore() {
		return utpBalBefore;
	}

	public void setUtpBalBefore(String utpBalBefore) {
		this.utpBalBefore = utpBalBefore;
	}

	public String getUtpVExpd() {
		return utpVExpd;
	}

	public void setUtpVExpd(String utpVExpd) {
		this.utpVExpd = utpVExpd;
	}

	public String getUtpVPeriod() {
		return utpVPeriod;
	}

	public void setUtpVPeriod(String utpVPeriod) {
		this.utpVPeriod = utpVPeriod;
	}

	public String getUtpVSerial() {
		return utpVSerial;
	}

	public void setUtpVSerial(String utpVSerial) {
		this.utpVSerial = utpVSerial;
	}

	public String getVehicleNum() {
		return vehicleNum;
	}

	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	
}