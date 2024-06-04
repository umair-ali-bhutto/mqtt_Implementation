package com.ag.mportal.services;

import com.ag.mportal.entity.PosRequestCalls;

public interface PosRequestCallsService {

	public PosRequestCalls fetchByMessageType(String messageType);

}
