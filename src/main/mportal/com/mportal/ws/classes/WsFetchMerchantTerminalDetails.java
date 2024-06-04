package com.mportal.ws.classes;

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
import com.ag.mportal.model.MerchantTerminalDetailsModel;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.MerchantTerminalDetailsService;

@Component("com.mportal.ws.classes.WsFetchMerchantTerminalDetails")
public class WsFetchMerchantTerminalDetails implements Wisher {

	@Autowired
	MerchantTerminalDetailsService merchantTerminalDetailsService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		String TxnId = (rm.getAdditionalData().containsKey("txnId")) ? rm.getAdditionalData().get("txnId").toString()
				: null;
		String isDetailRequired = (rm.getAdditionalData().containsKey("isDetailRequired"))
				? rm.getAdditionalData().get("isDetailRequired").toString()
				: null;
		try {

			if (Objects.isNull(TxnId)) {
				return UtilAccess.generateResponse("0001", "Record Not Exists");
			}
			if (isDetailRequired != null && isDetailRequired.equals("Y")) {

				ReportModel details = merchantTerminalDetailsService.txnDetails(Integer.parseInt(TxnId));

				if (details != null) {
					List<MerchantTerminalDetailsModel> mst = merchantTerminalDetailsService
							.listMerchantTerminalDetails(Integer.parseInt(TxnId));

					HashMap<Object, Object> obj = new HashMap<Object, Object>();
					obj.put("lst", mst);
					obj.put("details", details);
					return UtilAccess.generateResponse("0000", "SUCCESS", obj);

				} else {
					return UtilAccess.generateResponse("0002", "No Record Found");
				}

			} else {

				List<MerchantTerminalDetailsModel> mst = merchantTerminalDetailsService
						.listMerchantTerminalDetails(Integer.parseInt(TxnId));
				if (mst != null) {
					HashMap<Object, Object> obj = new HashMap<Object, Object>();
					obj.put("lst", mst);
					return UtilAccess.generateResponse("0000", "SUCCESS", obj);

				} else {
					return UtilAccess.generateResponse("0003", "No Detail Record Found");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ", e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}