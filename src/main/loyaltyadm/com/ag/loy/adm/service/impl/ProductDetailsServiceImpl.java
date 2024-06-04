package com.ag.loy.adm.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.loy.adm.entity.ProductDetails;
import com.ag.loy.adm.service.ProductDetailsService;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Long saveProductDetails(ProductDetails sdt) {
		try {
			entityManager.persist(sdt);
			return Long.parseLong(sdt.getProductId());
		} catch (Exception nre) {
			return 0l;
		}
	}

}