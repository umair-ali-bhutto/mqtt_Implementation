package com.ag.mportal.model;

public class DonationBinRangesModel {

	private int id;
	private String fromBin;
	private String toBin;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromBin() {
		return fromBin;
	}

	public void setFromBin(String fromBin) {
		this.fromBin = fromBin;
	}

	public String getToBin() {
		return toBin;
	}

	public void setToBin(String toBin) {
		this.toBin = toBin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
