package com.ag.mportal.discount;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.ResponseModel;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.DiscountBin;
import com.ag.mportal.entity.DiscountDateTime;
import com.ag.mportal.entity.DiscountMaster;
import com.ag.mportal.entity.DiscountMerchant;
import com.ag.mportal.entity.DiscountMerchantTerminal;
import com.ag.mportal.entity.DiscountSlab;
import com.ag.mportal.model.DiscountDecisionModel;
import com.ag.mportal.model.DiscountMasterSlabModel;
import com.ag.mportal.services.DiscountBinService;
import com.ag.mportal.services.DiscountDateTimeService;
import com.ag.mportal.services.DiscountLogService;
import com.ag.mportal.services.DiscountMasterService;
import com.ag.mportal.services.DiscountMerchantService;
import com.ag.mportal.services.DiscountMerchantTerminalService;
import com.ag.mportal.services.DiscountSlabService;
import com.ag.mportal.util.DiscountUtil;

@Component
public class BAFLDiscount {

	@Autowired
	DiscountMasterService discountMasterService;

	@Autowired
	DiscountSlabService discountSlabService;

	@Autowired
	DiscountBinService discountBinService;

	@Autowired
	DiscountMerchantService discountMerchantService;

	@Autowired
	DiscountMerchantTerminalService discountMerchantTerminalService;

	@Autowired
	DiscountDateTimeService discountDateTimeService;

	@Autowired
	DiscountLogService discountLogService;

