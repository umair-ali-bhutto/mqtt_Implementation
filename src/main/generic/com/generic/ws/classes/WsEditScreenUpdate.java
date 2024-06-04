package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsEditScreenUpdate")
public class WsEditScreenUpdate implements Wisher {

	@Autowired
	UserScreenService userScreenService;

	// static UserScreenService userScreenService = UtilAccess.userScreenService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		
		try {
			String screenId = rm.getAdditionalData().get("screenId").toString();
			String screenDesc = rm.getAdditionalData().get("screenDesc").toString();
			String fileName = rm.getAdditionalData().get("fileName").toString();
			String userName = rm.getAdditionalData().get("userName").toString();
			String isActive = (String) rm.getAdditionalData().get("isActive");
			String icon = rm.getAdditionalData().get("icon").toString();
			String routerLink = rm.getAdditionalData().get("routerLink").toString();

			UserScreen screen = new UserScreen();

			if (!screenDesc.isEmpty()) {
				// List<UserScreen> checkScreen = new userScreenService.;
				List<UserScreen> checkScreen = userScreenService.getUserScreens(screenDesc,rm.getCorpId());
				if (checkScreen.size() > 0) {
					// UserScreenService userscreenServ = null;
					UserScreen scrn = userScreenService.viewUserScreen(Integer.parseInt(screenId),rm.getCorpId());
					Date date = new Date();
					Timestamp time = new Timestamp(date.getTime());

					// int screenCode= (userScreenService.getScreencode())+1;
					// int screenCode= (userScreenService.getScreencode())+1;

					scrn.setInsertBy(userName);
					scrn.setInsertOn(time);
					scrn.setFileName(fileName);
					scrn.setScreenDesc(screenDesc);
					scrn.setIsActive(Integer.parseInt(isActive));
					scrn.setLastUpdateBy(userName);
					scrn.setLastUpdateOn(time);
					scrn.setRouterLink(routerLink);
					scrn.setIcon(icon);
					// scrn.setScreenCode(String.valueOf(screenCode) );

					// scrn.setScreenId(Integer.parseInt(screenId));

					userScreenService.updateScreen(screen);
					response.setCode("0000");
					response.setMessage("SCREEN UPDATED SUCCESSFULLY");
				} else {
					response.setCode("0001");
					response.setMessage("SCREEN DOES NOT EXIST ");

				}

			} else {
				response.setCode("0001");
				response.setMessage("SCREEN DESC IS MISSING OR File Type IS NOT OF TYPE .xhtml ");

			}

			Map<Object, Object> mp = new HashMap<Object, Object>();

//			if (screen != null) {
//				mp.put("screen", screen);
//
//				response.setCode("0000");
//				response.setMessage("SUCCESS");
//				response.setData(mp);
			// return response;

//			} else {
//				response.setCode("0001");
//				response.setMessage("DATA missing or incorrect");
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9995");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}
}