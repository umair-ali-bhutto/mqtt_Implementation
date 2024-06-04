package com.ag.fuel.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.fuel.entity.Eftloghisttbl;
import com.ag.fuel.services.EftloghisttblService;

@Service
public class EftloghisttblServiceImpl implements EftloghisttblService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public Boolean insert(Eftloghisttbl eftloghisttbl) {
		try {
			entityManager.persist(eftloghisttbl);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	@Transactional
	public Boolean update(Eftloghisttbl eftloghisttbl) {
		try {
			entityManager.merge(eftloghisttbl);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public List<Eftloghisttbl> findAll() {
		try {
			Query query = entityManager.createNamedQuery("Eftloghisttbl.findAll",Eftloghisttbl.class);
			@SuppressWarnings("unchecked")
			List<Eftloghisttbl> list = query.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
		
	}

    @Override
	@Transactional
	public Boolean insertList(List<Eftloghisttbl> lst) {
		try {
            for(Eftloghisttbl eftloghisttbl : lst){
                entityManager.persist(eftloghisttbl);        
            }
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

    @Override
	public Eftloghisttbl findById(Integer id) {
		try {
			Query query = entityManager.createNamedQuery("Eftloghisttbl.findById",Eftloghisttbl.class);
            query.setParameter("id", id);
			return (Eftloghisttbl) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
	}

}
