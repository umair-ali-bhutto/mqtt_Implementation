package com.fuel.ws.classes;

import java.sql.Timestamp;
import java.util.Date;

import org.castor.core.util.Base64Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.entity.FuelProfilePicture;
import com.ag.fuel.services.FuelProfilePictureService;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.fuel.ws.classes.WsFuelAppProfileImageUpdate")
public class WsFuelAppProfileImageUpdate implements Wisher {

	@Autowired
	FuelProfilePictureService fuelProfilePictureService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String img = rm.getAdditionalData().get("img").toString();

			byte[] imageByte = Base64Decoder.decode(img);

			int userId = Integer.parseInt(rm.getUserid());

			FuelProfilePicture mdl = fuelProfilePictureService.findByUserId(userId);

			if (mdl != null) {
				mdl.setProfilePic(imageByte);
				mdl.setUpdatedOn(new Timestamp(new Date().getTime()));
				fuelProfilePictureService.update(mdl);
				AgLogger.logInfo("Profile Picture Data Updated.");
			} else {
				FuelProfilePicture mdl2 = new FuelProfilePicture();
				mdl2.setCorpId(rm.getCorpId());
				mdl2.setEntryDate(new Timestamp(new Date().getTime()));
				mdl2.setProfilePic(imageByte);
				mdl2.setUserId(userId);
				fuelProfilePictureService.insert(mdl2);
				AgLogger.logInfo("Profile Picture Data Inserted.");
			}

			response.setCode("0000");
			response.setMessage("Success.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
