package com.ag.generic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import com.ag.generic.entity.ReqCall;
import com.ag.generic.entity.ReqCallsParam;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;


public class Validator {

	public ResponseModel doValidate(RequestModel rmk) throws IllegalArgumentException, IllegalAccessException {
		ResponseModel rm = new ResponseModel();
		ReqCall rq = UtilAG.fetchRequestCalls(rmk.getMessage());
		List<String> listErrors = new ArrayList<String>();
		if (rq != null) {
			List<ReqCallsParam> reqParam = UtilAG.fetchRequestParametersByCallId(rq.getId());
			if (reqParam != null) {
				for (ReqCallsParam parameter : reqParam) {
					String[] k = doValidateParams(parameter, rmk);
					rm.setCode("0000");
					rm.setMessage("VALIDATE");
					if (k[0].equals("1")) {
						listErrors.add(k[1]);
					}
				}

				if (listErrors.size() != 0) {
					rm.setCode("9903");
					rm.setMessage(listErrors.toString());
				} else {
					rm.setCode("0000");
					rm.setMessage("VALID");
				}

			} else {
				rm.setCode("9902");
				rm.setMessage("INVALID MESSAGE TYPE RECIEVED.");
			}

		} else {
			rm.setCode("9901");
			rm.setMessage("INVALID MESSAGE TYPE RECIEVED.");
		}

		return rm;
	}

	String[] doValidateParams(ReqCallsParam par, RequestModel rq) {
		String[] df = new String[2];
		df[0] = "0";
		df[1] = "OK";
		if (par.getIsHttpRequired().charAt(0) == 'N') {
			switch (par.getParameterNameIn().toLowerCase()) {
			case "userid":
				df = doPerformValidation(rq.getUserid(), par.getErrorMessageRequired(), par.getIsRequired().charAt(0),
						par.getIsValidationRequired().charAt(0), par.getValidatorRegex(), par.getErrorMessageValidation());
				break;
			case "password":
				df = doPerformValidation(rq.getPassword(), par.getErrorMessageRequired(), par.getIsRequired().charAt(0),
						par.getIsValidationRequired().charAt(0), par.getValidatorRegex(), par.getErrorMessageValidation());
				break;
			case "lang":
				df = doPerformValidation(rq.getLang(), par.getErrorMessageRequired(), par.getIsRequired().charAt(0),
						par.getIsValidationRequired().charAt(0), par.getValidatorRegex(), par.getErrorMessageValidation());
				break;
			case "corpid":
				df = doPerformValidation(rq.getCorpId(), par.getErrorMessageRequired(), par.getIsRequired().charAt(0),
						par.getIsValidationRequired().charAt(0), par.getValidatorRegex(), par.getErrorMessageValidation());
				break;
			case "imei":
				df = doPerformValidation(rq.getImei(), par.getErrorMessageRequired(), par.getIsRequired().charAt(0),
						par.getIsValidationRequired().charAt(0), par.getValidatorRegex(), par.getErrorMessageValidation());
				break;
			case "channel":
				df = doPerformValidation(rq.getImei(), par.getErrorMessageRequired(), par.getIsRequired().charAt(0),
						par.getIsValidationRequired().charAt(0), par.getValidatorRegex(), par.getErrorMessageValidation());
				break;
			}
		} else {

			String p = par.getParameterNameIn();
			String m = null;
			if (rq.getAdditionalData() != null) {
				if (rq.getAdditionalData().containsKey(p)) {
					if(!Objects.isNull(rq.getAdditionalData().get(par.getParameterNameIn()))) {
						m = rq.getAdditionalData().get(par.getParameterNameIn()).toString();
					}else {
						m = "";
					}
					
				}
			}

			df = doPerformValidation(m, par.getErrorMessageRequired(), par.getIsRequired().charAt(0),
					par.getIsValidationRequired().charAt(0), par.getValidatorRegex(), par.getErrorMessageValidation());

		}

		return df;
	}

	String[] doPerformValidation(String value, String errorMessage, char isRequired, char isValidationReq,
			String validationParam, String regixValidaotrMesage) {
		String[] doPerform = new String[2];
		doPerform[0] = "0";
		doPerform[1] = "OK";

		if (isRequired == 'Y') {
			if (value != null) {
				if (isValidationReq == 'Y') {
					if (validationParam != null) {
						boolean b2 = Pattern.compile(validationParam).matcher(value).matches();
						if (!b2) {
							doPerform[0] = "1";
							doPerform[1] = regixValidaotrMesage;
						}
					}
				} else {
					doPerform[0] = "0";
					doPerform[1] = "OK";
				}
			} else {

				doPerform[0] = "1";
				doPerform[1] = errorMessage;
			}
		}

		return doPerform;
	}

}
