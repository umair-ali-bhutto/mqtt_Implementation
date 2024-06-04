package com.ag.generic.action.approve;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.KeyValueModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountBin;
import com.ag.mportal.entity.DiscountDateTime;
import com.ag.mportal.entity.DiscountMaster;
import com.ag.mportal.entity.DiscountMerchant;
import com.ag.mportal.entity.DiscountMerchantTerminal;
import com.ag.mportal.entity.DiscountSlab;
import com.ag.mportal.model.DiscountAddModel;
import com.ag.mportal.model.DiscountBinBasedListModel;
import com.ag.mportal.model.DiscountDateBasedListModel;
import com.ag.mportal.services.DiscountBinService;
import com.ag.mportal.services.DiscountDateTimeService;
import com.ag.mportal.services.DiscountMasterService;
import com.ag.mportal.services.DiscountMerchantService;
import com.ag.mportal.services.DiscountMerchantTerminalService;
import com.ag.mportal.services.DiscountSlabService;
import com.ag.mportal.util.DiscountUtil;
import com.google.gson.Gson;

@Component("com.ag.generic.action.approve.WsDiscountCreation")
public class WsDiscountCreation implements WisherForApprover {

	@Autowired
	MakerCheckerDataService mdService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	private DiscountMasterService discountMasterService;

	@Autowired
	private DiscountSlabService discountSlabService;

	@Autowired
	private DiscountBinService discountBinService;

	@Autowired
	private DiscountMerchantService discountMerchantService;

	@Autowired
	private DiscountDateTimeService discountDateTimeService;

	@Autowired
	private DiscountMerchantTerminalService discountMerchantTerminalService;

