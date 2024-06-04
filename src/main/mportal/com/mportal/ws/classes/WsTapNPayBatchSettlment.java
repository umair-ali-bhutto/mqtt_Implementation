package com.mportal.ws.classes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TapNPayBatchConfig;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.entity.TapNPayTransactions;
import com.ag.mportal.model.BatchSettlementResponseModel;
import com.ag.mportal.model.BatchSettlmentViewModel;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.services.TapNPayBatchConfigService;
import com.ag.mportal.services.TapNPayTransactionService;
import com.ag.mportal.util.ISO8583Util;
import com.ag.mportal.util.String_to_bcd;
import com.ag.mportal.util.TapNPayUtil;

@Component("com.mportal.ws.classes.WsTapNPayBatchSettlment")
public class WsTapNPayBatchSettlment implements Wisher {

	@Autowired
	TapNPayTransactionService tapNPayTransactionService;

	@Autowired
	TapNPayBatchConfigService tapNPayBatchConfigService;

	@Autowired
	ISO8583Util iSO8583Util;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			TapNPayRoutingConfig trConfig = TapNPayUtil.getRouting(rm.getCorpId(), "BATCH_STMT", "-");
			if (trConfig != null) {
				String mid = rm.getAdditionalData().get("MID").toString();
				String tid = rm.getAdditionalData().get("TID").toString();
				List<BatchSettlmentViewModel> lst = tapNPayTransactionService.fetchUnSettledTxn(mid, tid);
				if (lst.size() != 0) {
					List<TapNPayTransactions> lstM = tapNPayTransactionService.fetchUnSettledTxnUpdate(mid, tid);
					TapNPayBatchConfig btc = tapNPayBatchConfigService.fetchBatchConfig(mid, tid);
					if (btc != null) {
						String[] result = fetchTotalTxn(lst);
						String totalTxnSum = result[1];
						String toalTxnCount = result[0];

						ISO8583Model mRequest = doProcessBatch(btc, trConfig, toalTxnCount, totalTxnSum, mid, tid);
						if (mRequest != null) {
							ISO8583Model responseIso = iSO8583Util.doProcess(mRequest);
							AgLogger.logInfo(responseIso.getCode() + "|" + responseIso.getMessage());
							response.setCode(responseIso.getCode());
							response.setMessage(responseIso.getMessage());
							if (responseIso.getCode().equals("00")) {
								SimpleDateFormat sdfTimeFormat = new SimpleDateFormat("HH:mm:ss");
								SimpleDateFormat sdfDateFormat = new SimpleDateFormat("dd/MM/yyyy");
								response.setCode("0000");
								response.setMessage("SUCCESS");
								BatchSettlementResponseModel btm = new BatchSettlementResponseModel();
								btm.setSettledAmount(totalTxnSum);
								btm.setSettledCount(toalTxnCount);
								btm.setSettledDate(sdfDateFormat.format(new java.util.Date().getTime()));
								btm.setSettledTime(sdfTimeFormat.format(new java.util.Date().getTime()));

								// Update Data To Settled.
								for (TapNPayTransactions t : lstM) {
									t.setIsSettled("Y");
									t.setSettledDate(new Timestamp(new java.util.Date().getTime()));
									tapNPayTransactionService.update(t);
								}

								// Update Batch Number
								int batchNumberDefault = btc.getBatchNumber() + 1;
								btc.setBatchNumber(batchNumberDefault);
								tapNPayBatchConfigService.updateBatch(btc);

								HashMap<Object, Object> obj = new HashMap<Object, Object>();
								obj.put("response", btm);
								response.setData(obj);
							} else {
								response.setCode("0002");
								response.setMessage(TapNPayUtil.getIsoErrorCode(responseIso.getCode()));
							}

						} else {
							response.setCode("0003");
							response.setMessage("Invalid Host Request.");
						}

					} else {
						response.setCode("0001");
						response.setMessage("No Batch Cofiguration Found.");
					}
				} else {
					response.setCode("0001");
					response.setMessage("No Record For Settlment.");
				}
			} else {
				response.setCode("9993");
				response.setMessage("No Configuration Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public ISO8583Model doProcessBatch(TapNPayBatchConfig btc, TapNPayRoutingConfig routing, String countTxn,
			String totalAmount, String mid, String tid) {
		try {

			String peckagerPath = routing.getIsoPeckagerPath();
			boolean loggerEnable = Boolean.parseBoolean(routing.getLogger());
			String nacIp = routing.getNacIp();
			int nacPort = Integer.parseInt(routing.getNacPort());
			String isoTpdu = routing.getIsoTpdu();

			// Configuration Values
			String f0 = routing.getIsoMessage();
			String f3 = routing.getIsoTxnType(); // To be defined by HOST ITS SALE TXN
			String f11 = StringUtils.leftPad(tapNPayTransactionService.getStanSequence() + "", 6, "0"); // STAN
			String f24 = isoTpdu.substring(2, 6); // NII FROM TPDU
			String f41 = tid; // TID FROM APP
			String f42 = mid; // MID FROM APP
			String f60 = StringUtils.leftPad(btc.getBatchNumber() + "", 6, "0"); // BATCH NUMBER
			String f63 = "0000000000000000000000000000000000000000000000000000000000"; // DATA OF COUNT AND SUM

			AgLogger.logInfo(
					"LENGHT " + String_to_bcd.convert("00" + f63.length() + Hex.encodeHexString(f63.getBytes())));

			ISOMsg mRequest = new ISOMsg();
			GenericPackager packager = new GenericPackager(peckagerPath);
			mRequest.setPackager(packager);
			mRequest.set(0, f0);
			mRequest.set(3, String_to_bcd.convert(f3));
			mRequest.set(11, String_to_bcd.convert(f11));
			mRequest.set(24, String_to_bcd.convert(f24));
			mRequest.set(41, f41);
			mRequest.set(42, f42);
			mRequest.set(60, String_to_bcd.convert("000" + f60.length() + Hex.encodeHexString(f60.getBytes())));
			mRequest.set(63, String_to_bcd.convert("00" + f63.length() + Hex.encodeHexString(f63.getBytes())));

			byte[] data = mRequest.pack();
			String isoMessageDataWithoutField55 = TapNPayUtil.formatByte(data, false, false);
			mRequest.setHeader(String_to_bcd.convert(isoTpdu + isoMessageDataWithoutField55));

			ISO8583Model mdl = new ISO8583Model();
			mdl.setHeader(isoTpdu);
			mdl.setIsoMessageRequest(mRequest);
			mdl.setLoggerEnable(loggerEnable);
			mdl.setNacIp(nacIp);
			mdl.setNacPort(nacPort);
			mdl.setPeckager(peckagerPath);
			return mdl;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	private String[] fetchTotalTxn(List<BatchSettlmentViewModel> lst) {
		String[] s = new String[2];
		s[0] = "0";
		s[1] = "0.0";
		for (BatchSettlmentViewModel b : lst) {
			if (b.getTxnType().equals("Total")) {
				s[0] = b.getTxnCount();
				s[1] = b.getTnxAmount();
				return s;
			}
		}
		return s;
	}

}