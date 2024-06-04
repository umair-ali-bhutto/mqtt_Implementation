package com.mportal.ws.classes;

import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.FileDownload;
import com.ag.mportal.services.FileDownloadService;

@Component("com.mportal.ws.classes.WsApkUploader")
public class WsApkUploader implements Wisher {

	@Autowired
	FileDownloadService fileDownloadService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		byte[] fileBytes = rm.getAdditionalData().get("file") == null ? null
				: (byte[]) rm.getAdditionalData().get("file");

		String userId = rm.getUserid();

		String fileName = rm.getAdditionalData().get("fileName") == null ? null
				: rm.getAdditionalData().get("fileName").toString();

		String selectedReleaseType = rm.getAdditionalData().get("selectedReleaseType") == null ? null
				: rm.getAdditionalData().get("selectedReleaseType").toString();

		String version = rm.getAdditionalData().get("version") == null ? null
				: rm.getAdditionalData().get("version").toString();

		String terminalIds = rm.getAdditionalData().get("terminalIds") == null ? null
				: rm.getAdditionalData().get("terminalIds").toString();

		try {

			if (Objects.isNull(fileBytes)) {
				return UtilAccess.generateResponse("0001", "File not exists.");
			}

			if (Objects.isNull(fileName) || fileName.isEmpty()) {
				return UtilAccess.generateResponse("0001", "Please provide a filename to a file.");
			}

			if (!FilenameUtils.getExtension(fileName).equals("apk")) {
				return UtilAccess.generateResponse("0001", "Please select APK file.");
			}

			if (Objects.isNull(selectedReleaseType)) {
				return UtilAccess.generateResponse("0001", "Please select release type.");
			}

			if (Objects.isNull(version)) {
				return UtilAccess.generateResponse("0001", "Please provide version.");
			}

			if (selectedReleaseType.equals("Specific Terminals")) {
				if (Objects.isNull(terminalIds) || terminalIds.isEmpty()) {
					return UtilAccess.generateResponse("0001", "Please provide terminal ids.");
				}
			}

			Integer count = fileDownloadService.getCountReleaseTypeAndStatusWise(selectedReleaseType, "NEW");
			if (!Objects.isNull(count) && count > 0) {
				return UtilAccess.generateResponse("0004", "APK already pending for approval.");
			}
			
			FileDownload fileDownloadByVersion = fileDownloadService.fetchByVersion(version);
			if (!Objects.isNull(fileDownloadByVersion)) {
				AgLogger.logInfo("WsApkUploader ", "Version already exists with id: " + fileDownloadByVersion.getId());
				return UtilAccess.generateResponse("0003", "Version already exists.");
			}

			AgLogger.logInfo("WsApkUploader ", "ALL VALIDATIONS SUCCESSFULL");

			String fileNamePath = saveFile(fileBytes, fileName);

			if (Objects.isNull(fileNamePath)) {
				AgLogger.logInfo("WsApkUploader ", "File not uploaded");
				return UtilAccess.generateResponse("0002", "File already exists. Change the filename.");
			} else {
				AgLogger.logInfo("WsApkUploader ", "File uploaded.");
			}

			

			FileDownload fileDownload = createFileDownloadObj(fileName, selectedReleaseType, version, terminalIds,
					userId, fileNamePath);

			Long id = fileDownloadService.insert(fileDownload);

			if (Objects.isNull(id)) {
				AgLogger.logInfo("INSERTION STATUS ", "Insertion for fileDownload object is unsuccessfull.");
				return UtilAccess.generateResponse("0001", "Something went wrong.");
			}

			AgLogger.logInfo("INSERTION STATUS ", "Insertion for fileDownload object is successfull with id ." + id);

			return UtilAccess.generateResponse("0000", "SUCCESS");

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ", e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public FileDownload createFileDownloadObj(String fileName, String releaseType, String version, String terminalIds,
			String userId, String filePath) {
		FileDownload fileDownload = new FileDownload();

		fileDownload.setFileName(fileName);
		fileDownload.setReleaseType(releaseType);
		fileDownload.setVersion(version);
		fileDownload.setTerminalIds(terminalIds);
		fileDownload.setEntryBy(userId);
		fileDownload.setEntryDate(new Timestamp(new java.util.Date().getTime()));
		fileDownload.setFilePath(filePath);
		fileDownload.setStatus("NEW");
		fileDownload.setIsActive(0);

		return fileDownload;
	}

	public String saveFile(byte[] fileBytes, String fileName) {

		File result = null;
		try {
			File resultm = new File(AppProp.getProperty("apk.uploader.path"));
			if (!resultm.exists()) {
				resultm.mkdirs();
			}
			result = new File(AppProp.getProperty("apk.uploader.path") + fileName);

			if (result.exists()) {
				AgLogger.logInfo("WsApkUploader ", "File already exists with same name " + result.getAbsolutePath());
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

}