package com.ag.fuel.services;

import com.ag.fuel.entity.FuelProfilePicture;

public interface FuelProfilePictureService {

	public void insert(FuelProfilePicture fuelProfilePicture);

	public void update(FuelProfilePicture fuelProfilePicture);

	public FuelProfilePicture findByUserId(int userId);

}
