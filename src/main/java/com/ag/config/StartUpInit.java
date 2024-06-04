package com.ag.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ReqCall;
import com.ag.generic.entity.ReqCallsParam;
import com.ag.generic.service.ConfigPropertiesService;
import com.ag.generic.service.ReqCallService;
import com.ag.generic.service.ReqCallsParamService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAG;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.services.MidTidDetailsConfigService;
import com.ag.mportal.services.PosEntryModeConfigService;

@Component
public class StartUpInit {

	@Autowired
	private ReqCallService reqCallsService;

	@Autowired
	private ReqCallsParamService reqCallsParamsService;

	@Autowired
	private ConfigPropertiesService configPropertiesService;

	@Autowired
	PosEntryModeConfigService posEntryModeConfigService;

	@Autowired
	MidTidDetailsConfigService midTidDetailsConfigService;

	@PostConstruct
	public void init() {
		loadConfigLists();
	}

	private void loadConfigLists() {

		HashMap<String, Object> requestCallsMap = new HashMap<String, Object>();
		HashMap<Long, List<ReqCallsParam>> requestCallParametersMap = new HashMap<Long, List<ReqCallsParam>>();
		HashMap<String, PosEntryModeConfig> pemConfigMap = new HashMap<String, PosEntryModeConfig>();

		UtilAccess.setRequestCallsList(reqCallsService.fetchByAllRequestCalls());
		UtilAccess.setRequestCallsParametersList(reqCallsParamsService.fetchByAllRequestCallsParameters());
		UtilAccess.setLstPemConfig(posEntryModeConfigService.fetchAll());
		UtilAccess.setMerchantTerminalDetailsConfigList(midTidDetailsConfigService.fetchAllConfig());

		try {
			for (ReqCall requestCallsObj : UtilAccess.getRequestCallsList()) {
				requestCallsMap.put(requestCallsObj.getMessageType(), requestCallsObj);
			}
			UtilAG.setRequestCallsMap(requestCallsMap);
		} catch (Exception ex) {

			AgLogger.logerror(getClass(), ex.toString(), ex);
		}

		try {
			List<ReqCallsParam> requestCallsParametersList = reqCallsParamsService.fetchByAllRequestCallsParameters();

			for (ReqCallsParam r : requestCallsParametersList) {

				if (requestCallParametersMap.containsKey(r.getCallId())) {
					List<ReqCallsParam> tempList = requestCallParametersMap.get(r.getCallId());
					tempList.add(r);
					requestCallParametersMap.put(r.getCallId(), tempList);
				} else {
					List<ReqCallsParam> tempList = new ArrayList<ReqCallsParam>();
					tempList.add(r);
					requestCallParametersMap.put(r.getCallId(), tempList);
				}
			}

			UtilAG.setRequestCallParametersMap(requestCallParametersMap);
			UtilAccess.setConfigPropMap(configPropertiesService.SelectAllConfigMap());
		} catch (Exception ex) {
			ex.printStackTrace();
			AgLogger.logerror(getClass(), "EXCEPTION IN RequestCallsParameters LOOP : " + ex.toString(), ex);
		}

		try {

			for (PosEntryModeConfig pemConfig : UtilAccess.getLstPemConfig()) {
				pemConfigMap.put(pemConfig.getPosEntryMode(), pemConfig);
			}
			UtilAG.setPemConfigMap(pemConfigMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			AgLogger.logerror(getClass(), "EXCEPTION IN RequestCallsParameters LOOP : " + ex.toString(), ex);
		}
	}

}
