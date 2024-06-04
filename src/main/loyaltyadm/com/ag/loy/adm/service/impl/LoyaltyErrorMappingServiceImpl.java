package com.ag.loy.adm.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.loy.adm.entity.AccountMaster;
import com.ag.loy.adm.entity.LoyaltyErrorMapping;
import com.ag.loy.adm.service.LoyaltyErrorMappingService;

@Service
public class LoyaltyErrorMappingServiceImpl implements LoyaltyErrorMappingService{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String fetchErrorMessage(String code) {
		String message = "N/A";
		try {
			Query query = entityManager.createNamedQuery("LoyaltyErrorMapping.fetchByCode", LoyaltyErrorMapping.class);
			query.setParameter("code", code);
			LoyaltyErrorMapping lst = (LoyaltyErrorMapping) query.getSingleResult();
			message = lst.getMessage();
		} catch (NoResultException e) {
			message = AppProp.getProperty("default.loy.error.mapping");
		}
		return message;
	}
	

}
