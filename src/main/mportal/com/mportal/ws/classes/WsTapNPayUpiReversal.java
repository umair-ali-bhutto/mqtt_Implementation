package com.mportal.ws.classes;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.model.TapNPayModel;
import com.ag.mportal.services.WisherTap;

@Component("com.mportal.ws.classes.WsTapNPayUpiReversal")
public class WsTapNPayUpiReversal implements WisherTap {

	@Override
	public ISO8583Model doProcess(RequestModel rm, TapNPayModel tap, TapNPayRoutingConfig routing, String stan,
			String invoiceNumber) {
		try {

			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

}