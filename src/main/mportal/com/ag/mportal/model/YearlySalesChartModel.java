package com.ag.mportal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class YearlySalesChartModel {
	
	private List<String> labels;
	private String label;
	private List<Float> data;
	private String fill;
	private String backgroundColor;
	private String borderColor;
	private List<YearlySalesChartModel> datasets;
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Float> getData() {
		return data;
	}
	public void setData(List<Float> data) {
		this.data = data;
	}
	public String getFill() {
		return fill;
	}
	public void setFill(String fill) {
		this.fill = fill;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public List<YearlySalesChartModel> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<YearlySalesChartModel> datasets) {
		this.datasets = datasets;
	}

	
	
	
	
}
