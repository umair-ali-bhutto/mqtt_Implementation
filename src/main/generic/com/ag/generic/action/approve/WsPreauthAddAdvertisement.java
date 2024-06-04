package com.ag.generic.action.approve;

import java.io.File;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.DevAdvDetail;
import com.ag.mportal.entity.DevAdvMaster;
import com.ag.mportal.entity.DevAdvMerchant;
import com.ag.mportal.entity.DevAdvTerminal;
import com.ag.mportal.services.DevAdvDetailService;
import com.ag.mportal.services.DevAdvMasterService;
import com.ag.mportal.services.DevAdvMerchantService;
import com.ag.mportal.services.DevAdvTerminalService;

@Component("com.ag.generic.action.approve.WsPreauthAddAdvertisement")
public class WsPreauthAddAdvertisement implements WisherForApprover {

	@Autowired
	MakerCheckerDataService mdService;

	@Autowired
	DevAdvDetailService advDetailService;

	@Autowired
	DevAdvMasterService advMasterService;

	@Autowired
	DevAdvMerchantService advMerchantService;

	@Autowired
	DevAdvTerminalService advTerminalService;

	@Override
	public ResponseModel doProcess(RequestModel rm, MakerCheckerConfig mck, MakerCheckerData md) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String remarks = (rm.getAdditionalData().containsKey("remarks"))
					? rm.getAdditionalData().get("remarks").toString()
					: null;

			String requestedData = md.getRequestedData();
			org.json.simple.JSONArray onjArray = (org.json.simple.JSONArray) JSONValue.parse(requestedData);
			JSONObject onj = (JSONObject) onjArray.get(0);

			String action = onj.get("typeAction").toString();

			AgLogger.logInfo("Action Performed: " + action);

			// move image
			String filePath = AppProp.getProperty("addvertisement.file.path.approver");
			File image = new File(onj.get("image").toString());
			boolean moved = moveFileToDirectory(image, filePath);
			String movedImage = filePath + "" + image.getName();

			long advId = advMasterService.fetchAdvId();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			LocalDate date = LocalDate.of(2030, Month.DECEMBER, 31);
			Date dateTo = sdf.parse(date.toString());
			Date dateFrom = new Date();

			String defaultFlag = "N";

			if (action.equals("ALL")) {
				defaultFlag = "Y";
			}

			// DEV_ADV_MASTER
			DevAdvMaster master = new DevAdvMaster();
			master.setActive("Y");
			master.setAdvId(advId);
			master.setAdvType("I");
			master.setCorpId("00000");
			master.setCrBy(rm.getUserid() + "");
			master.setCrOn(new Timestamp(new java.util.Date().getTime()));
			master.setDateFrom(dateFrom);
			master.setDateTo(dateTo);
			master.setDefaultFlag(defaultFlag);
			master.setName(onj.get("description").toString());
			master.setNoOfMedia(1);
			master.setPushFlag("Y");
			master.setSourceRef(UtilAccess.rrn());
			master.setVersion(1);
			advMasterService.insert(master);

			// DEV_ADV_DETAIL
			DevAdvDetail detail = new DevAdvDetail();
			detail.setActive("Y");
			detail.setAdvId(advId);
			detail.setCorpId("00000");
			detail.setCrBy(rm.getUserid() + "");
			detail.setCrOn(new Timestamp(new java.util.Date().getTime()));
			detail.setMediaUrl(movedImage);
			detail.setSno(1);
			advDetailService.insert(detail);

			if (action.equals("MID")) {
				// DEV_ADV_MERCHANT
				DevAdvMerchant merchant = new DevAdvMerchant();
				merchant.setActive("Y");
				merchant.setAdvId(advId);
				merchant.setCorpId("00000");
				merchant.setCrBy(rm.getUserid() + "");
				merchant.setCrOn(new Timestamp(new java.util.Date().getTime()));
				merchant.setMid(onj.get("mid").toString());
				advMerchantService.insert(merchant);
			}
			if (action.equals("TID")) {

				JSONArray json = new JSONArray(onj.get("tid").toString());

				for (int l = 0; l < json.length(); l++) {
					String tidValue = ((org.json.JSONObject) json.get(l)).get("value").toString();

					DevAdvTerminal terminal = new DevAdvTerminal();
					terminal.setActive("Y");
					terminal.setAdvId(advId);
					terminal.setCorpId("00000");
					terminal.setCrBy(rm.getUserid() + "");
					terminal.setCrOn(new Timestamp(new java.util.Date().getTime()));
					terminal.setMid(onj.get("mid").toString());
					terminal.setTid(tidValue);
					advTerminalService.insert(terminal);
				}

			}

			// MAKER_CHECKER_DATA
			md.setStatus("APPROVED");
			md.setApprovedBy(Integer.parseInt(rm.getUserid()));
			md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
			md.setApproverRemarks(remarks);
			mdService.update(md);

			response.setCode("0000");
			response.setMessage("Record Approved.");
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}
		return response;
	}

	private boolean moveFileToDirectory(File sourceFile, String targetPath) {
		File tDir = new File(targetPath);
		if (tDir.exists()) {
			String newFilePath = targetPath + File.separator + sourceFile.getName();
			File movedFile = new File(newFilePath);
			if (movedFile.exists())
				movedFile.delete();
			return sourceFile.renameTo(new File(newFilePath));
		} else {
			return false;
		}
	}

}
