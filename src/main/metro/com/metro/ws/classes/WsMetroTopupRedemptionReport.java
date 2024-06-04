package com.metro.ws.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.metro.entity.MetroTxnDetail;
import com.ag.metro.services.MetroTxnDetailService;

@Component("com.metro.ws.classes.WsMetroTopupRedemptionReport")
public class WsMetroTopupRedemptionReport implements Wisher {

	@Autowired
	MetroTxnDetailService metroTxnDetailService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
			String screenType = rm.getAdditionalData().get("screen").toString();

			String store = rm.getAdditionalData().get("store") == null ? null
					: rm.getAdditionalData().get("store").toString();

			if (store != null) {
				// store = String.format("%.0f", store);
				store = StringUtils.leftPad(store, 15, "0");
			}

			String batchNumber = rm.getAdditionalData().get("batchNumber") == null ? null
					: rm.getAdditionalData().get("batchNumber").toString();

			Date dateFrom = rm.getAdditionalData().get("dateFrom") == null ? null
					: sd.parse(rm.getAdditionalData().get("dateFrom").toString());

			Date dateTo = rm.getAdditionalData().get("dateTo") == null ? null
					: sd.parse(rm.getAdditionalData().get("dateTo").toString());

			String cardType = rm.getAdditionalData().get("cardType") == null ? null
					: rm.getAdditionalData().get("cardType").toString();

			String txnStatus = rm.getAdditionalData().get("txnStatus") == null ? null
					: rm.getAdditionalData().get("txnStatus").toString();

			if (screenType.equals("TOPUP_DETAILS")) {

				List<MetroTxnDetail> lst = metroTxnDetailService.fetchResultTopup(cardType, store, batchNumber,
						dateFrom, dateTo);
				if (lst.size() != 0) {
					response.setCode("0000");
					response.setMessage("Success.");
					HashMap<Object, Object> obj = new HashMap<Object, Object>();
					obj.put("lst", lst);
					String[] blob = metroTxnDetailService.createReportforTopup(lst, rm);
					if (!Objects.isNull(blob)) {
						obj.put("blob", blob[0]);
						obj.put("fileName", blob[1]);
					}
					response.setData(obj);
				} else {
					response.setCode("0002");
					response.setMessage("No Record Found.");
				}
			} 
			
			
			
			
			
			else if (screenType.equals("REDEMPTION_DETAILS")) {
				List<MetroTxnDetail> lst = metroTxnDetailService.fetchResultRedemption(cardType, store, txnStatus,
						dateFrom, dateTo);
				if (lst.size() != 0) {
					response.setCode("0000");
					response.setMessage("Success.");
					HashMap<Object, Object> obj = new HashMap<Object, Object>();
					obj.put("lst", lst);
					String[] blob = metroTxnDetailService.createReportforRedemption(lst, rm);
					if (!Objects.isNull(blob)) {
						obj.put("blob", blob[0]);
						obj.put("fileName", blob[1]);
					}
					response.setData(obj);
				} else {
					response.setCode("0002");
					response.setMessage("No Record Found.");
				}

			}
			
			
			
			else {
				response.setCode("0001");
				response.setMessage("Invalid Screen Type.");
			}

//			response.setCode("0000");
//			response.setMessage("SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}
		return response;
	}

}
