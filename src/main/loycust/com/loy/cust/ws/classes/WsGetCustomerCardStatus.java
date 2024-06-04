package com.loy.cust.ws.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.service.impl.UserLoginServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CustomerUserMaster;
import com.ag.loy.adm.entity.UserStatusMaster;
import com.ag.loy.adm.service.CustomerUserMasterService;
import com.ag.loy.adm.service.UserStatusService;

@Component("com.loy.cust.ws.classes.WsGetCustomerCardStatus")
public class WsGetCustomerCardStatus implements Wisher {

	@Autowired
	UserLoginServiceImpl userLoginServiceImpl;

	@Autowired
	UserStatusService userStatusService;

	@Autowired
	CustomerUserMasterService customerUserMasterService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			UserLogin ukl = userLoginServiceImpl.validetUserid(Integer.parseInt(rm.getUserid()));
			if (ukl != null) {

				CustomerUserMaster c = customerUserMasterService.findAllByUidCorpId(ukl.getUserCode(), rm.getCorpId());

				if (c != null) {

					UserStatusMaster usm = userStatusService.fetchByUsertype(rm.getCorpId(), c.getUsertype());

					if (usm != null && c.getStatus().equals(usm.getId().getStatus())) {
						String nameOnCard = c.getUserdispname();
						String cardNumber = c.getId().getUserid();
						String cid = c.getId().getCid();
						String issueDate = new SimpleDateFormat("MM/yy").format(c.getIssuedate());
						String expiryDate = new SimpleDateFormat("MM/yy").format(c.getExpirydate());
						Boolean cardStatus = true;

						List<String> cardList = new ArrayList<String>();
						cardList.add("12124125125125");
						cardList.add("54383838383838");
						cardList.add("99898989898989");

						HashMap<Object, Object> oj = new HashMap<Object, Object>();
						oj.put("nameOnCard", nameOnCard);
						oj.put("cardNumber", cardNumber);
						oj.put("cid", cid);
						oj.put("issueDate", issueDate);
						oj.put("expiryDate", expiryDate);
						oj.put("cardStatus", cardStatus);
						oj.put("cardList", cardList);
						oj.put("cardIssuerType", "0008");
						oj.put("cardIssuerSubType", "00013");

						response.setCode("0000");
						response.setData(oj);
						response.setMessage("SUCCESS");

					} else {
						response.setCode("0001");
						response.setMessage("No Active Cards Found.");
					}

				} else {
					response.setCode("0002");
					response.setMessage("No Active Cards Found.");
				}

			} else {
				response.setCode("0003");
				response.setMessage("Invalid User.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}