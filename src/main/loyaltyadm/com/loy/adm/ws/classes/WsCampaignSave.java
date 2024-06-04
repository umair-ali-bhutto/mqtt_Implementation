package com.loy.adm.ws.classes;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AwardMasters;
import com.ag.loy.adm.entity.CalenderMaster;
import com.ag.loy.adm.entity.CampaignCalender;
import com.ag.loy.adm.entity.CampaignCalenderProduct;
import com.ag.loy.adm.entity.CampaignCalenderProductPK;
import com.ag.loy.adm.entity.CampaignCurrency;
import com.ag.loy.adm.entity.CampaignCurrencyProduct;
import com.ag.loy.adm.entity.CampaignCurrencyProductPK;
import com.ag.loy.adm.entity.CampaignFeatureProduct;
import com.ag.loy.adm.entity.CampaignFeatureProductPK;
import com.ag.loy.adm.entity.CampaignFeatures;
import com.ag.loy.adm.entity.CampaignMaster;
import com.ag.loy.adm.entity.CampaignMerchantProduct;
import com.ag.loy.adm.entity.CampaignMerchantProductPK;
import com.ag.loy.adm.entity.CampaignMerchants;
import com.ag.loy.adm.entity.CampaignProducts;
import com.ag.loy.adm.model.AwardUtil;
import com.ag.loy.adm.service.AwardMastersService;
import com.ag.loy.adm.service.CalenderMasterService;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component("com.loy.adm.ws.classes.WsCampaignSave")
public class WsCampaignSave implements Wisher {

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
	AwardMastersService awardMastersService;

	@Autowired
	CalenderMasterService calenderMasterService;

	@Autowired
	CampaignMerchantsService campaignMerchantsService;

	@Autowired
	CampaignMerchantProductService campaignMerchantProductService;

	String[] calenderSelected;
	String[] currencySelected;
	String[] featureSelected;
	String[] merchantSelected;
	String[] productsSelected;

