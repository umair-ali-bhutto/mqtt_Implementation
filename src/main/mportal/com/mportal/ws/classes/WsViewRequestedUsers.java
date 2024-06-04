package com.mportal.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.ComplCategory;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ComplAssignmentsCustom;
import com.ag.generic.model.DataWrapper;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.ComplCategoryService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;

@Component("com.mportal.ws.classes.WsViewRequestedUsers")
public class WsViewRequestedUsers implements Wisher {

	@Autowired
	ComplCategoryService complCategoryService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	ComplAssignmentsService complAssignmentsService;

	@Autowired
	ComplaintsService complaintsService;

	public List<ComplAssignmentsCustom> complaintList;
	public List<ComplAssignmentsCustom> selectedComplaint;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			String userId = rm.getUserid();

			complaintList = new ArrayList<ComplAssignmentsCustom>();
			selectedComplaint = new ArrayList<ComplAssignmentsCustom>();
			ComplCategory cs = new ComplCategory();
			cs.setId(1);

			ComplCategory ctd = complCategoryService.fetchByID(cs);

			UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(userId));

			List<ComplAssignment> tempcomplaintList = complAssignmentsService.fetchAllByIDByCategory(ukl.getUserId(),
					ctd.getCode(), ctd.getTypeCode(), ctd.getSubTypeCode());

			for (ComplAssignment comp : tempcomplaintList) {

				Complaint cks = complaintsService.fetchComplaintById(comp.getCompId());

				ComplAssignmentsCustom c = new ComplAssignmentsCustom();

				DataWrapper dwp = new DataWrapper(cks.getDescription(), cks.getCategory(), cks.getType(),
						cks.getSubType(), "", "");
				c.setCategory(cks.getCategory());
				c.setCompId(comp.getCompId());
				c.setDescription(cks.getDescription());
				c.setEmail(dwp.getEmail());
				c.setEntryBy(cks.getEntryBy());
				c.setEntryDate(cks.getEntryDate());
				c.setId((int) comp.getId());
				c.setMid(cks.getMid());
				c.setMname("");
				c.setModel(dwp.getModel());
				c.setMsdisn(dwp.getMsdisn());
				c.setPriority("");
				c.setScreenId(0);
				c.setSerialNum(dwp.getSerialNum());
				c.setSubType(cks.getSubType());
				c.setType(cks.getType());
				c.setUserId(comp.getUserId());
				c.setMdrOffUs("00.00");
				c.setMdrOnUs("00.00");
				complaintList.add(c);
			}
			response.setCode("0000");
			response.setMessage("Success");
			response.setData(new HashMap<Object, Object>() {
				{
					put("userList", complaintList);

				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}