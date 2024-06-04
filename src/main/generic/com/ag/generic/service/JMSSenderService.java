package com.ag.generic.service;

import org.springframework.stereotype.Service;

@Service
public interface JMSSenderService {
	
	 public void send(String message);
	 
	 public void sendBatch(String message);
	 
	 public void sendOffline(String message);

}
