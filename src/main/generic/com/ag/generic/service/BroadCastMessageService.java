package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.BroadcastMsg;


public interface BroadCastMessageService
{
	public void save(BroadcastMsg sdt);
	public List<BroadcastMsg> searchBroadCastMessage();
	public void updateBroadCastMessagById(BroadcastMsg id);
}
