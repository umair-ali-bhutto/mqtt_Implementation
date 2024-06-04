package com.mportal.ws.classes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.Wisher;

import com.ag.generic.util.AgLogger;

@Component("com.mportal.ws.classes.WsViewPendingRequestForApproval")
public class WsViewPendingRequestForApproval implements Wisher {

	@Autowired
	MakerCheckerDataService makerCheckerData;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String groupCode = rm.getAdditionalData().get("groupCode") == null ? null
					: rm.getAdditionalData().get("groupCode").toString();

				List<MakerCheckerData> lst = makerCheckerData.fetchbyGroupCode(groupCode);
				if (lst.size() != 0) {
					HashMap<Object, Object> o = new HashMap<Object, Object>();
					List<MakerCheckerData> lstTemp = new ArrayList<MakerCheckerData>();
					for (MakerCheckerData m : lst) {
						if (m.getConfigId() == 7) {
							//m.getUniqueidentifier().equals("ADD_ADVERTISEMENT")
							String requestedData = m.getRequestedData();
							
							org.json.simple.JSONArray onjArray = (org.json.simple.JSONArray) JSONValue
									.parse(requestedData);
							JSONObject onj = (JSONObject) onjArray.get(0);
							
							String imageFilePath = onj.get("image").toString();
							
							
							Path pathToImage = Paths.get(imageFilePath);
							if (Files.notExists(pathToImage)) {
								AgLogger.logInfo("Image does not exist at the specified path");
							}
							// 1. Convert image to an array of bytes
							byte[] imageBytes = Files.readAllBytes(pathToImage);
							// 2. Encode image bytes[] to Base64 encoded String
							String base64EncodedImageBytes = Base64.getEncoder().encodeToString(imageBytes);
							String finalImage = "data:image/png;base64," + base64EncodedImageBytes;
							
							onj.remove("image");
							onj.put("image", finalImage);
							
							JSONArray arr = new JSONArray();
							arr.put(0, onj);
							
							m.setRequestedData(arr.toString());
						}
						lstTemp.add(m);

					}
					o.put("lst", lstTemp);
					response.setData(o);
					response.setCode("0000");
					response.setMessage("Success.");
				} else {
					response.setCode("0002");
					response.setMessage("No Pending Records.");
				}
				

			
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
