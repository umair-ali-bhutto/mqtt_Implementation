package com.ag.config;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.ReconConfig;
import com.ag.mportal.services.ReconConfigService;
import com.ag.mportal.services.ReconLockService;

@Component
@EnableAsync
public class ReconSchedular {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	ReconConfigService reconConfigService;

	@Autowired
	ReconLockService reconLockService;

	@Value("${recon.schedular}")
	public String reconSchedular;

	private volatile boolean isSchedulerRunning = false;

	@Async
	@Scheduled(cron = "${recon.cron}")
	public void doProcessRecon() {
		if (reconSchedular.equalsIgnoreCase("true")) {
			if (isSchedulerRunning) {
				AgLogger.logInfo("Recon scheduler is already running. Exiting.");
				return;
			}

			AgLogger.logInfo("@@ RECON SCHEDULAR STARTED. @@");
			try {
				isSchedulerRunning = true;

				List<ReconConfig> configList = reconConfigService.fetchAll();
				List<Long> lockConfigIds = reconLockService.fetchConfigIds();

				AgLogger.logInfo("Recon Configurations Size: " + configList.size());
				if (configList.size() != 0) {
					AgLogger.logInfo("Recon Lock Size: " + lockConfigIds.size());

					ExecutorService executorService = Executors.newFixedThreadPool(configList.size());

					// Use CompletableFuture to run initialization tasks asynchronously
					List<CompletableFuture<Void>> futures = configList.stream()
							.filter(cfg -> !lockConfigIds.contains(cfg.getId()))
							.map(cfg -> CompletableFuture.runAsync(() -> {
								initializeClass(cfg);
							}, executorService)).collect(Collectors.toList());

					// Wait for all CompletableFuture tasks to complete
					CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

					executorService.shutdown();
				} else {
					AgLogger.logInfo("No Configurations Found.");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				isSchedulerRunning = false;
				AgLogger.logInfo("@@ RECON SCHEDULAR ENDED. @@");
			}

			
		}
	}

	private void initializeClass(ReconConfig cfg) {
		try {

			Object bean = applicationContext.getBean(cfg.getConfigClass());
			Method doProcessMethod = bean.getClass().getMethod("doProcess", ReconConfig.class);
			doProcessMethod.invoke(bean, cfg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
