package com.mportal.ws.classes;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.model.TapNPayModel;
import com.ag.mportal.services.WisherTap;

@Component("com.mportal.ws.classes.WsTapNPayUpiCompletion")
public class WsTapNPayUpiCompletion implements WisherTap {
	
	@Override
	public ISO8583Model doProcess(RequestModel rm, TapNPayModel tap, TapNPayRoutingConfig routing, String stan,
			String invoiceNumber) {
		//Its ofline Txn Do not process Anything.
		return null;
	}

}