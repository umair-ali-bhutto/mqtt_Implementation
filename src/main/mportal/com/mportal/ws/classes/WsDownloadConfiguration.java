package com.mportal.ws.classes;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ConfigCity;
import com.ag.generic.entity.ConfigCorporate;
import com.ag.generic.entity.ConfigRegion;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.CaptchaService;
import com.ag.generic.service.ConfigCityService;
import com.ag.generic.service.ConfigCorporateService;
import com.ag.generic.service.ConfigPropertiesService;
import com.ag.generic.service.ConfigRegionService;
import com.ag.generic.service.LovService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.CaptchaUtil;
import com.ag.mportal.entity.ContactUs;
import com.ag.mportal.services.ContactUsService;

import cn.apiclub.captcha.Captcha;

@Component("com.mportal.ws.classes.WsDownloadConfiguration")
public class WsDownloadConfiguration implements Wisher {
	@Autowired
	LovService lovService;
	@Autowired
	ConfigPropertiesService configPropertiesService;
	@Autowired
	ContactUsService contactUsService;
	@Autowired
	ConfigCityService configCityService;
	@Autowired
	ConfigRegionService configRegionService;
	@Autowired
	ConfigCorporateService configCorporateService;
	@Autowired
	CaptchaService captchaService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			boolean isCaptcha = false;

			if (rm.getChannel().equalsIgnoreCase("portal")) {
				isCaptcha = true;
			}
			String pkgName = Objects.isNull(rm.getAdditionalData())
					|| Objects.isNull(rm.getAdditionalData().get("packageName")) ? null
							: rm.getAdditionalData().get("packageName").toString();

			response.setCode("0000");
			response.setMessage("Success");
			HashMap<Object, Object> mp = new HashMap<Object, Object>();
			String corpId = "00";

			if (!Objects.isNull(pkgName) && !pkgName.isEmpty()) {
				ConfigCorporate configCorporate = configCorporateService.getCorporateDetails(pkgName);
				if (!Objects.isNull(configCorporate)) {
					mp.put("corpId", configCorporate.getCorpId());
					corpId = configCorporate.getCorpId().trim();
				}
			}

			if (isCaptcha) {
				captchaService.updateAll(rm.getImei());
				Captcha captcha = CaptchaUtil.createCaptcha(172, 37);
				com.ag.generic.entity.Captcha cpt = new com.ag.generic.entity.Captcha();
				cpt.setCaptchaAns(captcha.getAnswer());
				cpt.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				cpt.setIsActive(1);
				cpt.setUuid(rm.getImei());
				long ifds = captchaService.insert(cpt);
				mp.put("captchaImg", CaptchaUtil.encodeCaptcha(captcha));
				mp.put("captchaId", ifds+"");
			} else {

				List<LovMaster> lstLov = lovService.fetchLovs(corpId);
				List<LovDetail> listLovDetails = lovService.fetchLovsDetailsAll(corpId);
				List<ContactUs> listContactUs = contactUsService.fetchContact(corpId);
				List<ConfigCity> listConfigCity = configCityService.findAll(corpId);
				List<ConfigRegion> listConfigRegion = configRegionService.findAll(corpId);
				String qr = AppProp.getProperty("QRText");
				List<LovMaster> lstLovMaster = lovService.fetchLovs(AppProp.getProperty("generic.corpId"));
				List<LovDetail> lstBank = null;
				List<LovDetail> lstBussinessCat = null;
				List<LovDetail> lstPaymentType = null;

				for (LovMaster lovMaster : lstLovMaster) {
					switch (lovMaster.getValue()) {
					case "BANK":
						lstBank = lovService.fetchLovsDetails(lovMaster.getId(), AppProp.getProperty("generic.corpId"));
						break;
					case "PAYMENT_TYPE":
						lstPaymentType = lovService.fetchLovsDetails(lovMaster.getId(),
								AppProp.getProperty("generic.corpId"));
						break;
					case "BUSINESS_CAT":
						lstBussinessCat = lovService.fetchLovsDetails(lovMaster.getId(),
								AppProp.getProperty("generic.corpId"));
						break;
					default:
						break;
					}
				}

				if (!Objects.isNull(lstBank) && !lstBank.isEmpty()) {
					mp.put("banks", lstBank);
				}
				if (!Objects.isNull(lstBussinessCat) && !lstBussinessCat.isEmpty()) {
					mp.put("businessCategories", lstBussinessCat);
				}
				if (!Objects.isNull(lstPaymentType) && !lstPaymentType.isEmpty()) {
					mp.put("paymentModes", lstPaymentType);
				}

				if (!Objects.isNull(lstLov) && !lstLov.isEmpty()) {
					mp.put("lov", lstLov);
				}
				if (!Objects.isNull(listLovDetails) && !listLovDetails.isEmpty()) {
					mp.put("lovDetails", listLovDetails);
				}
				if (!Objects.isNull(listContactUs) && !listContactUs.isEmpty()) {
					mp.put("contact", listContactUs);
				}
				if (!Objects.isNull(listConfigCity) && !listConfigCity.isEmpty()) {
					mp.put("city", listConfigCity);
				}
				if (!Objects.isNull(listConfigRegion) && !listConfigRegion.isEmpty()) {
					mp.put("region", listConfigRegion);
				}
				if (!Objects.isNull(qr) && !qr.isEmpty()) {
					mp.put("qrText", qr);
				}
			}
			response.setData(mp);

		}

		catch (Exception ex) {

			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo(rm.getUserid(), "EXCEPTION IN DoLogin :  " + ex);
			ex.printStackTrace();
		}

		return response;
	}

}
