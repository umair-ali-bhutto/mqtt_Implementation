package com.ag.loy.adm.model;

import java.util.List;

public class StackedBarChartModel {

	private List<String> labels;
	private List<stackedBarDatasets> datasets;

	public class stackedBarDatasets {
		private String label;
		private List<Float> data;
		private String backgroundColor;
		private List<Integer> count;
		private List<String> dataCommaSeperated;
		private List<String> countCommaSeperated;

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

		public String getBackgroundColor() {
			return backgroundColor;
		}

		public void setBackgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
		}

		public List<Integer> getCount() {
			return count;
		}

		public void setCount(List<Integer> count) {
			this.count = count;
		}

		public List<String> getDataCommaSeperated() {
			return dataCommaSeperated;
		}

		public void setDataCommaSeperated(List<String> dataCommaSeperated) {
			this.dataCommaSeperated = dataCommaSeperated;
		}

		public List<String> getCountCommaSeperated() {
			return countCommaSeperated;
		}

		public void setCountCommaSeperated(List<String> countCommaSeperated) {
			this.countCommaSeperated = countCommaSeperated;
		}

	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<stackedBarDatasets> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<stackedBarDatasets> datasets) {
		this.datasets = datasets;
	}

}
