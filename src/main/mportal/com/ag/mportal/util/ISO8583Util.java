package com.ag.mportal.util;

import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.model.ISO8583Model;

@Component
public class ISO8583Util {

	public ISO8583Model doProcess(ISO8583Model mdlRequest) {
		try {
			mdlRequest.setCode("0000");
			mdlRequest.setMessage("SUCCESS");
			Logger logger = new Logger();
			if (mdlRequest.isLoggerEnable()) {
				logger.addListener(new SimpleLogListener(System.out));
			}
			byte[] fHeader = new byte[5];
			fHeader = String_to_bcd.convert(mdlRequest.getHeader());
			ISOPackager newspecpackager = new GenericPackager(mdlRequest.getPeckager());
			AgLogger.logDebug("NAC", "DIALING TO " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			PostChannel channel = new PostChannel(mdlRequest.getNacIp(), mdlRequest.getNacPort(), newspecpackager);
			channel.setHeader(fHeader);
			channel.setTimeout(50000);
			
			if (mdlRequest.isLoggerEnable()) {
				((LogSource) channel).setLogger(logger, "test-channel");
			}
			channel.connect();
			AgLogger.logDebug("NAC", "CONNECTED TO " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			channel.send(mdlRequest.getIsoMessageRequest());
			mdlRequest.setIsoMessageResponse(channel.receive());
			AgLogger.logDebug("NAC", "DISCONNECTED FROM " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			mdlRequest.setCode(mdlRequest.getIsoMessageResponse().getString(39));
			channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			mdlRequest.setCode("0001");
			mdlRequest.setMessage("Something Went Wrong.");
		}
		return mdlRequest;
	}

}
