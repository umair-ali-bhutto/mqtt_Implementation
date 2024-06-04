package com.mportal.ws.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.model.UploaderModel;
import com.ag.mportal.services.FedRateService;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;



@Component("com.mportal.ws.classes.WsUploader")
public class WsUploader implements Wisher {

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	FedRateService fedRateService;
	
	private static final int BUFFER_SIZE = 6124;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		byte[] fileBytes = rm.getAdditionalData().get("file") == null ? null
				: (byte[]) rm.getAdditionalData().get("file");
		
		String userId = rm.getAdditionalData().get("userId") == null ? null
				: rm.getUserid();
		
		String fileName = rm.getAdditionalData().get("fileName") == null ? null
				: rm.getAdditionalData().get("fileName").toString();

		try {
			if (!Objects.isNull(fileBytes)) {
				AgLogger.logInfo(rm.getUserid(), "File Exists");
				
				if (FilenameUtils.getExtension(fileName).equals("csv")) {
					String fileNamePath = saveFile(fileBytes,fileName);
					List<UploaderModel> lstUploader = readCsv(fileNamePath);
					if (!lstUploader.isEmpty()) {
						List<UploaderModel> lstResp = validationInsertions(lstUploader, userId,rm.getCorpId());
						
						HashMap<Object, Object> obj = new HashMap<Object, Object>();
						obj.put("details", lstResp);
						
						AgLogger.logInfo(rm.getUserid(), "File is uploaded");
						return UtilAccess.generateResponse("0000", "SUCCESS",obj);
					} else {
						AgLogger.logInfo(rm.getUserid(), "File is empty");
						return UtilAccess.generateResponse("6666", "File is empty");
					}

				} else {
					AgLogger.logInfo(rm.getUserid(), "Non CSV file selected");
					return UtilAccess.generateResponse("7777", "Please Select CSV file");
				}

			} else {
				return UtilAccess.generateResponse("8888", "Please Select Date or Merchant ID or Terminal ID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ",e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public static List<UploaderModel> readCsv(String file) {
		List<UploaderModel> lstUploader = new ArrayList<>();
		
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("Sr. #", "sNo");
		mapping.put("Merchant ID", "merchantId");
		mapping.put("MDR Onus", "mdrOnUs");
		mapping.put("MDR Offus", "mdrOffUs");
		mapping.put("Province(FED)", "province");

		HeaderColumnNameTranslateMappingStrategy<UploaderModel> strategy = new HeaderColumnNameTranslateMappingStrategy<UploaderModel>();
		strategy.setType(UploaderModel.class);
		strategy.setColumnMapping(mapping);

		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		CsvToBean csvToBean = new CsvToBean();

		List<UploaderModel> list = csvToBean.parse(strategy, csvReader);

		return list;
	}

	public List<UploaderModel> validationInsertions(List<UploaderModel> lstUploader, String userCode,String corpId) {
		List<String> lstMerchantId = new ArrayList<>();

		for (UploaderModel uploaderModel : lstUploader) {
			if (Objects.isNull(uploaderModel.getMerchantId()) || uploaderModel.getMerchantId().isEmpty()) {
				uploaderModel.setMerchantId("N/A");
				uploaderModel.setStatus("Invalid Record");
			} else {
				lstMerchantId.add(uploaderModel.getMerchantId());
			}
		}

		HashMap<String, Integer> mapUser = getMapMidUserId(lstMerchantId,corpId);
		lstMerchantId = validateLstMerchantId(mapUser, lstMerchantId, lstUploader);
		if(mapUser.size()>0) {
			List<UserSetting> oldLstUserSetting = getUserSettings(mapUser);
			List<UserSetting> newLstUserSetting = createUserSettings(lstUploader, mapUser, userCode);
			updateAndInsertUserSettings(oldLstUserSetting, newLstUserSetting);
			lstUploader = setSuccessMsg(lstUploader, mapUser);
		}
		
		return lstUploader;

	}

	public HashMap<String, Integer> getMapMidUserId(List<String> lstMerchantId, String corpId) {

		String[] merchants = new String[lstMerchantId.size()];
		
		List<UserLogin> lstUserIdMId = userLoginService.lstUserMerchants(lstMerchantId, corpId);

		HashMap<String, Integer> mapUser = new HashMap<String, Integer>();
		for (UserLogin user : lstUserIdMId) {
			mapUser.put(user.getMid(), user.getUserId());
		}

		return mapUser;
	}

	public List<String> validateLstMerchantId(HashMap<String, Integer> mapUser, List<String> lstMerchantId,
			List<UploaderModel> lstUploader) {
		// Filter Lst Merchant ID exist or not in UserLogin
		List<String> tempLstMerchantId = new ArrayList<String>();
		boolean flag = false;
		for (String merchantId : lstMerchantId) {
			Integer userId = mapUser.get(merchantId);
			if (!Objects.isNull(userId)) {
				tempLstMerchantId.add(merchantId);
			}
		}

		for (UploaderModel uploader : lstUploader) {
			flag = false;
			for (String merchantId : tempLstMerchantId) {
				if (merchantId.equals(uploader.getMerchantId())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				mapUser.remove(uploader.getMerchantId());
				uploader.setStatus("No Merchant Found.");
			}
		}

		return tempLstMerchantId;
	}

	public List<UserSetting> getUserSettings(HashMap<String, Integer> mapUser) {
//		Integer[] lstUserId = new Integer[mapUser.size()];
//		lstUserId = mapUser.values().toArray(lstUserId);
		List<Integer> listUserId = new ArrayList<Integer>(mapUser.values());
		List<UserSetting> lstUserSetting = userSettingService.fetchSettingByLstUserLoginId(listUserId);

		return lstUserSetting;

	}

	public List<UserSetting> createUserSettings(List<UploaderModel> lstUploaderModel, HashMap<String, Integer> mapUser,
			String userCode) {

		List<UserSetting> lstUserSetting = new ArrayList<UserSetting>();

		Iterator hmIterator = mapUser.entrySet().iterator();
		while (hmIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry) hmIterator.next();
			String merchantId = mapElement.getKey().toString();
			for (UploaderModel uploaderModel : lstUploaderModel) {
				if (merchantId.equals(uploaderModel.getMerchantId())) {

					UserSetting mdrOnUs = UtilAccess.createUserSettingObj(uploaderModel.getMdrOnUs(), "MDR_ON_US",
							(int) mapElement.getValue(), userCode);
					UserSetting mdrOffUs = UtilAccess.createUserSettingObj(uploaderModel.getMdrOffUs(), "MDR_OFF_US",
							(int) mapElement.getValue(), userCode);
					UserSetting province = UtilAccess.createUserSettingObj(uploaderModel.getProvince(), "PROVINCE",
							(int) mapElement.getValue(), userCode);
					UserSetting fedRate = UtilAccess
							.createUserSettingObj(
									fedRateService.retrieveRateByProvince(uploaderModel.getProvince())
											.getRateValue().toString(),
									"FED_RATES", (int) mapElement.getValue(), userCode);

					lstUserSetting.add(mdrOnUs);
					lstUserSetting.add(mdrOffUs);
					lstUserSetting.add(province);
					lstUserSetting.add(fedRate);
				}
			}
		}

		return lstUserSetting;
	}

	public void updateAndInsertUserSettings(List<UserSetting> oldLstUserSettings,
			List<UserSetting> newLstUserSettings) {

		userSettingService.updateAndinsertUserSettings(oldLstUserSettings, newLstUserSettings);

	}

	public List<UploaderModel> setSuccessMsg(List<UploaderModel> lstUploaderModel, HashMap<String, Integer> mapUser) {
		Iterator hmIterator = mapUser.entrySet().iterator();
		while (hmIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry) hmIterator.next();
			String merchantId = mapElement.getKey().toString();
			for (UploaderModel uploaderModel : lstUploaderModel) {
				if (merchantId.equals(uploaderModel.getMerchantId())) {
					uploaderModel.setStatus("Successful");
				}
			}
		}
		return lstUploaderModel;
	}
	
	public String saveFile(byte[] fileBytes,String fileName) {
		
		File result = null;
		try {
			File resultm = new File(AppProp.getProperty("file.uploader.path"));
			if (!resultm.exists()) {
				resultm.mkdirs();
			}
			result = new File(AppProp.getProperty("file.uploader.path") + fileName);
			
			Files.write(result.toPath(), fileBytes);

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logDebug(getClass(), "Exception: "+e);
		}
		return result.getAbsolutePath();
	}

}