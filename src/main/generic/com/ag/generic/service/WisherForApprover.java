package com.ag.generic.service;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;

public interface WisherForApprover {
	public ResponseModel doProcess(RequestModel rm,MakerCheckerConfig mck,MakerCheckerData md);
}
