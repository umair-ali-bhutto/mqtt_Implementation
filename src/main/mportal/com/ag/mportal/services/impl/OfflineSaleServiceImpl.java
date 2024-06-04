package com.ag.mportal.services.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.BatchSettlement;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.Paymentconfiguration;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.model.OfflineDetailsReportModel;
import com.ag.mportal.model.OfflineSaleTxnBatchModel;
import com.ag.mportal.model.OfflineSaleTxnBatchModel.AdditionalData;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.model.UtilReport;
import com.ag.mportal.services.BatchSettlementService;
import com.ag.mportal.services.OfflineSaleService;
import com.ag.mportal.services.PaymentConfigurationService;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.services.TxnLogsService;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;

@Service
public class OfflineSaleServiceImpl implements OfflineSaleService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilAccess utilAccess;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	TxnLogsService txnLogsService;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	BatchSettlementService batchSettlementService;

	@Autowired
	PaymentConfigurationService paymentConfigurationService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	UtilService utilService;

	@Override
	public List<ReportModel> fetchOfflineSaleTxnReport(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth, int numberOfRows, int pageNumber) {
		List<ReportModel> lstReportItems = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("td.ENTRY_DATE") + " between '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}

			if (merchant.size() != 0) {
				String jk = "";
				for (String mer : merchant) {
					jk += "'" + mer + "',";
				}

				if (jk.length() != 0) {
					jk = jk.substring(0, jk.length() - 1);
				}

				whereClause += " AND td.MERCHANT_ID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND td.TERMINAL_ID = '" + terminal + "' ";
			}

			if (setled != null) {
				whereClause += "  AND td.SETTELED = '" + setled + "' ";
			}

			if (batch != null) {
				whereClause += "  AND td.BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND td.AUTH_ID_N = '" + auth + "' ";
			}

			if (channelList.size() != 0) {
				String channels = "";
				for (String channel : channelList) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				whereClause += " AND el.CORPORATE IN (" + channels + ")";
			}
			whereClause += " AND td.TYPE IN ( " + AppProp.getProperty("offline.txn.summary.types") + " )";

			String quer = DBUtil.getWebEcrTxnDetailsQuery();
			quer = quer.replace("@whereClause", whereClause);

			if (numberOfRows != 0) {
				int startingValue = 0;
				int endingValue = 0;

				// FOR SQL
				if (DBUtil.getDialect() == 1) {
					quer = quer + " OFFSET @startingValue ROWS FETCH NEXT @endingValue ROWS ONLY ";
					startingValue = numberOfRows * pageNumber;
					endingValue = numberOfRows;
				} else {
					// FOR ORACLE (rn Alias for ROWNUM)
					quer = "SELECT * FROM (" + quer + ") WHERE rn BETWEEN @startingValue AND @endingValue ";
					startingValue = 1 + (numberOfRows * pageNumber);
					endingValue = numberOfRows * (pageNumber + 1);
				}

				quer = quer.replace("@startingValue", String.valueOf(startingValue));
				quer = quer.replace("@endingValue", String.valueOf(endingValue));
			}

			// AgLogger.logInfo("", quer);
			AgLogger.logDebug(TxnLogsDetailServiceImpl.class, quer);
			Query cb = entityManager.createNativeQuery(quer);

			@SuppressWarnings("unchecked")
			List<Object[]> lst = cb.getResultList();

			if (!lst.isEmpty()) {
				lstReportItems = UtilAccess.reportList(lst);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lstReportItems;
	}

	@Override
	public List<TxnSummary> fetchOfflineSaleTxnSummary(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth) {
		List<TxnSummary> fileUploadProp = null;
		List<Object[]> objs = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String quer = DBUtil.getOfflineSaleTxnSummaryQuery();

			String m = "";
			if (from != null && to != null) {

				m += " AND (" + DBUtil.getDateQueryParam("td.ENTRY_DATE") + " between '" + sdf.format(from) + "' AND '"
						+ sdf.format(to) + "') ";
			}
			if (merchant.size() != 0) {
				String jk = "";
				for (String mer : merchant) {
					jk += "'" + mer + "',";
				}

				if (jk.length() != 0) {
					jk = jk.substring(0, jk.length() - 1);
				}

				m += " AND td.MERCHANT_ID IN (" + jk + ")";
			}

			if (terminal != null) {
				m += " AND  td.TERMINAL_ID = '" + terminal + "' ";
			}

			if (auth != null) {
				m += " AND  td.AUTH_ID_N = '" + auth + "' ";
			}

			if (batch != null) {
				m += " AND  td.BATCH_NUMBER = '" + batch + "' ";
			}

			if (setled != null) {
				m += " AND  td.SETTELED = '" + setled + "' ";
			}

			if (channelList.size() != 0) {
				String channels = "";
				for (String channel : channelList) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				m += " AND el.CORPORATE IN (" + channels + ")";
			}

			m += " AND td.TYPE IN ( " + AppProp.getProperty("offline.txn.summary.types") + " )";
			quer = quer.replaceAll("@PARAM", m);
			AgLogger.logDebug("", "TxnSummary Query: " + quer);

			Query cb = entityManager.createNativeQuery(quer);
			objs = (List<Object[]>) cb.getResultList();

			fileUploadProp = utilAccess.convertObjToTxnSummaryModel(objs);

			if (fileUploadProp != null) {

				TxnSummary tempTotal = new TxnSummary();
				tempTotal.setTxnType("TOTAL");

				Map<String, TxnSummary> map = fileUploadProp.stream()
						.collect(Collectors.toMap(TxnSummary::getTxnType, Function.identity()));

				for (Map.Entry<String, TxnSummary> entry : map.entrySet()) {
					String key = entry.getKey();
					TxnSummary value = entry.getValue();
					System.out.println("Key: " + key + ", Value.txntype: " + value.getTxnType() + ", Value.cnt: "
							+ value.getCnt() + ", Value.amt: " + value.getAmount());
				}

				Integer totalCount = UtilAccess.getCount(map, AppProp.getProperty("calc.total.count.txn.summary"));
				tempTotal.setCnt(!Objects.isNull(totalCount) ? totalCount : 0);

				Float totalAmount = UtilAccess.getAmount(map, AppProp.getProperty("calc.total.amount.txn.summary"));
				tempTotal.setAmount(!Objects.isNull(totalAmount) ? totalAmount : 0f);

				fileUploadProp.add(tempTotal);

			}

		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception while TxnSummary: ", e);
		}
		return fileUploadProp;
	}

	@Override
	@Transactional
	public String[] doProcessBatchSettlmentOffline(String data) {
		// isko fix karo
		String[] s = new String[2];
		try {
			OfflineSaleTxnBatchModel model = new Gson().fromJson(data, OfflineSaleTxnBatchModel.class);
			UserLogin userMdl = userLoginService.validateUserPassword(model.getTxnInfo().getUserName(),
					model.getTxnInfo().getPassword());

//			AgLogger.logInfo("USER NAME = "+model.getTxnInfo().getUserName()+" PASSWORD ="+
//					model.getTxnInfo().getPassword());

			if (userMdl != null) {
				long txnRecId = 0;
				List<BatchSettlement> lst = new ArrayList<BatchSettlement>();
				List<String> dataList = model.getTxnData();
				for (String sData : dataList) {
					String flightNumber = "";

					Date cardExpiry = null;
					BatchSettlement btch = new BatchSettlement();
					String[] keyData = sData.split("~");
					try {
						flightNumber = keyData[18];
					} catch (Exception e) {
						e.printStackTrace();
					}
					btch.setBin(keyData[2]);
					btch.setAuthId(keyData[0]);
					btch.setFieldOne(keyData[1]);
					btch.setAmount(keyData[12]);
					btch.setMid(model.getTxnInfo().getMID());
					btch.setTid(model.getTxnInfo().getTID());
					int year = Calendar.getInstance().get(Calendar.YEAR);
					String dateTime = "";
					try {
						dateTime += keyData[10] + year;
					} catch (Exception e) {
						dateTime += "0101" + year;
					}
					try {
						dateTime += keyData[11];
					} catch (Exception e) {
						dateTime += "000000";
					}

					btch.setEntryMode(keyData[4]);
					btch.setBatchNumber(keyData[9]);
					btch.setRrn(keyData[8]);
					btch.setInvoiceNumber(keyData[5]);
					btch.setStan(keyData[7]);
					btch.setTxnType(keyData[3]);
					btch.setModel(model.getTxnInfo().getModel());
					btch.setSerialNumber(model.getTxnInfo().getSerialNumber());
					Date dfs = new java.util.Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss");
					dfs = sdf.parse(dateTime);

					btch.setTxnDate(new Timestamp(dfs.getTime()));
					btch.setEntryDate(new Timestamp(new java.util.Date().getTime()));

					try {
						String tempCardExpiry = keyData[17];
						SimpleDateFormat sdfM = new SimpleDateFormat("MM/yy");

						cardExpiry = sdfM.parse(tempCardExpiry);
					} catch (Exception e) {
						// AgLogger.logerror(getClass(), "Exception Card Expiry....", e);
					}

					boolean isExist = txnDetailsService.checkExistanceOfTxnDetail(model.getTxnInfo().getMID(),
							model.getTxnInfo().getTID(), btch.getBatchNumber(), btch.getRrn(), btch.getAuthId(),
							btch.getTxnType());
					if (!isExist) {
						UserLogin userMdlRequest = userLoginService
								.validetUserWithoutCorpId(model.getTxnInfo().getMID());
						if (!Objects.isNull(userMdlRequest)) {
							List<UserSetting> lstUserSetting = userSettingService
									.fetchSettingByUserLoginId(userMdlRequest.getUserId());
							if (lstUserSetting.size() != 0) {
								HashMap<String, String> mapUserSetting = UtilAccess.lstToMapUserSetting(lstUserSetting);

								Double mdrOffus = UtilAccess.calcTxnMdrOffus(Double.valueOf(btch.getAmount()),
										!Objects.isNull(mapUserSetting.get("MDR_OFF_US"))
												? Double.valueOf(mapUserSetting.get("MDR_OFF_US"))
												: 0.0,
										!Objects.isNull(mapUserSetting.get("MDR_ON_US"))
												? Double.valueOf(mapUserSetting.get("MDR_ON_US"))
												: 0.0,
										!Objects.isNull(mapUserSetting.get("FED_RATES"))
												? Double.valueOf(mapUserSetting.get("FED_RATES"))
												: 0.0);

								Double mdrOnnus = UtilAccess.calcTxnMdrOnus(Double.valueOf(btch.getAmount()),
										!Objects.isNull(mapUserSetting.get("MDR_OFF_US"))
												? Double.valueOf(mapUserSetting.get("MDR_OFF_US"))
												: 0.0,
										!Objects.isNull(mapUserSetting.get("MDR_ON_US"))
												? Double.valueOf(mapUserSetting.get("MDR_ON_US"))
												: 0.0,
										!Objects.isNull(mapUserSetting.get("FED_RATES"))
												? Double.valueOf(mapUserSetting.get("FED_RATES"))
												: 0.0);

								Double fedRate = UtilAccess.calcTxnFed(Double.valueOf(btch.getAmount()), mdrOffus,
										mdrOnnus,
										!Objects.isNull(mapUserSetting.get("FED_RATES"))
												? Double.valueOf(mapUserSetting.get("FED_RATES"))
												: 0.0);

								Double netAmount = UtilAccess.calcTxnNetAmount(Double.valueOf(btch.getAmount()),
										mdrOffus, mdrOnnus, fedRate);

								TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(btch.getFieldOne().substring(0, 6), btch.getMid(),
										btch.getTid(), null, btch.getAmount(), "0000", "SUCCESS", btch.getTxnType(), "Y",
										"N", "N", btch.getBatchNumber(), "SUCCESS", btch.getRrn(), "N/A", 0,
										/*17*/ "BAF-Payment", /*18*/ btch.getStan(), "N/A",
										btch.getInvoiceNumber(), btch.getEntryMode(), 0, dfs,
										model.getTxnInfo().getModel(), keyData[6], model.getTxnInfo().getSerialNumber(),
										btch.getAuthId(), btch.getFieldOne(), "N/A", "0.00", "0.00", "N/A", "N/A", "N/A", 0, netAmount,
										mdrOnnus, mdrOffus, fedRate, "PKR", keyData[13], /*42*/ keyData[14], cardExpiry,
										/*44*/ keyData[6], keyData[15]);

								entityManager.persist(txnLogCustId);
								txnRecId = txnLogCustId.getId();

							} else {
								TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(btch.getFieldOne().substring(0, 6), btch.getMid(),
										btch.getTid(), null, btch.getAmount(), "0000", "SUCCESS", btch.getTxnType(), "Y",
										"N", "N", btch.getBatchNumber(), "SUCCESS", btch.getRrn(), "N/A", 0,
										"BAF-Payment", btch.getStan(), "N/A",
										btch.getInvoiceNumber(), btch.getEntryMode(), 0, dfs,
										model.getTxnInfo().getModel(), keyData[6], model.getTxnInfo().getSerialNumber(),
										btch.getAuthId(), btch.getFieldOne(), "N/A", "0.00", "0.00", "N/A", "N/A", "N/A", 0,
										Double.valueOf(btch.getAmount()), 0.0, 0.0, 0.0, "PKR", keyData[13],
										keyData[14], cardExpiry, keyData[6], keyData[15]);
								entityManager.persist(txnLogCustId);
								txnRecId = txnLogCustId.getId();

							}
						} else {
							TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(btch.getFieldOne().substring(0, 6), btch.getMid(),
									btch.getTid(), null, btch.getAmount(), "0000", "SUCCESS", btch.getTxnType(), "Y", "N",
									"N", btch.getBatchNumber(), "SUCCESS", btch.getRrn(), "N/A", 0, "BAF-Payment",
									btch.getAuthId(), "N/A", btch.getInvoiceNumber(), btch.getEntryMode(), 0, dfs,
									model.getTxnInfo().getModel(), keyData[6], model.getTxnInfo().getSerialNumber(),
									btch.getAuthId(), btch.getFieldOne(), "N/A", "0.00", "0.00", "N/A", "N/A", "N/A", 0,
									Double.valueOf(btch.getAmount()), 0.0, 0.0, 0.0, "PKR", keyData[13], keyData[14],
									cardExpiry, keyData[6], keyData[15]);
							entityManager.persist(txnLogCustId);
							txnRecId = txnLogCustId.getId();

						}

						if (txnRecId != 0) {
							List<MerchantTerminalDetail> DetailsList = new ArrayList<MerchantTerminalDetail>();
							MerchantTerminalDetail mtd = null;
							AdditionalData addData = model.getAdditionalData();
							HashMap<String, String> ethernet = addData.getEthernetInfo();
							if (ethernet.size() != 0) {
								for (Map.Entry<String, String> set : ethernet.entrySet()) {
									try {
										mtd = new MerchantTerminalDetail();
										mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
										mtd.setMid(model.getTxnInfo().getMID());
										mtd.setTid(model.getTxnInfo().getTID());
										mtd.setType("TRANSACTION-ETHERNETINFO");
										mtd.setDataName(set.getKey().toUpperCase());
										mtd.setDataValue(set.getValue().toUpperCase());
										mtd.setTxnLogsId((int) txnRecId);
										DetailsList.add(mtd);
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							}

							HashMap<String, String> additionalInfo = addData.getAdditionalinfo();
							if (additionalInfo.size() != 0) {
								for (Map.Entry<String, String> set : additionalInfo.entrySet()) {
									try {
										mtd = new MerchantTerminalDetail();
										mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
										mtd.setMid(model.getTxnInfo().getMID());
										mtd.setTid(model.getTxnInfo().getTID());
										mtd.setType("TRANSACTION-ADDINFO");
										mtd.setDataName(set.getKey().toUpperCase());
										mtd.setDataValue(set.getValue().toUpperCase());
										mtd.setTxnLogsId((int) txnRecId);
										DetailsList.add(mtd);
									} catch (Exception e) {
										e.printStackTrace();
									}

								}

								if (flightNumber != "") {
									mtd = new MerchantTerminalDetail();
									mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
									mtd.setMid(model.getTxnInfo().getMID());
									mtd.setTid(model.getTxnInfo().getTID());
									mtd.setType("TRANSACTION-ADDINFO");
									mtd.setDataName("FLIGHT NUMBER");
									mtd.setDataValue(flightNumber.toUpperCase());
									mtd.setTxnLogsId((int) txnRecId);
									DetailsList.add(mtd);
								}
							}

							HashMap<String, String> gsmInfo = addData.getGsmInfo();
							if (gsmInfo.size() != 0) {
								for (Map.Entry<String, String> set : gsmInfo.entrySet()) {
									try {
										mtd = new MerchantTerminalDetail();
										mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
										mtd.setMid(model.getTxnInfo().getMID());
										mtd.setTid(model.getTxnInfo().getTID());
										mtd.setType("TRANSACTION-GSMINFO");
										mtd.setDataName(set.getKey().toUpperCase());
										mtd.setDataValue(set.getValue().toUpperCase());
										mtd.setTxnLogsId((int) txnRecId);
										DetailsList.add(mtd);
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							}

							HashMap<String, String> wlanInfo = addData.getWlanInfo();
							if (wlanInfo.size() != 0) {
								for (Map.Entry<String, String> set : wlanInfo.entrySet()) {
									try {
										mtd = new MerchantTerminalDetail();
										mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
										mtd.setMid(model.getTxnInfo().getMID());
										mtd.setTid(model.getTxnInfo().getTID());
										mtd.setType("TRANSACTION-WLANINFO");
										mtd.setDataName(set.getKey().toUpperCase());
										mtd.setDataValue(set.getValue().toUpperCase());
										mtd.setTxnLogsId((int) txnRecId);
										DetailsList.add(mtd);
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							}

							utilService.saveToMerchantTerminalDetails(DetailsList);
						}

						lst.add(btch);
					} else {
//						TxnLogError txnLog = UtilAccess.doLogTestForNonExistTxnRecord(btch.getFieldOne(), btch.getMid(),
//								btch.getTid(), "", btch.getAmount(), "0000", "SUCCESS", btch.getTxnType(), "Y", "N",
//								"N", btch.getBatchNumber(), "SUCCESS", btch.getRrn(), "N/A", 0, "SUCCESS",
//								btch.getRrn(), "N/A", btch.getInvoiceNumber(), btch.getEntryMode(), 0, dfs,
//								model.getTxnInfo().getSerialNumber(), keyData[6], model.getTxnInfo().getSerialNumber(),
//								btch.getAuthId(), btch.getFieldOne(), keyData[6], model.getTxnInfo().getSerialNumber(),
//								btch.getAuthId(), btch.getFieldOne(), 0.0, keyData[13], keyData[14], cardExpiry,
//								keyData[16], keyData[15]);
//						entityManager.persist(txnLog);
					}

				}
				insertBatchRec(lst, userMdl);

				TxnLog txnLog = UtilAccess.insertTxnLogs(AppProp.getProperty("act.batch"),
						"BATCH SETTLED PROCESSED : " + new Gson().toJson(model.getSummary()), lst.get(0).getMid(),
						lst.get(0).getTid(), lst.get(0).getModel(), lst.get(0).getSerialNumber(), null, null, null,
						null, null, null, null, null, null, null, null);
				txnLogsService.insertLog(txnLog);
				s[0] = "0000";
				s[1] = "SUCCESS";
			} else {
				s[0] = "9999";
				s[1] = "Invalid User Name Password";
			}
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logInfo(data);
			s[0] = "9999";
			s[1] = "Exception ";
		}
		String arrayAsString = Arrays.toString(s);
		AgLogger.logInfo(arrayAsString + " @@@@@");
		return s;
	}

	public void insertBatchRec(List<BatchSettlement> lst, UserLogin userLogin) {

		for (BatchSettlement btch : lst) {
			try {
				TxnDetail txn = fetchTxnDetailNonVoid(btch.getBatchNumber(), btch.getTid(), btch.getInvoiceNumber());
				if (txn != null) {
					txn.setSetteled("Y");
					Date date = new Date();
					txn.setSetteledDate(new Timestamp(date.getTime()));
					txn.setPaymentDate(new Timestamp(getPaymentDate(date, userLogin.getCorpId()).getTime()));
					txnDetailsService.updateTxn(txn);
					TxnDetail txns = txnDetailsService.fetchTxnDetailVoid(btch.getBatchNumber(), btch.getTid(),
							btch.getInvoiceNumber());
					if (txns != null) {
						System.out.println(txns.getId() + ": void id");
						txns.setSetteled("Y");
						Date date2 = new Date();
						txn.setSetteledDate(new Timestamp(date2.getTime()));
						txn.setPaymentDate(new Timestamp(getPaymentDate(date2, userLogin.getCorpId()).getTime()));
						txnDetailsService.updateTxn(txns);
					}
					btch.setIsSetteled("Y");
				} else {
					btch.setIsSetteled("N");
				}
				batchSettlementService.insertBatch(btch);
			} catch (Exception e) {
				e.printStackTrace();
				btch.setIsSetteled("E");
				batchSettlementService.insertBatch(btch);
			}
		}
	}

	public Date getPaymentDate(Date date, String corpId) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(date);
			Paymentconfiguration paymentConfig = paymentConfigurationService.retrievePaymentConfigByDay(date.getDay(),
					corpId);
			c.add(Calendar.DAY_OF_MONTH, paymentConfig.getNumberOfDaysAdded());
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception ", e);
		}
		return c.getTime();
	}

	public TxnDetail fetchTxnDetailNonVoid(String batch, String tid, String invoice) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchTxnDetailsVoid");
			Query cb = entityManager.createNamedQuery("TxnDetail.fetchTxnDetailsNonVoid")
					.setParameter("status", "SUCCESS").setParameter("batchNumber", batch)
					.setParameter("terminalID", tid).setParameter("invoiceNum", invoice).setParameter("type", "VOID");
			return (TxnDetail) cb.setMaxResults(1).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void createOfflineSummaryDetailCsvFile(List<TxnSummary> txnSummaryModel,
			List<OfflineDetailsReportModel> txnDetails, String filePath) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			if (!Objects.isNull(txnSummaryModel)) {
				data.add(new String[] { "Summary:" });
				data.add(new String[] { "S.NO", "Txn Type", "Count", "Amount" });

				int i = 0;
				for (TxnSummary txnSummary : txnSummaryModel) {
					i++;
					data.add(new String[] { i + "", txnSummary.getTxnType() + "", txnSummary.getCnt() + "",
							UtilAccess.exponent(txnSummary.getAmount() + "") });
				}
				if (txnDetails != null) {
					data.add(new String[] { " " });
					data.add(new String[] { "Details:" });
				}
			}

			if (txnDetails != null) {
				data.add(new String[] { "S.NO", "Txndetailid", "Type", "Field_one", "Merchantid  ", "Terminalid  ",
						"Entrydate", "Txnamount  ", "Authid  ", "Customername  ", "Scheme", "Model", "Card_type",
						"Card_expiry", "Serial_number", "Rrn", "Mdr_onus", "Settled_date", "Mdr_offus", "Adj_amount",
						"Invoice_num", "Fed", "Tim_amount", "Pos_entry_mode", "Net_amount", "Tvr", "Aid", "Setteled",
						"Batch_number", "Flight_number" });
				int k = 0;
				for (OfflineDetailsReportModel m : txnDetails) {
					k++;
					data.add(new String[] { k + "", m.getTxndetailid() + "", m.getType() + "", m.getFieldOne() + "",
							m.getMerchantid() + "", m.getTerminalid() + "", m.getEntrydate() + "",
							m.getTxnamount() + "", m.getAuthid() + "", m.getCustomername() + "", m.getScheme() + "",
							m.getModel() + "", m.getCardType() + "", m.getCardExpiry() + "", m.getSerialNumber() + "",
							m.getRrn() + "", m.getMdrOnus() + "", m.getSettledDate() + "", m.getMdrOffus() + "",
							m.getAdjAmount() + "", m.getInvoiceNum() + "", m.getFed() + "", m.getTimAmount() + "",
							m.getPosEntryMode() + "", m.getNetAmount() + "", m.getTvr() + "", m.getAid() + "",
							m.getSetteled() + "", m.getBatchNumber() + "", m.getFlightNumber() + "" });
				}

			}
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			AgLogger.logerror(UtilReport.class, "Exception while creating txn detail csv : ", e);
		}
	}

	@Override
	public List<OfflineDetailsReportModel> fetchOfflineSaleTxnDetailsReport(List<String> merchant,
			List<String> channelList, String terminal, Date from, Date to, String setled, String batch, String auth,
			int numberOfRows, int pageNumber) {
		List<OfflineDetailsReportModel> lstReportItems = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("ENTRYDATE") + " between '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}

			if (merchant.size() != 0) {
				String jk = "";
				for (String mer : merchant) {
					jk += "'" + mer + "',";
				}

				if (jk.length() != 0) {
					jk = jk.substring(0, jk.length() - 1);
				}

				whereClause += " AND MERCHANTID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND TERMINALID = '" + terminal + "' ";
			}

			if (setled != null) {
				whereClause += "  AND SETTELED = '" + setled + "' ";
			}

			if (batch != null) {
				whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND AUTHID = '" + auth + "' ";
			}

			if (channelList.size() != 0) {
				String channels = "";
				for (String channel : channelList) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				// whereClause += " AND CORPORATE IN (" + channels + ")";
			}
//			whereClause += " AND TYPE IN ( " + AppProp.getProperty("offline.txn.summary.types") + " )";

			String quer = DBUtil.getOfflineSaleTxnDetailsQuery();
			quer = quer.replace("@whereClause", whereClause);

			if (numberOfRows != 0) {
				int startingValue = 0;
				int endingValue = 0;

				// FOR SQL
				if (DBUtil.getDialect() == 1) {
					quer = quer + " OFFSET @startingValue ROWS FETCH NEXT @endingValue ROWS ONLY ";
					startingValue = numberOfRows * pageNumber;
					endingValue = numberOfRows;
				} else {
					// FOR ORACLE (rn Alias for ROWNUM)
					quer = "SELECT * FROM (" + quer + ") WHERE rn BETWEEN @startingValue AND @endingValue ";
					startingValue = 1 + (numberOfRows * pageNumber);
					endingValue = numberOfRows * (pageNumber + 1);
				}

				quer = quer.replace("@startingValue", String.valueOf(startingValue));
				quer = quer.replace("@endingValue", String.valueOf(endingValue));
			}

			// AgLogger.logInfo("", quer);
			AgLogger.logDebug(TxnLogsDetailServiceImpl.class, quer);
			Query cb = entityManager.createNativeQuery(quer);

			@SuppressWarnings("unchecked")
			List<Object[]> lst = cb.getResultList();

			if (!lst.isEmpty()) {
				lstReportItems = UtilAccess.offlineReportList(lst);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lstReportItems;
	}

	@Override
	public String fetchOfflineSaleDetailsCount(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth, String reportType) {
		String count = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("ENTRYDATE") + " between '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}

			if (merchant.size() != 0) {
				String jk = "";
				for (String mer : merchant) {
					jk += "'" + mer + "',";
				}

				if (jk.length() != 0) {
					jk = jk.substring(0, jk.length() - 1);
				}

				whereClause += " AND MERCHANTID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND TERMINALID = '" + terminal + "' ";
			}

			if (setled != null) {
				whereClause += "  AND SETTELED = '" + setled + "' ";
			}

			if (batch != null) {
				whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND AUTHID = '" + auth + "' ";
			}

			if (channelList.size() != 0) {
				String channels = "";
				for (String channel : channelList) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				// whereClause += " AND CORPORATE IN (" + channels + ")";
			}

			String quer = DBUtil.getOfflineSaleTxnDetailsCountQuery();
			quer = quer.replace("@whereClause", whereClause);

			AgLogger.logDebug(TxnDetailsServiceImpl.class, quer);

			Query cb = entityManager.createNativeQuery(quer);
			int result = (int) cb.getSingleResult();
			
			count = String.valueOf(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
