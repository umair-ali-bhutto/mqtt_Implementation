package com.loy.adm.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.MerchantMaster;
import com.ag.loy.adm.model.MerchantKycModel;
import com.ag.loy.adm.service.MerchantMasterService;

@Component("com.loy.adm.ws.classes.WsMerchantUpdateStatus")
public class WsMerchantUpdateStatus implements Wisher {

	
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
			case "UPDATE":
				response = updateRequest(rm);
				break;
			case "FETCH":
				response = fetchRequest(rm);
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
	
	
	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String merchantId = Objects.isNull(rm.getAdditionalData().get("merchantId")) ? null
					: rm.getAdditionalData().get("merchantId").toString().trim();
			if (merchantId != null) {
				MerchantMaster mm = merchantMasterService.findAllByMidCorpId(merchantId, rm.getCorpId());
				if(!Objects.isNull(mm)) {
					HashMap<Object, Object> okj = new HashMap<Object, Object>();

					List<MerchantKycModel> ls = new ArrayList<MerchantKycModel>();

					MerchantKycModel mName = new MerchantKycModel();
					mName.setKycTitle("NAME");
					mName.setKycValue(mm.getName());
					ls.add(mName);

					MerchantKycModel mDesc = new MerchantKycModel();
					mDesc.setKycTitle("DESCRIPTION");
					mDesc.setKycValue(mm.getDescription());
					ls.add(mDesc);
					
					MerchantKycModel mReg = new MerchantKycModel();
					mReg.setKycTitle("REGION");
					mReg.setKycValue(mm.getRegion());
					ls.add(mReg);
					
					MerchantKycModel mCityId = new MerchantKycModel();
					mCityId.setKycTitle("CITY_ID");
					mCityId.setKycValue(mm.getCityId());
					ls.add(mCityId);
					
					MerchantKycModel mArea = new MerchantKycModel();
					mArea.setKycTitle("AREA");
					mArea.setKycValue(mm.getArea());
					ls.add(mArea);
					
					MerchantKycModel mAddr1 = new MerchantKycModel();
					mAddr1.setKycTitle("ADDRESS1");
					mAddr1.setKycValue(mm.getAddress1());
					ls.add(mAddr1);
					
					MerchantKycModel mAddr2 = new MerchantKycModel();
					mAddr2.setKycTitle("ADDRESS2");
					mAddr2.setKycValue(mm.getAddress2());
					ls.add(mAddr2);
					
					MerchantKycModel mLocLat = new MerchantKycModel();
					mLocLat.setKycTitle("LOC_LAT");
					mLocLat.setKycValue(mm.getLocLat().toString());
					ls.add(mLocLat);
					
					MerchantKycModel mLocLng = new MerchantKycModel();
					mLocLng.setKycTitle("LOC_LNG");
					mLocLng.setKycValue(mm.getLocLng().toString());
					ls.add(mLocLng);
					
					MerchantKycModel mLocAccu = new MerchantKycModel();
					mLocAccu.setKycTitle("LOC_ACCU");
					mLocAccu.setKycValue(mm.getLocAccu().toString());
					ls.add(mLocAccu);
					
					MerchantKycModel mCatCode = new MerchantKycModel();
					mCatCode.setKycTitle("CAT_CODE");
					mCatCode.setKycValue(mm.getCatCode());
					ls.add(mCatCode);
					
					MerchantKycModel mGlAcc1 = new MerchantKycModel();
					mGlAcc1.setKycTitle("GL_ACC1");
					mGlAcc1.setKycValue(mm.getGlAcc1());
					ls.add(mGlAcc1);
					
					MerchantKycModel mGlAcc2 = new MerchantKycModel();
					mGlAcc2.setKycTitle("GL_ACC2");
					mGlAcc2.setKycValue(mm.getGlAcc2());
					ls.add(mGlAcc2);
					
					MerchantKycModel mGlAcc3 = new MerchantKycModel();
					mGlAcc3.setKycTitle("GL_ACC3");
					mGlAcc3.setKycValue(mm.getGlAcc3());
					ls.add(mGlAcc3);
					
					MerchantKycModel mActive = new MerchantKycModel();
					mActive.setKycTitle("ACTIVE");
					mActive.setKycValue(mm.getActive());
					ls.add(mActive);
					
					MerchantKycModel mSrcRef = new MerchantKycModel();
					mSrcRef.setKycTitle("SOURCE_REF");
					mSrcRef.setKycValue(mm.getSourceRef());
					ls.add(mSrcRef);
					
					MerchantKycModel mCrBy = new MerchantKycModel();
					mCrBy.setKycTitle("CR_BY");
					mCrBy.setKycValue(mm.getCrBy());
					ls.add(mCrBy);
					
//					MerchantKycModel mCrOn = new MerchantKycModel();
//					mCrOn.setKycTitle("CR_ON");
//					mCrOn.setKycValue(new SimpleDateFormat().format(mm.getCrOn()));
//					ls.add(mCrOn);
					

					okj.put("merchantKyc", ls);

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
	
	ResponseModel updateRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}
