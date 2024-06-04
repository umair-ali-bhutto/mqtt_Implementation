package com.loy.adm.ws.classes;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.service.MerchantMasterService;
import com.ag.loy.adm.entity.AccountPayments;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.entity.MerchantMaster;
import com.ag.loy.adm.model.MerchantKycModel;
import com.ag.loy.adm.service.CorporateMasterService;
import com.ag.loy.adm.service.impl.AccountPaymentsServiceImpl;

@Component("com.loy.adm.ws.classes.WsMerchantSearch")
public class WsMerchantSearch implements Wisher {

	@Autowired
	AccountPaymentsServiceImpl accountPaymentsServiceImpl;

	@Autowired
	CorporateMasterService corporateMasterService;
	
	@Autowired
	MerchantMasterService merchantMasterService;
	
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
				response = fetchRequest(rm);
				break;
			case "INIT":
				response = initRequest(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}
			

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}
	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpId = rm.getCorpId();
			int groupCode = 0;
			groupCode = Objects.isNull(rm.getAdditionalData().get("groupCode")) ? groupCode
					: Integer.parseInt(rm.getAdditionalData().get("groupCode").toString());
			
			List<CorporateMaster> lst = corporateMasterService.fetchAllList(groupCode, corpId);
			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("corporates", lst);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
				response.setData(obj);
			}else {
				response.setCode("0001");
				response.setMessage("No Corporated Found.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
	
	private static final DecimalFormat df = new DecimalFormat("#,###.##");
	
	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String merchantId = Objects.isNull(rm.getAdditionalData().get("merchantId")) ? null
					: rm.getAdditionalData().get("merchantId").toString().trim();
			
			String corpId = Objects.isNull(rm.getAdditionalData().get("corpId")) ? null
					: rm.getAdditionalData().get("corpId").toString().trim();
			
			rm.setCorpId(corpId);
			
			boolean isDataRequired = (rm.getAdditionalData().containsKey("isDataRequired"))
					? Boolean.parseBoolean(rm.getAdditionalData().get("isDataRequired").toString())
					: false;

			if (merchantId != null) {
				MerchantMaster mm = merchantMasterService.findAllByMidCorpId(merchantId, rm.getCorpId());
				if(!Objects.isNull(mm)) {
					HashMap<Object, Object> okj = new HashMap<Object, Object>();
					double totalSet = 0.0;
					double totalUnset = 0.0;
					double totalPaid = 0.0;

					List<MerchantKycModel> ls = new ArrayList<MerchantKycModel>();

					MerchantKycModel mName = new MerchantKycModel();
					mName.setKycTitle("Merchant Name");
					mName.setKycValue(mm.getName());
					ls.add(mName);
					
					MerchantKycModel mReg = new MerchantKycModel();
					mReg.setKycTitle("Region");
					mReg.setKycValue(mm.getRegion());
					ls.add(mReg);
					
					MerchantKycModel mCityId = new MerchantKycModel();
					mCityId.setKycTitle("City");
					mCityId.setKycValue(mm.getCityId());
					ls.add(mCityId);
					
					MerchantKycModel mArea = new MerchantKycModel();
					mArea.setKycTitle("Area");
					mArea.setKycValue(mm.getArea());
					ls.add(mArea);
					
					MerchantKycModel mAddr1 = new MerchantKycModel();
					mAddr1.setKycTitle("Address");
					mAddr1.setKycValue(mm.getAddress1());
					ls.add(mAddr1);
					
					MerchantKycModel mAddr2 = new MerchantKycModel();
					mAddr2.setKycTitle("");
					mAddr2.setKycValue(mm.getAddress2());
					ls.add(mAddr2);
					
					MerchantKycModel mLocLat = new MerchantKycModel();
					mLocLat.setKycTitle("Latitude");
					mLocLat.setKycValue(mm.getLocLat().toString());
					ls.add(mLocLat);
					
					MerchantKycModel mLocLng = new MerchantKycModel();
					mLocLng.setKycTitle("Longitude");
					mLocLng.setKycValue(mm.getLocLng().toString());
					ls.add(mLocLng);
					
					MerchantKycModel mCatCode = new MerchantKycModel();
					mCatCode.setKycTitle("Merchant Category");
					mCatCode.setKycValue(mm.getCatCode());
					ls.add(mCatCode);
					
					MerchantKycModel mActive = new MerchantKycModel();
					mActive.setKycTitle("Status");
					mActive.setKycValue(mm.getActive());
					ls.add(mActive);
					

					okj.put("merchantKyc", ls);
					if (isDataRequired) {
						ArrayList<AccountPayments> unsettledTxn = accountPaymentsServiceImpl.fetch(null, null, mm.getGlAcc2(),
								null, null, merchantId, rm.getCorpId());
						ArrayList<AccountPayments> settledTxn = accountPaymentsServiceImpl.fetch(null, null, mm.getGlAcc1(),
								null, null, merchantId, rm.getCorpId());
						ArrayList<AccountPayments> paidTxn = accountPaymentsServiceImpl.fetch(null, null, mm.getGlAcc3(), null,
								null, merchantId, rm.getCorpId());
						
						okj.put("unsettledTxn", unsettledTxn);
						okj.put("settledTxn", settledTxn);
						okj.put("paidTxn", paidTxn);
						
						for(AccountPayments a:unsettledTxn) {
							totalUnset = totalUnset+a.getTxnAmount().doubleValue();
						}
						for(AccountPayments a:settledTxn) {
							totalSet = totalSet+a.getTxnAmount().doubleValue();
						}
						for(AccountPayments a:paidTxn) {
							totalPaid = totalPaid+a.getTxnAmount().doubleValue();
						}
					}
					
					
					okj.put("settled",df.format(totalSet) + " PKR");
					okj.put("unsettled",df.format(totalUnset) +" PKR");
					okj.put("paid",df.format(totalPaid) +" PKR");

					response.setCode("0000");
					response.setMessage("SUCCESS");
					response.setData(okj);
					
					
				}else {
					response.setCode("0001");
					response.setMessage("No Merchant Found.");
					return response;
				}
			} else {
				response.setCode("0001");
				response.setMessage("Invalid Merchant ID.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}
