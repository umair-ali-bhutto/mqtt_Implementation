package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.Paymentconfiguration;

public interface PaymentConfigurationService
{
	public List<Paymentconfiguration> retrieveAllPaymentConfigration(String corpId);
	public Paymentconfiguration retrievePaymentConfigByDay(Integer dayNumber,String corpId);

	
}
