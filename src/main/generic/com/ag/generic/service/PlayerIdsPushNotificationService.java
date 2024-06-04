package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.PlayersIdsPushNotification;


public interface PlayerIdsPushNotificationService
{
	
	public int insert(PlayersIdsPushNotification playerIdsPushNotification);
	public void update(PlayersIdsPushNotification playerIdsPushNotification);
	public void updateByUserLoginId(int userId,String playerId,String channel);
	public List<PlayersIdsPushNotification> searchAll();
	public PlayersIdsPushNotification searchByUserLoginId(int userId);
	
	
}
