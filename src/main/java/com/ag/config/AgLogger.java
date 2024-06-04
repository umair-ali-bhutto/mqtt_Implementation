package com.ag.config;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class AgLogger {

	public static void logInfo(@SuppressWarnings("rawtypes") java.lang.Class obj, String message) {
		final Logger logger = (Logger) LogManager.getLogger(obj);
		if (logger.isInfoEnabled()) {
			logger.info(message);
		}
	}

	public static void logInfo(String message) {
		final Logger logger = (Logger) LogManager.getLogger(AgLogger.class);
		if (logger.isInfoEnabled()) {
			logger.info(message);
		}
	}

	public static void logInfo(String userId, String message) {
		final Logger logger = (Logger) LogManager.getLogger(AgLogger.class);
		if (logger.isInfoEnabled()) {
			logger.info(userId + "|" + message);
		}
	}

	public static void logTrace(@SuppressWarnings("rawtypes") java.lang.Class obj, String message, Exception e) {
		final Logger logger = (Logger) LogManager.getLogger(obj);
		e.printStackTrace();
		if (logger.isInfoEnabled()) {
			logger.info(message);
		}
	}

	public static void logDebug(@SuppressWarnings("rawtypes") java.lang.Class obj, String message) {
		final Logger logger = (Logger) LogManager.getLogger(obj);
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	public static void logDebug(String userId, String message) {
		final Logger logger = (Logger) LogManager.getLogger(AgLogger.class);
		if (logger.isDebugEnabled()) {
			logger.debug(userId + "|" + message);
		}
	}

	public static void logWarn(@SuppressWarnings("rawtypes") java.lang.Class obj, String message) {
		final Logger logger = (Logger) LogManager.getLogger(obj);
		if (logger.isWarnEnabled()) {
			logger.warn(message);
		}
	}

	public static void logerror(@SuppressWarnings("rawtypes") java.lang.Class obj, String message, Exception e) {
		final Logger logger = (Logger) LogManager.getLogger(obj);
		logger.error(message + "|" + e.getLocalizedMessage());
		if(e instanceof NoResultException) {
			
		}else {
			e.printStackTrace();	
		}
		

	}

	public static void logInfoService(@SuppressWarnings("rawtypes") java.lang.Class obj, String refNum,
			String serviceName, String message) {
		final Logger logger = (Logger) LogManager.getLogger(obj);
		if (logger.isInfoEnabled()) {
			logger.info(refNum + " ~ " + serviceName + " ~ " + message);
		}
	}

}
