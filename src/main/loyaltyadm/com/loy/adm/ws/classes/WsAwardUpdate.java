package com.loy.adm.ws.classes;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONStreamAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AwardMasters;
import com.ag.loy.adm.service.AwardMastersService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.xml.messaging.saaj.packaging.mime.internet.ParseException;

@Component("com.loy.adm.ws.classes.WsAwardUpdate")
public class WsAwardUpdate implements Wisher {
	
	@Autowired
	AwardMastersService awardMastersService;
	


	String mAwardId = "";
	String createdBy = "";
	String createdOn = "";
	Date crOn = null;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			if (rm.getAdditionalData().containsKey("awardMaster") && rm.getAdditionalData().containsKey("awardPoints")) {
				AwardMasters awardMasters = new Gson().fromJson(rm.getAdditionalData().get("awardMaster").toString(),
						AwardMasters.class);

				mAwardId = rm.getAdditionalData().get("mAwardId").toString();
				mAwardId = StringUtils.leftPad(mAwardId, 5, "0");
				createdBy = rm.getAdditionalData().get("createdBY").toString();
				createdOn = rm.getAdditionalData().get("createdOn").toString();
				
				 String pattern = "yyyy-MM-dd HH:mm:ss";
				 
				 try {
			            // Create a SimpleDateFormat object with the desired pattern
			            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

			            // Parse the string to get a Date object
			            crOn = sdf.parse(createdOn);

			            System.out.println("Parsed Date: " + crOn);
			        } catch (java.text.ParseException e) {
			            e.printStackTrace();
			        }
				
				System.out.println("+++++++++"+"Description = "+awardMasters.getDescription()+" | Exp Days = "+awardMasters.getExpDays()+"+++++++++"+" | Award ID = "+mAwardId+" | Created On = "+crOn+" | Created By = "+createdBy);
				
				if(awardMasters.getDescription() == "" || awardMasters.getExpDays() == 0) {
					response.setCode("0001");
					response.setMessage("Description or Expiry Days should no be null.");
				}
				else {

					awardMasters.setmAwardId(Long.parseLong(mAwardId));
					awardMasters.setUpdatedBy(rm.getUserid());
					awardMasters.setUpdateOn(new java.util.Date());
					awardMasters.setCreatedOn(crOn);
					awardMasters.setCreatedBY(createdBy);
					awardMastersService.updateAwardMasters(awardMasters);

					response.setCode("0000");
					response.setMessage("SUCCESS");
				}
				
			} else {
				System.out.println("IN Else ____________");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

//	public void update() {
//
//		DoPrint.doPrint("checkAwardPointsLength : " + checkAwardPointsLength);
//		FacesContext context = FacesContext.getCurrentInstance();
//		checkAwardPointsLength = createObjectLenght(awardPointBean);
//		DoPrint.doPrint("checkAwardPointsLength : " + checkAwardPointsLength);
//
//		if (checkAwardPointsLength > 0 || awardPointBean != null) {
//			String minRecord = SystemProperties.getProperty("award.points.min.record");
//			String maxRecord = SystemProperties.getProperty("award.points.max.record");
//			if (checkAwardPointsLength < Integer.parseInt(minRecord)) {
//				context.addMessage(null, new FacesMessage("Please enter complete data for Award configuration."));
//			} else if (checkAwardPointsLength > Integer.parseInt(maxRecord)) {
//				context.addMessage(null, new FacesMessage(
//						"Maximum slabs records for Award configuration cannot exceed " + Integer.parseInt(maxRecord)));
//			} else {
//				try {
//					System.out.println("Update Method Working");
//					AwardPointsDAOImpl awardPointsDAOImpl = new AwardPointsDAOImpl();
//					AwardMastersDAOImpl awardMastersDAOImpl = new AwardMastersDAOImpl();
//					System.out.println(">>>>>" + items.size());
//					Long mawarId = null;
//					System.out.println("awardMasters : " + awardMasters);
//					if (awardMasters != null) {
//						if (awardMasters.getExpDays() != null) {
//							if (awardMasters.getExpDays() > 0 && awardMasters.getExpDays() <= Integer
//									.parseInt(SystemProperties.getProperty("max.awards.exp.days"))) {
//
//								String mesgg = checkErrorAmount(awardPointBean);
//								if (mesgg == null) {
//									if ((!checkErroMessage)) {
//										int check = awardPointsDAOImpl.deleteAwardPoints(awardMasters.getmAwardId());
//										DoPrint.doPrint("DELETE CHECK : " + check);
////				if (check != 0) {
//										if (awardMasters != null) {
//											System.out.println("awardMasters IF : " + awardMasters);
//											awardMasters.setCreatedBY(SessionManager.getSessionAttribute("loginID"));
//											awardMasters.setCreatedOn(new java.util.Date());
//											awardMasters.setUpdatedBy(SessionManager.getSessionAttribute("loginID"));
//											awardMasters.setUpdateOn(new java.util.Date());
//											awardMasters.setActive("A");
//											awardMastersDAOImpl.updateAwardMasters(awardMasters);
//											mawarId = awardMasters.getmAwardId();
//										}
//										if (checkAwardPointsLength > 0) {
//											AwardPoints aps = null;
//											for (int i = 1; i <= checkAwardPointsLength; i++) {
//												switch (i) {
//												case 1:
//													aps = new AwardPoints();
//													aps.setAmountFrom(awardPointBean.getAmountFrom1());
//													aps.setAmountTo(awardPointBean.getAmountTo1());
//													aps.setAmountSlab(i);
//													aps.setExpDays(awardPointBean.getExpDays1());
//													aps.setMax(awardPointBean.getMax1());
//													aps.setMin(awardPointBean.getMin1());
//													aps.setPrec(awardPointBean.getPrec1());
//													aps.setAccountDr(awardPointBean.getAccountDr1());
//													aps.setCreatedOn(new java.util.Date());
//													aps.setCreatedBY(SessionManager.getSessionAttribute("loginID"));
//													awardPointsDAOImpl.saveAwardPoints(aps, awardMasters.getmAwardId());
//
//													break;
//												case 2:
//													aps = new AwardPoints();
//													aps.setAmountFrom(awardPointBean.getAmountFrom2());
//													aps.setAmountTo(awardPointBean.getAmountTo2());
//													aps.setAmountSlab(i);
//													aps.setExpDays(awardPointBean.getExpDays2());
//													aps.setMax(awardPointBean.getMax2());
//													aps.setMin(awardPointBean.getMin2());
//													aps.setPrec(awardPointBean.getPrec2());
//													aps.setAccountDr(awardPointBean.getAccountDr2());
//													aps.setCreatedOn(new java.util.Date());
//													aps.setCreatedBY(SessionManager.getSessionAttribute("loginID"));
//													awardPointsDAOImpl.saveAwardPoints(aps, mawarId);
//													break;
//												case 3:
//													aps = new AwardPoints();
//													aps.setAmountFrom(awardPointBean.getAmountFrom3());
//													aps.setAmountTo(awardPointBean.getAmountTo3());
//													aps.setAmountSlab(i);
//													aps.setExpDays(awardPointBean.getExpDays3());
//													aps.setMax(awardPointBean.getMax3());
//													aps.setMin(awardPointBean.getMin3());
//													aps.setPrec(awardPointBean.getPrec3());
//													aps.setAccountDr(awardPointBean.getAccountDr3());
//													aps.setCreatedOn(new java.util.Date());
//													aps.setCreatedBY(SessionManager.getSessionAttribute("loginID"));
//													awardPointsDAOImpl.saveAwardPoints(aps, mawarId);
//													break;
//												case 4:
//													aps = new AwardPoints();
//													aps.setAmountFrom(awardPointBean.getAmountFrom4());
//													aps.setAmountTo(awardPointBean.getAmountTo4());
//													aps.setAmountSlab(i);
//													aps.setExpDays(awardPointBean.getExpDays4());
//													aps.setMax(awardPointBean.getMax4());
//													aps.setMin(awardPointBean.getMin4());
//													aps.setPrec(awardPointBean.getPrec4());
//													aps.setAccountDr(awardPointBean.getAccountDr4());
//													aps.setCreatedOn(new java.util.Date());
//													aps.setCreatedBY(SessionManager.getSessionAttribute("loginID"));
//													awardPointsDAOImpl.saveAwardPoints(aps, mawarId);
//													break;
//												case 5:
//													aps = new AwardPoints();
//													aps.setAmountFrom(awardPointBean.getAmountFrom5());
//													aps.setAmountTo(awardPointBean.getAmountTo5());
//													aps.setAmountSlab(i);
//													aps.setExpDays(awardPointBean.getExpDays5());
//													aps.setMax(awardPointBean.getMax5());
//													aps.setMin(awardPointBean.getMin5());
//													aps.setPrec(awardPointBean.getPrec5());
//													aps.setAccountDr(awardPointBean.getAccountDr5());
//													aps.setCreatedOn(new java.util.Date());
//													aps.setCreatedBY(SessionManager.getSessionAttribute("loginID"));
//													awardPointsDAOImpl.saveAwardPoints(aps, mawarId);
//													break;
//												case 6:
//													aps = new AwardPoints();
//													aps.setAmountFrom(awardPointBean.getAmountFrom6());
//													aps.setAmountTo(awardPointBean.getAmountTo6());
//													aps.setAmountSlab(i);
//													aps.setExpDays(awardPointBean.getExpDays6());
//													aps.setMax(awardPointBean.getMax6());
//													aps.setMin(awardPointBean.getMin6());
//													aps.setPrec(awardPointBean.getPrec6());
//													aps.setAccountDr(awardPointBean.getAccountDr6());
//													aps.setCreatedOn(new java.util.Date());
//													aps.setCreatedBY(SessionManager.getSessionAttribute("loginID"));
//													awardPointsDAOImpl.saveAwardPoints(aps, mawarId);
//													break;
//												default:
//													break;
//												}
//												if (i == checkAwardPointsLength) {
//													context.addMessage(null,
//															new FacesMessage("Record Updated Successfully."));
//													awardMastersList = new AwardMastersDAOImpl().fetchAllRecords();
//													cancle();
//												}
//
//											}
//										}
//									}
//
//								} else {
//									context.addMessage(null, new FacesMessage(mesgg));
//								}
////								} else {
////									context.addMessage(null, new FacesMessage("Error in Updated Record."));
////								}
//
//							} else {
//								context.addMessage(null, new FacesMessage("Please enter Valid Expiry Days."));
//							}
//						} else {
//							context.addMessage(null, new FacesMessage("Please enter Valid Expiry Days."));
//						}
//					} else {
//						context.addMessage(null, new FacesMessage("There Should be some data in Award Masters."));
//					}
//
//				} catch (Exception ex) {
//					context.addMessage(null, new FacesMessage("ERROR in Update."));
//					ex.printStackTrace();
//				}
//			}
//		} else {
//			context.addMessage(null, new FacesMessage("There should be some data in Award Points."));
//		}
//
//	}

}
