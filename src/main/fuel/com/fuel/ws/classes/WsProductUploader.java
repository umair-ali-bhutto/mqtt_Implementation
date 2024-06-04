package com.fuel.ws.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.model.ProductPriceUploadModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.FileUploader;
import com.ag.mportal.entity.FileUploaderDetails;
import com.ag.mportal.services.FileUploaderDetailsService;
import com.ag.mportal.services.FileUploaderService;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@Component("com.fuel.ws.classes.WsProductUploader")
public class WsProductUploader implements Wisher {

	@Autowired
	UserScreenService screenService;

	@Autowired
	FileUploaderService fileUploaderService;
	
	@Autowired
	FileUploaderDetailsService fileUploaderDetailsService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "INIT":
				response = initRequest(rm);
				break;
			case "FETCH":
				response = fetchRequest(rm);
				break;
			case "PROCESS":
				response = processRequest(rm);
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
			response.setCode("0000");
			response.setMessage("Success.");

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			response.setCode("0000");
			response.setMessage("Success.");

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	ResponseModel processRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		byte[] fileBytes = rm.getAdditionalData().get("file") == null ? null
				: (byte[]) rm.getAdditionalData().get("file");

		String fileName = rm.getAdditionalData().get("fileName") == null ? null
				: rm.getAdditionalData().get("fileName").toString();

		String fileSize = rm.getAdditionalData().get("fileSize") == null ? null
				: rm.getAdditionalData().get("fileSize").toString();

		String selectedFileType = rm.getAdditionalData().get("selectedFileType") == null ? null
				: rm.getAdditionalData().get("selectedFileType").toString();

		try {
			FileUploader fu = fileUploaderService.checkStatus();
			if(fu == null) {
			if (!Objects.isNull(fileBytes)) {
				AgLogger.logInfo(rm.getUserid(), "File Exists");
				if (FilenameUtils.getExtension(fileName).equals("csv")) {
					String fileNamePath = saveFile(fileBytes, fileName);
					List<ProductPriceUploadModel> lstUploader = readCsv(fileNamePath);
					if (!lstUploader.isEmpty()) {

						FileUploader fd = new FileUploader();
						fd.setFileName(fileName);
						fd.setCrOn(new Timestamp(new java.util.Date().getTime()));
						fd.setCrBy(rm.getUserid());
						fd.setStatus("NEW");
						fd.setTotalRec(BigDecimal.valueOf(lstUploader.size()));
						fd.setType(selectedFileType);
						fd.setFilePath(fileNamePath);
						fd.setCorpId(rm.getCorpId());
						long idRec = fileUploaderService.insertRecords(fd);
						
						
						for(ProductPriceUploadModel f: lstUploader) {
							FileUploaderDetails fupd = new FileUploaderDetails();
							fupd.setFileId(Long.toString(idRec));
							fupd.setField1(f.getField1());
							fupd.setField2(f.getField2());
							fupd.setField3(f.getField3());							
							fupd.setEntryBy(rm.getUserid());
							fupd.setEntryDate(new Timestamp(new java.util.Date().getTime()));						
							fupd.setState("NEW");
							fileUploaderDetailsService.insertRecords(fupd);							
						}
						

						HashMap<Object, Object> obj = new HashMap<Object, Object>();
						obj.put("details", lstUploader);
						obj.put("fileID", idRec);

						AgLogger.logInfo(rm.getUserid(), "File is uploaded");
						return UtilAccess.generateResponse("0000", "SUCCESS", obj);
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

			}else {
			AgLogger.logInfo(rm.getUserid(), "File Aleady in Process");
			return UtilAccess.generateResponse("5555", "File Aleady in Process");}
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception: ", e);
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public static List<ProductPriceUploadModel> readCsv(String file) {

		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("TID", "field1");
		mapping.put("Product", "field2");
		mapping.put("Price", "field3");

		HeaderColumnNameTranslateMappingStrategy<ProductPriceUploadModel> strategy = new HeaderColumnNameTranslateMappingStrategy<ProductPriceUploadModel>();
		strategy.setType(ProductPriceUploadModel.class);
		strategy.setColumnMapping(mapping);

		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		CsvToBean csvToBean = new CsvToBean();

		List<ProductPriceUploadModel> list = csvToBean.parse(strategy, csvReader);

		return list;
	}

	public String saveFile(byte[] fileBytes, String fileName) {

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
			AgLogger.logDebug(getClass(), "Exception: " + e);
		}
		return result.getAbsolutePath();
	}

}
