package com.generic.ws.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserGroups;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserGroupService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsViewUserScreen")
public class WsViewUserScreen implements Wisher{
	
	@Autowired
	UserScreenService userScreenService;
	@Autowired
	UserGroupService userGroupService;
	
//	static UserScreenService userScreenService = UtilAccess.userScreenService;
//	static UserGroupService userGroupService = UtilAccess.userGroupService;
	
	
	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		
		try {
			String screenId= rm.getAdditionalData().get("screenId").toString();
			UserScreen screen= new UserScreen();
			screen=  userScreenService.viewUserScreen(Integer.parseInt(screenId), rm.getCorpId());
			List<UserScreen> scrnLst = userScreenService.getAllScreens(rm.getCorpId());
			List<UserGroups> screenGroups= userGroupService.getUserGroupsByScreen(Integer.parseInt(screenId),rm.getCorpId());
			
			Map<Object, Object> mp= new HashMap<Object, Object>();
			

			if (screen != null) {
				mp.put("screen", screen);
				mp.put("screenGroup", screenGroups);
				
				
				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(mp);
	
			}else {
				response.setCode("0001");
				response.setMessage("Enter screen id");
				
			}
			
			
		}
		catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			ex.printStackTrace();
		}

		
		
		return response;
		
		
	}

}
