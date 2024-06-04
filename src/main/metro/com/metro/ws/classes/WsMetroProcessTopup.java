package com.metro.ws.classes;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProcedureMetro;
import com.ag.db.proc.MetroTopupProcessModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.metro.entity.MetroBatchSummary;
import com.ag.metro.model.TopUpModel;
import com.ag.metro.model.TopUpResponseModel;
import com.ag.metro.services.MetroBatchSummaryService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.metro.ws.classes.WsMetroProcessTopup")
public class WsMetroProcessTopup implements Wisher {

	@Autowired
	MetroBatchSummaryService metroBatchSummaryService;

	public List<TopUpResponseModel> listResponse;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			String topUpCardList = Objects.isNull(rm.getAdditionalData().get("topuplist")) ? null
					: rm.getAdditionalData().get("topuplist").toString();

			AgLogger.logDebug("", topUpCardList);

			String batchNumber = Objects.isNull(rm.getAdditionalData().get("batchNumber")) ? null
					: rm.getAdditionalData().get("batchNumber").toString();

			String systemDate = Objects.isNull(rm.getAdditionalData().get("systemDate")) ? null
					: rm.getAdditionalData().get("systemDate").toString();

			String cardBin = Objects.isNull(rm.getAdditionalData().get("cardBin")) ? null
					: rm.getAdditionalData().get("cardBin").toString();

			Type listType = new TypeToken<List<TopUpModel>>() {
			}.getType();
			List<TopUpModel> topList = new Gson().fromJson(topUpCardList, listType);

			if (batchNumber != null && systemDate != null && topList.size() != 0) {
				listResponse = new ArrayList<TopUpResponseModel>();
				response.setCode("0000");
				response.setMessage("SUCCESS");
				int k = 1;
				for (TopUpModel t : topList) {
					String[] sResp = performTopup(t, k, batchNumber, systemDate, rm.getUserid() + "", cardBin, rm);
					parseToResponse(t, sResp[0], sResp[1], sResp[2], sResp[3], sResp[4], sResp[5], sResp[6]);
					k++;
				}

				HashMap<Object, Object> lstResponse = new HashMap<Object, Object>();
				lstResponse.put("topResponse", listResponse);
				response.setData(lstResponse);

			} else {
				response.setCode("0003");
				response.setMessage("Batch Number is invalid or System Date is invalid.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("SERVICE ERROR");
		}
		return response;

	}

	public String[] performTopup(TopUpModel mdl, int recId, String batchNumber, String systemDate, String userId,
			String cardBin, RequestModel rm) {
		String[] resp = new String[7];
		try {
			resp[0] = "0009";
			resp[1] = "INVALID";
			resp[2] = "0";
			resp[3] = "0";
			resp[4] = "0";
			resp[5] = "0";
			resp[6] = "0";
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			MetroBatchSummary btchSummary = new MetroBatchSummary();
			btchSummary.setBatchNo(batchNumber);
			btchSummary.setCardFrom(mdl.getCardfrom());
			btchSummary.setCardTo(mdl.getCardto());
			btchSummary.setDc1(mdl.getRemarks());
			btchSummary.setExecutedBy(rm.getUserid() + "");
			btchSummary.setExecutedOn(new Timestamp(sdf.parse(systemDate).getTime()));
			btchSummary.setExpOn(mdl.getExpiresOn());
			btchSummary.setMemberId(mdl.getCustomerNo());
			btchSummary.setTopupAmt(mdl.getTopupamt());
			btchSummary.setTotalCards(Integer.parseInt(mdl.getTotalCards()));
			btchSummary.setTotalTopupAmt(mdl.getTotaltopupamt());
			btchSummary.setShelfLife(Integer.parseInt(mdl.getShelfLife()));

			int recIdSummary = metroBatchSummaryService.insertMetroBatchSummary(btchSummary);

			// db id
			resp[2] = recIdSummary + "";

			// recId
			resp[3] = recId + "";

			// ref
			resp[4] = "00000";

			// success

			MetroTopupProcessModel d = DBProcedureMetro.metroTopupProcess(cardBin, resp[2], batchNumber, userId);

			if (d.getVrsp_code().equals("0000")) {
				resp[0] = "0000";
				resp[1] = "SUCCESS";
				resp[5] = d.getSuccess();
				resp[6] = d.getRejected();

			} else {
				resp[0] = "0008";
				resp[1] = "PROC ERROR.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp[0] = "0008";
			resp[1] = "Something went wrong.";
		}
		return resp;
	}

	public void parseToResponse(TopUpModel mdl, String code, String message, String dbId, String recId, String ref,
			String success, String reject) {
		TopUpResponseModel m = new TopUpResponseModel();
		m.setCardfrom(StringUtils.leftPad(String.format("%.0f", Double.parseDouble(mdl.getCardfrom())), 9, "0"));
		m.setCardto(StringUtils.leftPad(String.format("%.0f", Double.parseDouble(mdl.getCardto())), 9, "0"));
		m.setCode(code);
		m.setCustomerNo(String.format("%.0f", Double.parseDouble(mdl.getCustomerNo())));
		m.setDbId(dbId);
		m.setExpiresOn(mdl.getExpiresOn());
		m.setMessage(message);
		m.setRecId(recId);
		m.setRef(ref);
		m.setRejectCard(reject);
		m.setRemarks(mdl.getRemarks());
		m.setSuccessCard(success);
		m.setTopupamt(String.format("%.0f", Double.parseDouble(mdl.getTopupamt())));
		m.setTotalCards(String.format("%.0f", Double.parseDouble(mdl.getTotalCards())));
		m.setTotaltopupamt(String.format("%.0f", Double.parseDouble(mdl.getTotaltopupamt())));
		m.setShelfLife(mdl.getShelfLife());
		listResponse.add(m);
	}

}
