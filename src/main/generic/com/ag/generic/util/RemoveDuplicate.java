package com.ag.generic.util;

import java.util.ArrayList;
import java.util.List;

import com.ag.loy.adm.entity.CampaignCalender;
import com.ag.loy.adm.entity.CampaignCurrency;
import com.ag.loy.adm.entity.CampaignFeatures;
import com.ag.loy.adm.entity.CampaignMerchants;
import com.ag.loy.adm.entity.CampaignProducts;

public class RemoveDuplicate {

	private ArrayList<CampaignProducts> lstProduct;
	private ArrayList<CampaignFeatures> lstFeature;
	private ArrayList<CampaignCurrency> lstCurrency;
	private ArrayList<CampaignCalender> lstCalender;
	private ArrayList<CampaignMerchants> lstMerchant;

	public ArrayList<CampaignProducts> removeDuplicateProducts(List<CampaignProducts> lst) {
		lstProduct = new ArrayList<CampaignProducts>();
		for (CampaignProducts cpf : lst) {
			if (!containsPrd(cpf.getProductId())) {
				lstProduct.add(cpf);
			}
		}
		return lstProduct;
	}

	public ArrayList<CampaignFeatures> removeDuplicateFeature(List<CampaignFeatures> lst) {
		lstFeature = new ArrayList<CampaignFeatures>();
		for (CampaignFeatures cpf : lst) {
			if (!containsFtr(cpf.getFeaturedId())) {
				lstFeature.add(cpf);
			}
		}
		return lstFeature;
	}

	public ArrayList<CampaignCurrency> removeDuplicateCurrency(List<CampaignCurrency> lst) {
		lstCurrency = new ArrayList<CampaignCurrency>();
		for (CampaignCurrency cpf : lst) {
			if (!containsCurr(cpf.getCurrency())) {
				lstCurrency.add(cpf);
			}
		}
		return lstCurrency;
	}

	public ArrayList<CampaignCalender> removeDuplicateCalender(List<CampaignCalender> lst) {
		lstCalender = new ArrayList<CampaignCalender>();
		for (CampaignCalender cpf : lst) {
			if (!containsCal(cpf.getCalId())) {
				lstCalender.add(cpf);
			}
		}
		return lstCalender;
	}

	public ArrayList<CampaignMerchants> removeDuplicateMerchant(List<CampaignMerchants> lst) {
		lstMerchant = new ArrayList<CampaignMerchants>();
		for (CampaignMerchants cm : lst) {
			if (!containsMerchant(cm.getmId())) {
				lstMerchant.add(cm);
			}
		}
		return lstMerchant;
	}

	boolean containsPrd(String value) {
		for (CampaignProducts c : lstProduct) {
			if (c.getProductId().equals(value)) {
				return true;
			}
		}
		return false;
	}

	boolean containsFtr(String value) {
		for (CampaignFeatures c : lstFeature) {
			if (c.getFeaturedId().equals(value)) {
				return true;
			}
		}
		return false;
	}

	boolean containsCurr(String value) {
		for (CampaignCurrency c : lstCurrency) {
			if (c.getCurrency().equals(value)) {
				return true;
			}
		}
		return false;
	}

	boolean containsCal(String value) {
		for (CampaignCalender c : lstCalender) {
			if (c.getCalId().equals(value)) {
				return true;
			}
		}
		return false;
	}

	boolean containsMerchant(String value) {
		for (CampaignMerchants c : lstMerchant) {
			if (c.getmId().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
