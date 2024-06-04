package com.ag.generic.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NotificationModel {

	private Integer complaintId;
	private String notificationHeader;
	private String notificationContent;
	private Long complaintAssignId;
	private List<NotificationModelDetails> ntmDetails;
	
	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}
	public String getNotificationHeader() {
		return notificationHeader;
	}
	public void setNotificationHeader(String notificationHeader) {
		this.notificationHeader = notificationHeader;
	}
	public String getNotificationContent() {
		return notificationContent;
	}
	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}
	public Long getComplaintAssignId() {
		return complaintAssignId;
	}
	public void setComplaintAssignId(Long complaintAssignId) {
		this.complaintAssignId = complaintAssignId;
	}
	public List<NotificationModelDetails> getNtmDetails() {
		return ntmDetails;
	}
	public void setNtmDetails(List<NotificationModelDetails> ntmDetails) {
		this.ntmDetails = ntmDetails;
	}

	

}
