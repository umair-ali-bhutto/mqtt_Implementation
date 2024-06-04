package com.generic.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.UserGroups;
import com.ag.generic.entity.UserScreensGroup;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.ScreenDeclarModel;
import com.ag.generic.service.MakerCheckerConfigService;
import com.ag.generic.service.UserGroupService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.UserScreensGroupsService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsEditGroupScreen")
public class WsEditGroupScreen implements Wisher {

	@Autowired
	UserGroupService userGroupService;

	@Autowired
	UserScreenService userScreenService;

	@Autowired
	UserScreensGroupsService userScreensGroupsService;

	@Autowired
	MakerCheckerConfigService checkerConfigService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String groupId = rm.getAdditionalData().get("groupId").toString();
			Map<Object, Object> mp = new HashMap<Object, Object>();

			if (groupId != null) {

				UserGroups group = userGroupService.viewGroup(Integer.parseInt(groupId), rm.getCorpId());
				List<UserScreensGroup> screenGroups = userScreensGroupsService.viewScrnGroup(Integer.parseInt(groupId),
						rm.getCorpId());
				List<ScreenDeclarModel> screens = userScreenService.getUserScreensAllConverted(rm.getCorpId());
				List<UserScreensGroup> tempScreenGroups = new ArrayList<UserScreensGroup>();
				if (group != null) {
					mp.put("group", group);
					for (UserScreensGroup s : screenGroups) {
						for (ScreenDeclarModel k : screens) {
							if (k.getKey().equals(s.getScreenId() + "")) {
								tempScreenGroups.add(s);
								break;
							}
						}
					}

					List<MakerCheckerConfig> approverList = checkerConfigService
							.fetchByApproverGroupId(Integer.parseInt(groupId));

					List<MakerCheckerConfig> checkerList = checkerConfigService
							.fetchByVerifierGroupId(Integer.parseInt(groupId));

					mp.put("approverList", approverList);
					mp.put("checkerList", checkerList);
					mp.put("screenGroup", tempScreenGroups);
					response.setCode("0000");
					response.setMessage("SUCCESS");
					response.setData(mp);
				} else {
					response.setCode("0001");
					response.setMessage("GROUP ID MISSING OR INCORRECT");
				}

			} else {
				response.setCode("0002");
				response.setMessage("GROUP ID MISSING OR INCORRECT");

			}
		} catch (Exception ex) {
			response.setCode("9995");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}