	@Override
	public ResponseModel doProcess(RequestModel rm, MakerCheckerConfig mck, MakerCheckerData md) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + getClass());
		ResponseModel response = new ResponseModel();

		String remarks = (rm.getAdditionalData().containsKey("remarks"))
				? rm.getAdditionalData().get("remarks").toString()
				: null;

		String requestedData = md.getRequestedData();
		AgLogger.logInfo("REQUESTED DATA: " + requestedData);
		AgLogger.logInfo("REMARKS: " + remarks);

		try {
			DiscountAddModel discountModel = new Gson().fromJson(requestedData, DiscountAddModel.class);

			if (discountModel != null) {
				DiscountMaster discountMaster = new DiscountMaster();
				discountMaster.setDiscName(discountModel.getDiscName());
				discountMaster.setDiscDescription(discountModel.getDiscDescription());
				discountMaster.setStartDate(convertStringToTimestamp(discountModel.getStartDate(), "dd-MM-yyyy"));
				discountMaster.setEndDate(convertStringToTimestamp(discountModel.getEndDate(), "dd-MM-yyyy"));
				discountMaster.setDiscStatus(DiscountUtil.getDiscountStatusFromStatus("ACTIVE"));
				discountMaster.setBinSpecific(discountModel.isBinBased() ? 1 : 0);
				discountMaster.setMerchantSpecific(discountModel.isMerchantBased() ? 1 : 0);
				discountMaster.setTimeSpecific(discountModel.isDateBased() ? 1 : 0);
				discountMaster.setTerminalSpecific(discountModel.isTerminalBased() ? 1 : 0);
				discountMaster.setMerchantBasedDiscount(discountModel.isMerchantBased() ? 1 : 0);
				discountMaster.setKycSpecific(0);
				discountMaster.setCategorySpecific(0);
				discountMaster.setCitySpecific(0);

				discountMaster.setGlAccount("");
				discountMaster.setGlAccount2("");

				if (discountModel.isMerchantLogin()) {
					discountMaster.setDiscCampaignType("MERCHANT");
				} else {
					discountMaster.setDiscCampaignType("ADMIN");
				}

				discountMaster.setBugdetCapping(discountModel.isBudgetCapping() ? 1 : 0);

				if (discountModel.isBudgetCapping()) {
					discountMaster.setBudgetAmountTotal(Double.parseDouble(discountModel.getBudgetoryAmount()));
					discountMaster.setBudgetAmountAvbl(Double.parseDouble(discountModel.getBudgetoryAmount()));
				} else {
					discountMaster.setBudgetAmountTotal(0.0);
					discountMaster.setBudgetAmountAvbl(0.0);
				}

				discountMaster.setDiscAllowedTxtPerDay(Integer.parseInt(discountModel.getDiscTxnPerDay()));
				discountMaster.setDiscAllowedTxtPerWeek(Integer.parseInt(discountModel.getDiscTxnPerWeek()));
				discountMaster.setDiscAllowedTxtPerMonth(Integer.parseInt(discountModel.getDiscTxnPerMonth()));
				discountMaster.setDiscAllowedTxtPerYear(Integer.parseInt(discountModel.getDiscTxnPerYear()));
				discountMaster.setEntryBy(rm.getUserid());
				discountMaster.setEntryOn(new Timestamp(new Date().getTime()));
				discountMaster.setCorpId(rm.getCorpId());
				discountMaster.setCurrency(DiscountUtil.getCurrencyFromCode("PKR"));
				discountMasterService.insert(discountMaster);

				DiscountSlab discountSlab = new DiscountSlab();
				discountSlab.setDiscId(discountMaster.getId());

				if (discountModel.isDiscAmountLimit()) {
					discountSlab.setAmountFrom(Double.parseDouble(discountModel.getMinimumAmount()));
					discountSlab.setAmountTo(Double.parseDouble(discountModel.getMaximumAmount()));
				} else {
					discountSlab.setAmountFrom(1.0);
					discountSlab.setAmountTo(999999999.99);
				}

				discountSlab.setDiscType(DiscountUtil.getDiscountTypeFromCode(discountModel.getDiscType()));
				discountSlab.setDiscValue(Double.parseDouble(discountModel.getDiscount()));
				discountSlab.setMaxDiscountPerTxn(Double.parseDouble(discountModel.getMaxDiscAmountPerTxn()));
				discountSlabService.insert(discountSlab);

				for (DiscountBinBasedListModel mdl : discountModel.getBinBasedList()) {
					DiscountBin discountBin = new DiscountBin();
					discountBin.setBinFrom(mdl.getFromBin());
					discountBin.setBinTo(mdl.getToBin());
					discountBin.setBinProductName(mdl.getProductDescription());
					discountBin.setBinProductId(mdl.getProductId());
					discountBin.setBinDiscType(DiscountUtil.getDiscountTypeFromCode(mdl.getDiscountType()));
					discountBin.setBinDiscValue(
							mdl.getDiscountType().equals("Rate") ? Double.parseDouble(mdl.getDiscountPercentage())
									: Double.parseDouble(mdl.getDiscountAmount()));
					discountBin.setMaxDiscPerTxn(Double.parseDouble(discountModel.getMaxDiscAmountPerTxn()));
					discountBin.setDiscId(discountMaster.getId());
					discountBinService.insert(discountBin);

				}

				for (DiscountDateBasedListModel mdl : discountModel.getDateBasedList()) {
					for (Date m : mdl.getDays()) {
						DiscountDateTime discountDateTime = new DiscountDateTime();
						discountDateTime.setDiscId(discountMaster.getId());
						discountDateTime.setDiscStartTime(getTimestampForDateTimeSpecific(
								convertStringToTimestamp(m.toString(), "EEE MMM dd HH:mm:ss zzz yyyy"),
								convertStringToTimestamp(mdl.getStartTime().toString(),
										"EEE MMM dd HH:mm:ss zzz yyyy")));
						discountDateTime.setDiscEndTime(getTimestampForDateTimeSpecific(
								convertStringToTimestamp(m.toString(), "EEE MMM dd HH:mm:ss zzz yyyy"),
								convertStringToTimestamp(mdl.getEndTime().toString(), "EEE MMM dd HH:mm:ss zzz yyyy")));
						discountDateTimeService.insert(discountDateTime);

					}
				}

				DiscountMerchant discountMerchant = null;
				if (discountModel.isMerchantBased()) {
					for (KeyValueModel m : discountModel.getMid()) {
						discountMerchant = new DiscountMerchant();
						discountMerchant.setMid(m.getKey());
						discountMerchant.setMerchantName(m.getValue().split(" \\(" + m.getKey() + "\\)")[0]);
						discountMerchant
								.setMaxDiscountPerTxn(Double.parseDouble(discountModel.getMaxDiscAmountPerTxn()));
						discountMerchant.setDiscId(discountMaster.getId());

						discountMerchantService.insert(discountMerchant);

						if (discountModel.isTerminalBased()) {
							List<String> filteredTids = discountModel.getTid().stream()
									.filter(tid -> tid.contains("(" + m.getKey() + ")")).collect(Collectors.toList());
							if (!filteredTids.isEmpty()) {
								for (String tid : filteredTids) {
									DiscountMerchantTerminal discountMerchantTerminal = new DiscountMerchantTerminal();
									discountMerchantTerminal.setDiscId(discountMaster.getId());
									discountMerchantTerminal.setDiscMidId(discountMerchant.getId());
									discountMerchantTerminal.setTerminalId(tid.split(" \\(" + m.getKey() + "\\)")[0]);
									discountMerchantTerminal.setTerminalDesc("");
									discountMerchantTerminal.setMaxDiscountPerTxn(
											Double.parseDouble(discountModel.getMaxDiscAmountPerTxn()));
									discountMerchantTerminalService.insert(discountMerchantTerminal);
								}
							} else {
								AgLogger.logInfo("No TID Selected For MID:" + m.getKey());
							}

						}

					}
				}

				md.setStatus("APPROVED");
				md.setApprovedBy(Integer.parseInt(rm.getUserid()));
				md.setApprovedOn(new Timestamp(new Date().getTime()));
				md.setApproverRemarks(remarks);
				mdService.update(md);

				response.setCode("0000");
				response.setMessage("Success");
			} else {
				response.setCode("0001");
				response.setMessage("Unable To Parse Value");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

	public static Timestamp convertStringToTimestamp(String dateString, String format) {
		try {
			return new Timestamp(new SimpleDateFormat(format).parse(dateString).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Timestamp getTimestampForDateTimeSpecific(Timestamp dateTimestamp, Timestamp timeTimestamp) {
		try {
			Calendar dateCal = Calendar.getInstance();
			dateCal.setTimeInMillis(dateTimestamp.getTime());
			int year = dateCal.get(Calendar.YEAR);
			int month = dateCal.get(Calendar.MONTH);
			int dayOfMonth = dateCal.get(Calendar.DAY_OF_MONTH);

			Calendar timeCal = Calendar.getInstance();
			timeCal.setTimeInMillis(timeTimestamp.getTime());
			int hourOfDay = timeCal.get(Calendar.HOUR_OF_DAY);
			int minute = timeCal.get(Calendar.MINUTE);
			int second = timeCal.get(Calendar.SECOND);

			Calendar combinedCal = Calendar.getInstance();
			combinedCal.set(year, month, dayOfMonth, hourOfDay, minute, second);
			return new Timestamp(combinedCal.getTimeInMillis());
		} catch (Exception e) {
			return null;
		}

	}

}
