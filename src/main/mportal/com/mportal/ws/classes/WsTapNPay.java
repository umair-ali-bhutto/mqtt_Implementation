package com.mportal.ws.classes;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.TapNPayBatchConfig;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.entity.TapNPaySaf;
import com.ag.mportal.entity.TapNPayTransactions;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.model.TapNPayModel;
import com.ag.mportal.model.TapNPayResponseModel;
import com.ag.mportal.services.TapNPayBatchConfigService;
import com.ag.mportal.services.TapNPaySafService;
import com.ag.mportal.services.TapNPayTransactionService;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.services.WisherTap;
import com.ag.mportal.util.EmvCardScheme;
import com.ag.mportal.util.ISO8583Util;
import com.ag.mportal.util.TapNPayEncDecUtil;
import com.ag.mportal.util.TapNPayUtil;
import com.google.gson.Gson;

@Component("com.mportal.ws.classes.WsTapNPay")
public class WsTapNPay implements Wisher {

	@Autowired
	private ApplicationContext context;

	@Autowired
	ISO8583Util iSO8583Util;

	@Autowired
	TapNPayTransactionService tapNPayTransactionService;

	UtilAccess utilAccess;

	@Autowired
	TapNPayBatchConfigService tapNPayBatchConfigService;

	@Autowired
	UtilService utilService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	TapNPaySafService tapNPaySafService;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			WisherTap wisher = null;
			if (rm.getAdditionalData().containsKey("request")) {
				AgLogger.logDebug(rm.getUserid(), rm.getAdditionalData().get("request").toString());
				TapNPayModel tap = new Gson().fromJson(rm.getAdditionalData().get("request").toString(),
						TapNPayModel.class);
				EmvCardScheme e = TapNPayUtil.findCardScheme(tap.getFciModel().getAid(), null);
				if (tap.getCode().equals("0000")) {
					if (e != null) {
						TapNPayRoutingConfig trConfig = TapNPayUtil.getRouting(rm.getCorpId(),
								tap.getTransactionTypeField(), e.getName());
						if (trConfig != null) {
							wisher = setupClass(trConfig.getRouterClass());
							response = doCallClass(rm, wisher, tap, trConfig);
						} else {
							response.setCode("9993");
							response.setMessage("No Configuration Found.");
						}
					} else {
						response.setCode("9992");
						response.setMessage("Card Not Supported.");
					}
				} else {
					response.setCode("9991");
					response.setMessage(tap.getMessage());
				}

			} else {
				response.setCode("9999");
				response.setMessage("Invalid Request.");
			}
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	WisherTap setupClass(String className) {
		WisherTap wisher = null;
		try {
			AgLogger.logDebug("", className + "....@@@@");
			wisher = (WisherTap) context.getBean(className);
		} catch (Exception ex) {
			AgLogger.logInfo("SETUP CLASS EXCEPTION: " + ex);
		}
		return wisher;
	}

