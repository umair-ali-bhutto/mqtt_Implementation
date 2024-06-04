package com.ag.fuel.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.fuel.entity.MasFleetBill;
import com.ag.fuel.services.MasFleetBillService;

@Service
public class MasFleetBillServiceImpl implements MasFleetBillService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public Boolean insert(MasFleetBill masfleetbill) {
		try {
			entityManager.persist(masfleetbill);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	@Transactional
	public Boolean update(MasFleetBill masfleetbill) {
		try {
			entityManager.merge(masfleetbill);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public List<MasFleetBill> findAll() {
		try {
			Query query = entityManager.createNamedQuery("MasFleetBill.findAll",MasFleetBill.class);
			List<MasFleetBill> list = query.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
		
	}

    @Override
	@Transactional
	public Boolean insertList(List<MasFleetBill> lst) {
		try {
            for(MasFleetBill masfleetbill : lst){
                entityManager.persist(masfleetbill);        
            }
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

    @Override
	public MasFleetBill findById(Integer id) {
		try {
			Query query = entityManager.createNamedQuery("MasFleetBill.findById",MasFleetBill.class);
            query.setParameter("id", id);
			return (MasFleetBill) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
	}

}
