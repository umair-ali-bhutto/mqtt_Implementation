package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsAddNewScreen")
public class WsAddNewScreen implements Wisher {

	@Autowired
	UserScreenService userScreenService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			String screenDesc = (String) rm.getAdditionalData().get("screenDesc");
			String isActive = (String) rm.getAdditionalData().get("isActive");
			String fileName = rm.getAdditionalData().get("fileName").toString();
			String userId = rm.getUserid();
			String icon = rm.getAdditionalData().get("icon").toString();
			String routerLink = rm.getAdditionalData().get("routerLink").toString();

			if (screenDesc != null) {
				List<UserScreen> checkScreen = userScreenService.getUserScreens(screenDesc,rm.getCorpId());
				if (checkScreen.size() == 0) {
					UserScreen scrn = new UserScreen();
					Date date = new Date();
					Timestamp time = new Timestamp(date.getTime());
					int screenCode = (int) (userScreenService.getScreencode()) + 1;

					scrn.setInsertBy(userId);
					scrn.setInsertOn(time);
					scrn.setFileName(fileName);
					scrn.setScreenDesc(screenDesc);
					scrn.setIsActive(Integer.parseInt(isActive));
					scrn.setLastUpdateBy(null);
					scrn.setLastUpdateOn(null);
					scrn.setScreenCode(String.valueOf(screenCode));
					scrn.setRouterLink(routerLink);
					scrn.setIcon(icon);
					scrn.setCorpId(rm.getCorpId());

					userScreenService.addNewScreen(scrn);
					response.setCode("0000");
					response.setMessage("SCREEN ADDED SUCCESSFULLY");
				} else {
					response.setCode("0001");
					response.setMessage("SCREEN ALREADY EXISTS ");

				}

			} else {
				response.setCode("0001");
				response.setMessage("SCREEN DESC IS MISSING OR File Type IS NOT OF TYPE .xhtml ");

			}

		} catch (Exception ex) {

			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}