	ResponseModel doCallClass(RequestModel rms, WisherTap wisher, TapNPayModel tap, TapNPayRoutingConfig routing) {
		ResponseModel response = new ResponseModel();
		String stan = "N/A";
		String invoiceNumber = "N/A";
		String batchNumber = "N/A";
		String code = "N/A";
		String message = "N/A";
		String isoRequest = "N/A";
		String isoResponse = "N/A";
		String rrn = "N/A";
		String imei = rms.getImei();
		String transactionStatus = "";
		String authId = "N/A";
		String aid = "N/A";
		String mid = "N/A";
		String tid = "N/A";
		String tvr = "N/A";
		String currency = "N/A";
		Date cardExpiry = null;
		String cardNumber = "N/A";
		String amount = "N/A";
		String cardType = "N/A";
		String cardScheme = "N/A";
		String txnType = tap.getTransactionTypeField();
		String recId = tap.getCardNumber();
		Timestamp txnDateNTime = new Timestamp(new java.util.Date().getTime());
		Timestamp posDateNTime = null;
		SimpleDateFormat sdfDateForField55 = new SimpleDateFormat("yyMMdd");
		Date txnDate = null;
		String track2Data = null;
		String field55Data = null;
		String isSettled = "N";
		Timestamp settledDate = null;
		String isReversed = "N";
		Timestamp reversalDate = null;
		String isVoid = "N";
		String panSeqNo = "N/A";
		Timestamp voidDate = null;
		boolean isReversal = false;

		try {
			stan = StringUtils.leftPad(tapNPayTransactionService.getStanSequence() + "", 6, "0");
			invoiceNumber = StringUtils.leftPad(tapNPayTransactionService.getInvoiceNumberSequence() + "", 6, "0");
			aid = tap.getFciModel().getAid();
			mid = tap.getMid();
			tid = tap.getTid();
			tvr = tap.getTerminalVerificationResult();
			panSeqNo = tap.getApplicationPanSequenceNumber();
			currency = tap.getTransactionCurrencyCode();
			cardNumber = TapNPayUtil.mask(tap.getCardNumber());
			cardExpiry = (tap.getCardExpiry() != null) ? (new Date(tap.getCardExpiry().getTime())) : null;
			amount = tap.getAmountAuthorizedNumeric();
			track2Data = tap.getTrack2Data();

			cardType = "Contactless";
			cardScheme = (tap.getFciModel().getApplicationLabel() != null)
					? (tap.getFciModel().getApplicationLabel().toUpperCase())
					: "N/A";
			isoRequest = "N/A";
			isoResponse = "N/A";
			authId = "N/A";

			txnDate = (tap.getTransactionDate() != null)
					? (new Date(sdfDateForField55.parse(tap.getTransactionDate()).getTime()))
					: null;
			posDateNTime = (txnDate != null) ? (new Timestamp(txnDate.getTime())) : null;
			TapNPayBatchConfig btc = tapNPayBatchConfigService.fetchBatchConfig(tap.getMid(), tap.getTid());
			if (btc != null) {
				ISO8583Model mRequest = wisher.doProcess(rms, tap, routing, stan, invoiceNumber);
				if (mRequest != null) {
					field55Data = mRequest.getField55Data();
					batchNumber = StringUtils.leftPad(btc.getBatchNumber() + "", 6, "0"); // BacthNumber Field.
					ISO8583Model responseIso = iSO8583Util.doProcess(mRequest);
					AgLogger.logInfo(responseIso.getCode() + "|" + responseIso.getMessage());
					response.setCode(responseIso.getCode());
					response.setMessage(responseIso.getMessage());
					if (responseIso.getCode().equals("00")) {

						transactionStatus = "SUCCESS";
						String dtResponseField12Time = responseIso.getIsoMessageResponse().getString(12);
						String dtResponseField13Date = responseIso.getIsoMessageResponse().getString(13);
						String dateTime = "";
						int year = Calendar.getInstance().get(Calendar.YEAR);
						try {
							dateTime += dtResponseField13Date + year;
						} catch (Exception e) {
							dateTime += "0101" + year;
						}

						try {
							dateTime += dtResponseField12Time;
						} catch (Exception e) {
							dateTime += "000000";
						}

						try {
							SimpleDateFormat sdfIsoTimeParser = new SimpleDateFormat("MMddyyyyHHmmss");
							txnDateNTime = new Timestamp(sdfIsoTimeParser.parse(dateTime).getTime());
						} catch (Exception e) {
							AgLogger.logerror(getClass(), " EXCEPTION  ", e);
						}
						response.setCode("0000");
						response.setMessage(TapNPayUtil.getIsoErrorCode(responseIso.getCode()));
						code = "00";
						message = TapNPayUtil.getIsoErrorCode(responseIso.getCode());
						rrn = responseIso.getIsoMessageResponse().getString(37);
						authId = responseIso.getIsoMessageResponse().getString(38);

					} else {
						if (response.getCode().equals("0003")) {
							isReversal = true;
							code = responseIso.getCode();
							message = TapNPayUtil.getIsoErrorCode(responseIso.getCode());
							response.setMessage(TapNPayUtil.getIsoErrorCode(responseIso.getCode()));
							// -Unable to connect.

						} else {
							code = responseIso.getCode();
							message = TapNPayUtil.getIsoErrorCode(responseIso.getCode());
							response.setMessage(TapNPayUtil.getIsoErrorCode(responseIso.getCode()));
						}
					}

				} else {
					if (tap.getTransactionTypeField().equals("COMPLETION")) {
						rrn = "000000000000";
						authId = rms.getAdditionalData().get("authId").toString();
						code = "00";
						message = "SUCCESS";
						response.setCode("0000");
						response.setMessage("SUCCESS.");
					} else {
						code = "99";
						message = "N/A";
						response.setCode("9995");
						response.setMessage("ERROR OCCURED.");
					}
				}
			} else {
				code = "99";
				message = "N/A";
				response.setCode("0001");
				response.setMessage("No Batch Cofiguration Found.");
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			code = "99";
			message = "N/A";
			response.setCode("9991");
			response.setMessage("ERROR OCCURED.");
		} finally {
			if (txnType.equals("VOID")) {
				TapNPayTransactions orignalTxn = tapNPayTransactionService.fetchTxnByRecId(Long.parseLong(recId));
				if (code.equals("00")) {
					orignalTxn.setIsVoid("Y");
					orignalTxn.setVoidDate(new Timestamp(new java.util.Date().getTime()));
					tapNPayTransactionService.update(orignalTxn);
				}

				TapNPayTransactions txn = new TapNPayTransactions();
				txn.setAid(orignalTxn.getAid());
				txn.setAuthId(orignalTxn.getAuthId());
				txn.setBatchNumber(batchNumber);
				txn.setCardExpiriy(orignalTxn.getCardExpiriy());
				txn.setCardNumber(orignalTxn.getCardNumber());
				txn.setCardSheme(orignalTxn.getCardSheme());
				txn.setCardType(orignalTxn.getCardType());
				txn.setCode(code);
				txn.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				txn.setInvoiceNumber(orignalTxn.getInvoiceNumber());
				txn.setIsoRequest(isoRequest);
				txn.setIsoResponse(isoResponse);
				txn.setMessage(message);
				txn.setMid(mid);
				txn.setStan(orignalTxn.getStan());
				txn.setTid(tid);
				txn.setTxnAmount(orignalTxn.getTxnAmount());
				txn.setTxnDate(orignalTxn.getTxnDate());
				txn.setRrn(orignalTxn.getRrn());
				txn.setEntryBy(Integer.parseInt(rms.getUserid()));
				txn.setTransactionType("VOID");
				txn.setPosDate(orignalTxn.getPosDate());
				txn.setTransactionStatus(transactionStatus);
				txn.setTrack2Data(TapNPayEncDecUtil.encrypt(orignalTxn.getTrack2Data()));
				txn.setField55Data(TapNPayEncDecUtil.encrypt(orignalTxn.getField55Data()));
				txn.setIsSettled("N");
				txn.setSettledDate(null);
				txn.setIsReversed("N");
				txn.setReversalDate(null);
				txn.setIsVoid("N");
				txn.setVoidDate(null);
				txn.setTvr(orignalTxn.getTvr());
				txn.setPanSeqNo(orignalTxn.getPanSeqNo());

				tapNPayTransactionService.insertBatch(txn);
				
				
				if (code.equals("00")) {
					UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rms.getUserid()));
					if (ukl != null) {
						String textTitle = AppProp.getProperty("tap.n.pay.app.notification.title");
						String text = AppProp.getProperty("tap.n.pay.app.notification");
						text = text.replaceAll("@PARAMCUSTOMERNAME", ukl.getUserName());
						text = text.replaceAll("@PARAMAMOUNT", Float.toString(txn.getTxnAmount()));
						text = text.replaceAll("@PARAMTXNDATE", new SimpleDateFormat("dd-MM-yyyy").format(orignalTxn.getTxnDate()));
						utilService.doSendAppNotificationOnly(Integer.parseInt(rms.getUserid()), textTitle, text);

						String sms = AppProp.getProperty("tap.n.pay.sms");
						sms = sms.replaceAll("@PARAMAMOUNT", Float.toString(txn.getTxnAmount()));
						sms = sms.replaceAll("@PARAMTXNDATEE", txn.getTxnDate().toString());
						utilService.doSendSmsOnly(ukl.getMsisdn(), sms, rms.getCorpId());
					}
				}

				try {
					if (code.equals("00")) {
						TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(orignalTxn.getCardNumber().substring(0, 5),
								mid, tid, " ", Float.toString(orignalTxn.getTxnAmount()), code, "SUCCESS", txnType,
								isSettled, "N", isReversed, batchNumber, "SUCCESS", orignalTxn.getRrn(),
								orignalTxn.getCardType(), 0, message, orignalTxn.getStan(), "N/A",
								orignalTxn.getInvoiceNumber(), "0072", 0, orignalTxn.getTxnDate(), "SOFT_POS",
								orignalTxn.getCardSheme(), imei, orignalTxn.getAuthId(), orignalTxn.getCardNumber(),
								"N/A", "0.00", "0.00", " ", " ", " ", 0, Double.valueOf(orignalTxn.getTxnAmount()), 0.0,
								0.0, 0.0, currency, tvr, orignalTxn.getAid(), orignalTxn.getCardExpiriy(),
								orignalTxn.getCardType(), "/");
						AgLogger.logDebug("", "@@@ TXNLOGn For Void: " + txnLogCustId);
						txnDetailsService.insertLog(txnLogCustId);
						AgLogger.logDebug("", "@@@ TXNLOGn: Saved [VOID]:" + txnLogCustId);

					}

				} catch (Exception e) {
					e.printStackTrace();
					AgLogger.logerror(getClass(), " EXCEPTION  ", e);

				}
				SimpleDateFormat sdfTxnDate = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdfTxnTime = new SimpleDateFormat("HH:mm:ss");

				TapNPayResponseModel respModel = new TapNPayResponseModel();
				respModel.setAid(orignalTxn.getAid());
				respModel.setAmount(Float.toString(orignalTxn.getTxnAmount()));
				respModel.setAuthId(orignalTxn.getAuthId());
				respModel.setBatchNumber(batchNumber);
				respModel.setCardExpiry(sdfDateForField55.format(orignalTxn.getCardExpiriy()));
				respModel.setCardNumber(orignalTxn.getCardNumber());
				respModel.setCardScheme(orignalTxn.getCardSheme());
				respModel.setCardType(orignalTxn.getCardType());
				respModel.setCurrency("PKR");
				respModel.setInvoiceNumber(orignalTxn.getInvoiceNumber());
				respModel.setMid(mid);
				respModel.setRrn(orignalTxn.getRrn());
				respModel.setTid(tid);
				respModel.setTvr(orignalTxn.getTvr());
				respModel.setTxnDate(sdfTxnDate.format(orignalTxn.getTxnDate()));
				respModel.setTxnTime(sdfTxnTime.format(orignalTxn.getTxnDate()));
				respModel.setTxnType(txnType);
				respModel.setTipAmount("0.00");
				respModel.setTotalAmount(Float.toString(orignalTxn.getTxnAmount()));
				// Double.parseDouble(amount.subSequence(0, 10) + "." + amount.substring(10,
				// 12)) + "");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("txn", respModel);
				response.setData(obj);

			} else {
				TapNPayTransactions txn = new TapNPayTransactions();
				txn.setAid(aid);
				txn.setAuthId(authId);
				txn.setBatchNumber(batchNumber);
				txn.setCardExpiriy(cardExpiry);
				txn.setCardNumber(cardNumber);
				txn.setCardSheme(cardScheme);
				txn.setCardType(cardType);
				txn.setCode(code);
				txn.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				txn.setInvoiceNumber(invoiceNumber);
				txn.setIsoRequest(isoRequest);
				txn.setIsoResponse(isoResponse);
				txn.setMessage(message);
				txn.setMid(mid);
				txn.setStan(stan);
				txn.setTid(tid);
				txn.setTxnAmount(UtilAccess.amountParser(amount));
				txn.setTxnDate(txnDateNTime);
				txn.setRrn(rrn);
				txn.setEntryBy(Integer.parseInt(rms.getUserid()));
				txn.setTransactionType(txnType);
				txn.setPosDate(posDateNTime);
				txn.setTransactionStatus(transactionStatus);
				txn.setTrack2Data(TapNPayEncDecUtil.encrypt(track2Data));
				txn.setField55Data(TapNPayEncDecUtil.encrypt(field55Data));
				txn.setIsSettled(isSettled);
				txn.setSettledDate(settledDate);
				txn.setIsReversed(isReversed);
				txn.setReversalDate(reversalDate);
				txn.setIsVoid(isVoid);
				txn.setVoidDate(voidDate);
				txn.setTvr(tvr);
				txn.setPanSeqNo(panSeqNo);

				if (code.equals("00")) {
					UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rms.getUserid()));
					if (ukl != null) {
						String textTitle = AppProp.getProperty("tap.n.pay.app.notification.title");
						String text = AppProp.getProperty("tap.n.pay.app.notification");
						text = text.replaceAll("@PARAMCUSTOMERNAME", ukl.getUserName());
						text = text.replaceAll("@PARAMAMOUNT", Float.toString(txn.getTxnAmount()));
						text = text.replaceAll("@PARAMTXNDATE", new SimpleDateFormat("dd-MM-yyyy").format(txnDate));
						utilService.doSendAppNotificationOnly(Integer.parseInt(rms.getUserid()), textTitle, text);

						String sms = AppProp.getProperty("tap.n.pay.sms");
						sms = sms.replaceAll("@PARAMAMOUNT", Float.toString(txn.getTxnAmount()));
						sms = sms.replaceAll("@PARAMTXNDATEE", txn.getTxnDate().toString());
						utilService.doSendSmsOnly(ukl.getMsisdn(), sms, rms.getCorpId());
					}
				}

