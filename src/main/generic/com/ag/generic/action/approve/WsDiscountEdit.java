package com.ag.generic.action.approve;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.KeyValueModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.MakerCheckerDataService;
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

@Component("com.ag.generic.action.approve.WsDiscountEdit")
public class WsDiscountEdit implements WisherForApprover {

	@Autowired
	MakerCheckerDataService mdService;

	@Autowired
	DiscountMasterService discountMasterService;

	@Autowired
	DiscountSlabService discountSlabService;

	@Autowired
	DiscountBinService discountBinService;

	@Autowired
	DiscountMerchantService discountMerchantService;

	@Autowired
	DiscountDateTimeService discountDateTimeService;

	@Autowired
	DiscountMerchantTerminalService discountMerchantTerminalService;

	@Autowired
	AuditLogService auditLogService;

	@Override
	public ResponseModel doProcess(RequestModel rm, MakerCheckerConfig mck, MakerCheckerData md) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + getClass());
		ResponseModel response = new ResponseModel();

		String deletedRecords = "";

		String remarks = (rm.getAdditionalData().containsKey("remarks"))
				? rm.getAdditionalData().get("remarks").toString()
				: null;

		String requestedData = md.getRequestedData();
		AgLogger.logInfo("REQUESTED DATA: " + requestedData);
		AgLogger.logInfo("REMARKS: " + remarks);

		DiscountAddModel discountModel = null;
		try {
			discountModel = new Gson().fromJson(requestedData, DiscountAddModel.class);

			if (discountModel != null) {
				DiscountMaster discountMaster = discountMasterService.fetchById(discountModel.getDiscId());
				discountMaster.setBinSpecific(discountModel.isBinBased() ? 1 : 0);
				discountMaster.setMerchantSpecific(discountModel.isMerchantBased() ? 1 : 0);
				discountMaster.setTimeSpecific(discountModel.isDateBased() ? 1 : 0);
				discountMaster.setTerminalSpecific(discountModel.isTerminalBased() ? 1 : 0);
				discountMaster.setMerchantBasedDiscount(discountModel.isMerchantBased() ? 1 : 0);
				if (discountModel.isMerchantLogin()) {
					discountMaster.setDiscCampaignType("MERCHANT");
				} else {
					discountMaster.setDiscCampaignType("ADMIN");
				}
				discountMaster.setBugdetCapping(discountModel.isBudgetCapping() ? 1 : 0);
				if (discountModel.isBudgetCapping()) {
					if (discountMaster.getBudgetAmountTotal() == 0.0 && discountMaster.getBudgetAmountAvbl() == 0.0) {
						discountMaster.setBudgetAmountTotal(Double.parseDouble(discountModel.getBudgetoryAmount()));
						discountMaster.setBudgetAmountAvbl(Double.parseDouble(discountModel.getBudgetoryAmount()));
					} else {
						discountMaster.setBudgetAmountTotal(Double.parseDouble(discountModel.getBudgetoryAmount()));
					}
				}
				discountMaster.setDiscAllowedTxtPerDay(Integer.parseInt(discountModel.getDiscTxnPerDay()));
				discountMaster.setDiscAllowedTxtPerWeek(Integer.parseInt(discountModel.getDiscTxnPerWeek()));
				discountMaster.setDiscAllowedTxtPerMonth(Integer.parseInt(discountModel.getDiscTxnPerMonth()));
				discountMaster.setDiscAllowedTxtPerYear(Integer.parseInt(discountModel.getDiscTxnPerYear()));
				discountMaster.setUpdBy(rm.getUserid());
				discountMaster.setUpdOn(new Timestamp(new Date().getTime()));
				discountMasterService.update(discountMaster);

				DiscountSlab discountSlab = discountSlabService.fetchByDiscId(discountMaster.getId());
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
				discountSlabService.update(discountSlab);

				List<DiscountBin> binList = discountBinService.fetchAllByDiscId(discountMaster.getId());
				if (binList.size() != 0) {
					deletedRecords += " \nDELETED DISC_BIN: " + new Gson().toJson(binList);
					discountBinService.deleteByDiscId(discountMaster.getId());
				}
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

				List<DiscountDateTime> dateTimeList = discountDateTimeService.fetchByDisc(discountMaster.getId());
				if (dateTimeList.size() != 0) {
					deletedRecords += " \nDELETED DISC_DATE_TIME: " + new Gson().toJson(dateTimeList);
					discountDateTimeService.deleteByDiscId(discountMaster.getId());
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

				List<DiscountMerchant> merList = discountMerchantService.fetchAllByDiscId(discountMaster.getId());
				if (merList.size() != 0) {
					deletedRecords += " \nDELETED DISC_MERCHANT: " + new Gson().toJson(merList);
					discountMerchantService.deleteByDiscId(discountMaster.getId());
				}

				List<DiscountMerchantTerminal> merTerminalList = discountMerchantTerminalService
						.fetchByDiscId(discountMaster.getId());
				if (merTerminalList.size() != 0) {
					deletedRecords += " \nDELETED DISC_MERCHANT_TERMINAL: " + new Gson().toJson(merTerminalList);
					discountMerchantTerminalService.deleteByDiscId(discountMaster.getId());
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
		} finally {
			AuditLog adt = new AuditLog();
			adt.setUserId(rm.getUserid());
			adt.setTid("N/A");
			adt.setEntryDate(new Timestamp(new Date().getTime()));
			adt.setRequest(new Gson().toJson(discountModel));
			adt.setResponse(deletedRecords + " \nRESPONSE:" + new Gson().toJson(response));
			adt.setRequestMode("PORTAL");
			adt.setRequestIp("N/A");
			adt.setTxnType("EDIT DISCOUNT");
			adt.setMid("N/A");
			adt.setSerialNum("N/A");
			adt.setCorpId(rm.getCorpId());
			auditLogService.insertAuditLog(adt);
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
