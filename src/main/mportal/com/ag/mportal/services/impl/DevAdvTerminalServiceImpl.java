package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DevAdvTerminal;
import com.ag.mportal.services.DevAdvTerminalService;

@Service
public class DevAdvTerminalServiceImpl implements DevAdvTerminalService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DevAdvTerminal> fetchAll() {
		List<DevAdvTerminal> lst = new ArrayList<DevAdvTerminal>();
		try {
			Query cb = entityManager.createNamedQuery("DevAdvTerminal.fetchAll");
			lst = (List<DevAdvTerminal>) cb.getResultList();
			AgLogger.logDebug("", "Query: " + cb);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@Override
	@Transactional
	public boolean insert(DevAdvTerminal DevAdvTerminal) {
		try {

			entityManager.flush();
			entityManager.clear();
			entityManager.persist(DevAdvTerminal);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public void update(DevAdvTerminal DevAdvTerminal) {
		try {
			entityManager.merge(DevAdvTerminal);
		} catch (Exception e) {
		}
	}

}
