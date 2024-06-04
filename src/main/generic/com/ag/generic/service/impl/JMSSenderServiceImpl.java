package com.ag.generic.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ag.generic.service.JMSSenderService;

@Service
public class JMSSenderServiceImpl implements JMSSenderService {

	 @Autowired
	    private JmsTemplate jmsTemplate;
	
	@Override
	public void send(String message) {
		 jmsTemplate.convertAndSend("txn-queue", message);
	}

	@Override
	public void sendBatch(String message) {
		 jmsTemplate.convertAndSend("batch-queue", message);
	}
	
	@Override
	public void sendOffline(String message) {
		 jmsTemplate.convertAndSend("offline-txn-queue", message);
	}

	

}
