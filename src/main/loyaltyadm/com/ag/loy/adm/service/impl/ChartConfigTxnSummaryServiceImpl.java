package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.loy.adm.entity.ChartConfigTxnSummary;
import com.ag.loy.adm.service.ChartConfigTxnSummaryService;

@Service
public class ChartConfigTxnSummaryServiceImpl implements ChartConfigTxnSummaryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ChartConfigTxnSummary> fetchAllByCorpId(String corpId) {
		List<ChartConfigTxnSummary> lst = new ArrayList<ChartConfigTxnSummary>();
		try {
			Query cb = entityManager.createNamedQuery("ChartConfigTxnSummary.fetchAllByCorpId",
					ChartConfigTxnSummary.class);
			cb.setParameter("corpId", corpId);

			lst = (List<ChartConfigTxnSummary>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

}
