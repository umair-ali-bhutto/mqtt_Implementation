package com.ag.notification.client;

public class NotificationSendControllerProxy implements com.ag.notification.client.NotificationSendController {
  private String _endpoint = null;
  private com.ag.notification.client.NotificationSendController notificationSendController = null;
  
  public NotificationSendControllerProxy() {
    _initNotificationSendControllerProxy();
  }
  
  public NotificationSendControllerProxy(String endpoint) {
    _endpoint = endpoint;
    _initNotificationSendControllerProxy();
  }
  
  private void _initNotificationSendControllerProxy() {
    try {
      notificationSendController = (new com.ag.notification.client.NotificationSendControllerServiceLocator()).getNotificationSendController();
      if (notificationSendController != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)notificationSendController)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)notificationSendController)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (notificationSendController != null)
      ((javax.xml.rpc.Stub)notificationSendController)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ag.notification.client.NotificationSendController getNotificationSendController() {
    if (notificationSendController == null)
      _initNotificationSendControllerProxy();
    return notificationSendController;
  }
  
  public com.ag.notification.client.ResponseModel testSmsService(com.ag.notification.client.NotificationSendModel ntm) throws java.rmi.RemoteException{
    if (notificationSendController == null)
      _initNotificationSendControllerProxy();
    return notificationSendController.testSmsService(ntm);
  }
  
  public com.ag.notification.client.ResponseModel testEmailService(com.ag.notification.client.NotificationSendModel ntm) throws java.rmi.RemoteException{
    if (notificationSendController == null)
      _initNotificationSendControllerProxy();
    return notificationSendController.testEmailService(ntm);
  }
  
  public com.ag.notification.client.ResponseModel saveNotification(com.ag.notification.client.NotificationSendModel ntm) throws java.rmi.RemoteException{
    if (notificationSendController == null)
      _initNotificationSendControllerProxy();
    return notificationSendController.saveNotification(ntm);
  }
  
  
}