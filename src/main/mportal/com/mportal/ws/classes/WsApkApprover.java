package com.mportal.ws.classes;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.FileDownload;
import com.ag.mportal.services.FileDownloadService;

@Component("com.mportal.ws.classes.WsApkApprover")
public class WsApkApprover implements Wisher {

	@Autowired
	FileDownloadService fileDownloadService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		String userId = rm.getUserid();

		String actionType = rm.getAdditionalData().get("actionType") == null ? null
				: rm.getAdditionalData().get("actionType").toString();

		String recId = rm.getAdditionalData().get("recId") == null ? null
				: rm.getAdditionalData().get("recId").toString();

		try {

			if (Objects.isNull(actionType) || actionType.isEmpty()) {
				return UtilAccess.generateResponse("0001", "Please provide a action type.");
			}

			if (Objects.isNull(recId) || recId.isEmpty()) {
				return UtilAccess.generateResponse("0001", "Please provide record id.");
			}

			AgLogger.logInfo("WsApkApprover ", "ACTION TYPE: " + actionType);

			FileDownload fileDownload = fileDownloadService.fetchById(Long.valueOf(recId));

			if (Objects.isNull(fileDownload)) {
				return UtilAccess.generateResponse("0002", "No record found.");
			}

			switch (actionType) {
				case "approve":
					fileDownload = approveApk(fileDownload, userId);
					deactivePrevApk(fileDownload);
					break;
				case "reject":
					fileDownload = rejectApk(fileDownload, userId);
					break;
				default:
					return UtilAccess.generateResponse("0001", "Invalid actionType.");
			}

			boolean flag = fileDownloadService.update(fileDownload);
			
			if(flag) {
				if(actionType.equals("approve")) {
					if(fileDownloadService.deactivePrevApk(fileDownload.getId(), fileDownload.getReleaseType())) {
						return UtilAccess.generateResponse("0000", "SUCCESS");
					}else {
						return UtilAccess.generateResponse("0004", "FAILED TO DEACTIVE APKs");
					}
					
				}else {
					return UtilAccess.generateResponse("0000", "SUCCESS");
				}
				
			}else {
				return UtilAccess.generateResponse("0003", "FAILED");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ", e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	public FileDownload approveApk(FileDownload fileDownload, String userId) {
		fileDownload.setApprovedBy(userId);
		fileDownload.setApprovedDate(new Timestamp(new Date().getTime()));
		fileDownload.setIsActive(1);
		fileDownload.setStatus("APPROVED");
		return fileDownload;
	}

	public FileDownload rejectApk(FileDownload fileDownload, String userId) {
		fileDownload.setRejectedBy(userId);
		fileDownload.setRejectedDate(new Timestamp(new Date().getTime()));
		fileDownload.setIsActive(0);
		fileDownload.setStatus("REJECTED");
		fileDownload.setRemarks("Rejected By UserId: " + userId);
		return fileDownload;
	}
	
	public boolean deactivePrevApk(FileDownload fileDownload) {
		String releaseType = fileDownload.getReleaseType();
		Long id = fileDownload.getId();
		
		boolean flag = fileDownloadService.deactivePrevApk(id, releaseType);
		
		return flag;
		
	}

}