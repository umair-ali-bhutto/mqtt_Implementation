package com.ag.controller;

import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.model.ResponseModel;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.google.gson.Gson;

@RestController
public class DownloadReportController {

	@Autowired
	UtilAccess utilAccess;

	@CrossOrigin()
	@GetMapping("/reportDownload")
	public ResponseEntity<byte[]> doProcessRocImages(@RequestParam(name = "url") String url,
			@RequestParam(name = "type") String type, HttpServletRequest request) {
		AgLogger.logInfo("ROC CALL REPORT DOWNLOAD");
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		try {
			InputStreamResource imageFile = new InputStreamResource(new FileInputStream(url));
			byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
			if (type.equals("csv")) {
				return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/csv")).body(imageBytes);
			} else if (type.equals("xlsx")) {
				return ResponseEntity.ok()
						.contentType(MediaType
								.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
						.body(imageBytes);
			} else {
				return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(imageBytes);
			}

		} catch (Exception e) {
			Gson gson = new Gson();
			ResponseModel response = new ResponseModel();
			e.printStackTrace();
			response.setCode("0003");
			response.setMessage("Record not found.");
			return ResponseEntity.ok().body(gson.toJson(response).getBytes());
		}

	}
}
