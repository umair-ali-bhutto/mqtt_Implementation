package com.ag.mportal.services;

import java.util.List;
import com.ag.mportal.entity.PreauthMerchant;
public interface PreauthMerchantService {
	
	public boolean insert(PreauthMerchant preauthMerchant);
	public List<PreauthMerchant> fetchData(String mId,String preauthId);
	public PreauthMerchant fetchByID(long id);
	public PreauthMerchant fetchByIDActive(long id);


	public boolean update(PreauthMerchant preauthMerchant);
	

}
