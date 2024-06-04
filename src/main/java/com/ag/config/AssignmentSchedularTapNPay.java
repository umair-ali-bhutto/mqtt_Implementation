package com.ag.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.mportal.ws.classes.WsTapNPayReversal;

@Component
public class AssignmentSchedularTapNPay {

	@Autowired
	WsTapNPayReversal wsTapNPayReversal;
	

	@PersistenceContext
	private EntityManager entityManager;
	
	

	@Value("${tapNPay.schedular}")
	public String tapNPaySchedular;

	@Scheduled(cron = "*/5 * * * * ?")
	@Transactional
	public void doProcessReversal() {
		if (tapNPaySchedular.equalsIgnoreCase("true")) {

			try {
				AgLogger.logDebug("", "@@@ REVERSAL SCHEDULAR STARTED.");
				wsTapNPayReversal.doProcessSaf();
			} catch (Exception e) {
				e.printStackTrace();
			}

			AgLogger.logDebug("", "@@@ REVERSAL SCHEDULAR ENDED.");
		}
	}

}
