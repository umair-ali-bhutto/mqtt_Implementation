package com.mportal.ws.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

import com.ag.generic.util.UtilAccess;

import com.ag.mportal.entity.VwPreauth;
import com.ag.mportal.services.VwPreauthService;

@Component("com.mportal.ws.classes.WsPreAuthReport")
public class WsPreAuthReport implements Wisher {

	@Autowired
	VwPreauthService preauthService;

	@Autowired
	UtilService utilService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			Date frDate = null, tDate = null;
			List<String> midList = new ArrayList<String>();

			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
			String fromdate = rm.getAdditionalData().get("fromdate") == null ? null
					: rm.getAdditionalData().get("fromdate").toString();
			String todate = rm.getAdditionalData().get("todate") == null ? null
					: rm.getAdditionalData().get("todate").toString();
			String authID = rm.getAdditionalData().get("authIDs") == null ? null
					: rm.getAdditionalData().get("authIDs").toString();
			String status = rm.getAdditionalData().get("status") == null ? null
					: rm.getAdditionalData().get("status").toString();

			String currentPage = rm.getAdditionalData().get("currentPage") == null ? null
					: rm.getAdditionalData().get("currentPage").toString();

			String flag = rm.getAdditionalData().get("flag") == null ? null
					: rm.getAdditionalData().get("flag").toString();

			String totalRecords = null;

			if (!Objects.isNull(merchantId) || (!Objects.isNull(fromdate) && !Objects.isNull(todate))
					|| !Objects.isNull(authID)) {

				if (fromdate != null && todate != null) {

					Date finFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
					Date finToDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
					frDate = finFromDate;
					tDate = finToDate;
					long diff = finToDate.getTime() - finFromDate.getTime();
					diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					AgLogger.logInfo("txn rpt diffrence ..........." + diff);
					if (diff > Long.parseLong(AppProp.getProperty("rpt.max.days"))) {
						return UtilAccess.generateResponse("8888", "Data can not be fetched more than "
								+ Long.parseLong(AppProp.getProperty("rpt.max.days")) + " days.");
					}
				}
				if (!Objects.isNull(merchantId) && !merchantId.equals("All")) {
					midList = new ArrayList<String>();
					if (merchantId.contains("[")) {
						merchantId = merchantId.replace("[", "");
					}
					if (merchantId.contains("]")) {
						merchantId = merchantId.replace("]", "");
					}
					String[] tempmid = merchantId.split(", ");
					if (tempmid.length <= Integer.parseInt(AppProp.getProperty("view.link.merchant.cnt"))) {
						String a = String.join("','", tempmid);
						midList.add(a);
					} else {
						return UtilAccess.generateResponse("8881", "You Cannot Select More Than "
								+ AppProp.getProperty("view.link.merchant.cnt") + " Merchant IDs");
					}
//					mId = merchantId;
//					midList = new ArrayList<String>();
//					midList.add(mId);
				}

				int rows = Integer.parseInt(AppProp.getProperty("txn.details.rows").toString());

				int pageNumber = Integer.parseInt(currentPage);

				List<VwPreauth> lst = preauthService.fetchByParams(midList, frDate, tDate, authID, status, rows,
						pageNumber);

				if (lst.size() != 0) {
					HashMap<Object, Object> obj = new HashMap<Object, Object>();

					if (flag.equals("Y")) {
						totalRecords = preauthService.fetchByParamsCount(midList, frDate, tDate, authID, status);
						obj.put("totalRecords", totalRecords);
					}
					obj.put("lst", lst);
					// Y for yes N for no
					obj.put("downloadEnabled", "Y");
					obj.put("rows", rows);

					return UtilAccess.generateResponse("0000", "SUCCESS", obj);

				} else {
					response.setCode("0002");
					response.setMessage("No Data Found.");
				}
			} else {
				response.setCode("0001");
				//response.setMessage("Please Enter Proper Data.");
				response.setMessage("Please Select Date or Merchant ID or Auth ID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}
		return response;
	}

}
