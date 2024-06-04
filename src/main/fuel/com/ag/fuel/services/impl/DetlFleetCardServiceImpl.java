package com.ag.fuel.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.fuel.entity.DetlFleetCard;
import com.ag.fuel.services.DetlFleetCardService;

@Service
public class DetlFleetCardServiceImpl implements DetlFleetCardService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public Boolean insert(DetlFleetCard detlfleetcard) {
		try {
			entityManager.persist(detlfleetcard);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	@Transactional
	public Boolean update(DetlFleetCard detlfleetcard) {
		try {
			entityManager.merge(detlfleetcard);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public List<DetlFleetCard> findAll() {
		try {
			Query query = entityManager.createNamedQuery("DetlFleetCard.findAll",DetlFleetCard.class);
			@SuppressWarnings("unchecked")
			List<DetlFleetCard> list = query.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
		
	}

    @Override
	@Transactional
	public Boolean insertList(List<DetlFleetCard> lst) {
		try {
            for(DetlFleetCard detlfleetcard : lst){
                entityManager.persist(detlfleetcard);        
            }
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

    @Override
	public DetlFleetCard findById(Integer id) {
		try {
			Query query = entityManager.createNamedQuery("DetlFleetCard.findById",DetlFleetCard.class);
            query.setParameter("id", id);
			return (DetlFleetCard) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
	}

}
