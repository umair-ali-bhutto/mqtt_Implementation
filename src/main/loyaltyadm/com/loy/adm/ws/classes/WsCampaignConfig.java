package com.loy.adm.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.LovService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AwardMasters;
import com.ag.loy.adm.entity.CalenderMaster;
import com.ag.loy.adm.entity.CampaignMaster;
import com.ag.loy.adm.entity.CurrencyMaster;
import com.ag.loy.adm.entity.FeatureMaster;
import com.ag.loy.adm.entity.MerchantMaster;
import com.ag.loy.adm.entity.ProductMaster;
import com.ag.loy.adm.service.AwardMastersService;
import com.ag.loy.adm.service.CalenderMasterService;
import com.ag.loy.adm.service.CampaignMasterService;
import com.ag.loy.adm.service.CurrencyMasterService;
import com.ag.loy.adm.service.FeatureMasterService;
import com.ag.loy.adm.service.MerchantMasterService;
import com.ag.loy.adm.service.ProductMasterService;
import com.ag.mportal.model.DisplayModel;

@Component("com.loy.adm.ws.classes.WsCampaignConfig")
public class WsCampaignConfig implements Wisher {

	@Autowired
	AwardMastersService awardMastersService;

	@Autowired
	ProductMasterService productMasterService;

	@Autowired
	FeatureMasterService featureMasterService;

	@Autowired
	CalenderMasterService calenderMasterService;

	@Autowired
	CurrencyMasterService currencyMasterService;

	@Autowired
	LovService lovService;

	@Autowired
	CampaignMasterService campaignMasterService;

	@Autowired
	MerchantMasterService merchantMasterService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {

			String corpId = (rm.getAdditionalData().containsKey("corpId"))
					? rm.getAdditionalData().get("corpId").toString()
					: null;

			List<DisplayModel> statusModel = new ArrayList<DisplayModel>();
			List<DisplayModel> awardTypeModel = new ArrayList<DisplayModel>();
			List<DisplayModel> campaignType = new ArrayList<DisplayModel>();
			List<DisplayModel> campaignSubType = new ArrayList<DisplayModel>();
			List<DisplayModel> allMerchants = new ArrayList<DisplayModel>();

			List<AwardMasters> awardNameList = awardMastersService.fetchAllActiveRecords(corpId);
			List<ProductMaster> productList = productMasterService.fetchAllRecord(corpId);
			List<FeatureMaster> featureList = featureMasterService.getAllFeatures(corpId);
			List<CurrencyMaster> currencyList = currencyMasterService.fetchAllRecord(corpId);
			List<CalenderMaster> calenderList = calenderMasterService.fetchAllRecord(corpId);
			ArrayList<CampaignMaster> lstCamp = campaignMasterService.fetchAllRecords(corpId);
			List<MerchantMaster> lstMerchants = merchantMasterService.findAllByCorpId(corpId);
			campaignSubType = lovService.fetchLov("CAMPAIGN_MASTER", "CAMPAIGN_SUB_TYPE");

			DisplayModel dms;
			dms = new DisplayModel();
			dms.setKey("A");
			dms.setValue("Active");
			statusModel.add(dms);
			
			dms = new DisplayModel();
			dms.setKey("I");
			dms.setValue("In-Active");
			statusModel.add(dms);

			dms = new DisplayModel();
			dms.setKey("P");
			dms.setValue("P");
			awardTypeModel.add(dms);

			dms = new DisplayModel();
			dms.setKey("BASIC");
			dms.setValue("BASIC");
			campaignType.add(dms);

			dms = new DisplayModel();
			dms.setKey("BONUS");
			dms.setValue("BONUS");
			campaignType.add(dms);

			if (lstMerchants.size() != 0) {
				for (MerchantMaster mm : lstMerchants) {
					// AgLogger.logInfo(mm.getId().getMid() + " - " + mm.getName() + " - " +
					// mm.getAddress1());
					dms = new DisplayModel();
					dms.setKey(mm.getId().getMid());
					dms.setValue(mm.getId().getMid() + " - " + mm.getName() + " - " + mm.getAddress1());
					allMerchants.add(dms);
				}
			}

			response.setCode("0000");
			response.setMessage("SUCCESS");
			HashMap<Object, Object> obj = new HashMap<Object, Object>();
			obj.put("status", statusModel);
			obj.put("awardType", awardTypeModel);
			obj.put("compaignType", campaignType);
			obj.put("compaignSubType", campaignSubType);
			obj.put("allMerchants", allMerchants);
			obj.put("awardNameList", awardNameList);
			obj.put("productList", productList);
			obj.put("featureList", featureList);
			obj.put("currencyList", currencyList);
			obj.put("calenderList", calenderList);
			obj.put("lstCamp", lstCamp);

			response.setData(obj);
		} catch (Exception ex) {
			ex.printStackTrace();

			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}
