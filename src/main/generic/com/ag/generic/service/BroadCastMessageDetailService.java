package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.BroadcastMsgDetail;
import com.ag.generic.model.BroadCastMessageModel;



public interface BroadCastMessageDetailService
{
	
	
	public void save(BroadcastMsgDetail sdt);
	public BroadcastMsgDetail searBroadCastMessageDetail();
	
	public List<BroadCastMessageModel> searchBrdMsgByReadDateAndUserId(int userLoginId);
	
	
	
}
