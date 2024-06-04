package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.ScreenDeclarModel;
import com.ag.generic.model.WsMenuModel;

public interface UserScreenService {
	public void addNewScreen(UserScreen UserScreen);

	public UserScreen updateScreen(UserScreen UserScreen);

	public UserScreen viewUserScreen(int screenId, String corpId);

	public List<UserScreen> getAllScreens(String corpId);

	public List<UserScreen> getUserScreens(String screenDesc, String corpId);

	public int getScreencode();

	public List<ScreenDeclarModel> getUserScreensAllConverted(String corpId);

	public List<WsMenuModel> screenRights(int groupCode, String corpId, int subSegment);

	public UserScreen getParentID(int screenId, String corpId);

	public UserScreen fetchScreenIdByRoute(String route, String corpId);

}