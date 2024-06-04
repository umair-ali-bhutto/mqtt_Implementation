package com.ag.config;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TapNPayResponseCodesConfig;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.services.TapNPayResponseCodesConfigService;
import com.ag.mportal.services.TapNPayRoutingConfigService;
import com.ag.mportal.util.TapNPayUtil;

@Component
public class StartUpInitTapNPay {

	@Autowired
	TapNPayResponseCodesConfigService tapNPayResponseCodesConfigService;

	@Autowired
	TapNPayRoutingConfigService tapNPayRoutingConfigService;

	@PostConstruct
	public void init() {
		loadConfigLists();
	}

	private void loadConfigLists() {

		HashMap<String, String> tapNPayResponseCodeMap = new HashMap<String, String>();
		HashMap<String, TapNPayRoutingConfig> tapNPayRoutingConfigMap = new HashMap<String, TapNPayRoutingConfig>();

		try {
			for (TapNPayResponseCodesConfig tapNPay : tapNPayResponseCodesConfigService.fetchAll()) {
				tapNPayResponseCodeMap.put(tapNPay.getCode(), tapNPay.getMessage());
			}
			TapNPayUtil.setTapNPayResponseCodeMap(tapNPayResponseCodeMap);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), ex.toString(), ex);
		}

		try {
			for (TapNPayRoutingConfig tapNPay : tapNPayRoutingConfigService.fetchAll()) {
				tapNPayRoutingConfigMap
						.put(tapNPay.getCorpId() + "|" + tapNPay.getTxnType() + "|" + tapNPay.getCardScheme(), tapNPay);
			}
			TapNPayUtil.setTapNPayRoutingMap(tapNPayRoutingConfigMap);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), ex.toString(), ex);
		}

	}

}
