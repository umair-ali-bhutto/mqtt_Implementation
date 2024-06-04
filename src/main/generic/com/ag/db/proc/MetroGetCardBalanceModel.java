package com.ag.db.proc;

import java.util.List;

import com.ag.metro.model.GenericLovModel;

public class MetroGetCardBalanceModel {
	
	private String vrsp_code;
	private String vmessage;
	private String vstatus;
	private String vbalance;
	private List<GenericLovModel> vallow_status;
	
	
	public String getVrsp_code() {
		return vrsp_code;
	}
	public void setVrsp_code(String vrsp_code) {
		this.vrsp_code = vrsp_code;
	}
	public String getVmessage() {
		return vmessage;
	}
	public void setVmessage(String vmessage) {
		this.vmessage = vmessage;
	}
	public String getVstatus() {
		return vstatus;
	}
	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
	}
	public String getVbalance() {
		return vbalance;
	}
	public void setVbalance(String vbalance) {
		this.vbalance = vbalance;
	}
	public List<GenericLovModel> getVallow_status() {
		return vallow_status;
	}
	public void setVallow_status(List<GenericLovModel> vallow_status) {
		this.vallow_status = vallow_status;
	}
	
	
	
	
}
