package com.mportal.ws.classes;

import java.io.File;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.UtilAccess;

@Component("com.mportal.ws.classes.WsServiceCheckDownloadLgs")
public class WsServiceCheckDownloadLgs implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String userId = rm.getUserid();
			String type = rm.getAdditionalData().get("type").toString();
			if (userId != null) {
				if (type.equals("SMS")) {
					String path = AppProp.getProperty("sms.log.file.path");
					File result = new File(AppProp.getProperty("sms.log.file.path"));
					if (result.exists()) {
						String filename = result.getName();
						String file = UtilAccess.convertFileToBlob(result.getAbsolutePath());
						if (file == null) {
							response.setCode("0002");
							response.setMessage("FAILED.");
							return response;
						}
						HashMap<Object, Object> obj = new HashMap<Object, Object>();
						obj.put("file", file);
						obj.put("fileName", filename);

						response.setCode("0000");
						response.setMessage("SUCCESS.");
						response.setData(obj);
					} else {
						response.setCode("8888");
						response.setMessage("FILE DOES NOT EXISTS.");
					}
				} else if (type.equals("EMAIL")) {
					File eresult = new File(AppProp.getProperty("email.log.file.path"));
					if (eresult.exists()) {
						String filename = eresult.getName();
						String file = UtilAccess.convertFileToBlob(eresult.getAbsolutePath());
						if (file == null) {
							response.setCode("0002");
							response.setMessage("FAILED.");
							return response;
						}
						HashMap<Object, Object> obj = new HashMap<Object, Object>();
						obj.put("file", file);
						obj.put("fileName", filename);

						response.setCode("0000");
						response.setMessage("SUCCESS.");
						response.setData(obj);

					} else {
						response.setCode("8888");
						response.setMessage("FILE DOES NOT EXISTS.");
					}
				}

			} else {
				response.setCode("0001");
				response.setMessage("INVALID USER.");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}