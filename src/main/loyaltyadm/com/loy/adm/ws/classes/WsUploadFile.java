package com.loy.adm.ws.classes;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.FileUploader;
import com.ag.mportal.services.FileUploaderService;

@Component("com.loy.adm.ws.classes.WsUploadFile")
public class WsUploadFile implements Wisher {

	@Autowired
	FileUploaderService fileUploaderService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		byte[] fileBytes = rm.getAdditionalData().get("file") == null ? null
				: (byte[]) rm.getAdditionalData().get("file");

		String userId = rm.getUserid();

		String fileName = rm.getAdditionalData().get("fileName") == null ? null
				: rm.getAdditionalData().get("fileName").toString();

		String selectedFileType = rm.getAdditionalData().get("selectedFileType") == null ? null
				: rm.getAdditionalData().get("selectedFileType").toString();
		
		String corpId = rm.getCorpId();


		try {

			if (Objects.isNull(fileBytes)) {
				return UtilAccess.generateResponse("0001", "File not exists.");
			}

			if (Objects.isNull(fileName) || fileName.isEmpty()) {
				return UtilAccess.generateResponse("0001", "Please provide a filename to a file.");
			}

//			if (!FilenameUtils.getExtension(fileName).equals("apk")) {
//				return UtilAccess.generateResponse("0001", "Please select APK file.");
//			}

			if (Objects.isNull(selectedFileType)) {
				return UtilAccess.generateResponse("0001", "Please select file type.");
			}

			AgLogger.logInfo("WsApkUploader ", "ALL VALIDATIONS SUCCESSFULL");

			String fileNamePath = saveFile(fileBytes, fileName);

			if (Objects.isNull(fileNamePath)) {
				AgLogger.logInfo("WsApkUploader ", "File not uploaded");
				return UtilAccess.generateResponse("0002", "File already exists. Change the filename.");
			} else {
				FileUploader fileUploader = createFileUploderObj(fileName, userId, "0", selectedFileType,corpId);
				fileUploaderService.insertRecords(fileUploader);
				AgLogger.logInfo("INSERTION STATUS ", "Insertion for fileDownload object is successfull with id ."+fileUploader.getId());
				AgLogger.logInfo("WsApkUploader ", "File uploaded.");
			}


			

			return UtilAccess.generateResponse("0000", "SUCCESS");

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ", e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public String saveFile(byte[] fileBytes, String fileName) {

		File result = null;
		try {
			File resultm = new File(AppProp.getProperty("merchant.file.uploader.path"));
			if (!resultm.exists()) {
				resultm.mkdirs();
			}
			result = new File(AppProp.getProperty("merchant.file.uploader.path") + fileName);

			if (result.exists()) {
				AgLogger.logInfo("WsUploadFile ", "File already exists with same name " + result.getAbsolutePath());
				return null;
			} else {
				Files.write(result.toPath(), fileBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logDebug(getClass(), "Exception: " + e);
			return null;
		}
		return result.getAbsolutePath();
	}

	public FileUploader createFileUploderObj(String fileName, String userId, String totalRec, String fileType,String corpId) {
		FileUploader fileUploader = new FileUploader();
		
		fileUploader.setFileName(fileName);
		fileUploader.setCrBy(userId);
		fileUploader.setCrOn(new Timestamp(new Date().getTime()));
		fileUploader.setStatus("NEW");
		fileUploader.setTotalRec(new BigDecimal(totalRec));
		fileUploader.setType(fileType);
		fileUploader.setFilePath(AppProp.getProperty("merchant.file.uploader.path"));
		fileUploader.setCorpId(corpId);
		
		return fileUploader;
	}
}