	String loginId = "N/A";

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		loginId = rm.getUserid();
		try {

			if (rm.getAdditionalData().containsKey("campaignMaster")) {

				Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
				CampaignMaster campaignMaster = gson.fromJson(rm.getAdditionalData().get("campaignMaster").toString(),
						CampaignMaster.class);

				calenderSelected = (rm.getAdditionalData().get("calenderSelected") != null)
						? rm.getAdditionalData().get("calenderSelected").toString().split(", ")
						: null;
				currencySelected = (rm.getAdditionalData().get("currencySelected") != null)
						? rm.getAdditionalData().get("currencySelected").toString().split(", ")
						: null;
				featureSelected = (rm.getAdditionalData().get("featureSelected") != null)
						? rm.getAdditionalData().get("featureSelected").toString().split(", ")
						: null;
				merchantSelected = (rm.getAdditionalData().get("merchantSelected") != null)
						? rm.getAdditionalData().get("merchantSelected").toString().split(", ")
						: null;
				productsSelected = (rm.getAdditionalData().get("productsSelected") != null)
						? rm.getAdditionalData().get("productsSelected").toString().split(", ")
						: null;
				String awardIdProduct = rm.getAdditionalData().get("awardIdProduct").toString();
				String corpId = rm.getAdditionalData().get("corpId").toString();
				rm.setCorpId(corpId);

				String message = "";
				boolean b = true;

				if (campaignMaster.getCampaignType().equalsIgnoreCase("bonus")) {
					if (campaignMaster.getName().length() == 0 || campaignMaster.getDescription().length() == 0) {
						b = false;
						message += " Please enter campaign data.";
					}

					switch (campaignMaster.getCampaignSubType()) {
					case "000000":
						b = false;
						message += "Bonus Campaign: Can not select default campaign sub type.";
						break;

					case "CALENDAR":
						if (calenderSelected == null) {
							b = false;
							message += "Bonus Campaign: Calender Data can not be empty.";
						} else {
							if (calenderSelected.length == 0 || calenderSelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Calender Data can not be empty.";
							}
						}
						break;
					case "CURRENCY":
						if (currencySelected == null) {
							b = false;
							message += "Bonus Campaign: Currency Data can not be empty.";
						} else {
							if (currencySelected.length == 0 || currencySelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Currency Data can not be empty.";
							}
						}
						break;
					case "FEATURE":
						if (featureSelected == null) {
							b = false;
							message += "Bonus Campaign: Feature Data can not be empty.";
						} else {
							if (featureSelected.length == 0 || featureSelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Feature Data can not be empty.";
							}
						}
						break;
					case "MERCHANT":
						if (merchantSelected == null) {
							b = false;
							message += "Bonus Campaign: Merchant Data can not be empty.";
						} else {
							if (merchantSelected.length == 0 || merchantSelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Merchant Data can not be empty.";
							}
						}
						break;
					case "PRODUCT":
						if (productsSelected == null) {
							b = false;
							message += "Bonus Campaign: Product Data can not be empty.";
						} else {
							if (productsSelected.length == 0 || productsSelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Product Data can not be empty.";
							}
						}
						break;
					case "PRODUCT+CALENDAR":
						if (productsSelected == null || calenderSelected == null) {
							b = false;
							message += "Bonus Campaign: Product/Calender Data can not be empty.";
						} else {
							if (productsSelected.length == 0 || calenderSelected.length == 0
									|| productsSelected[0].equals("[]") || calenderSelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Product/Calender Data can not be empty.";
							}
						}
						break;
					case "PRODUCT+CURRENCY":
						if (productsSelected == null || currencySelected == null) {
							b = false;
							message += "Bonus Campaign: Product/Currency Data can not be empty.";
						} else {
							if (productsSelected.length == 0 || currencySelected.length == 0
									|| productsSelected[0].equals("[]") || currencySelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Product/Currency Data can not be empty.";
							}
						}
						break;
					case "PRODUCT+FEATURE":
						if (productsSelected == null || featureSelected == null) {
							b = false;
							message += "Bonus Campaign: Product/Feature Data can not be empty.";
						} else {
							if (productsSelected.length == 0 || featureSelected.length == 0
									|| productsSelected[0].equals("[]") || featureSelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Product/Feature Data can not be empty.";
							}
						}
						break;
					case "PRODUCT+MERCHANT":
						if (productsSelected == null || merchantSelected == null) {
							b = false;
							message += "Bonus Campaign: Product/Merchant Data can not be empty.";
						} else {
							if (productsSelected.length == 0 || merchantSelected.length == 0
									|| productsSelected[0].equals("[]") || merchantSelected[0].equals("[]")) {
								b = false;
								message += "Bonus Campaign: Product/Merchant Data can not be empty.";
							}
						}
						break;
					default:
						break;
					}

					if (calenderSelected != null || productsSelected != null || currencySelected != null
							|| featureSelected != null || merchantSelected != null) {
						AgLogger.logInfo("OK");
					} else {
						b = false;
						message += " Please select any of configuration from Calender,Product,Feature.";
					}

				} else if (campaignMaster.getCampaignType().equalsIgnoreCase("basic")) {
					campaignMaster.setCampaignSubType("000000");

					if (campaignMaster.getName().length() == 0 || campaignMaster.getDescription().length() == 0) {
						b = false;
						message += "Basic Campaign: Please enter campaign data.";
					}

					if (productsSelected == null) {
						b = false;
						message += "Basic Campaign: Product Data can not be empty.";
					} else {
						if (productsSelected.length == 0 || productsSelected[0].equals("[]")) {
							b = false;
							message += "Basic Campaign: Product Data can not be empty.";
						}
					}

					if (featureSelected == null) {
						b = false;
						message += "Basic Campaign: Feature Data can not be empty.";
					} else {
						if (featureSelected.length == 0 || featureSelected[0].equals("[]")) {
							b = false;
							message += "Basic Campaign: Feature Data can not be empty.";
						}
					}
				}

				if (b) {
					if (!campaignMaster.getDateFrom().after(campaignMaster.getToDate())) {

						Long campaignId = (long) 0;

						String campId = campaignMasterService.fetchMaxCampId();

						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(campaignMaster.getToDate().getTime());
						calendar.set(Calendar.HOUR_OF_DAY, 23);
						calendar.set(Calendar.MINUTE, 59);
						calendar.set(Calendar.SECOND, 59);
						calendar.set(Calendar.MILLISECOND, 900);
						Timestamp todate = new Timestamp(calendar.getTimeInMillis());

						AgLogger.logInfo("DateFrom: " + campaignMaster.getDateFrom() + "| DateTo: " + todate);

						campaignMaster.setCampId(campId);
						campaignMaster.setName(campaignMaster.getName());
						campaignMaster.setDateFrom(campaignMaster.getDateFrom());
						campaignMaster.setToDate(todate);
						campaignMaster.setStatus(campaignMaster.getStatus());

						if (campaignMaster.getCampaignType().equalsIgnoreCase("basic")) {
							campaignMaster.setAllMerchants("Y");
						} else {
							campaignMaster.setAllMerchants(campaignMaster.getAllMerchants());
						}

						campaignMaster.setCampaignType(campaignMaster.getCampaignType());
						campaignMaster.setCampaignSubType(campaignMaster.getCampaignSubType());
						campaignMaster.setDescription(campaignMaster.getDescription());
						campaignMaster.setAccountDr("10023402");
						campaignMaster.setCorpId(rm.getCorpId());
						campaignMaster.setUpdatedBy(null);
						campaignMaster.setUpdateOn(null);

						campaignMaster.setCreatedBy(rm.getUserid());
						campaignMaster.setCreatedOn(new java.util.Date());
						campaignMaster.setAwardId(AwardUtil.getLeadingZero(awardIdProduct));
						campaignMaster.setAwardType("P");

						campaignId = campaignMasterService.save(campaignMaster);

						if (campaignId != 0) {
							List<AwardMasters> awd = awardMastersService
									.fetchAllRecordById(Long.parseLong(awardIdProduct), rm.getCorpId());

							if (campaignMaster.getCampaignType().equalsIgnoreCase("basic")) {

								if (productsSelected.length != 0) {
									saveProducts(awardIdProduct, awd.get(0).getType(), String.valueOf(campaignId),
											rm.getCorpId());
								}

								if (featureSelected.length != 0) {
									saveFeature(awardIdProduct, awd.get(0).getType(), String.valueOf(campaignId),
											rm.getCorpId(), "T");
								}

							} else if (campaignMaster.getCampaignType().equalsIgnoreCase("bonus")) {
								switch (campaignMaster.getCampaignSubType()) {
								case "CALENDAR":
									if (calenderSelected.length != 0) {
										saveCalender(awardIdProduct, awd.get(0).getType(), String.valueOf(campaignId),
												rm.getCorpId(), "P");
									}
									break;
								case "CURRENCY":
									if (currencySelected.length != 0) {
										saveCurrency(awardIdProduct, awd.get(0).getType(), String.valueOf(campaignId),
												rm.getCorpId());
									}
									break;
								case "FEATURE":
									if (featureSelected.length != 0) {
										saveFeature(awardIdProduct, awd.get(0).getType(), String.valueOf(campaignId),
												rm.getCorpId(), "T");
									}
									break;

								case "MERCHANT":
									if (merchantSelected.length != 0) {
										saveMerchants(awardIdProduct, awd.get(0).getType(), String.valueOf(campaignId),
												rm.getCorpId(), 0);
									}
									break;

								case "PRODUCT":
									if (productsSelected.length != 0) {
										saveProducts(awardIdProduct, awd.get(0).getType(), String.valueOf(campaignId),
												rm.getCorpId());
									}
									break;
								case "PRODUCT+CALENDAR":
									if (productsSelected.length != 0) {
										saveProductCalender(awardIdProduct, awd.get(0).getType(),
												String.valueOf(campaignId), rm.getCorpId());
									}

									break;
								case "PRODUCT+CURRENCY":
									if (productsSelected.length != 0) {
										saveProductCurrency(awardIdProduct, awd.get(0).getType(),
												String.valueOf(campaignId), rm.getCorpId());
									}

									break;
								case "PRODUCT+FEATURE":
									if (productsSelected.length != 0) {
										saveProductFeature(awardIdProduct, awd.get(0).getType(),
												String.valueOf(campaignId), rm.getCorpId());
									}
									break;
								case "PRODUCT+MERCHANT":
									if (productsSelected.length != 0) {
										saveProductMerchant(awardIdProduct, awd.get(0).getType(),
												String.valueOf(campaignId), rm.getCorpId());
									}
									break;
								default:
									break;
								}
							}
							response.setCode("0000");
							response.setMessage("Campaign Configured Successfully.");

						} else {
							response.setCode("0003");
							response.setMessage("Please select valid data.");
						}
					} else {
						response.setCode("0004");
						response.setMessage("From Date Cannot Be Greater Than To Date.");
					}

				} else {
					response.setCode("0002");
					response.setMessage(message);
				}

			} else {
				response.setCode("0001");
				response.setMessage("Please Enter Some values.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	void saveProducts(String awardID, String awardType, String campID, String corpID) {
		if (productsSelected.length != 0) {
			for (String sp : productsSelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}

				CampaignProducts campaignProducts = new CampaignProducts();
				campaignProducts.setAccountDr("10023402");
				campaignProducts.setAwardId(AwardUtil.getLeadingZero(awardID));
				campaignProducts.setAwardType(awardType);
				campaignProducts.setCampId(AwardUtil.getLeadingZero(campID));
				campaignProducts.setCorpId(corpID);
				campaignProducts.setProductId(sp);
				campaignProducts.setCreatedBy(loginId);
				campaignProducts.setCreatedOn(new Date(new java.util.Date().getTime()));
				campaignProducts.setUpdatedBy(null);
				campaignProducts.setUpdateOn(null);
				campaignProductsService.saveCampaignProducts(campaignProducts);
			}
		}

	}

	void saveFeature(String awardID, String awardType, String campID, String corpID, String featureType) {
		if (featureSelected.length != 0) {
			for (String sp : featureSelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}

				CampaignFeatures campaignFeature = new CampaignFeatures();
				campaignFeature.setAccountDr("10023402");
				campaignFeature.setAwardId(AwardUtil.getLeadingZero(awardID));
				campaignFeature.setAwardType(awardType);
				campaignFeature.setCampId(AwardUtil.getLeadingZero(campID));
				campaignFeature.setCorpId(corpID);
				campaignFeature.setCreatedBy(loginId);
				campaignFeature.setCreatedOn(new Date(new java.util.Date().getTime()));
				campaignFeature.setFeaturedId(sp);
				campaignFeature.setUpdatedBy(null);
				campaignFeature.setUpdateOn(null);
				campaignFeature.setFeatureType(featureType);
				campaignFeaturesService.saveCampaignFeatures(campaignFeature);

			}
		}

	}

