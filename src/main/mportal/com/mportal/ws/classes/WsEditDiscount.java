package com.mportal.ws.classes;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.SDiscountsDetailBin;
import com.ag.mportal.entity.SDiscountsDetailSlab;
import com.ag.mportal.entity.SDiscountsMaster;
import com.ag.mportal.services.SDiscountsDetailBinService;
import com.ag.mportal.services.SDiscountsDetailSlabService;
import com.ag.mportal.services.SDiscountsMasterService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.mportal.ws.classes.WsEditDiscount")
public class WsEditDiscount implements Wisher {

	@Autowired
	UtilAccess utilAccess;

	@Autowired
	SDiscountsMasterService sDiscountsMasterService;

	@Autowired
	SDiscountsDetailBinService sDiscountsDetailBinService;

	@Autowired
	SDiscountsDetailSlabService sDiscountsDetailSlabService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		ArrayList<SDiscountsDetailBin> lstBinDetail = new ArrayList<SDiscountsDetailBin>();
		String rrn = utilAccess.rrn();

		try {
			String userId = rm.getUserid();
			String name = Objects.isNull(rm.getAdditionalData().get("name")) ? null
					: rm.getAdditionalData().get("name").toString().trim();
			String description = Objects.isNull(rm.getAdditionalData().get("description")) ? null
					: rm.getAdditionalData().get("description").toString().trim();
			String startDate = Objects.isNull(rm.getAdditionalData().get("startDate")) ? null
					: rm.getAdditionalData().get("startDate").toString().trim();
			String endDate = Objects.isNull(rm.getAdditionalData().get("endDate")) ? null
					: rm.getAdditionalData().get("endDate").toString().trim();
			Boolean allBinCheckBox = Objects.isNull(rm.getAdditionalData().get("allBinCheckBox")) ? null
					: (Boolean) rm.getAdditionalData().get("allBinCheckBox");
			String status = Objects.isNull(rm.getAdditionalData().get("status")) ? null
					: rm.getAdditionalData().get("status").toString().trim();
			Double rate = Objects.isNull(rm.getAdditionalData().get("rate")) ? null
					: Double.valueOf(rm.getAdditionalData().get("rate").toString().trim());
			String lstBin = Objects.isNull(rm.getAdditionalData().get("lstBin")) ? null
					: rm.getAdditionalData().get("lstBin").toString();
			String discountId = Objects.isNull(rm.getAdditionalData().get("discountId")) ? null
					: rm.getAdditionalData().get("discountId").toString().trim();

			if (Objects.isNull(name) || name.isEmpty() || Objects.isNull(description) || description.isEmpty()
					|| Objects.isNull(startDate) || startDate.isEmpty() || Objects.isNull(endDate) || endDate.isEmpty()
					|| Objects.isNull(allBinCheckBox) || Objects.isNull(status) || Objects.isNull(rate)
					|| Objects.isNull(discountId)) {
				response.setCode("0001");
				response.setMessage("Required parameter/s missing.");
				return response;
			}

			if (!allBinCheckBox) {
				if (Objects.isNull(lstBin) || lstBin.isEmpty()) {
					response.setCode("0002");
					response.setMessage("Bin entries also required.");
					return response;
				} else {
					Type listType = new TypeToken<ArrayList<SDiscountsDetailBin>>() {
					}.getType();
					List<SDiscountsDetailBin> items = new Gson()
							.fromJson(rm.getAdditionalData().get("lstBin").toString(), listType);
					for (SDiscountsDetailBin obj : items) {
						SDiscountsDetailBin sDiscountsDetailBin = createLstDetailBin(obj, userId, rrn);
						lstBinDetail.add(sDiscountsDetailBin);
					}

				}
			}

			SDiscountsMaster discountsMaster = sDiscountsMasterService.fetchByDiscountId(discountId);
			if (Objects.isNull(discountsMaster)) {
				response.setCode("8888");
				response.setMessage("No Record Found.");
				return response;
			}

			List<String> lstDiscountId = new ArrayList<String>();
			lstDiscountId.add(discountsMaster.getDiscId());
			List<SDiscountsDetailSlab> lstDiscountsDetailSlabs = sDiscountsDetailSlabService
					.getSlabByDiscountId(lstDiscountId);
			if (Objects.isNull(lstDiscountsDetailSlabs) || lstDiscountsDetailSlabs.isEmpty()) {
				response.setCode("8888");
				response.setMessage("No Record Found/Rate Not Found.");
				return response;
			}

			String oldBinStatus = discountsMaster.getAllBins();

			String sDate = startDate + " 00:00:00.000";
			String eDate = endDate + " 00:00:00.000";
			discountsMaster.setName(name.trim());
			discountsMaster.setDescription(description.trim());
			discountsMaster.setAllBins(allBinCheckBox ? "Y" : "N");
			if (!startDate.contains("T")) {
				discountsMaster.setDateFrom(utilAccess.getTimeStamp(sDate));
			}
			if (!endDate.contains("T")) {
				discountsMaster.setDateTo(utilAccess.getTimeStamp(eDate));
			}

			if (discountsMaster.getDateFrom().after(discountsMaster.getDateTo())) {
				response.setCode("0002");
				response.setMessage("Start Date should be before the End Date.");
				return response;
			}

			List<SDiscountsMaster> lst = sDiscountsMasterService.fetchDiscountInDateRangeNotInID(
					discountsMaster.getDateFrom(), discountsMaster.getDateTo(), discountId);
			if (!Objects.isNull(lst) && !lst.isEmpty()) {
				response.setCode("0003");
				response.setMessage("Date lie in existing discount records.");
				return response;
			}

			discountsMaster.setSourceRef(rrn);
			discountsMaster.setUpdBy(userId);
			discountsMaster.setUpdOn(new Timestamp(new Date().getTime()));
			discountsMaster.setActive(status);

			SDiscountsDetailSlab discountSlab = lstDiscountsDetailSlabs.get(0);
			discountSlab.setPerc(rate);
			discountSlab.setUpdBy(userId);
			discountSlab.setUpdOn(new Timestamp(new Date().getTime()));
			discountSlab.setSourceRef(rrn);

			boolean flag = sDiscountsMasterService.update(discountsMaster, discountSlab, lstBinDetail, oldBinStatus);

			if (!flag) {
				response.setCode("7777");
				response.setMessage("Something Went Wrong, Please try again.");
				return response;
			}

			response.setCode("0000");
			response.setMessage("SUCCESS");

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	public SDiscountsDetailBin createLstDetailBin(SDiscountsDetailBin node, String userId, String rrn) {

		SDiscountsDetailBin detailBin = new SDiscountsDetailBin();
		detailBin.setBinDesc(node.getBinDesc());
		detailBin.setBinStart(node.getBinStart());
		detailBin.setBinEnd(node.getBinEnd());
		detailBin.setCorpid("00000");
		detailBin.setActive("Y");
		detailBin.setSourceRef(rrn);
		detailBin.setCrBy(userId);
		detailBin.setCrOn(new Timestamp(new Date().getTime()));

		return detailBin;

	}

}