				try {
					if (code.equals("00")) {
						Date dfs = new Date(txnDateNTime.getTime());
						TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(cardNumber.substring(0, 5), mid, tid, " ",
								Float.toString(UtilAccess.amountParser(amount)), code, "SUCCESS", txnType, isSettled,
								"N", isReversed, batchNumber, "SUCCESS", rrn, cardType, 0, message, stan, "N/A",
								invoiceNumber, "0072", 0, dfs, "SOFT_POS", cardScheme, imei, authId, cardNumber, "N/A",
								"0.00", "0.00", " ", " ", " ", 0, Double.valueOf(amount), 0.0, 0.0, 0.0, currency, tvr,
								aid, cardExpiry, cardType, "/");
						AgLogger.logDebug("", "@@@ TXNLOGn: " + txnLogCustId);
						txnDetailsService.insertLog(txnLogCustId);
						AgLogger.logDebug("", "@@@ TXNLOGn: Saved." + txnLogCustId);

					}

				} catch (Exception e) {
					e.printStackTrace();
					AgLogger.logerror(getClass(), " EXCEPTION  ", e);

				}

				long idVal = tapNPayTransactionService.insertBatch(txn);

				if (isReversal) {
					TapNPaySaf tapnpaySaf = new TapNPaySaf();
					tapnpaySaf.setEntryDate(new Timestamp(new java.util.Date().getTime()));
					tapnpaySaf.setMaxHits(Integer.parseInt(AppProp.getProperty("saf.max.hits")));
					tapnpaySaf.setMinHits(0);
					tapnpaySaf.setTapnpayTxnId(idVal);
					tapnpaySaf.setTxnType(txnType);

					tapNPaySafService.insert(tapnpaySaf);
				}

