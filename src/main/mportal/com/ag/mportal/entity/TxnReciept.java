package com.ag.mportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the TXN_RECIEPT database table.
 * 
 */
@Entity
@Table(name = "TXN_RECIEPT")
//@NamedQuery(name = "TxnReciept.findAll", query = "SELECT t FROM TxnReciept t")
@NamedQueries({ 
@NamedQuery(name = "TxnReciept.fetchTxnReciept", query = "SELECT t FROM TxnReciept t WHERE t.refNum=:refNum ")
})

public class TxnReciept implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "TXN_RECIEPT_SEQ")
	@SequenceGenerator(name="TXN_RECIEPT_SEQ",sequenceName="TXN_RECIEPT_SEQ", allocationSize=1)
	private long id;

	@Lob
	@Column(name = "FILE_BYTE")
	private byte[] fileByte;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "REF_NUM")
	private String refNum;

	@Column(name = "TXN_LOG_ID")
	private int txnLogId;

	public TxnReciept() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getFileByte() {
		return this.fileByte;
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRefNum() {
		return this.refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public int getTxnLogId() {
		return this.txnLogId;
	}

	public void setTxnLogId(int txnLogId) {
		this.txnLogId = txnLogId;
	}

}