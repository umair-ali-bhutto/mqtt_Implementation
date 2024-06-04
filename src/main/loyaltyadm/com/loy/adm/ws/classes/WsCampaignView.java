package com.loy.adm.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.RemoveDuplicate;
import com.ag.loy.adm.entity.CampaignCalender;
import com.ag.loy.adm.entity.CampaignCalenderProduct;
import com.ag.loy.adm.entity.CampaignCurrency;
import com.ag.loy.adm.entity.CampaignCurrencyProduct;
import com.ag.loy.adm.entity.CampaignFeatureProduct;
import com.ag.loy.adm.entity.CampaignFeatures;
import com.ag.loy.adm.entity.CampaignMaster;
import com.ag.loy.adm.entity.CampaignMerchantProduct;
import com.ag.loy.adm.entity.CampaignMerchants;
import com.ag.loy.adm.entity.CampaignProducts;
import com.ag.loy.adm.service.CampaignCalenderProductService;
import com.ag.loy.adm.service.CampaignCalenderService;
import com.ag.loy.adm.service.CampaignCurrencyProductService;
import com.ag.loy.adm.service.CampaignCurrencyService;
import com.ag.loy.adm.service.CampaignFeatureProductService;
import com.ag.loy.adm.service.CampaignFeaturesService;
import com.ag.loy.adm.service.CampaignMasterService;
import com.ag.loy.adm.service.CampaignMerchantProductService;
import com.ag.loy.adm.service.CampaignMerchantsService;
import com.ag.loy.adm.service.CampaignProductsService;

@Component("com.loy.adm.ws.classes.WsCampaignView")
public class WsCampaignView implements Wisher {

	@Autowired
	CampaignMasterService campaignMasterService;

	@Autowired
	CampaignCalenderProductService campaignCalenderProductService;

	@Autowired
	CampaignCurrencyProductService campaignCurrencyProductService;

	@Autowired
	CampaignFeatureProductService campaignFeatureProductService;

	@Autowired
	CampaignProductsService campaignProductsService;

	@Autowired
	CampaignFeaturesService campaignFeaturesService;

	@Autowired
	CampaignCurrencyService campaignCurrencyService;

	@Autowired
	CampaignCalenderService campaignCalenderService;

	@Autowired
	CampaignMerchantsService campaignMerchantsService;

