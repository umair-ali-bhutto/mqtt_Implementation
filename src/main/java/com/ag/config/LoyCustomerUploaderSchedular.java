package com.ag.config;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.LoyCustomerFile;
import com.ag.loy.adm.entity.LoyFileUploadProc;
import com.ag.loy.adm.service.LoyCustomerFileService;
import com.ag.loy.adm.service.LoyFileUploadProcService;

@Component
public class LoyCustomerUploaderSchedular {

	@Autowired
	LoyFileUploadProcService loyFileUploadProcService;

	@Autowired
	LoyCustomerFileService loyCustomerFileService;

	@Value("${loy.cust.uploader.schedular}")
	public String custUploaderSchedular;

	@Scheduled(cron = "*/30 * * * * ?")
	public void doProcessReversal() {
		if (custUploaderSchedular.equalsIgnoreCase("true")) {
			AgLogger.logDebug("", "@@@ LOY CUST UPLOADER SCHEDULAR STARTED.");

			try {
				List<LoyCustomerFile> customerFileList = loyCustomerFileService.fetchAll();

				if (customerFileList.size() != 0) {
					for (LoyCustomerFile lcf : customerFileList) {
						
						List<LoyFileUploadProc> fileUploadProcList = loyFileUploadProcService
								.FetchAllByCorpId(lcf.getCorpId(), "Customer");

						if (fileUploadProcList.size() != 0) {
							
							

						} else {
							AgLogger.logInfo("No Configuration Found.");

							lcf.setRejectedDate(new Timestamp(new Date().getTime()));
							lcf.setRejectedBy("");
							lcf.setRemarks("No Configuration Found");

							// loyCustomerFileService.update(lcf);
						}
					}
				} else {
					AgLogger.logInfo("No New Records Found.");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			AgLogger.logDebug("", "@@@ LOY CUST UPLOADER SCHEDULAR ENDED.");
		}
	}

}
