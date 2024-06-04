package com.ag.config;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.ag.generic.util.EcrUtil;
import com.ag.mportal.entity.ConfigEcrRouting;
import com.ag.mportal.entity.EcrLog;
import com.ag.mportal.entity.EcrSaf;
import com.ag.mportal.model.EcrModel;
import com.ag.mportal.model.EcrRespModel;
import com.ag.mportal.services.EcrLogsService;
import com.ag.mportal.services.impl.ConfigEcrRoutingServiceImpl;
import com.ag.mportal.services.impl.EcrSafServiceImpl;
import com.google.gson.Gson;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

@Component
public class EcrSchedular {

	@Autowired
	EcrSafServiceImpl ecrSafServiceImpl;

	@Autowired
	ConfigEcrRoutingServiceImpl configEcrRoutingServiceImpl;

	@Autowired
	EcrLogsService ecrLogsService;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${ecr.schedular}")
	public String ecrSchedular;

	// @Scheduled(cron="#{@getNextRun}")
	@Scheduled(cron = "*/15 * * * * ?")
	@Transactional
	public void doProcessReversal() {
		if (ecrSchedular.equalsIgnoreCase("true")) {
			try {
				AgLogger.logDebug("", "@@@ ECR SCHEDULAR STARTED.");
				List<EcrSaf> lstFedRates = null;
				lstFedRates = (List<EcrSaf>) ecrSafServiceImpl.fetchAllSaf();
				AgLogger.logInfo("LIST SAF SIZE" + lstFedRates.size());
				for (EcrSaf c : lstFedRates) {
					EcrModel retrieveDiscountReqModel = new Gson().fromJson(c.getRequestData(), EcrModel.class);
					ConfigEcrRouting crfRoutings = configEcrRoutingServiceImpl.fetchByID(c.getConfigEcrRoutingId());
					EcrRespModel respMdl = fetchPayment(crfRoutings, retrieveDiscountReqModel);
					if (respMdl.getCode().equals("0000")) {

						Date currentTime = new Date();

						String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

						SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

						String formattedTimestamp = sdf.format(currentTime);

						c.setStatus("SUCCESS");
						c.setMinRetry(c.getMaxRetry());
						c.setLastRun(formattedTimestamp);

						ecrSafServiceImpl.update(c);

						AgLogger.logInfo("############# TEST " + retrieveDiscountReqModel.getMid() + " "
								+ retrieveDiscountReqModel.getTid() + " " + c.getEcrLogId());

						EcrLog eLogModel = ecrLogsService.fetchByIDforPaymentSchedular(
								retrieveDiscountReqModel.getMid(), retrieveDiscountReqModel.getTid(), c.getEcrLogId());
						String[] resp = new String[2];
						resp[0] = respMdl.getCode();
						resp[1] = respMdl.getMessage();
						String status = "SUCCESS";
						eLogModel.setState("PAYMENT_DONE");
						eLogModel.setRrn(retrieveDiscountReqModel.getRRN());
						eLogModel.setPaymentAmount(retrieveDiscountReqModel.getTxnAmount());
						eLogModel.setPaymentDate(new Timestamp(new java.util.Date().getTime()));
						eLogModel.setPaymentStatus(status);
						// eLogModel.setRecType("PAID");
						eLogModel.setRemarks(resp[0] + "|" + resp[1]);
						eLogModel.setResponseData(respMdl.getResponse());

						ecrLogsService.update(eLogModel);

					} else {
						boolean isNextRunAvaialble = false;

						Long timeInSeconds = 0l;
						String retriesTimes = crfRoutings.getSafRetriesConfig();
						if (retriesTimes.contains(",")) {
							String[] nextRetries = retriesTimes.split(",");
							if (c.getMinRetry() < Long.parseLong(nextRetries[0])) {
								isNextRunAvaialble = true;
								try {
									timeInSeconds = Long.parseLong(nextRetries[c.getMinRetry() + 1]);
								} catch (Exception e) {
									timeInSeconds = 0l;
									isNextRunAvaialble = false;
								}
							}

						} else {
							isNextRunAvaialble = false;
						}

						Date currentTime = new Date();

						String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

						SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

						String formattedTimestamp = sdf.format(currentTime);

						if (!isNextRunAvaialble) {
							c.setStatus("FAILED");
							c.setMinRetry(c.getMaxRetry());
							c.setLastRun(formattedTimestamp);
							ecrSafServiceImpl.update(c);
						} else {
							LocalDateTime currentTimestamp = LocalDateTime.now();
							LocalDateTime timestamp = currentTimestamp.plus(timeInSeconds, ChronoUnit.SECONDS);
							Timestamp newTimestamp = Timestamp.valueOf(timestamp);
							c.setLastRun(formattedTimestamp);
							c.setMinRetry(c.getMinRetry() + 1);
							c.setNextRun(newTimestamp);
							ecrSafServiceImpl.update(c);
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			AgLogger.logDebug("", "@@@ ECR SCHEDULAR ENDED.");
		}
	}

	EcrRespModel fetchPayment(ConfigEcrRouting routing, EcrModel emModel) {
		EcrRespModel mdl = new EcrRespModel();
		try {
			String requestData = routing.getRequestTemplete();
			String tempModel = requestData;
			requestData = EcrUtil.convertObjectToJSONMapper(requestData, emModel);
			AgLogger.logInfo(requestData + ".........");
			routing.setRequestTemplete(requestData);
			GroovyClassLoader classLoader = new GroovyClassLoader();
			String groovyScript = routing.getNetworkHandler();
			Class<?> groovy = classLoader.parseClass(groovyScript.toString());
			GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
			Object output = groovyObj.invokeMethod("perform", routing);
			mdl = (EcrRespModel) output;
			routing.setRequestTemplete(tempModel);
			classLoader.close();
		} catch (Exception e) {
			e.printStackTrace();
			mdl.setCode("0099");
			mdl.setMessage("Something Went Wrong.");
		}
		return mdl;
	}

}