	@Autowired
	CampaignMerchantProductService campaignMerchantProductService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {

			String campId = rm.getAdditionalData().get("campId").toString();
			String corpId = rm.getAdditionalData().get("corpId").toString();

			CampaignMaster campaignMaster = campaignMasterService.fetchAllRecordById(campId, corpId);
			if (campaignMaster != null) {

				String sbType = (campaignMaster.getCampaignSubType() != null) ? campaignMaster.getCampaignSubType()
						: "N/A";

				String[] calenderSelected = null;
				String[] currencySelected = null;
				String[] featureSelected = null;
				String[] productsSelected = null;
				String[] merchantSelected = null;

				if (sbType.equals("PRODUCT+CALENDAR") || sbType.equals("PRODUCT+CURRENCY")
						|| sbType.equals("PRODUCT+FEATURE") || sbType.equals("PRODUCT+MERCHANT")) {

					ArrayList<CampaignProducts> viewCampaignProductList = new ArrayList<CampaignProducts>();
					ArrayList<CampaignFeatures> viewCampaignFeaturesList = new ArrayList<CampaignFeatures>();
					ArrayList<CampaignCurrency> viewCampaignCurrenciesList = new ArrayList<CampaignCurrency>();
					ArrayList<CampaignCalender> viewCampaignCalenderList = new ArrayList<CampaignCalender>();
					ArrayList<CampaignMerchants> viewCampaignMerchantList = new ArrayList<CampaignMerchants>();

					ArrayList<CampaignCalenderProduct> atPcalenr = campaignCalenderProductService
							.fetchAllRecordByCampId(campId, corpId);
					ArrayList<CampaignCurrencyProduct> atPCurrecny = campaignCurrencyProductService
							.fetchAllRecordByCampId(campId, corpId);
					ArrayList<CampaignFeatureProduct> atPFeature = campaignFeatureProductService
							.fetchAllRecordByCampId(campId, corpId);
					ArrayList<CampaignMerchantProduct> atPMerchant = campaignMerchantProductService
							.fetchAllRecordByCampId(campId, corpId);

					if (atPcalenr != null) {
						for (CampaignCalenderProduct ctPcal : atPcalenr) {
							CampaignCalender e = new CampaignCalender();
							e.setCalId(ctPcal.getId().getCalid());
							viewCampaignCalenderList.add(e);

							CampaignProducts spd = new CampaignProducts();
							spd.setProductId(ctPcal.getId().getProductId());
							viewCampaignProductList.add(spd);
						}
						viewCampaignCalenderList = new RemoveDuplicate()
								.removeDuplicateCalender(viewCampaignCalenderList);
						calenderSelected = new String[viewCampaignCalenderList.size()];
						int n = 0;
						for (CampaignCalender c : viewCampaignCalenderList) {
							calenderSelected[n] = c.getCalId();
							n++;
						}
					}

					if (atPCurrecny != null) {
						for (CampaignCurrencyProduct ctPcal : atPCurrecny) {
							CampaignCurrency e = new CampaignCurrency();
							e.setCurrency(ctPcal.getId().getCurrency());
							viewCampaignCurrenciesList.add(e);

							CampaignProducts spd = new CampaignProducts();
							spd.setProductId(ctPcal.getId().getProductId());
							viewCampaignProductList.add(spd);
						}
						viewCampaignCurrenciesList = new RemoveDuplicate()
								.removeDuplicateCurrency(viewCampaignCurrenciesList);
						currencySelected = new String[viewCampaignCurrenciesList.size()];
						int o = 0;
						for (CampaignCurrency c : viewCampaignCurrenciesList) {
							currencySelected[o] = c.getCurrency();
							o++;
						}
					}

					if (atPFeature != null) {
						for (CampaignFeatureProduct ctPcal : atPFeature) {
							CampaignFeatures e = new CampaignFeatures();
							e.setFeaturedId(ctPcal.getId().getFeatureid());
							viewCampaignFeaturesList.add(e);

							CampaignProducts spd = new CampaignProducts();
							spd.setProductId(ctPcal.getId().getProductId());
							viewCampaignProductList.add(spd);
						}
						viewCampaignFeaturesList = new RemoveDuplicate()
								.removeDuplicateFeature(viewCampaignFeaturesList);
						featureSelected = new String[viewCampaignFeaturesList.size()];
						int m = 0;
						for (CampaignFeatures c : viewCampaignFeaturesList) {
							featureSelected[m] = c.getFeaturedId();
							m++;
						}
					}

					if (atPMerchant != null) {
						for (CampaignMerchantProduct ctPmer : atPMerchant) {
							CampaignMerchants e = new CampaignMerchants();
							e.setmId(ctPmer.getId().getMid());
							viewCampaignMerchantList.add(e);

							CampaignProducts spd = new CampaignProducts();
							spd.setProductId(ctPmer.getId().getProductId());
							viewCampaignProductList.add(spd);
						}
						viewCampaignMerchantList = new RemoveDuplicate()
								.removeDuplicateMerchant(viewCampaignMerchantList);
						merchantSelected = new String[viewCampaignMerchantList.size()];
						int p = 0;
						for (CampaignMerchants c : viewCampaignMerchantList) {
							merchantSelected[p] = c.getmId();
							p++;
						}
					}

					viewCampaignProductList = new RemoveDuplicate().removeDuplicateProducts(viewCampaignProductList);

					int l = 0;
					productsSelected = new String[viewCampaignProductList.size()];
					for (CampaignProducts c : viewCampaignProductList) {
						productsSelected[l] = c.getProductId();
						l++;
					}

				} else {
					ArrayList<CampaignProducts> cpd = campaignProductsService.fetchAllRecordByCampId(campId, corpId);
					productsSelected = new String[cpd.size()];

					int l = 0;
					for (CampaignProducts c : cpd) {
						productsSelected[l] = c.getProductId();
						l++;
					}

					ArrayList<CampaignFeatures> cpf = campaignFeaturesService.fetchAllRecordByCampId(campId, corpId);
					featureSelected = new String[cpf.size()];
					int m = 0;
					for (CampaignFeatures c : cpf) {
						featureSelected[m] = c.getFeaturedId();
						m++;
					}

					ArrayList<CampaignCalender> cpc = campaignCalenderService.fetchAllRecordByCampId(campId, corpId);
					calenderSelected = new String[cpc.size()];
					int n = 0;
					for (CampaignCalender c : cpc) {
						calenderSelected[n] = c.getCalId();
						n++;
					}

					ArrayList<CampaignCurrency> cpcd = campaignCurrencyService.fetchAllRecordByCampId(campId, corpId);
					currencySelected = new String[cpcd.size()];
					int o = 0;
					for (CampaignCurrency c : cpcd) {
						currencySelected[o] = c.getCurrency();
						o++;
					}

					ArrayList<CampaignMerchants> mer = campaignMerchantsService.fetchAllRecordByCampId(campId, corpId);
					merchantSelected = new String[mer.size()];
					int p = 0;
					for (CampaignMerchants c : mer) {
						merchantSelected[p] = c.getmId();
						p++;
					}
				}

				response.setCode("0000");
				response.setMessage("SUCCESS");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("compMaster", campaignMaster);
				obj.put("calenderSelected", calenderSelected);
				obj.put("currencySelected", currencySelected);
				obj.put("featureSelected", featureSelected);
				obj.put("productsSelected", productsSelected);
				obj.put("merchantSelected", merchantSelected);
				response.setData(obj);

			} else {
				response.setCode("0001");
				response.setMessage("No Campaign Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}