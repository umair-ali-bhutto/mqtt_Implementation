package com.ag.generic.model;

import java.util.List;

import com.ag.metro.model.GenericLovModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NotificationModelDetails {

	private Integer screenId;
	private String className;
	private String buttonName;
	private String routerLink;
	private String inputType;
	private String inputLabelText;
	private String inputRegix;
	private String actionType;
	private List<GenericLovModel> inputLovs;

	public NotificationModelDetails() {

	}

	public Integer getScreenId() {
		return screenId;
	}

	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getRouterLink() {
		return routerLink;
	}

	public void setRouterLink(String routerLink) {
		this.routerLink = routerLink;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getInputLabelText() {
		return inputLabelText;
	}

	public void setInputLabelText(String inputLabelText) {
		this.inputLabelText = inputLabelText;
	}

	public String getInputRegix() {
		return inputRegix;
	}

	public void setInputRegix(String inputRegix) {
		this.inputRegix = inputRegix;
	}

	public List<GenericLovModel> getInputLovs() {
		return inputLovs;
	}

	public void setInputLovs(List<GenericLovModel> inputLovs) {
		this.inputLovs = inputLovs;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	

}
