package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.DocumentConfig;

public interface DocumentConfigService {

	public List<DocumentConfig> fetchAll();

	public List<DocumentConfig> fetchAllByGroupCode(String corpId, int userGroup);

}
