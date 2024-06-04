package com.ag.generic.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.service.AuditLogService;

@Service
public class AuditLogServiceImpl implements AuditLogService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insertAuditLog(AuditLog adt) {
		try {
			entityManager.merge(adt);
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}

}
