package com.ag.mportal.services;

import com.ag.mportal.entity.QrPayments;


public interface QrPaymentsService
{
	
	public boolean save(QrPayments sdt);
	public QrPayments fetch(String mid,String tid,String serialNum);
	public boolean update(QrPayments qrPayment) ;
	
	
}
