package com.ag.generic.service;

import com.ag.generic.entity.Captcha;

public interface CaptchaService
{
	public long insert(Captcha adt);
	public Captcha validateCaptcha(int captchaId,String uuid);
	public void updateCaptcha(Captcha captchaId);
	public void updateAll(String uuid);
	
}
