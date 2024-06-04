package com.ag.loy.adm.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.ag.loy.adm.entity.FeatureMaster;
import com.ag.loy.adm.entity.ProductMaster;

public interface FeatureMasterService {

	public void saveFeature(FeatureMaster sdt);

	public List<FeatureMaster> getAllFeatures(String corpId);

	public FeatureMaster getAllFeatures(String featureID, String corpId);

	public void updateFeatureMaster(FeatureMaster sdt);

	public String fetchMaxId();

}