package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.QrConfig;

public interface QrConfigService {
	public List<QrConfig> SelectAll(String corpId);
	public List<QrConfig> SelectAllByMid(String corpId, String mid);
}
