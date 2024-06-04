package com.ag.generic.model;

import java.util.List;

public class ReportFilterModel {

	private String inputType;
	private String inputLabelText;
	private String inputRegix;
	private List<KeyValue> inputLovs;
	private String isRequired;
	private String reportParamName;
	private String inputHint;

	public class KeyValue {
		private String key;
		private String value;

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

	public List<KeyValue> getInputLovs() {
		return inputLovs;
	}

	public void setInputLovs(List<KeyValue> inputLovs) {
		this.inputLovs = inputLovs;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getReportParamName() {
		return reportParamName;
	}

	public void setReportParamName(String reportParamName) {
		this.reportParamName = reportParamName;
	}

	public String getInputHint() {
		return inputHint;
	}

	public void setInputHint(String inputHint) {
		this.inputHint = inputHint;
	}

}
