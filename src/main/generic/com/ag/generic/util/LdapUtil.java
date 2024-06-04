package com.ag.generic.util;

import java.util.HashMap;
import java.util.Map;

import com.ag.generic.model.LDAPResponseModel;
import com.ag.generic.model.LDAPUserModel;
import com.ag.generic.prop.AppProp;

public class LdapUtil {

	public LDAPResponseModel getLdapValidate(String userName) {
		LDAPResponseModel rm = new LDAPResponseModel();
		try {
			String sk = AppProp.getProperty("ldap.url.validate");
			sk = sk.replace("@PARUSER", userName);
			AgLogger.logInfo( sk);
			String m = new HttpUtil().doGetWithoutTime(sk);
			AgLogger.logInfo( m);
			Map<String, String> ms = convertMap(m);
			rm.setCode(ms.get("code"));
			rm.setMessage(ms.get("message"));
			try {
				if (ms.get("code").equals("0000")) {
					LDAPUserModel usm = new LDAPUserModel();
					usm.setsAMAccountName(ms.get("userAmaName"));
					usm.setGivenName(ms.get("userGivenName"));
					usm.setDepartment(ms.get("userDepartment"));
					usm.setCountryCode(ms.get("userCountryCode"));
					usm.setCo(ms.get("userCountry"));
					usm.setCn(ms.get("userCn"));
					usm.setStreetAddress(ms.get("userStrtAddres"));
					usm.setUserPrincipalName(ms.get("userPrincipleName"));
					usm.setDistinguishedname(ms.get("userDistinguishName"));
					usm.setUserAccountControl(ms.get("userAccountControl").replace("}", "").replace("{", ""));
					usm.setTelephoneNumber(ms.get("userTelephoneNumber"));
					rm.setUsm(usm);
				}
			} catch (Exception e) {
				AgLogger.logerror(getClass(), e.toString(),e);
			}
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  " + e,e);
		}

		return rm;
	}

	public LDAPResponseModel getLdapLogin(String userName, String pass) {
		LDAPResponseModel rm = new LDAPResponseModel();
		try {
			String sk = AppProp.getProperty("ldap.url.login");
			sk = sk.replace("@PARUSER", userName).replace("@PARPASS", pass);
			String gk = AppProp.getProperty("ldap.url.login");
			gk = gk.replace("@PARUSER", userName);
			AgLogger.logInfo( gk);
			String m = new HttpUtil().doGetWithoutTime(sk);
			System.out.println(m);
			Map<String, String> ms = convertMap(m);
			rm.setCode(ms.get("code"));
			rm.setMessage(ms.get("message"));
			try {
				if (ms.get("code").equals("0000")) {
					LDAPUserModel usm = new LDAPUserModel();
					usm.setsAMAccountName(ms.get("userAmaName"));
					usm.setGivenName(ms.get("userGivenName"));
					usm.setDepartment(ms.get("userDepartment"));
					usm.setCountryCode(ms.get("userCountryCode"));
					usm.setCo(ms.get("userCountry"));
					usm.setCn(ms.get("userCn"));
					usm.setStreetAddress(ms.get("userStrtAddres"));
					usm.setUserPrincipalName(ms.get("userPrincipleName"));
					usm.setDistinguishedname(ms.get("userDistinguishName"));
					usm.setUserAccountControl(ms.get("userAccountControl").replace("}", "").replace("{", ""));
					usm.setTelephoneNumber(ms.get("userTelephoneNumber"));
					rm.setUsm(usm);
				}
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " ERROR " + e,e);
			}
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION "+ e,e);
		}

		return rm;
	}

	public static void main(String args[]) {
		LDAPResponseModel rs = new LdapUtil().getLdapValidate("anwar");
		System.out.println(rs.getCode());
	}
	
	
	public static Map<String, String> convertMap(String k) {
		// k = k.replaceAll("}", "");
		String parts[] = k.split(",");
		Map<String, String> hMapData = new HashMap<String, String>();

		// iterate the parts and add them to a map
		for (String part : parts) {

			// split the employee data by : to get id and name
			String empdata[] = part.split("=");
			String strId = empdata[0].trim();
			String strName = "";

			try {
				if (empdata[1] != null) {
					strName = empdata[1];
				}

			} catch (Exception e) {
				strName = "-";
			}

			// add to map
			hMapData.put(strId, strName);
		}
		return hMapData;
	}
}