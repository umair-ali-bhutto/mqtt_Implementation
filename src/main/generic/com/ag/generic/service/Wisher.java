package com.ag.generic.service;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;

public interface Wisher {
	public ResponseModel doProcess(RequestModel rm);
}
