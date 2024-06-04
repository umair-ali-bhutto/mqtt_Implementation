package com.ag.fuel.model;

import com.ag.fuel.entity.FuelProductConfig;

public class FuelConfigModel {
	private FuelProductConfig fuelProductConfig;
	private String amount;
	private int count;

	public FuelProductConfig getFuelProductConfig() {
		return fuelProductConfig;
	}

	public void setFuelProductConfig(FuelProductConfig fuelProductConfig) {
		this.fuelProductConfig = fuelProductConfig;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
