package com.ag.generic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ConfigProperty;
import com.ag.generic.service.ConfigPropertiesService;

@Service
public class ConfigPropertiesServiceImpl implements ConfigPropertiesService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigProperty> SelectAllConfig() {
		try {
		List<ConfigProperty> user = new ArrayList<ConfigProperty>();
		Query cb = entityManager.createNamedQuery("ConfigProperty.findAll");
		user = (List<ConfigProperty>) cb.getResultList();
		return user;
		} catch (NoResultException nre) {
			return null;
		}
	}
	@Override
	public List<ConfigProperty> SelectConfigByGroup(String group) {
		try {
		List<ConfigProperty> user = new ArrayList<ConfigProperty>();
		Query cb = entityManager.createNamedQuery("ConfigProperty.findByGroup").setParameter("group", group);
		user = (List<ConfigProperty>) cb.getResultList();
		return user;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public HashMap<String, String> SelectAllConfigMap() {
		try {
			List<ConfigProperty> user = new ArrayList<ConfigProperty>();
			Query cb = entityManager.createNamedQuery("ConfigProperty.findAll");
			user = (List<ConfigProperty>) cb.getResultList();
			HashMap<String, String> mp = new HashMap<String, String>();
			for(ConfigProperty c:user) {
				mp.put(c.getPropKey(), c.getValue());
			}
			return mp;
			} catch (NoResultException nre) {
				return null;
			}
	}

}
