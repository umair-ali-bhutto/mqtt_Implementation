package com.ag.loy.adm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PieChartModel {
	private List<String> labels;
	private List<Float> data;
	private List<String> backgroundColor;

	private List<PieChartModel> datasets;

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<Float> getData() {
		return data;
	}

	public void setData(List<Float> data) {
		this.data = data;
	}

	public List<String> getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(List<String> backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public List<PieChartModel> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<PieChartModel> datasets) {
		this.datasets = datasets;
	}

}
