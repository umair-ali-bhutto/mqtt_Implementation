package com.loy.adm.ws.classes;

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
import com.ag.mportal.entity.FileUploader;
import com.ag.mportal.services.FileUploaderService;

@Component("com.loy.adm.ws.classes.WsUpdateFileStatus")
public class WsUpdateFileStatus implements Wisher {

	@Autowired
	FileUploaderService fileUploaderService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		String corpId = rm.getCorpId();
		String userId = rm.getUserid();
		String actionType = rm.getAdditionalData().get("actionType") == null ? null
				: rm.getAdditionalData().get("actionType").toString();
		String id = rm.getAdditionalData().get("id") == null ? null : rm.getAdditionalData().get("id").toString();
		try {

			if (Objects.isNull(corpId)) {
				return UtilAccess.generateResponse("0001", "CorpId is missing.");
			}

			if (Objects.isNull(id) || id.isEmpty()) {
				return UtilAccess.generateResponse("0001", "Record Id is missing.");
			}

			if (Objects.isNull(actionType) || actionType.isEmpty()) {
				return UtilAccess.generateResponse("0001", "Action Type is missing.");
			}

			FileUploader fileUploader = fileUploaderService.fetchById(Long.valueOf(id), corpId);
			
			if (Objects.isNull(fileUploader)) {
				return UtilAccess.generateResponse("0002", "Record not found for update.");
			}
			
			switch (actionType) {
				case "approve":
					approveRecord(fileUploader,userId);
					break;
				case "reject":
					rejectRecord(fileUploader,userId);
					break;
				default:
					return UtilAccess.generateResponse("8888", "Invalid Action Type.");
			}
			
			boolean flag = fileUploaderService.update(fileUploader);
			
			if(flag) {
				return UtilAccess.generateResponse("0000", "SUCCESS");
			}else {
				return UtilAccess.generateResponse("0004", "Something went wrong.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ", e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public FileUploader approveRecord(FileUploader fileUploader,String userId) {

		fileUploader.setApprOn(new Timestamp(new Date().getTime()));
		fileUploader.setApprBy(userId);
		fileUploader.setStatus("APPROVED");
		
		return fileUploader;
	}

	public FileUploader rejectRecord(FileUploader fileUploader,String userId) {

		fileUploader.setRejBy(userId);
		fileUploader.setRejOn(new Timestamp(new Date().getTime()));
		fileUploader.setStatus("REJECTED");
		
		return fileUploader;
	}

}