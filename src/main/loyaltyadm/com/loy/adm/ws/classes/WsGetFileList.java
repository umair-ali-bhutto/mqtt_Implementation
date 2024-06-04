package com.loy.adm.ws.classes;

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
import com.ag.mportal.entity.FileUploader;
import com.ag.mportal.services.FileUploaderService;

@Component("com.loy.adm.ws.classes.WsGetFileList")
public class WsGetFileList implements Wisher {

	@Autowired
	FileUploaderService fileUploaderService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		String corpId = rm.getCorpId();
		String type = rm.getAdditionalData().get("type") == null ? null : rm.getAdditionalData().get("type").toString();
		String status = rm.getAdditionalData().get("status") == null ? null
				: rm.getAdditionalData().get("status").toString();
		List<FileUploader> lstFiles = null;
		try {

			if (Objects.isNull(corpId)) {
				return UtilAccess.generateResponse("0001", "CorpId is missing.");
			}

			if (Objects.isNull(type) || type.isEmpty()) {
				return UtilAccess.generateResponse("0001", "Type is missing.");
			}

			String[] types = type.split(",");

			if (Objects.isNull(status) || status.isEmpty()) {
				lstFiles = fileUploaderService.fetchListByType(types,corpId);
			} else {
				lstFiles = fileUploaderService.fetchListByTypeAndStatus(types, status,corpId);
			}

			if (Objects.isNull(lstFiles) || lstFiles.isEmpty()) {
				return UtilAccess.generateResponse("0003", "No Records Found");
			}

			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("lstFiles", lstFiles);
			return UtilAccess.generateResponse("0000", "SUCCESS", map);

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ", e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}