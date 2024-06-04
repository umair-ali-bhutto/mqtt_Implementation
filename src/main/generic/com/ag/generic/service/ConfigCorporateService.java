package com.ag.generic.service;

import com.ag.generic.entity.ConfigCorporate;

public interface ConfigCorporateService {
	public ConfigCorporate getCorporateDetails(String packageName);

	public ConfigCorporate fetchByCorpId(String corpId);

}
