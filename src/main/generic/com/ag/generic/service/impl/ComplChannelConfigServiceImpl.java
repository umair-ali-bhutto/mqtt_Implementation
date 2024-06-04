package com.ag.generic.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ComplChannelConfig;
import com.ag.generic.service.ComplChannelConfigService;
import com.ag.generic.util.AgLogger;

@Service
public class ComplChannelConfigServiceImpl implements ComplChannelConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long insert(ComplChannelConfig tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public ComplChannelConfig fetchByID(ComplChannelConfig tcn) {
		return null;
	}

	@Override
	public List<ComplChannelConfig> fetchByOtherParams(ComplChannelConfig tcn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams category:" + tcn.getCategory()
					+ " type:" + tcn.getType() + " subType:" + tcn.getSubType() + " corpId:" + tcn.getCorpId());
			Query cb = entityManager.createNamedQuery("ComplChannelConfig.fetchAllByOtherParams")
					.setParameter("category", tcn.getCategory()).setParameter("type", tcn.getType())
					.setParameter("subType", tcn.getSubType()).setParameter("corpId", tcn.getCorpId());

			return (List<ComplChannelConfig>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> fetchAll() {
		List<String> str = new ArrayList<String>();
		try {

			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");

			Query cb = entityManager.createNamedQuery("ComplChannelConfig.fetchAll");
			List<ComplChannelConfig> l = (List<ComplChannelConfig>) cb.getResultList();
			for (ComplChannelConfig c : l) {
				str.add(c.getChannel());
			}
			List<String> listWithoutDuplicates = str.stream().distinct().collect(Collectors.toList());

			str = listWithoutDuplicates;
			return str;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplChannelConfig> fetchAllByID(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
			Query cb = entityManager.createNamedQuery("ComplChannelConfig.fetchAllByID").setParameter("id", id);
			return (List<ComplChannelConfig>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(ComplChannelConfig id) {
		try {
			entityManager.merge(id);
		} catch (NoResultException nre) {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplChannelConfig> fetchAllReq() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
			Query cb = entityManager.createNamedQuery("ComplChannelConfig.fetchAll");
			return (List<ComplChannelConfig>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
