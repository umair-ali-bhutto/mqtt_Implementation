package com.ag.mportal.recon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.ReconConfig;
import com.ag.mportal.services.ReconLockService;

@Component("com.ag.mportal.recon.TestRecon")
public class TestRecon {

	@Autowired
	ReconLockService reconLockService;

	public void doProcess(ReconConfig cfg) {

		try {
			AgLogger.logInfo("TestRecon RECON CALLED " + cfg.getId());
			reconLockService.createLock(cfg.getId());
			Thread.sleep(5000);
			AgLogger.logInfo("TestRecon THREAD ENDED" + cfg.getId());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reconLockService.releaseLock(cfg.getId());
		}

	}

}
