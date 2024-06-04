package com.mportal.ws.classes;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplCategory;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.ComplaintsWebService;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplCategoryService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.service.impl.LovServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.model.UtilReport;

@Component("com.mportal.ws.classes.WsViewComplaints")
public class WsViewComplaints implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	ComplCategoryService complCategoryService;

	@Autowired
	ComplaintsService complaintsService;

	@Autowired
	LovServiceImpl lovServiceImpl;
	
	

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			List<LovDetail> listLoveDetails = lovServiceImpl.fetchLovsDetailsAll(rm.getCorpId());
			List<LovMaster> listLovMaster = lovServiceImpl.fetchLovs(rm.getCorpId());
			HashMap<Integer, Object> userListAll = userLoginService.fetchAllUsersId(rm.getCorpId());
			Date frDate = null, tDate = null;
			List<Complaint> compLogs = null;
			ComplCategory cs = new ComplCategory();
			cs.setId(2);

			String mid = rm.getAdditionalData().get("mId") == null ? null
					: rm.getAdditionalData().get("mId").toString();
			String tid = rm.getAdditionalData().get("tid") == null ? null
					: rm.getAdditionalData().get("tid").toString();
			String FrDate = rm.getAdditionalData().get("FrDate") == null ? null
					: rm.getAdditionalData().get("FrDate").toString();
			String toDate = rm.getAdditionalData().get("tDate") == null ? null
					: rm.getAdditionalData().get("tDate").toString();
