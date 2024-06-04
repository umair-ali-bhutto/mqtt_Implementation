package com.ag.pos.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ag.config.AgLogger;

@RestController
public class Test {

	@CrossOrigin()
	@GetMapping("/test")
	public String doProcessRocImages(HttpServletRequest request) {
		AgLogger.logInfo("TEST CALL");

		return "TEST";
	}
}
