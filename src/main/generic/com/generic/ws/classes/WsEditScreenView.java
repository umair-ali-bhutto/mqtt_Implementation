package com.generic.ws.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsEditScreenView")
public class WsEditScreenView implements Wisher{

	@Autowired
	UserScreenService userScreenService ;
	
	//static UserScreenService userScreenService = UtilAccess.userScreenService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		UserLogin user = new UserLogin();
	
		try {
			String screenId= rm.getAdditionalData().get("screenId").toString();
//			String screenDesc= rm.getAdditionalData().get("screenDesc").toString();
//			String fileName= rm.getAdditionalData().get("fileName").toString();
			
			//response.setMessage(screenId);
			//UserScreenService userscreenServ = null;
			UserScreen screen= new UserScreen();
			screen =  userScreenService.viewUserScreen(Integer.parseInt(screenId),rm.getCorpId());
			List<UserScreen> scrnLst = userScreenService.getAllScreens(rm.getCorpId());
			//List<UserGroups> screenGroups= userGroupService.getUserGroupsByScreen(Integer.parseInt(screenId));
			Map<Object, Object> mp= new HashMap<Object, Object>();
			
//			if(scrnLst.size()>0) {
//				mp.put("allScreen", scrnLst);
//			}
			if (screen != null) {
				mp.put("screen", screen);
				//mp.put("screenGroups", screenGroups);
				
				
				
				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(mp);
	
			
			
			
			
		}else {
			response.setCode("0001");
			response.setMessage("id missing or incorrect");
			}
		}
		catch (Exception ex) {
			response.setCode("9995");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		
		
		return response;
		
		
	}

}