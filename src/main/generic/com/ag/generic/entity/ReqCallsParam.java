package com.ag.generic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the REQ_CALLS_PARAMS database table.
 * 
 */
@Entity
@Table(name = "REQ_CALLS_PARAMS")
@NamedQuery(name = "ReqCallsParam.fetchByAllRequestCallsParameters",			query = "SELECT a FROM ReqCallsParam a order by a.id asc")
public class ReqCallsParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "REQUEST_CALLS_PARAMETERS_SEQ")
	@SequenceGenerator(name="REQUEST_CALLS_PARAMETERS_SEQ",sequenceName="REQUEST_CALLS_PARAMETERS_SEQ", allocationSize=1)
	private long id;

	@Column(name = "CALL_ID")
	private Long callId;

	@Column(name = "ERROR_MESSAGE_REQUIRED")
	private String errorMessageRequired;

	@Column(name = "ERROR_MESSAGE_VALIDATION")
	private String errorMessageValidation;

	@Column(name = "IS_HTTP_REQUIRED")
	private String isHttpRequired;

	@Column(name = "IS_REQUIRED")
	private String isRequired;

	@Column(name = "IS_VALIDATION_REQUIRED")
	private String isValidationRequired;

	@Column(name = "PARAMETER_NAME_IN")
	private String parameterNameIn;

	@Column(name = "PARAMETER_NAME_OUT")
	private String parameterNameOut;

	@Column(name = "PARAMETER_VALUE")
	private String parameterValue;

	@Column(name = "VALIDATOR_REGEX")
	private String validatorRegex;

	public ReqCallsParam() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCallId() {
		return callId;
	}

	public void setCallId(Long callId) {
		this.callId = callId;
	}

	public String getErrorMessageRequired() {
		return this.errorMessageRequired;
	}

	public void setErrorMessageRequired(String errorMessageRequired) {
		this.errorMessageRequired = errorMessageRequired;
	}

	public String getErrorMessageValidation() {
		return this.errorMessageValidation;
	}

	public void setErrorMessageValidation(String errorMessageValidation) {
		this.errorMessageValidation = errorMessageValidation;
	}

	public String getIsHttpRequired() {
		return this.isHttpRequired;
	}

	public void setIsHttpRequired(String isHttpRequired) {
		this.isHttpRequired = isHttpRequired;
	}

	public String getIsRequired() {
		return this.isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getIsValidationRequired() {
		return this.isValidationRequired;
	}

	public void setIsValidationRequired(String isValidationRequired) {
		this.isValidationRequired = isValidationRequired;
	}

	public String getParameterNameIn() {
		return this.parameterNameIn;
	}

	public void setParameterNameIn(String parameterNameIn) {
		this.parameterNameIn = parameterNameIn;
	}

	public String getParameterNameOut() {
		return this.parameterNameOut;
	}

	public void setParameterNameOut(String parameterNameOut) {
		this.parameterNameOut = parameterNameOut;
	}

	public String getParameterValue() {
		return this.parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getValidatorRegex() {
		return this.validatorRegex;
	}

	public void setValidatorRegex(String validatorRegex) {
		this.validatorRegex = validatorRegex;
	}

}