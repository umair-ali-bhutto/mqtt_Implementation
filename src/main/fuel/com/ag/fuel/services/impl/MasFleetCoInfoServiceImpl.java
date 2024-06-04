package com.ag.fuel.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.fuel.entity.MasFleetCoInfo;
import com.ag.fuel.services.MasFleetCoInfoService;

@Service
public class MasFleetCoInfoServiceImpl implements MasFleetCoInfoService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public Boolean insert(MasFleetCoInfo masfleetcoinfo) {
		try {
			entityManager.persist(masfleetcoinfo);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	@Transactional
	public Boolean update(MasFleetCoInfo masfleetcoinfo) {
		try {
			entityManager.merge(masfleetcoinfo);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public List<MasFleetCoInfo> findAll() {
		try {
			Query query = entityManager.createNamedQuery("MasFleetCoInfo.findAll",MasFleetCoInfo.class);
			List<MasFleetCoInfo> list = query.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
		
	}

    @Override
	@Transactional
	public Boolean insertList(List<MasFleetCoInfo> lst) {
		try {
            for(MasFleetCoInfo masfleetcoinfo : lst){
                entityManager.persist(masfleetcoinfo);        
            }
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

    @Override
	public MasFleetCoInfo findById(Integer id) {
		try {
			Query query = entityManager.createNamedQuery("MasFleetCoInfo.findById",MasFleetCoInfo.class);
            query.setParameter("id", id);
			return (MasFleetCoInfo) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
	}

}