	void saveCurrency(String awardID, String awardType, String campID, String corpID) {
		if (currencySelected.length != 0) {
			for (String sp : currencySelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}

				CampaignCurrency campaignCurrency = new CampaignCurrency();
				campaignCurrency.setAccountDr("10023402");
				campaignCurrency.setAwardId(AwardUtil.getLeadingZero(awardID));
				campaignCurrency.setAwardType(awardType);
				campaignCurrency.setCampId(AwardUtil.getLeadingZero(campID));
				campaignCurrency.setCorpId(corpID);
				campaignCurrency.setCreatedBy(loginId);
				campaignCurrency.setCreatedOn(new Date(new java.util.Date().getTime()));
				campaignCurrency.setCurrency(sp);
				campaignCurrency.setUpdatedBy(null);
				campaignCurrency.setUpdateOn(null);
				campaignCurrencyService.saveCampaignCurrency(campaignCurrency);

			}
		}

	}

	void saveCalender(String awardID, String awardType, String campID, String corpID, String type) {
		if (calenderSelected.length != 0) {
			for (String sp : calenderSelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}

				CalenderMaster cms = calenderMasterService.fetchAllRecordById(sp, corpID);
				CampaignCalender cds = new CampaignCalender();
				cds.setAccountDr("10023402");
				cds.setAwardId(AwardUtil.getLeadingZero(awardID));
				cds.setCalId(sp);
				cds.setCampID(AwardUtil.getLeadingZero(campID));
				cds.setCorpId(corpID);
				cds.setCrBy(loginId);
				cds.setCrOn(new java.sql.Date(new java.util.Date().getTime()));
				cds.setDateFrom(cms.getDateFrom());
				cds.setDateTo(cms.getDateTo());
				cds.setDescrption(cms.getDescrption());
				cds.setKyc(cms.getKyc());
				cds.setType(type);
				cds.setUpdBy(null);
				cds.setUpdOn(null);
				campaignCalenderService.save(cds);

			}
		}

	}

	void saveMerchants(String awardID, String awardType, String campID, String corpID, double percShsred) {
		if (merchantSelected.length != 0) {
			for (String sp : merchantSelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}

				CampaignMerchants campaignMerchant = new CampaignMerchants();
				campaignMerchant.setAccountDr("10023402");
				campaignMerchant.setAwardId(AwardUtil.getLeadingZero(awardID));
				campaignMerchant.setAwardType(awardType);
				campaignMerchant.setCampId(AwardUtil.getLeadingZero(campID));
				campaignMerchant.setCorpId(corpID);
				campaignMerchant.setCreatedBy(loginId);
				campaignMerchant.setCreatedOn(new Date(new java.util.Date().getTime()));
				campaignMerchant.setmId(sp);
				campaignMerchant.setUpdatedBy(null);
				campaignMerchant.setUpdateOn(null);
				campaignMerchant.setPercShared(percShsred);
				campaignMerchantsService.saveCampaignCurrency(campaignMerchant);
			}
		}

	}

	void saveProductCalender(String awardID, String awardType, String campID, String corpID) {
		if (productsSelected.length != 0) {

			for (String sp : productsSelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}
				CampaignCalenderProduct pdst = new CampaignCalenderProduct();
				pdst.setAccountDr("10023402");
				pdst.setAwardId(AwardUtil.getLeadingZero(awardID));
				pdst.setAwardType(awardType);

				CampaignCalenderProductPK ccpp = new CampaignCalenderProductPK();
				ccpp.setCampid(AwardUtil.getLeadingZero(campID));
				ccpp.setCorpid(corpID);
				ccpp.setProductId(sp);

				pdst.setCreatedBy(loginId);
				pdst.setCreatedOn(new Date(new java.util.Date().getTime()));
				pdst.setPercShared("0");
				pdst.setUpdBy(null);
				pdst.setUpdOn(null);
				for (String spl : calenderSelected) {
					if (spl.contains("[")) {
						spl = spl.replace("[", "");
					}
					if (spl.contains("]")) {
						spl = spl.replace("]", "");
					}
					CalenderMaster cms = calenderMasterService.fetchAllRecordById(spl, corpID);
					ccpp.setCalid(spl);

					pdst.setId(ccpp);
					pdst.setDateFrom(cms.getDateFrom());
					pdst.setDateTo(cms.getDateTo());
					campaignCalenderProductService.saveCampaignProducts(pdst);
				}
			}
		}

	}

	void saveProductCurrency(String awardID, String awardType, String campID, String corpID) {

		if (productsSelected.length != 0) {
			for (String sp : productsSelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}

				CampaignCurrencyProduct pdst = new CampaignCurrencyProduct();
				pdst.setAccountDr("10023402");
				pdst.setAwardId(AwardUtil.getLeadingZero(awardID));
				pdst.setAwardType(awardType);

				CampaignCurrencyProductPK ccpp = new CampaignCurrencyProductPK();
				ccpp.setCampid(AwardUtil.getLeadingZero(campID));
				ccpp.setCorpid(corpID);
				ccpp.setProductId(sp);

				pdst.setCreatedBy(loginId);
				pdst.setCreatedOn(new Date(new java.util.Date().getTime()));
				pdst.setPercShared("0");

				pdst.setUpdBy(null);
				pdst.setUpdOn(null);
				for (String spl : currencySelected) {
					if (spl.contains("[")) {
						spl = spl.replace("[", "");
					}
					if (spl.contains("]")) {
						spl = spl.replace("]", "");
					}
					ccpp.setCurrency(spl);
					pdst.setId(ccpp);
					campaignCurrencyProductService.saveCampaignProducts(pdst);
				}
			}
		}
	}

	void saveProductFeature(String awardID, String awardType, String campID, String corpID) {

		if (productsSelected.length != 0) {
			for (String sp : productsSelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}

				CampaignFeatureProduct pdst = new CampaignFeatureProduct();
				pdst.setAccountDr("10023402");
				pdst.setAwardId(AwardUtil.getLeadingZero(awardID));
				pdst.setAwardType(awardType);

				CampaignFeatureProductPK cfpp = new CampaignFeatureProductPK();
				cfpp.setCampid(AwardUtil.getLeadingZero(campID));
				cfpp.setCorpid(corpID);
				cfpp.setProductId(sp);

				pdst.setCreatedBy(loginId);
				pdst.setCreatedOn(new Date(new java.util.Date().getTime()));
				pdst.setPercShared("0");
				pdst.setUpdBy(null);
				pdst.setUpdOn(null);
				for (String spl : featureSelected) {
					if (spl.contains("[")) {
						spl = spl.replace("[", "");
					}
					if (spl.contains("]")) {
						spl = spl.replace("]", "");
					}
					cfpp.setFeatureid(spl);
					pdst.setId(cfpp);
					campaignFeatureProductService.saveCampaignProducts(pdst);
				}
			}
		}
	}

	void saveProductMerchant(String awardID, String awardType, String campID, String corpID) {
		if (productsSelected.length != 0) {
			for (String sp : productsSelected) {
				if (sp.contains("[")) {
					sp = sp.replace("[", "");
				}
				if (sp.contains("]")) {
					sp = sp.replace("]", "");
				}

				CampaignMerchantProduct pdst = new CampaignMerchantProduct();
				pdst.setAccountDr("10023402");
				pdst.setAwardId(AwardUtil.getLeadingZero(awardID));
				pdst.setAwardType(awardType);

				CampaignMerchantProductPK cmpp = new CampaignMerchantProductPK();
				cmpp.setCampid(AwardUtil.getLeadingZero(campID));
				cmpp.setCorpid(corpID);
				cmpp.setProductId(sp);

				pdst.setCreatedBy(loginId);
				pdst.setCreatedOn(new Date(new java.util.Date().getTime()));
				pdst.setPercShared("0");
				pdst.setUpdBy(null);
				pdst.setUpdOn(null);
				for (String spl : merchantSelected) {
					if (spl.contains("[")) {
						spl = spl.replace("[", "");
					}
					if (spl.contains("]")) {
						spl = spl.replace("]", "");
					}
					cmpp.setMid(spl);
					pdst.setId(cmpp);
					campaignMerchantProductService.saveCampaignProducts(pdst);
				}
			}
		}

	}

}