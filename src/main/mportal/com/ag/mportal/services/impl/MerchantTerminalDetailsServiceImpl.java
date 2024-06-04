package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAG;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.model.MerchantTerminalDetailsModel;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.MerchantTerminalDetailsService;

@Service
public class MerchantTerminalDetailsServiceImpl implements MerchantTerminalDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(MerchantTerminalDetail tcn) {
		try {
			boolean exists = UtilAccess.getMerchantTerminalDetailsConfigList().stream().anyMatch(
					mdl -> tcn.getDataName().equals(mdl.getDataName()) && tcn.getType().equals(mdl.getType()));
			if (exists) {
				savetoMerchantTerminalDetails(tcn);
			} else {
				AgLogger.logInfo("Unable To Insert Into MerchantTerminalDetails With DataName:" + tcn.getDataName()
						+ " And Type:" + tcn.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void savetoMerchantTerminalDetails(MerchantTerminalDetail mtd) {
		try {
			entityManager.persist(mtd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MerchantTerminalDetailsModel> listMerchantTerminalDetails(int txnId) {
		List<MerchantTerminalDetailsModel> m = new ArrayList<MerchantTerminalDetailsModel>();
		try {
			String query = AppProp.getProperty("merchant.txn.details");
			String whereClaue = " WHERE txn_logs_id = " + txnId;
			query = query.replaceAll("@PARAMLOGID", whereClaue);
			AgLogger.logInfo("query ", query);
			Query cb = entityManager.createNativeQuery(query);
			List<Object[]> obj = cb.getResultList();
			for (Object[] o : obj) {
				MerchantTerminalDetailsModel k = new MerchantTerminalDetailsModel();
				k.setID(o[0].toString().toUpperCase());
				k.setVALUE(o[1].toString().toUpperCase());
				m.add(k);
			}
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ReportModel txnDetails(int txnId) {

		try {
			Query cb = entityManager.createNamedQuery("TxnDetail.fetchAllById").setParameter("id", txnId);
			TxnDetail l = (TxnDetail) cb.getSingleResult();

			ReportModel rm = new ReportModel();

			rm.setTxnDetailId(Objects.isNull(l.getId()) ? "" : String.valueOf(l.getId()));
			rm.setType(Objects.isNull(l.getType()) ? "" : l.getType());
			rm.setBin(Objects.isNull(l.getBin()) ? "" : l.getBin());
			rm.setResponse(Objects.isNull(l.getResponse()) ? "" : l.getResponse());
			rm.setEntryDate(Objects.isNull(l.getEntryDate()) ? "" : l.getEntryDate().toString());
			rm.setStatus(Objects.isNull(l.getStatus()) ? "" : l.getStatus());
			rm.setMerchantId(Objects.isNull(l.getMerchantId()) ? "" : l.getMerchantId());
			rm.setTerminalId(Objects.isNull(l.getTerminalId()) ? "" : l.getTerminalId());
			rm.setTxnAmount(Objects.isNull(l.getAmount()) ? "" : l.getAmount());
			rm.setTxnId(Objects.isNull(l.getTransactionId()) ? "" : l.getTransactionId());
			rm.setBatchNumber(Objects.isNull(l.getBatchNumber()) ? "" : l.getBatchNumber());
			rm.setReversal(Objects.isNull(l.getReversal()) ? "" : l.getReversal());
			rm.setSetteled(Objects.isNull(l.getSetteled()) ? "" : l.getSetteled());
			rm.setSetteledDate(Objects.isNull(l.getSetteledDate()) ? "" : l.getSetteledDate().toString());
			rm.setPaid(Objects.isNull(l.getPaid()) ? "" : l.getPaid());
			rm.setPaidDate(Objects.isNull(l.getPaidDate()) ? "" : l.getPaidDate().toString());
			rm.setResultStatus(Objects.isNull(l.getResultStatus()) ? "" : l.getResultStatus());
			rm.setRefNum(Objects.isNull(l.getRefNum()) ? "" : l.getRefNum());
			rm.setCardCcType(Objects.isNull(l.getCardCcType()) ? "" : l.getCardCcType());
			rm.setReversalDate(Objects.isNull(l.getReversalDate()) ? "" : l.getReversalDate().toString());
			rm.setBatchSettleRecords(
					Objects.isNull(l.getBatchSettleRecords()) ? "" : String.valueOf(l.getBatchSettleRecords()));
			rm.setMsg(Objects.isNull(l.getMsg()) ? "" : l.getMsg());
			rm.setAuthId(Objects.isNull(l.getAuthId()) ? "" : l.getAuthId());
			rm.setOrgRefNumber(Objects.isNull(l.getOrignalRef()) ? "" : l.getOrignalRef());
			rm.setInvoiceNumber(Objects.isNull(l.getInvoiceNum()) ? "" : l.getInvoiceNum());
			rm.setPosEntryMode(Objects.isNull(l.getPosEntryMode()) ? "" : l.getPosEntryMode());
			rm.setCustomerId(Objects.isNull(l.getCustId()) ? "" : String.valueOf(l.getCustId()));
			rm.setModel(Objects.isNull(l.getModel()) ? "" : l.getModel());
			rm.setScheme(Objects.isNull(l.getScheme()) ? "" : l.getScheme());
			rm.setTxnSerialNumber(Objects.isNull(l.getSerialNumber()) ? "" : l.getSerialNumber());
			rm.setAuthIdN(Objects.isNull(l.getAuthIdN()) ? "" : l.getAuthIdN());
			rm.setFieldOne(Objects.isNull(l.getFieldOne()) ? "" : l.getFieldOne());
			rm.setSettlementResponse(Objects.isNull(l.getSettelmentResponse()) ? "" : l.getSettelmentResponse());
			rm.setReason(Objects.isNull(l.getReason()) ? "" : l.getReason());
			rm.setAdjAmount(Objects.isNull(l.getAdjAmount()) ? "" : l.getAdjAmount());
			rm.setQuantityType(Objects.isNull(l.getQuantityType()) ? "" : l.getQuantityType());
			rm.setPrdName(Objects.isNull(l.getPrdName()) ? "" : l.getPrdName());
			rm.setPrdCode(Objects.isNull(l.getPrdCode()) ? "" : l.getPrdCode());
			rm.setTipAmount(Objects.isNull(l.getTipAmount()) ? "" : l.getTipAmount());
			rm.setQuantity(Objects.isNull(l.getQuantity()) ? "" : String.valueOf(l.getQuantity()));
			rm.setCurrency(Objects.isNull(l.getCurrency()) ? "" : l.getCurrency());
			rm.setMdrOnUs(Objects.isNull(l.getMdrOnUs()) ? "" : l.getMdrOnUs().toString());
			rm.setMdrOffUs(Objects.isNull(l.getMdrOffUs()) ? "" : l.getMdrOffUs().toString());
			rm.setFedRate(Objects.isNull(l.getFedRates()) ? "" : String.valueOf(l.getFedRates()));
			rm.setNetAmount(Objects.isNull(l.getNetAmount()) ? "" : l.getNetAmount());
			rm.setTvr(Objects.isNull(l.getTvr()) ? "" : l.getTvr());
			rm.setAid(Objects.isNull(l.getAid()) ? "" : l.getAid());
			rm.setCustomerName(Objects.isNull(l.getCustomerName()) ? "" : l.getCustomerName());
			rm.setCardType(Objects.isNull(l.getCardType()) ? "" : l.getCardType());
			rm.setCardExpiry(Objects.isNull(l.getCardExpiry()) ? "" : l.getCardExpiry().toString());
			rm.setPaymentDate(Objects.isNull(l.getPaymentDate()) ? "" : l.getPaymentDate().toString());

			try {
				PosEntryModeConfig posModeConfig = UtilAG.getPemConfigMap().get(l.getPosEntryMode());
				if (!Objects.isNull(posModeConfig)) {
					rm.setPemValue(posModeConfig.getPosEntryValue());
				} else {
					rm.setPemValue("N/A");
				}
			} catch (Exception e) {
				AgLogger.logerror(getClass(), "setPemValue Error||", e);
				rm.setPemValue("N/A");
			}

			return rm;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
