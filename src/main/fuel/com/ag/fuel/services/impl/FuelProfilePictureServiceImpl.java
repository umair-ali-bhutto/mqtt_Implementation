package com.ag.fuel.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.fuel.entity.FuelProfilePicture;
import com.ag.fuel.services.FuelProfilePictureService;

@Service
public class FuelProfilePictureServiceImpl implements FuelProfilePictureService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(FuelProfilePicture fuelProfilePicture) {
		try {
			entityManager.persist(fuelProfilePicture);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void update(FuelProfilePicture fuelProfilePicture) {
		try {
			entityManager.merge(fuelProfilePicture);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public FuelProfilePicture findByUserId(int userId) {
		FuelProfilePicture mdl = new FuelProfilePicture();
		try {
			Query query = entityManager.createNamedQuery("FuelProfilePicture.fetchByUserId", FuelProfilePicture.class);
			query.setParameter("userId", userId);
			mdl = (FuelProfilePicture) query.getSingleResult();
		} catch (NoResultException nre) {
			mdl = null;
		}
		return mdl;
	}

}
