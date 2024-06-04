package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.QrPayments;
import com.ag.mportal.services.QrPaymentsService;

@Service
public class QrPaymentsServiceImpl implements QrPaymentsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public QrPayments fetch(String mid, String tid,String serialNum) {

		try {
			Query cb = entityManager.createNamedQuery("QrPayments.retrieveUnpaidQrPayment").setParameter("mid", mid)
					.setParameter("tid", tid).setParameter("serialNumber", serialNum);
			return (QrPayments) cb.getSingleResult();
		} catch (Exception nre) {
			return null;
		}

	}

	@Transactional
	@Override
	public boolean update(QrPayments qrPayment) {
		try {
			entityManager.merge(qrPayment);
			return true;
		} catch (Exception nre) {
			return false;
		}

	}

	@Transactional
	@Override
	public boolean save(QrPayments sdt) {
		try {
			entityManager.persist(sdt);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
