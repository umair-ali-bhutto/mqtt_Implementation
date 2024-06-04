package com.ag.mportal.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.TapNPayTransactions;
import com.ag.mportal.model.BatchSettlmentViewModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.services.TapNPayTransactionService;
import com.google.gson.Gson;

@Service
public class TapNPayTransactionServiceImpl implements TapNPayTransactionService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insertBatch(TapNPayTransactions tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public BigDecimal getStanSequence() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNativeQuery("select TAPNPAY_STAN_SEQ.nextval from dual");
			return (BigDecimal) cb.getSingleResult();
		} catch (NoResultException nre) {
			return new BigDecimal(0);
		}
	}

	@Override
	public BigDecimal getInvoiceNumberSequence() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNativeQuery("select TAPNPAY_INVOICE_SEQ.nextval from dual");
			return (BigDecimal) cb.getSingleResult();
		} catch (NoResultException nre) {
			return new BigDecimal(0);
		}
	}

	@Override
	public TapNPayTransactions fetchTxnByRecId(long recId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("TapNPayTransactions.fetchByID").setParameter("id", recId);
			return (TapNPayTransactions) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

	}

	@Override
	public List<TapNPayTransactions> fetchTxnByMidTidForViewAll(String mid, String tid) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByMidTidForViewAll");
			Query cb = entityManager.createNamedQuery("TapNPayTransactions.fetchTxnByMidTidForViewAll")
					.setParameter("mid", mid).setParameter("tid", tid);
			return (List<TapNPayTransactions>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<TapNPayTransactions> fetchTxnByMidTidForVoid(String mid, String tid) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByMidTid");
			Query cb = entityManager.createNamedQuery("TapNPayTransactions.fetchTxnByMidTidForVoid")
					.setParameter("mid", mid).setParameter("tid", tid);
			return (List<TapNPayTransactions>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<BatchSettlmentViewModel> fetchUnSettledTxn(String mid, String tid) {
		List<BatchSettlmentViewModel> data = new ArrayList<BatchSettlmentViewModel>();
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByIsSettled");
			Query cb = entityManager.createNamedQuery("TapNPayTransactions.fetchUnSettledTxn").setParameter("mid", mid)
					.setParameter("tid", tid);
			List<Object[]> lst = (List<Object[]>) cb.getResultList();
			boolean isDataExist = false;
			for (Object[] o : lst) {
				isDataExist = true;
				BatchSettlmentViewModel s = new BatchSettlmentViewModel();
				s.setTnxAmount(o[1] + "");
				s.setTxnCount(o[0] + "");
				s.setTxnType(o[2] + "");
				data.add(s);
			}

			if (isDataExist) {
				String[] prop = AppProp.getProperty("tap.n.pay.batch.stmt.types").split(",");
				for (String sk : prop) {
					if (!isExistType(data, sk)) {
						BatchSettlmentViewModel s = new BatchSettlmentViewModel();
						s.setTnxAmount("0");
						s.setTxnCount("0");
						s.setTxnType(sk);
						data.add(s);
					}
				}
			}
			
			if(isDataExist) {
				Map<String, TxnSummary> map = new HashMap<String, TxnSummary>();
				for(BatchSettlmentViewModel gh:data) {
					TxnSummary temp = null;
					switch (gh.getTxnType()) {
					case "SALE":
						temp = new TxnSummary();
						temp.setTxnType("SALE");
						temp.setCnt(Integer.parseInt(gh.getTxnCount()));
						temp.setAmount(Float.parseFloat(gh.getTnxAmount()));
						map.put("SALE", temp);
						break;
					case "VOID":
						temp = new TxnSummary();
						temp.setTxnType("VOID");
						temp.setCnt(Integer.parseInt(gh.getTxnCount()));
						temp.setAmount(Float.parseFloat(gh.getTnxAmount()));
						map.put("VOID", temp);
						break;
					case "PRE AUTH":
						temp = new TxnSummary();
						temp.setTxnType("PRE AUTH");
						temp.setCnt(Integer.parseInt(gh.getTxnCount()));
						temp.setAmount(Float.parseFloat(gh.getTnxAmount()));
						map.put("PRE AUTH", temp);
						break;
					case "COMPLETION":
						temp = new TxnSummary();
						temp.setTxnType("COMPLETION");
						temp.setCnt(Integer.parseInt(gh.getTxnCount()));
						temp.setAmount(Float.parseFloat(gh.getTnxAmount()));
						map.put("COMPLETION", temp);
						break;
					

					default:
						break;
					}
				}
				
				AgLogger.logInfo(new Gson().toJson(map)+"...........");
				Integer totalCount = UtilAccess.getCount(map, AppProp.getProperty("calc.total.count.txn.summary"));
				int totalCountFin = (!Objects.isNull(totalCount) ? totalCount : 0);

				Float totalAmount = UtilAccess.getAmount(map, AppProp.getProperty("calc.total.amount.txn.summary"));
				float totalSum = (!Objects.isNull(totalAmount) ? totalAmount : 0f);
				
				BatchSettlmentViewModel s = new BatchSettlmentViewModel();
				s.setTnxAmount(totalSum+"");
				s.setTxnCount(totalCountFin+"");
				s.setTxnType("Total");
				data.add(s);
			}
			return data;
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	
	@Override
	public List<TapNPayTransactions> fetchUnSettledTxnUpdate(String mid, String tid) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByIsSettled");
			Query cb = entityManager.createNamedQuery("TapNPayTransactions.fetchUnSettledTxnUpdate").setParameter("mid", mid)
					.setParameter("tid", tid);
			List<TapNPayTransactions> lst = (List<TapNPayTransactions>) cb.getResultList();
			return lst;
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	

	private boolean isExistType(List<BatchSettlmentViewModel> ls, String type) {
		for (BatchSettlmentViewModel m : ls) {
			if (m.getTxnType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public void update(TapNPayTransactions tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}

	}

	@Override
	public List<TapNPayTransactions> validateAuthIdByMidTid(String mid, String tid, String authId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From validateAuthIdByMidTid");
			Query cb = entityManager.createNamedQuery("TapNPayTransactions.validateAuthIdByMidTid")
					.setParameter("mid", mid).setParameter("tid", tid).setParameter("authId",authId);
			return (List<TapNPayTransactions>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public TapNPayTransactions fetchTxnByRecIdForReversal(long recId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByIDForReversal");
			Query cb = entityManager.createNamedQuery("TapNPayTransactions.fetchTxnByRecIdForReversal").setParameter("id", recId);
			return (TapNPayTransactions) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}