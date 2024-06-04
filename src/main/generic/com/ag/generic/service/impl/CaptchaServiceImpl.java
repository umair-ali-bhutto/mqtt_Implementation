package com.ag.generic.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.Captcha;
import com.ag.generic.service.CaptchaService;
import com.ag.generic.util.AgLogger;



@Service
public class CaptchaServiceImpl implements CaptchaService {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	@Transactional
	public long insert(Captcha adt) {
		try {
			entityManager.persist(adt);
			return adt.getId();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return 0l;
		}

	}

	@Override
	public Captcha validateCaptcha(int captchaId,String uuid) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetch Captcha");
			AgLogger.logDebug(",,,,", captchaId+"...."+uuid);
			Query cb = entityManager.createNamedQuery("Captcha.retrieveAll").setParameter("id", captchaId).setParameter("uuid", uuid);
			return (Captcha) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void updateCaptcha(Captcha captchaId) {
		try {
			entityManager.merge(captchaId);
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void updateAll(String uuid) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec Update fetch Captcha");
			Query cb = entityManager.createNamedQuery("Captcha.update").setParameter("uuid", uuid);
			AgLogger.logDebug("...", uuid);
			cb.executeUpdate();
		} catch (NoResultException nre) {
			
		}
		
	}

}