	public ResponseModel processDiscount(String serialNumber, String corpId, String txnAmount, String mid, String tid,
			String cardNumberMasked, String productId, String bin, String currency, String status,
			Timestamp transactionTime, String txnDate) {
		ResponseModel resp = new ResponseModel();
		StringBuilder sb = new StringBuilder();
		long discId = 0;
		double discAmount = 0;
		double netAmount = 0;
		try {
			String posRequestValues = corpId + "|" + txnAmount + "|" + transactionTime + "|" + mid + "|" + tid + "|"
					+ cardNumberMasked + "|" + productId + "|" + bin;

			sb.append("\n");
			sb.append(serialNumber + ": 1. Fetching Discount with " + posRequestValues);
			sb.append("\n");

			List<DiscountMasterSlabModel> discountMasterList = discountMasterService.fetchAll(corpId, transactionTime,
					currency, txnAmount, status);
			if (discountMasterList.size() != 0) {
				String discountMasterIds = discountMasterList.stream().map(DiscountMasterSlabModel::getId)
						.map(Object::toString).collect(Collectors.joining(","));
				sb.append(serialNumber + ": 2. Total Discount Founds with IDS " + discountMasterIds);
				sb.append("\n");

				List<DiscountDecisionModel> discountListFinal = new ArrayList<DiscountDecisionModel>();
				for (DiscountMasterSlabModel dms : discountMasterList) {

					boolean isMerchantBasedDiscountFound = false;
					long discMerchantId = 0;

					// 1. Check Merchant Based
					if (dms.getMerchantSpecific() == 1) {
						sb.append(serialNumber + ": 3. Merchant Based Discount Yes.");
						sb.append("\n");
						DiscountMerchant discountMerchant = discountMerchantService.fetchByDiscIdAndMid(dms.getId(),
								mid);
						if (discountMerchant != null) {
							discMerchantId = discountMerchant.getId();
							sb.append(serialNumber + ": 3. Merchant Based Discount Found with ID: "
									+ discountMerchant.getId());
							sb.append("\n");
							DiscountDecisionModel discMerchant = new DiscountDecisionModel(dms.getId(),
									dms.getDiscType(), dms.getDiscValue(), "MERCHANT",
									DiscountUtil.calculateDiscount(txnAmount, dms.getDiscType(), dms.getDiscValue()));
							discountListFinal.add(discMerchant);
							isMerchantBasedDiscountFound = true;
						}
					}

					// 2. Check Terminal Based
					if (dms.getTerminalSpecific() == 1 && isMerchantBasedDiscountFound) {

						sb.append(serialNumber + ": 4. Terminal Based Discount Yes.");
						sb.append("\n");
						DiscountMerchantTerminal discountTerminalBased = discountMerchantTerminalService
								.fetchByDiscountIdAndTid(dms.getId(), discMerchantId, tid);

						if (discountTerminalBased != null) {
							sb.append(serialNumber + ": 4. Terminal Based Discount Found with ID: "
									+ discountTerminalBased.getId());
							sb.append("\n");
							DiscountDecisionModel discMerchantTerminal = new DiscountDecisionModel(dms.getId(),
									dms.getDiscType(), dms.getDiscValue(), "TERMINAL",
									DiscountUtil.calculateDiscount(txnAmount, dms.getDiscType(), dms.getDiscValue()));
							discountListFinal.add(discMerchantTerminal);

						} else {
							// See if MID Discount is avb then remove it.
							discountListFinal.removeIf(d -> d.getDiscountId() == dms.getId()
									&& "MERCHANT".equals(d.getDiscountCategory()));
							AgLogger.logInfo("Terminal ID Not Found So Record Removed From Discount List DiscountID:"
									+ dms.getId());
						}
					}

					// 3. Time specific
					if (dms.getTimeSpecific() == 1) {
						sb.append(serialNumber + ": 5. DateTime Based Discount Yes.");
						sb.append("\n");

						DiscountDateTime discountDateTimeBased = discountDateTimeService.fetchByTimestamp(dms.getId(),
								transactionTime);

						if (discountDateTimeBased != null) {
							sb.append(serialNumber + ": 5. DateTime Based Discount Found with ID: "
									+ discountDateTimeBased.getId());
							sb.append("\n");
							DiscountDecisionModel discDateTime = new DiscountDecisionModel(dms.getId(),
									dms.getDiscType(), dms.getDiscValue(), "DATETIME",
									DiscountUtil.calculateDiscount(txnAmount, dms.getDiscType(), dms.getDiscValue()));
							discountListFinal.add(discDateTime);
						} else {
							if (isMerchantBasedDiscountFound) {
								discountListFinal.removeIf(d -> d.getDiscountId() == dms.getId()
										&& "MERCHANT".equals(d.getDiscountCategory()));
								discountListFinal.removeIf(d -> d.getDiscountId() == dms.getId()
										&& "TERMINAL".equals(d.getDiscountCategory()));
								AgLogger.logInfo(
										"Time Specific Not Found So Record Removed From Discount List DiscountID:"
												+ dms.getId());
							}

						}
					}

					// BIN PRODUCT BASED CONFIGURATION
					// 4. Bin Based
					if (dms.getBinSpecific() == 1) {
						sb.append(serialNumber + ": 3. Bin Based Discount Yes.");
						sb.append("\n");

						DiscountBin discountBinBased = discountBinService.fetchAllByBinAndProductId(bin, productId,
								dms.getId());

						if (discountBinBased != null) {
							sb.append(serialNumber + ": 3. Bin Based Discount Found with ID: "
									+ discountBinBased.getId());
							sb.append("\n");
							DiscountDecisionModel discBin = new DiscountDecisionModel(dms.getId(),
									discountBinBased.getBinDiscType(), discountBinBased.getBinDiscValue(), "BIN",
									DiscountUtil.calculateDiscount(txnAmount, discountBinBased.getBinDiscType(),
											discountBinBased.getBinDiscValue()));
							discountListFinal.add(discBin);
						}
					}

					// GENERIC
					if (dms.getMerchantSpecific() == 0 && dms.getTerminalSpecific() == 0 && dms.getTimeSpecific() == 0
							&& dms.getBinSpecific() == 0) {
						DiscountDecisionModel d = new DiscountDecisionModel(dms.getId(), dms.getDiscType(),
								dms.getDiscValue(), "GENERIC",
								DiscountUtil.calculateDiscount(txnAmount, dms.getDiscType(), dms.getDiscValue()));
						discountListFinal.add(d);
					}

					// 5. Category Based
					// 6. City Based
					// 7. KYC Based

				}

				sb.append(serialNumber + ": Final Discount List");
				sb.append("\n");

				for (DiscountDecisionModel m : discountListFinal) {
					sb.append(serialNumber + ": DiscountId:" + m.getDiscountId() + " DiscountType:"
							+ m.getDiscountType() + " DiscountCategory:" + m.getDiscountCategory() + " Discount:"
							+ m.getDiscountAmount());
					sb.append("\n");
				}

				DiscountDecisionModel maxDiscountModel = discountListFinal.stream()
						.max(Comparator.comparingDouble(model -> Double.parseDouble(model.getDiscountAmount())))
						.orElse(null);

				if (maxDiscountModel != null) {

					DiscountMaster master = discountMasterService.fetchById(maxDiscountModel.getDiscountId());
					discId = master.getId();

					DiscountSlab slab = discountSlabService.fetchByDiscId(discId);

					discAmount = finalDiscountedAmount(Double.parseDouble(maxDiscountModel.getDiscountAmount()),
							slab.getMaxDiscountPerTxn());

					String discountedNetAmount = DiscountUtil.calculateDiscountedAmount(txnAmount,
							String.valueOf(discAmount));

					netAmount = Double.parseDouble(discountedNetAmount);

					sb.append(serialNumber + ": @@ MAX DISCOUNT SELECTED DiscountId:" + maxDiscountModel.getDiscountId()
							+ " DiscountType:" + maxDiscountModel.getDiscountType() + " DiscountCategory:"
							+ maxDiscountModel.getDiscountCategory() + " DiscountAmount:"
							+ maxDiscountModel.getDiscountAmount() + " CalculatedNetAmount:" + discountedNetAmount);
					sb.append("\n");

					if (master.getDiscAllowedTxtPerDay() != 0) {
						int txnPerDay = discountLogService.fetchTxnCountPerDay(cardNumberMasked, discId);
						if (txnPerDay >= master.getDiscAllowedTxtPerDay()) {
							sb.append(serialNumber + ": Daily Discount Limit Exceeded");
							sb.append("\n");
							resp = UtilAccess.generateResponse("0003", "Daily Discount Limit Exceeded");
							return resp;
						}
					}

					if (master.getDiscAllowedTxtPerWeek() != 0) {
						String[] weeks = DiscountUtil.getCurrentWeekDates(new Date());
						int txnPerWeek = discountLogService.fetchTxnCountPerWeek(cardNumberMasked, discId, weeks[0],
								weeks[1]);
						if (txnPerWeek >= master.getDiscAllowedTxtPerWeek()) {
							sb.append(serialNumber + ": Weekly Discount Limit Exceeded");
							sb.append("\n");
							resp = UtilAccess.generateResponse("0003", "Weekly Discount Limit Exceeded");
							return resp;
						}
					}

					if (master.getDiscAllowedTxtPerMonth() != 0) {
						int txnPerMonth = discountLogService.fetchTxnCountPerMonth(cardNumberMasked, discId);
						if (txnPerMonth >= master.getDiscAllowedTxtPerMonth()) {
							sb.append(serialNumber + ": Monthly Discount Limit Exceeded");
							sb.append("\n");
							resp = UtilAccess.generateResponse("0003", "Monthly Discount Limit Exceeded");
							return resp;
						}
					}

					if (master.getDiscAllowedTxtPerYear() != 0) {
						int txnPerYear = discountLogService.fetchTxnCountPerYear(cardNumberMasked, discId);
						if (txnPerYear >= master.getDiscAllowedTxtPerYear()) {
							sb.append(serialNumber + ": Yearly Discount Limit Exceeded");
							sb.append("\n");
							resp = UtilAccess.generateResponse("0003", "Yearly Discount Limit Exceeded");
							return resp;
						}
					}

					if (master.getBugdetCapping() == 1) {
						double budget = master.getBudgetAmountAvbl()
								- Double.parseDouble(maxDiscountModel.getDiscountAmount());
						if (!(budget < 0)) {
							resp.setCode("0000");
							resp.setMessage("Success");
							HashMap<Object, Object> dataValues = new HashMap<Object, Object>();
							dataValues.put("discountName", master.getDiscName());

							dataValues.put("netAmount", netAmount);
							dataValues.put("discountAmnt", discAmount);
							dataValues.put("discountPercentage",
									maxDiscountModel.getDiscountType().equalsIgnoreCase("AMOUNT") ? "0"
											: maxDiscountModel.getDiscountValue());
							dataValues.put("discId", discId);
							resp.setData(dataValues);
						} else {
							sb.append(serialNumber + ": Discount Budget Not Available");
							sb.append("\n");
							resp = UtilAccess.generateResponse("0002", "Discount Budget Not Available");
						}
					} else {
						resp.setCode("0000");
						resp.setMessage("Success");
						HashMap<Object, Object> dataValues = new HashMap<Object, Object>();
						dataValues.put("discountName", master.getDiscName());
						dataValues.put("netAmount", netAmount);
						dataValues.put("discountAmnt", discAmount);
						dataValues.put("discountPercentage",
								maxDiscountModel.getDiscountType().equalsIgnoreCase("AMOUNT") ? "0"
										: maxDiscountModel.getDiscountValue());
						dataValues.put("discId", discId);
						resp.setData(dataValues);
					}

				} else {
					sb.append(serialNumber + ": Total 0 Discount Found.");
					sb.append("\n");
					resp = UtilAccess.generateResponse("0002", "No Discount Found");
				}
			} else {
				sb.append(serialNumber + ": Total 0 Discount Found.");
				sb.append("\n");
				resp = UtilAccess.generateResponse("0001", "No Discount Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp = UtilAccess.generateResponse("9999", "Something Went Wrong");
		}
		finally {
			AgLogger.logInfo(sb.toString());
		}
		return resp;
	}

	public static double finalDiscountedAmount(double discountedAmount, double maxAmountAllowed) {
		if (discountedAmount >= maxAmountAllowed) {
			AgLogger.logInfo("Discount Is Greater Than Max Discount So Max Discount Is Used");
			return maxAmountAllowed;
		} else {
			return discountedAmount;
		}
	}
}
