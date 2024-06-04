package com.ag.generic.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.NotificationModel;
import com.ag.generic.model.NotificationModelDetails;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.UserScreenService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Component("PortalNotificationUtil")
public class PortalNotificationUtil {

	@Autowired
	ComplAssignmentsService complAssignmentsService;

	@Autowired
	UserScreenService userScreenService;

	public HashMap<Object, Object> getNotificationsUpdated(int userId,String coorpId) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		int notificationCount = 0;
		List<ComplAssignment> lstTxnNotificaions;
		List<NotificationModel> notificationModel = new ArrayList<NotificationModel>();
		lstTxnNotificaions = complAssignmentsService.fetchAllByID(userId);
		if (lstTxnNotificaions != null) {
			notificationCount = lstTxnNotificaions.size();
			for (ComplAssignment ck : lstTxnNotificaions) {
				NotificationModel m = new NotificationModel();
				m.setComplaintAssignId(ck.getId());
				m.setComplaintId(ck.getCompId());
				m.setNotificationContent(ck.getDescription());
				Type userListType = new TypeToken<ArrayList<NotificationModelDetails>>(){}.getType();
				AgLogger.logDebug(userId+"", ck.getClassName());
				try {
				ArrayList<NotificationModelDetails> userArray = new Gson().fromJson(ck.getClassName(), userListType); 
				m.setNtmDetails(userArray);
				}
				catch(JsonSyntaxException e) {
					AgLogger.logTrace(getClass(), " Error in JSON Parsing with Notifications.", e);
				}
				catch(Exception e) {
					AgLogger.logTrace(getClass(), " Error in JSON Parsing with Notifications.", e);
				}
				if (ck.getCategory().equals("001")) {
					m.setNotificationHeader("New Registration : #" + ck.getMid());
				} else {
					m.setNotificationHeader("Compaint : #" + ck.getCompId());
				}
				notificationModel.add(m);
			}

		}

		map.put("notifications", notificationModel);
		map.put("notifications_count", notificationCount);
		return map;
	}

	public Map<Integer, UserScreen> convertListToMapScreen(List<UserScreen> list) {
		Map<Integer, UserScreen> map = list.stream()
				.collect(Collectors.toMap(UserScreen::getScreenId, Function.identity()));
		return map;
	}

}
