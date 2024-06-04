package com.ag.config;

import java.util.List;

import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.services.MerchantTerminalDetailsService;
import com.ag.mportal.services.OfflineSaleService;
import com.ag.mportal.services.TxnDetailsService;

@Component
public class JMSConsumer {

	@Autowired
	UtilService utilService;
	
	@Autowired
	TxnDetailsService txnDetailsService;
	
	@Autowired
	OfflineSaleService offlineSaleService;
	
	@JmsListener(destination = "txn-queue")
	public void receiveMessage(@Payload String msg, @Headers MessageHeaders headers, Message message, Session session) {
		try {
			AgLogger.logDebug("", "Service Txn Recievd to JMS QUEUE START...");
			String[] res = utilService.responseServiceTxn(msg);
			if(!res[2].equalsIgnoreCase("0")) {
				List<MerchantTerminalDetail> tdLst = txnDetailsService.convertTxnDetails(msg, Integer.parseInt(res[2]));
				if(tdLst.size()!=0) {
					utilService.saveToMerchantTerminalDetails(tdLst);
				}
			}
			AgLogger.logDebug("", "Service Txn Recievd to JMS QUEUE ENDED...");
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "EXCEPTION ", e);
		}
	}
	
	@JmsListener(destination = "batch-queue")
	public void receiveMessageBatch(@Payload String msg, @Headers MessageHeaders headers, Message message, Session session) {
		try {
			AgLogger.logDebug("", "Batch Txn to JMS QUEUE STARTED.");
			utilService.processServiceSettelment(msg);
			AgLogger.logDebug("", "Batch Txn to JMS QUEUE ENDED.");
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "EXCEPTION ", e);
		}
	}

	
	@JmsListener(destination = "offline-txn-queue")
	public void receiveMessageOfflineTxn(@Payload String msg, @Headers MessageHeaders headers, Message message, Session session) {
		try {
			AgLogger.logDebug("", "Offline Txn to JMS QUEUE STARTED.");
			offlineSaleService.doProcessBatchSettlmentOffline(msg);
			AgLogger.logDebug("", "Offline Txn to JMS QUEUE ENDED.");
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "EXCEPTION ", e);
		}
	}

}
