package com.fuel.ws.classes;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ag.fuel.entity.MasFleetCoInfo;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.fuel.ws.classes.WsGetAccountInfoByAccountNumber")
public class WsGetAccountInfoByAccountNumber implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			response.setCode("0000");
			response.setMessage("SUCCESS");
			
			MasFleetCoInfo masFleetCoInfo = new MasFleetCoInfo();
			masFleetCoInfo.setCoName("DEFENSE OFFICE, EMBASSY OF CHINA");
			masFleetCoInfo.setCoBusName("DEFENSE OFFICE, EMBASSY OF CHINA");
			masFleetCoInfo.setCoCardName("DEFENSE OFFICE, EMBASSY OF CHINA");
			masFleetCoInfo.setCoStatus("A");
			masFleetCoInfo.setCoBusDesc("S");
			masFleetCoInfo.setCoAddress1("HOUSE NO.9, STREET NO.18,");
			masFleetCoInfo.setCoAddress2("SECTOR F-7/2,");
			masFleetCoInfo.setCoCity("Karachi");
			masFleetCoInfo.setCoTel1("051-2279607");
			masFleetCoInfo.setCoTel2("2279610");
			masFleetCoInfo.setCoFax1("2279609");
			masFleetCoInfo.setCoCardCnt(0l);
			masFleetCoInfo.setCoContPerson("CAPT. OUYANG HAISHENG");
			masFleetCoInfo.setContDesig("SECRETARY TO DEFENCE ATTACHE");
			masFleetCoInfo.setContDirTel1("051-2279607");
			masFleetCoInfo.setContMobile("0300-8553358");
			masFleetCoInfo.setContAddress("HOUSE NO.9, STREET NO 18,");
			masFleetCoInfo.setContCity("003");
			masFleetCoInfo.setCoFuelExp(50000.0);
			masFleetCoInfo.setCoDailyQty(0l);
			masFleetCoInfo.setCoDailyRate(0.0);
			masFleetCoInfo.setCoDlyAmt(0.0);
			masFleetCoInfo.setCoCheques("N");
			masFleetCoInfo.setCoDmndDrft("N");
			masFleetCoInfo.setCoPayOrder("N");
			masFleetCoInfo.setCoBank1Name("ABN-AMRO");
			masFleetCoInfo.setCoBank2Name("FIRST WOMEN BANK");
			masFleetCoInfo.setCoPsoCstmr("Y");
			masFleetCoInfo.setRef1StationNm("PSO SERVICE STATION, FOREIGN OFFICE");
			masFleetCoInfo.setCoAuth1Name("OUYANG HAISHENG");
			masFleetCoInfo.setCoAuth1Desig("SECRETARY TO MIL/NAV/AIR ATTACHE");
			masFleetCoInfo.setExported("Y");
			masFleetCoInfo.setCoFcardCnt(5l);
			masFleetCoInfo.setContAddress("SECTOR F-7/2,");
			masFleetCoInfo.setCoReqCrLimit(0.0);
			masFleetCoInfo.setOtherModePayment("CASH");
			masFleetCoInfo.setOtherMop("Y");
			masFleetCoInfo.setCustTaxRegno("Y");
			masFleetCoInfo.setCoAccno(123456789l);
			masFleetCoInfo.setFrequency("Monthly");
			masFleetCoInfo.setCoCode(3l);
			
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("masFleetCoInfo", masFleetCoInfo);
			
			response.setData(map);
			
			
		} catch (Exception ex) {

			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}
