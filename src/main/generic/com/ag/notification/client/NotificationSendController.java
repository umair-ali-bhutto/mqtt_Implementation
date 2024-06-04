/**
 * NotificationSendController.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ag.notification.client;

public interface NotificationSendController extends java.rmi.Remote {
    public com.ag.notification.client.ResponseModel testSmsService(com.ag.notification.client.NotificationSendModel ntm) throws java.rmi.RemoteException;
    public com.ag.notification.client.ResponseModel testEmailService(com.ag.notification.client.NotificationSendModel ntm) throws java.rmi.RemoteException;
    public com.ag.notification.client.ResponseModel saveNotification(com.ag.notification.client.NotificationSendModel ntm) throws java.rmi.RemoteException;
}
