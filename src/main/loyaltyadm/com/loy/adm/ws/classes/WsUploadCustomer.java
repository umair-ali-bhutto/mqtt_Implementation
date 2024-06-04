package com.loy.adm.ws.classes;

import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.entity.LoyCustomerFile;
import com.ag.loy.adm.service.CorporateMasterService;
import com.ag.loy.adm.service.LoyCustomerFileService;

@Component("com.loy.adm.ws.classes.WsUploadCustomer")
public class WsUploadCustomer implements Wisher {

	@Autowired
	LoyCustomerFileService customerFileService;

	@Autowired
	CorporateMasterService corporateMasterService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			AgLogger.logInfo("reqType : " + reqType);

			switch (reqType) {
			case "INIT":
				response = initRequest(rm);
				break;
			case "UPLOAD":
				response = process(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpId = rm.getCorpId();
			int groupCode = 0;
			groupCode = Objects.isNull(rm.getAdditionalData().get("groupCode")) ? groupCode
					: Integer.parseInt(rm.getAdditionalData().get("groupCode").toString());

			List<CorporateMaster> lst = corporateMasterService.fetchAllList(groupCode, corpId);
			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("corporates", lst);

				response.setCode("0000");
				response.setMessage("SUCCESS.");
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Corporated Found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9991");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel process(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		byte[] fileBytes = rm.getAdditionalData().get("file") == null ? null
				: (byte[]) rm.getAdditionalData().get("file");

		String userId = rm.getUserid();

		String fileName = rm.getAdditionalData().get("fileName") == null ? null
				: rm.getAdditionalData().get("fileName").toString();

		String selectedFileType = rm.getAdditionalData().get("selectedFileType") == null ? null
				: rm.getAdditionalData().get("selectedFileType").toString();

		String corpId = Objects.isNull(rm.getAdditionalData().get("corpId")) ? null
				: rm.getAdditionalData().get("corpId").toString().trim();

		try {

			if (Objects.isNull(fileBytes)) {
				return UtilAccess.generateResponse("0001", "File not exists.");
			}

			if (Objects.isNull(fileName) || fileName.isEmpty()) {
				return UtilAccess.generateResponse("0001", "Please provide a filename to a file.");
			}

			AgLogger.logInfo("WsUploadCustomer ", "ALL VALIDATIONS SUCCESSFULL");
			LoyCustomerFile mdl = new LoyCustomerFile();
			mdl = customerFileService.fetchByName(fileName + corpId);

			if (Objects.isNull(mdl)) {
				//String fileNamePath = 
				saveFile(fileBytes, fileName, corpId);

				LoyCustomerFile fileUploader = createCustomerUploaderObj(fileName, userId, selectedFileType, corpId);
				customerFileService.insert(fileUploader);
				AgLogger.logInfo("INSERTION STATUS ",
						"Insertion for Upload Customer object is successfull with id ." + fileUploader.getId());

				response.setCode("0000");
				response.setMessage("File Successfully Uploaded.");
			} else {
				response.setCode("0001");
				response.setMessage("File Already Uploaded on "
						+ new SimpleDateFormat("dd-MM-yyyy").format(mdl.getEntryDate()) + ".");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9991");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String saveFile(byte[] fileBytes, String fileName, String corpId) {

		File result = null;
		try {
			File resultm = new File(AppProp.getProperty("loy.upload.cust.file.path"));
			if (!resultm.exists()) {
				resultm.mkdirs();
			}
			result = new File(AppProp.getProperty("loy.upload.cust.file.path") + fileName + corpId);

			Files.write(result.toPath(), fileBytes);

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logDebug(getClass(), "Exception: " + e);
			return null;
		}
		return result.getAbsolutePath();
	}

	public LoyCustomerFile createCustomerUploaderObj(String fileName, String userId, String fileType, String corpId) {

		LoyCustomerFile lcf = new LoyCustomerFile();

		lcf.setCorpId(corpId);
		lcf.setFileName(fileName + corpId);
		lcf.setFileType(fileType);
		lcf.setStatus("NEW");
		lcf.setEntryBy(userId);
		lcf.setEntryDate(new Timestamp(new Date().getTime()));
		lcf.setTotalRecords(0);
		lcf.setSuccessRecords(0);
		lcf.setFailureRecords(0);
		lcf.setApprovedBy(null);
		lcf.setApprovedDate(null);
		lcf.setRejectedBy(null);
		lcf.setRejectedDate(null);
		lcf.setRemarks(null);

		return lcf;
	}
}