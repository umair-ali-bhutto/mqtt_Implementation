package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.exolab.castor.xml.handlers.ValueOfFieldHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserGroups;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.entity.UserScreensGroup;
import com.ag.generic.model.GroupDeclarModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerConfigService;
import com.ag.generic.service.UserGroupService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.UserScreensGroupsService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsCreateNewGroup")
public class WsCreateNewGroup implements Wisher {

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
			String groupDesc = (String) rm.getAdditionalData().get("groupDesc");
			String groupName = rm.getAdditionalData().get("groupName").toString();
			String userId = rm.getUserid();
			String screenId = rm.getAdditionalData().get("screenId").toString();
			List<String> childMenus = new ArrayList<String>();
			String[] screenIds = screenId.split(",");

			List<Integer> sParentMenuAddedList = new ArrayList<Integer>();

			if (groupDesc != null && groupName != null && screenId.length() != 0) {
				List<UserGroups> checkGroup = userGroupService.getUserGroups(groupName, rm.getCorpId());
				if (checkGroup.size() == 0) {
					UserGroups grp = new UserGroups();
					Date date = new Date();
					Timestamp time = new Timestamp(date.getTime());
					int groupCode = (userGroupService.getGroupcode()) + 1;

					grp.setDescription(groupDesc);
					grp.setGrpName(groupName);
					grp.setGrpCode(groupCode);
					grp.setInsertBy(userId);
					grp.setInsertOn(time);
					grp.setLastUpdateOn(null);
					grp.setLastUpdateBy(null);
					grp.setIsActive(1);
					grp.setApplicationSource("");
					grp.setCorpId(rm.getCorpId());

					userGroupService.addNewGroup(grp);
					AgLogger.logInfo(screenIds.length + "...........");
					int groupId = grp.getGrpId();

					for (int i = 0; i < screenIds.length; i++) {
						UserScreensGroup scrnGroup = new UserScreensGroup();
						scrnGroup.setGrpId(groupId);
						scrnGroup.setScreenId(Integer.parseInt(screenIds[i]));
						scrnGroup.setInsertBy(userId);
						scrnGroup.setInsertOn(time);
						scrnGroup.setLastUpdateOn(null);
						scrnGroup.setLastUpdateBy(null);
						scrnGroup.setIsActive(1);
						scrnGroup.setCorpId(rm.getCorpId());
						AgLogger.logDebug("", groupId + "|" + screenIds[i]);
						userScreensGroupsService.insertScreenGroup(scrnGroup);
						childMenus.add(screenIds[i]);
					}

					if (childMenus.size() != 0) {
						List<String> newList = childMenus.stream().distinct().collect(Collectors.toList());
						for (String s : newList) {
							UserScreen ul = userScreenService.getParentID(Integer.parseInt(s), rm.getCorpId());
							if (ul != null) {

								if (!sParentMenuAddedList.contains(ul.getParentId())) {
									UserScreensGroup scrnGroup = new UserScreensGroup();
									scrnGroup.setGrpId(groupId);
									scrnGroup.setScreenId(ul.getParentId());
									scrnGroup.setInsertBy(userId);
									scrnGroup.setInsertOn(time);
									scrnGroup.setLastUpdateOn(null);
									scrnGroup.setLastUpdateBy(null);
									scrnGroup.setIsActive(1);
									scrnGroup.setCorpId(rm.getCorpId());
									AgLogger.logDebug("", groupId + "|" + ul.getParentId());
									userScreensGroupsService.insertScreenGroup(scrnGroup);
									sParentMenuAddedList.add(ul.getParentId());
								} else {
									AgLogger.logDebug("", "Menu Parent Id " + ul.getParentId() + " Already Inserted.");
								}

							}
						}
					}

					// UPDATING APPROVERS
					String configId = rm.getAdditionalData().get("configId").toString() == "" ? ""
							: rm.getAdditionalData().get("configId").toString();
					if (!configId.equals("")) {
						List<Long> lstConfig = new ArrayList<Long>();
						if (configId.contains(",")) {
							String[] configIds = configId.split(",");
							for (String s : configIds) {
								lstConfig.add(Long.parseLong(s));
							}
						} else {
							String finalConfigId = configId;
							lstConfig.add(Long.parseLong(finalConfigId));
						}
						checkerConfigService.updateGroupAssignedApprover(lstConfig, groupId);
					}

					// UPDATING VERIFIERS
					String checkerConfigId = rm.getAdditionalData().get("checkerConfigId").toString() == "" ? ""
							: rm.getAdditionalData().get("checkerConfigId").toString();
					if (!checkerConfigId.equals("")) {
						List<Long> lstCheckerConfig = new ArrayList<Long>();
						if (checkerConfigId.contains(",")) {
							String[] checkerConfigIds = checkerConfigId.split(",");
							for (String s : checkerConfigIds) {
								lstCheckerConfig.add(Long.parseLong(s));
							}
						} else {
							String finalConfigId = checkerConfigId;
							lstCheckerConfig.add(Long.parseLong(finalConfigId));
						}
						checkerConfigService.updateGroupAssignedVerifier(lstCheckerConfig, groupId);
					}

					response.setCode("0000");
					response.setMessage("Group Created Successfully.");
					HashMap<Object, Object> objMap = new HashMap<Object, Object>();
					List<GroupDeclarModel> grps = userGroupService.getUserGroupsAllConverted(rm.getCorpId());
					objMap.put("groups", grps);
					response.setData(objMap);
				} else {
					response.setCode("0001");
					response.setMessage("Group Already Exists.");

				}

			} else {
				response.setCode("0001");
				response.setMessage("Group Name or Description must be entered.");

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9995");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}