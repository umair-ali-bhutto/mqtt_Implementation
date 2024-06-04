package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.ContactUs;
import com.ag.mportal.services.ContactUsService;

@Service
public class ContactUsServiceImpl implements ContactUsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ContactUs> fetchContact(String corpId) {
		// TODO Auto-generated method stub
		try {
		List<ContactUs> con = new ArrayList<ContactUs>();
		Query cb = entityManager.createNamedQuery("ContactUs.fetchActiveContact");
		cb.setParameter("corpId",corpId );
		con = (List<ContactUs>) cb.getResultList();
		return con;
		} catch (NoResultException nre) {
			return null;
		}
	}

}
