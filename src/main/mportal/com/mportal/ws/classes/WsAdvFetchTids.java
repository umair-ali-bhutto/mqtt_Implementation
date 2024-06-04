package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.mportal.ws.classes.WsAdvFetchTids")
public class WsAdvFetchTids implements Wisher {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			String mid = (rm.getAdditionalData().containsKey("mid")) ? rm.getAdditionalData().get("mid").toString()
					: null;

			List<String> lst = null;
			String query = AppProp.getProperty("adv.fetch.tids");
			query = query.replaceAll("@PARAM", "'"+mid+"'");
			AgLogger.logInfo(query);
			Query cb = entityManager.createNativeQuery(query);
			
			lst = (List<String>) cb.getResultList();
			

			if (lst.size() != 0) {
				
				response.setCode("0000");
				response.setMessage("Success.");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				
				obj.put("lst", lst);
				response.setData(obj);
			} else {
				response.setCode("0002");
				response.setMessage("No Record Found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
}