				SimpleDateFormat sdfTxnDate = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdfTxnTime = new SimpleDateFormat("HH:mm:ss");

				TapNPayResponseModel respModel = new TapNPayResponseModel();
				respModel.setAid(aid);
				respModel
						.setAmount(Double.parseDouble(amount.subSequence(0, 10) + "." + amount.substring(10, 12)) + "");
				respModel.setAuthId(authId);
				respModel.setBatchNumber(batchNumber);
				respModel.setCardExpiry(sdfDateForField55.format(cardExpiry));
				respModel.setCardNumber(cardNumber);
				respModel.setCardScheme(cardScheme);
				respModel.setCardType(cardType);
				respModel.setCurrency("PKR");
				respModel.setInvoiceNumber(invoiceNumber);
				respModel.setMid(mid);
				respModel.setRrn(rrn);
				respModel.setTid(tid);
				respModel.setTvr(tvr);
				respModel.setTxnDate(sdfTxnDate.format(txnDateNTime));
				respModel.setTxnTime(sdfTxnTime.format(txnDateNTime));
				respModel.setTxnType(txnType);
				respModel.setTipAmount("0.00");
				respModel.setTotalAmount(
						Double.parseDouble(amount.subSequence(0, 10) + "." + amount.substring(10, 12)) + "");

				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("txn", respModel);
				response.setData(obj);
			}
		}
		return response;
	}

}