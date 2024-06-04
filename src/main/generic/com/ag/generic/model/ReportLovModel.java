package com.ag.generic.model;

import java.util.List;

public class ReportLovModel {

	private List<Report> reportList;

	public List<Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}

	public class Report {
		private String key;
		private String value;
		private String exportOption;
		private String repName;
		private String downRepname;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getExportOption() {
			return exportOption;
		}

		public void setExportOption(String exportOption) {
			this.exportOption = exportOption;
		}

		public String getRepName() {
			return repName;
		}

		public void setRepName(String repName) {
			this.repName = repName;
		}

		public String getDownRepname() {
			return downRepname;
		}

		public void setDownRepname(String downRepname) {
			this.downRepname = downRepname;
		}

	}

}
