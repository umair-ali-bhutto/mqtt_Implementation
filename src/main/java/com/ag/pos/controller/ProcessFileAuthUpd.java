package com.ag.pos.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.FileDownload;
import com.ag.mportal.model.FileDownloadModel;

@RestController
public class ProcessFileAuthUpd {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@GetMapping({ "/FileAuthUpd", "/BaflFileAuthUpd" })
	public JSONObject doProcessFileAuth(@RequestParam("username") String userName,
			@RequestParam("password") String password, @RequestParam("vers") String version,
			HttpServletRequest request) {
		UserLogin user = new UserLogin();
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		JSONObject job = new JSONObject();
		try {
			if (!Objects.isNull(userName) && !Objects.isNull(password)) {
				user = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(user)) {
					AgLogger.logInfo("FILE AUTH CALL...." + userName + "|" + password + "|" + version);
					FileDownloadModel fd = fetchFileByVersion(version);
					if (fd != null) {
						MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
						job.put("code", "0000");
						job.put("msg", "SUCCESS");
						job.put("url", new String(
								java.util.Base64.getEncoder().encode(String.valueOf(fd.getId()).getBytes())));
						job.put("checksum",getFileChecksum(shaDigest, new File(fd.getFilePath())).toUpperCase());
						job.put("size", new File(fd.getFilePath()).length());
					} else {
						job.put("code", "1111");
						job.put("msg", "NO ANY VERSION FOUND.");
					}
				} else {
					job.put("Reponsemessage", "Credential Not Found");
				}
			} else {
				job.put("Reponsemessage", "Invalid Credentials");
			}
		} catch (Exception e) {
			e.printStackTrace();
			job.put("code", "1111");
			job.put("msg", "Exception.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setTid("N/A");
			adt.setEntryDate(time);
			adt.setRequest(userName + "|" + password + "|" + version);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("FileAuth");
			adt.setMid("N/A");
			adt.setSerialNum("N/A");
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return job;
	}

	public FileDownloadModel fetchFileByVersion(String version) {
		FileDownloadModel fileUploadProp = null;
		try {
			String query = AppProp.getProperty("check.file.download.version.query");
			String replacedStrQuery = "";
			try {
				replacedStrQuery = query.replace("@@version", version);
			} catch (Exception ex) {
				replacedStrQuery = "";
			}

			AgLogger.logInfo("CHECK VERSION QUERY " + replacedStrQuery);

			Query cb = entityManager.createNativeQuery(replacedStrQuery, FileDownload.class);
			FileDownload f = (FileDownload) cb.getSingleResult();
			if (f != null) {
				fileUploadProp = new FileDownloadModel();
				Long lng = f.getId();
				fileUploadProp.setId(lng.intValue());
				fileUploadProp.setFilePath(f.getFilePath());
			}
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}
		return fileUploadProp;
	}

	private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
		// Get file input stream for reading the file content
		FileInputStream fis = new FileInputStream(file);

		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		// Read file data and update in message digest
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}
		;

		// close the stream; We don't need it now.
		fis.close();

		// Get the hash's bytes
		byte[] bytes = digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		// return complete hash
		return sb.toString();
	}
}
