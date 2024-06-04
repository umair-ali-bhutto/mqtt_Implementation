package com.ag.mportal.services;

import com.ag.generic.model.RequestModel;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.model.TapNPayModel;

public interface WisherTap {
	public ISO8583Model doProcess(RequestModel rm,TapNPayModel tap,TapNPayRoutingConfig routing,String stan,String invoiceNumber);
}