//			String region = rm.getAdditionalData().get("region") == null ? null
//					: rm.getAdditionalData().get("region").toString();
//			String city = rm.getAdditionalData().get("city") == null ? null
//					: rm.getAdditionalData().get("city").toString();
			String complaintId = rm.getAdditionalData().get("compId") == null ? null
					: rm.getAdditionalData().get("compId").toString();
			
			int loggedInUserID = Integer.parseInt(rm.getUserid());

			UserLogin user = userLoginService.validetUserid(loggedInUserID);

			ComplCategory ctd = complCategoryService.fetchByID(cs);

			if (!Objects.isNull(complaintId)) {
				Integer complaintIdInt = null;
				try {
					complaintIdInt = Integer.parseInt(complaintId);
				} catch (Exception ex) {

				}
				if (!Objects.isNull(complaintIdInt)) {
					Complaint complaint = complaintsService.fetchComplaintById(complaintIdInt);
					if (!Objects.isNull(complaint)) {
						compLogs = new ArrayList<Complaint>();
						compLogs.add(complaint);
					}
				}
			} else {
				if (mid != null) {
					if (!mid.equalsIgnoreCase("all")) {
						mid = user.getMid();
					}

					if (mid.equalsIgnoreCase("all")) {
						mid = null;
					}
				}

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				try {
					if (FrDate != null) {
						frDate = sdf.parse(FrDate);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (toDate != null) {
						tDate = sdf.parse(toDate);
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}

				int SuperUserGroupCode = Integer.parseInt(AppProp.getProperty("super.user.group.code"));
				
				if(user.getGroupCode()!=SuperUserGroupCode) {
					compLogs = complaintsService.fetchAllComplaints(mid, tid, frDate, tDate, ctd.getCode(),rm.getUserid(),rm.getUserid());	
				}else {
					compLogs = complaintsService.fetchAllComplaints(mid, tid, frDate, tDate, ctd.getCode(),null,null);
				}
				
				
			}

			if (compLogs != null) {

				List<ComplaintsWebService> lstComp = new ArrayList<ComplaintsWebService>();
				for (Complaint com : compLogs) {
					ComplaintsWebService mdl = new ComplaintsWebService();
					mdl.setId(com.getId());
					mdl.setMid(com.getMid());
					mdl.setTid(com.getTid());
					mdl.setComplaintSub_Type(com.getComplaintSubType());
					mdl.setComplaintType(com.getComplaintType());
					mdl.setType(getTypeCompl(com.getType(),listLovMaster));
					mdl.setCategory(com.getCategory());
					mdl.setModel(com.getModel());
					mdl.setSubType(getSubTypeCompl(com.getComplaintSubType(), com.getComplaintType(), listLoveDetails));
					mdl.setSerialNumber(com.getSerialNumber());
					mdl.setMaker(com.getMaker());
					mdl.setStatus(com.getStatus());
					mdl.setEntryBy(com.getEntryBy());
					mdl.setEntryDate(com.getEntryDate());
					mdl.setLastUpdated(com.getLastUpdated());
					mdl.setClosureDate(com.getClosureDate());
					mdl.setClosedBy(getAssignedTo(Integer.parseInt((com.getClosedBy()!=null)?com.getClosedBy():"0"),userListAll));
					mdl.setAssignedDate(com.getAssignedDate());
					mdl.setAssignedTo(getAssignedTo(Integer.parseInt((com.getAssignedTo()!=null)?com.getAssignedTo():"0"),userListAll));
					mdl.setAssignedAddress(com.getAssignedAddress());
					mdl.setComplaintDesc(com.getComplaintDesc());
					mdl.setCanClosable(UtilAccess.allowableToClose(user, com.getStatus(), (com.getAssignedTo()!=null)?com.getAssignedTo():"0"));
					mdl.setCanbeAssigned(UtilAccess.allowableToAsign(user, com.getStatus(), (com.getAssignedTo()!=null)?com.getAssignedTo():"0"));
					mdl.setRemarks(com.getResolution());
					lstComp.add(mdl);
				}

				response.setCode("0000");
				response.setMessage("SUCCESS");
				HashMap<Object, Object> mk = new HashMap<Object, Object>();
				mk.put("complaints", lstComp);
				String[] blob = createReport(rm, lstComp);
				if (!Objects.isNull(blob)) {
					mk.put("blob", blob[0]);
					mk.put("fileName", blob[1]);
				}
				response.setData(mk);
			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	public static String getSubTypeCompl(String subtypeid, String type, List<LovDetail> listLoveDetails) {
		for (LovDetail lo : listLoveDetails) {
			if (lo.getId().equals(subtypeid) && lo.getLovId().equals(type)) {
				return lo.getValue();
			}
		}
		return "N/A";
	}

	public static String getTypeCompl(String typeid, List<LovMaster> lovMaster) {
		try {
			for (LovMaster m : lovMaster) {
				if (m.getId().equals(typeid)) {
					return m.getValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "N/A";
	}
	
	public static String getAssignedTo(int id,HashMap<Integer, Object> userListAll) {

		try {
			UserLogin usl = (UserLogin) userListAll.get(id);
			if (usl != null) {
				return usl.getUserName();
			}
		} catch (Exception e) {

		}
		return "N/A";
	}
	
	
	public String[] createReport(RequestModel rm, List<ComplaintsWebService> lstReportItems) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

		String rootReportPath = AppProp.getProperty("complaincsv.path") + rm.getUserid();
		File rootReport = new File(AppProp.getProperty("complaincsv.path") + rm.getUserid());
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}

		String reportFormat = AppProp.getProperty("complaint.report.format");
		if (Objects.isNull(reportFormat) || reportFormat.isEmpty()) {
			reportFormat = "csv";
		} else {
			reportFormat = reportFormat.trim();
		}

		String fileName = "Complaint" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			if (reportFormat.equals("csv")) {
				UtilReport.createComplaintCsvFile(lstReportItems,result.getAbsolutePath());
			} else {
				UtilReport.createComplaintExcelFile(lstReportItems,result.getAbsolutePath());
			}
		} catch (ParseException e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception while creating report", e);
		}

		String[] blob = null;
		try {
			blob = new String[2];
			blob[0] = UtilAccess.convertFileToBlob(result.getAbsolutePath());
			blob[1] = fileName;
		} catch (IOException e) {
			e.printStackTrace();
			blob = null;
			AgLogger.logerror(getClass(), "Exception while creating blob", e);
		}
		return blob;
	}

}
