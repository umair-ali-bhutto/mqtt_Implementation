package com.ag.pos.controller;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class ProcessFileDownload {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public UserLoginService userLoginService;
	
	@Autowired
	AuditLogService auditLogService;

	@SuppressWarnings("unchecked")
	@GetMapping({ "/FileDown", "/BaflFileDown" })
	public ResponseEntity<Resource> doProcessFileDownload(@RequestParam("username") String userName,
			@RequestParam("password") String password, @RequestParam("url") String url, HttpServletRequest request) {
		JSONObject job = new JSONObject();
		String ipAddress = request.getHeader("IP");

		if (ipAddress == null) {

			ipAddress = request.getRemoteAddr();
		}
		try {
			AgLogger.logInfo("... FILE DOWNLOAD CALL ");
			if (!Objects.isNull(userName) && !Objects.isNull(password)) {
				UserLogin user = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(user)) {
					String tempId = new String(java.util.Base64.getDecoder().decode(url));
					int id = 0;
					id = Integer.parseInt(tempId);
					FileDownloadModel fd = fetchFilefileId(id);
					if (fd != null) {
						File file = new File(fd.getFilePath());
						if (!file.exists()) {
							throw new ServletException("File doesn't exists on server. "+file.getName()+" | "+file.getAbsolutePath());
						}

						InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
						job.put("Reponsemessage", "APK Downloaded "+file.getName()+" | "+file.getAbsolutePath());
						return ResponseEntity.ok().contentLength(file.length())
								.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
								.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
					} else {
						AgLogger.logInfo(" NO FILE EXISTS BY ID." + id);
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
			job.put("msg", "Exception. "+e.getMessage());
		}finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setMid("N/A");
			adt.setTid("N/A");
			adt.setResponse(job.toJSONString());
			adt.setRequestMode("POS");
			adt.setRequest(userName+"|"+password+"|"+url);
			adt.setRequestIp(ipAddress);
			adt.setTxnType("FileDown");
			adt.setSerialNum("N/A");
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ResponseEntity.badRequest().body(null);
	}

	public FileDownloadModel fetchFilefileId(int id) {
		FileDownloadModel fileUploadProp = null;
		try {
			String query = AppProp.getProperty("fetch.file.download.version.query");
			String replacedStrQuery = "";
			try {
				replacedStrQuery = query.replace("@@id", id + "");
			} catch (Exception ex) {
				replacedStrQuery = "";
			}

			AgLogger.logInfo("FETCH VERSION QUERY " + replacedStrQuery);

			Query cb = entityManager.createQuery(replacedStrQuery);
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

}
