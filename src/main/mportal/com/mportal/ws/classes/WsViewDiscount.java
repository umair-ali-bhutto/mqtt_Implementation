package com.mportal.ws.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.DiscountBin;
import com.ag.mportal.entity.DiscountDateTime;
import com.ag.mportal.entity.DiscountMaster;
import com.ag.mportal.entity.DiscountMerchant;
import com.ag.mportal.entity.DiscountMerchantTerminal;
import com.ag.mportal.entity.DiscountSlab;
import com.ag.mportal.model.DiscountAddModel;
import com.ag.mportal.services.DiscountBinService;
import com.ag.mportal.services.DiscountDateTimeService;
import com.ag.mportal.services.DiscountMasterService;
import com.ag.mportal.services.DiscountMerchantService;
import com.ag.mportal.services.DiscountMerchantTerminalService;
import com.ag.mportal.services.DiscountSlabService;
import com.google.gson.Gson;

@Component("com.mportal.ws.classes.WsViewDiscount")
public class WsViewDiscount implements Wisher {

	@Autowired
	UtilAccess utilAccess;

	@Autowired
	DiscountMasterService discountMasterService;

	@Autowired
	DiscountBinService discountBinService;

	@Autowired
	DiscountDateTimeService discountDateTimeService;

	@Autowired
	DiscountSlabService discountSlabService;

	@Autowired
	DiscountMerchantService discountMerchantService;

	@Autowired
	DiscountMerchantTerminalService discountMerchantTerminalService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {

			case "FETCH":
				response = fetch(rm);
				break;

			case "FETCH_DETAILS":
				response = fetchDetails(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	public ResponseModel fetch(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String name = Objects.isNull(rm.getAdditionalData().get("name")) ? null
					: rm.getAdditionalData().get("name").toString().trim();
			String startDate = Objects.isNull(rm.getAdditionalData().get("startDate")) ? null
					: rm.getAdditionalData().get("startDate").toString().trim();
			String endDate = Objects.isNull(rm.getAdditionalData().get("endDate")) ? null
					: rm.getAdditionalData().get("endDate").toString().trim();
			String status = Objects.isNull(rm.getAdditionalData().get("status")) ? null
					: rm.getAdditionalData().get("status").toString().trim();

			String eDate = null;
			String sDate = null;
			String discStatus = null;
			SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String outputDateS = null;
			String outputDateE = null;

			if (!Objects.isNull(endDate)) {
				eDate = endDate + " 00:00:00.000";
				Date dateE = inputFormat.parse(eDate);
				outputDateE = outputFormat.format(dateE);
			}
			if (!Objects.isNull(startDate)) {
				sDate = startDate + " 00:00:00.000";
				Date dateS = inputFormat.parse(sDate);
				outputDateS = outputFormat.format(dateS);
			}
			if (!Objects.isNull(status)) {
				discStatus = status;
				if (discStatus.equalsIgnoreCase("Y")) {
					discStatus = "1";
				} else if (discStatus.equalsIgnoreCase("N")) {
					discStatus = "0";
				}
			}

			List<DiscountMaster> lstMasterDiscount = discountMasterService.fetchLstDiscountMaster(name, discStatus,
					outputDateS, outputDateE);

			if (lstMasterDiscount.size() != 0) {

				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("lst", lstMasterDiscount);

				response.setCode("0000");
				response.setMessage("Success.");
				response.setData(obj);

			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	public ResponseModel fetchDetails(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String discId = (rm.getAdditionalData().containsKey("discId"))
					? rm.getAdditionalData().get("discId").toString()
					: "N/A";
			DiscountMaster discountMaster = discountMasterService.fetchById(Long.parseLong(discId));
			
			DiscountSlab discountSlab = discountSlabService.fetchByDiscId(discountMaster.getId());
			List<DiscountMerchant> discountMerchantList = discountMerchantService.fetchAllByDiscId(discountMaster.getId());
			List<DiscountMerchantTerminal> discountMerchantTerminalList = discountMerchantTerminalService.fetchByDiscId(discountMaster.getId());
			List<DiscountDateTime> discountDateTimeList = discountDateTimeService.fetchByDisc(discountMaster.getId());
			List<DiscountBin> discountBinList = discountBinService.fetchAllByDiscId(discountMaster.getId());

			HashMap<Object, Object> obj = new HashMap<Object, Object>();
			obj.put("discountMaster", discountMaster);
			obj.put("discountSlab", discountSlab);
			obj.put("discountMerchantList", discountMerchantList);
			obj.put("discountMerchantTerminalList", discountMerchantTerminalList);
			obj.put("discountDateTimeList", discountDateTimeList);
			obj.put("discountBinList", discountBinList);
			
			return UtilAccess.generateResponse("0000", "SUCCESS", obj);

		} catch (

		Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}