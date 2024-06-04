package com.ag.loy.adm.model;

public class AwardUtil {

	
	
	public static String getLeadingZero(String ids) {
		String finId = "";
		switch (ids.length()) {
		case 5:
			finId = ids;
			break;
		case 4:
			finId = "0"+ids;
			break;
		case 3:
			finId = "00"+ids;
			break;
		case 2:
			finId = "000"+ids;
			break;
		case 1:
			finId = "0000"+ids;
			break;
		default:
			break;
		}
		return finId;
		
	}
	
}

