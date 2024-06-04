package com.loy.adm.ws.classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AwardMasters;
import com.ag.loy.adm.entity.AwardPoints;
import com.ag.loy.adm.service.AwardMastersService;
import com.ag.loy.adm.service.AwardPointsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.loy.adm.ws.classes.WsAwardSave")
public class WsAwardSave implements Wisher {

	@Autowired
	AwardMastersService awardMastersService;
	@Autowired
	AwardPointsService awardPointsService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			if (rm.getAdditionalData().containsKey("awardMaster")
					&& rm.getAdditionalData().containsKey("awardPoints")) {
				AwardMasters awardMasters = new Gson().fromJson(rm.getAdditionalData().get("awardMaster").toString(),
						AwardMasters.class);
				Type listType = new TypeToken<ArrayList<AwardPoints>>() {
				}.getType();
				List<AwardPoints> items = new Gson().fromJson(rm.getAdditionalData().get("awardPoints").toString(),
						listType);

				String awardId = awardMastersService.fetchMaxAwardId();

				if (awardMasters.getDescription() != null) {
					if (awardMasters.getExpDays() != null) {
						if (items != null) {
							AwardMasters awd = null;
							if (awardMasters != null) {
								awd = awardMastersService.fetchByAwardName(awardMasters.getDescription(),
										awardMasters.getCorpId());
								if (awd != null) {
									response.setCode("0001");
									response.setMessage("Award Name Already Exists");

								} else {
									AgLogger.logInfo(awardId);
									AgLogger.logInfo(awardMasters.getCorpId());

									awardMasters.setCreatedBY(rm.getUserid());
									awardMasters.setCreatedOn(new java.util.Date());
									awardMasters.setUpdateOn(null);
									awardMasters.setmAwardId(Long.parseLong(awardId));
									awardMastersService.saveAwardMasters(awardMasters);
								}
							}

							if (awd == null) {
								if (items != null) {
									for (AwardPoints awardPoints : items) {
										System.out.println("masters.getAmountTo() : " + awardPoints.getAmountTo());
										AwardPoints aps = new AwardPoints();
										aps.setAmountFrom(awardPoints.getAmountFrom());
										aps.setAmountTo(awardPoints.getAmountTo());
										aps.setAmountSlab(awardPoints.getAmountSlab());
										aps.setExpDays(awardPoints.getExpDays());
										aps.setMax(awardPoints.getMax());
										aps.setMin(awardPoints.getMin());
										aps.setPrec(awardPoints.getPrec());
										aps.setAccountDr(awardPoints.getAccountDr());
										aps.setCreatedOn(new java.util.Date());
										aps.setCreatedBY(rm.getUserid());
										aps.setCorpid(awardMasters.getCorpId());
										aps.setMAWARD_ID(awardId);
										awardPointsService.saveAwardPoints(aps);
									}
								}
								response.setCode("0000");
								response.setMessage("Award Configured Successfully.");
							}

						} else {
							response.setCode("0001");
							response.setMessage("There should be some data in Award Points.");
						}
					} else {
						response.setCode("0001");
						response.setMessage("Please enter Valid Expiry Days.");
					}
				} else {
					response.setCode("0001");
					response.setMessage("Kindly Add Description.");
				}
			} else {
				response.setCode("0001");
				response.setMessage("Please Enter Some values.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}