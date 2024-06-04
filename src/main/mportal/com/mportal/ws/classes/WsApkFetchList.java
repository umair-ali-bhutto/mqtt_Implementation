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
import com.ag.mportal.entity.FileDownload;
import com.ag.mportal.services.FileDownloadService;

@Component("com.mportal.ws.classes.WsApkFetchList")
public class WsApkFetchList implements Wisher {

	@Autowired
	FileDownloadService fileDownloadService;

	

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		
		String releaseType =rm.getAdditionalData()==null || rm.getAdditionalData().get("releaseType") == null ? null
				: rm.getAdditionalData().get("releaseType").toString();

		String terminalId = rm.getAdditionalData()==null || rm.getAdditionalData().get("terminalId") == null ? null
				: rm.getAdditionalData().get("terminalId").toString();

		String version = rm.getAdditionalData()==null || rm.getAdditionalData().get("version") == null ? null
				: rm.getAdditionalData().get("version").toString();

		String status = rm.getAdditionalData()==null || rm.getAdditionalData().get("status") == null ? null
				: rm.getAdditionalData().get("status").toString();
		
		String fromDate = rm.getAdditionalData()==null || rm.getAdditionalData().get("fromDate") == null ? null
				: rm.getAdditionalData().get("fromDate").toString();
		
		String toDate = rm.getAdditionalData()==null || rm.getAdditionalData().get("toDate") == null ? null
				: rm.getAdditionalData().get("toDate").toString();

		try {
			
			List<FileDownload> lstApks = fileDownloadService.fetchAll(releaseType,status,version,terminalId,fromDate,toDate);

			if (Objects.isNull(lstApks) || lstApks.isEmpty()) {
				AgLogger.logInfo("WsApkFetchList ", "NO RECORD APK/s FOUND.");
				return UtilAccess.generateResponse("0001", "No Record Found.");
			}
			
			
			
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("lstApk", lstApks);
			
			response.setCode("0000");
			response.setMessage("SUCCESS");
			response.setData(map);

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ", e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}