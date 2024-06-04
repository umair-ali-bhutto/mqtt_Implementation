package com.ag.fuel.model;

import java.util.List;

public class InputFormModel {

	private String inputType;
	private String inputLabelText;
	private String inputRegix;
	private List<KeyValue> inputLovs;
	private String isRequired;
	private String pageParamName;

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

	public String getPageParamName() {
		return pageParamName;
	}

	public void setPageParamName(String pageParamName) {
		this.pageParamName = pageParamName;
	}

}
