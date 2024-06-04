package com.ag.mportal.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the BATCH_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "TAPNPAY_ROUTING_CONFIG")
@NamedQueries({

		@NamedQuery(name = "TapNPayRoutingConfig.retrieveAll", query = "SELECT a FROM TapNPayRoutingConfig a WHERE a.isActive=1")

})
public class TapNPayRoutingConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name = "CORPID")
	private String corpId;

	@Column(name = "ISO_PECKAGER_PATH")
	private String isoPeckagerPath;

	@Column(name = "LOGGER")
	private String logger;

	@Column(name = "NAC_IP")
	private String nacIp;

	@Column(name = "NAC_PORT")
	private String nacPort;

	@Column(name = "ISO_TPDU")
	private String isoTpdu;

	@Column(name = "ISO_MESSAGE_TYPE")
	private String isoMessage;

	@Column(name = "ISO_TXN_TYPE")
	private String isoTxnType;

	@Column(name = "ISO_POS_ENTRY_MODE")
	private String isoPosEntryMode;

	@Column(name = "ISO_POINT_SERVICE_CODE")
	private String isoPointServiceCode;

	@Column(name = "ISO_PRIVATE_DATA")
	private String isoPrviateData;

	@Column(name = "ISO_CRYPTOGRAM_INFO_DATA")
	private String isoCryptoGram;

	@Column(name = "ISO_TERMINAL_CAP")
	private String isoTerminalCap;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "ROUTER_CLASS")
	private String routerClass;

	@Column(name = "TXN_TYPE")
	private String txnType;

	@Column(name = "CARD_SCHEME")
	private String cardScheme;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getIsoPeckagerPath() {
		return isoPeckagerPath;
	}

	public void setIsoPeckagerPath(String isoPeckagerPath) {
		this.isoPeckagerPath = isoPeckagerPath;
	}

	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public String getNacIp() {
		return nacIp;
	}

	public void setNacIp(String nacIp) {
		this.nacIp = nacIp;
	}

	public String getNacPort() {
		return nacPort;
	}

	public void setNacPort(String nacPort) {
		this.nacPort = nacPort;
	}

	public String getIsoTpdu() {
		return isoTpdu;
	}

	public void setIsoTpdu(String isoTpdu) {
		this.isoTpdu = isoTpdu;
	}

	public String getIsoMessage() {
		return isoMessage;
	}

	public void setIsoMessage(String isoMessage) {
		this.isoMessage = isoMessage;
	}

	public String getIsoTxnType() {
		return isoTxnType;
	}

	public void setIsoTxnType(String isoTxnType) {
		this.isoTxnType = isoTxnType;
	}

	public String getIsoPosEntryMode() {
		return isoPosEntryMode;
	}

	public void setIsoPosEntryMode(String isoPosEntryMode) {
		this.isoPosEntryMode = isoPosEntryMode;
	}

	public String getIsoPointServiceCode() {
		return isoPointServiceCode;
	}

	public void setIsoPointServiceCode(String isoPointServiceCode) {
		this.isoPointServiceCode = isoPointServiceCode;
	}

	public String getIsoPrviateData() {
		return isoPrviateData;
	}

	public void setIsoPrviateData(String isoPrviateData) {
		this.isoPrviateData = isoPrviateData;
	}

	public String getIsoCryptoGram() {
		return isoCryptoGram;
	}

	public void setIsoCryptoGram(String isoCryptoGram) {
		this.isoCryptoGram = isoCryptoGram;
	}

	public String getIsoTerminalCap() {
		return isoTerminalCap;
	}

	public void setIsoTerminalCap(String isoTerminalCap) {
		this.isoTerminalCap = isoTerminalCap;
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

	public String getRouterClass() {
		return routerClass;
	}

	public void setRouterClass(String routerClass) {
		this.routerClass = routerClass;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getCardScheme() {
		return cardScheme;
	}

	public void setCardScheme(String cardScheme) {
		this.cardScheme = cardScheme;
	}

}