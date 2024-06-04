package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.BannerTicker;


public interface BannerTickerService
{
	public List<BannerTicker> getBannerTickerByCorpId(String type,String corpId);
	
	
}
