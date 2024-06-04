package com.ag.mportal.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AngularMenuModel {
	
	private List<AngularMenuModel> data;
	private String label;
	private String icon;
	private ArrayList<String> routerLink;
	private ArrayList<AngularMenuModel> item;
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public ArrayList<String> getRouterLink() {
		return routerLink;
	}
	public void setRouterLink(ArrayList<String> routerLink) {
		this.routerLink = routerLink;
	}
	
	public ArrayList<AngularMenuModel> getItem() {
		return item;
	}
	public void setItem(ArrayList<AngularMenuModel> item) {
		this.item = item;
	}
	public List<AngularMenuModel> getData() {
		return data;
	}
	public void setData(List<AngularMenuModel> data) {
		this.data = data;
	}

}
