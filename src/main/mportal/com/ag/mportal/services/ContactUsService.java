package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ContactUs;

public interface ContactUsService {
	public List<ContactUs> fetchContact(String corpId);

}
