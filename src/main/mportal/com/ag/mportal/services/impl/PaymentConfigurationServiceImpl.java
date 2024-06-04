package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.Paymentconfiguration;
import com.ag.mportal.services.PaymentConfigurationService;

@Service
public class PaymentConfigurationServiceImpl implements PaymentConfigurationService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Paymentconfiguration> retrieveAllPaymentConfigration(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("Paymentconfiguration.getPaymentconfiguration").setParameter("corpId", corpId)
					.setParameter("isActive", 1);
			return (List<Paymentconfiguration>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Paymentconfiguration retrievePaymentConfigByDay(Integer dayNumber,String corpId) {

		try {
			Query cb = entityManager.createNamedQuery("Paymentconfiguration.getPaymentconfigurationByDay")
					.setParameter("isActive", 1).setParameter("day", dayNumber).setParameter("corpId", corpId);
			return (Paymentconfiguration) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
