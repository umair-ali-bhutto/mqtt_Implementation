package com.ag.generic.prop;

import com.ag.generic.util.UtilAccess;

public class AppProp {

	public static String getProperty(String key) {
		if (UtilAccess.getConfigPropMap().containsKey(key)) {
			return UtilAccess.getConfigPropMap().get(key);
		} else {
			new Exception("Property Not Found with Key " + key);
			return "N/A";
		}

	}

}
