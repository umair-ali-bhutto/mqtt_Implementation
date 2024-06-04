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
public class DownloadImgController {

	@Autowired
	UtilAccess utilAccess;

	@CrossOrigin()
	@GetMapping("/imgDownload")
	public ResponseEntity<byte[]> doProcessRocImages(@RequestParam(name = "id") String id,
			@RequestParam(name = "url") String url, HttpServletRequest request) {
		AgLogger.logInfo("ROC CALL");
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		try {
			InputStreamResource imageFile = new InputStreamResource(new FileInputStream(url));
			byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);

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
