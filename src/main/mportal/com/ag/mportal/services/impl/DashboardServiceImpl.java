package com.ag.mportal.services.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.mportal.model.PieDataChartModel;
import com.ag.mportal.model.WeeklySalesBarChartModel;
import com.ag.mportal.model.YearlySalesChartModel;
import com.ag.mportal.services.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public YearlySalesChartModel getYearSalesChartModel(List<String> mid) {
		String query = AppProp.getProperty("yearly.chart.query");
		List<Object[]> lstObj = null;
		YearlySalesChartModel mdl = null;
		List<YearlySalesChartModel> charList = null;
		boolean isAllDataZero = true;
		if (mid != null) {
			if (mid.size() != 0) {
				String jk = "";
				for (String mer : mid) {
					jk += "'" + mer + "',";
				}

				if (jk.length() != 0) {
					jk = jk.substring(0, jk.length() - 1);
				}

				String m = " AND MERCHANT_ID in (" + jk + ") ";

				query = query.replaceAll("@PARAMMIDCLAUSE", m);
			} else {
				query = query.replaceAll("@PARAMMIDCLAUSE", "");
			}
		} else {
			query = query.replaceAll("@PARAMMIDCLAUSE", "");
		}

		AgLogger.logDebug("0","Fetching Rec From getYearSalesChartModel " + query);
		try {
			Query cb = entityManager.createNativeQuery(query);
			lstObj = cb.getResultList();
		} catch (NoResultException nre) {
			AgLogger.logInfo("No Rec Found From getYearSalesChartModel");
		}

		if (lstObj != null) {
			mdl = new YearlySalesChartModel();
			charList = new ArrayList<YearlySalesChartModel>();
			List<String> monthList = new ArrayList<String>();
			List<String> txnTypeList = new ArrayList<String>();
			HashMap<String, List<Float>> mp = new HashMap<String, List<Float>>();
			List<Float> lbw = null;
			for (Object[] obj : lstObj) {
				txnTypeList.add(obj[0].toString());
				monthList.add(obj[2].toString());
				Float txnAmount = Float.parseFloat(obj[3].toString());
				if (txnAmount != 0.0) {
					isAllDataZero = false;
				}
				if (mp.containsKey(obj[0].toString())) {
					lbw = mp.get(obj[0].toString());
					lbw.add(txnAmount);
					mp.put(obj[0].toString(), lbw);
				} else {
					lbw = new ArrayList<Float>();
					lbw.add(txnAmount);
					mp.put(obj[0].toString(), lbw);
				}

			}

			List<String> monthListFinal = monthList.stream().distinct().collect(Collectors.toList());
			List<String> txnTypeListFinal = txnTypeList.stream().distinct().collect(Collectors.toList());
			String YearlyChartcolor = AppProp.getProperty("yearly.chart.color");
			String[] color = YearlyChartcolor.split(",");
			int i = 0;
			for (String l : txnTypeListFinal) {
				YearlySalesChartModel ysc = new YearlySalesChartModel();
				ysc.setBackgroundColor(color[i]);
				ysc.setBorderColor(color[i]);
				ysc.setData(mp.get(l));
				ysc.setFill("false");
				ysc.setLabel(l);
				charList.add(ysc);
				i++;
			}

			if (!isAllDataZero) {
				mdl.setLabels(monthListFinal);
				mdl.setDatasets(charList);
			} else {
				List<String> s = new ArrayList<String>();
				List<Float> f = new ArrayList<Float>();
				s.add("No data");
				f.add(0.0f);
				mdl.setLabels(s);
				List<YearlySalesChartModel> charListFin = new ArrayList<YearlySalesChartModel>();
				YearlySalesChartModel wsb = new YearlySalesChartModel();
				wsb.setBackgroundColor("#D3D3D3");
				wsb.setBorderColor("#D3D3D3");
				wsb.setData(f);
				wsb.setLabel("No data");
				charListFin.add(wsb);
				mdl.setDatasets(charListFin);
			}

			return mdl;
		} else {
			return null;
		}

	}

	@Override
	public WeeklySalesBarChartModel getWeeklySalesChartModel(List<String> mid, String inputdate) {
		boolean isAllDataZero = true;
		String[] datValues = getCurrentAndPreviousWeekDates(inputdate);
		if (datValues != null) {
			String query = AppProp.getProperty("weekly.chart.query");
			List<Object[]> lstObj = null;
			WeeklySalesBarChartModel mdl = null;
			List<WeeklySalesBarChartModel> charList = null;
			if (mid != null) {
				if (mid.size() != 0) {
					String jk = "";
					for (String mer : mid) {
						jk += "'" + mer + "',";
					}

					if (jk.length() != 0) {
						jk = jk.substring(0, jk.length() - 1);
					}

					String m = " AND MERCHANT_ID in (" + jk + ") ";

					query = query.replaceAll("@PARAMMIDCLAUSE", m);
				} else {
					query = query.replaceAll("@PARAMMIDCLAUSE", "");
				}
			} else {
				query = query.replaceAll("@PARAMMIDCLAUSE", "");
			}

			query = query.replaceAll("@PARAMDATE1", datValues[0]);
			query = query.replaceAll("@PARAMDATE2", datValues[1]);
			query = query.replaceAll("@PARAMDATE3", datValues[2]);
			query = query.replaceAll("@PARAMDATE4", datValues[3]);

			AgLogger.logDebug("0","Fetching Rec From getWeeklySalesChartModel " + query);
			try {
				Query cb = entityManager.createNativeQuery(query);
				lstObj = cb.getResultList();
			} catch (NoResultException nre) {
				AgLogger.logInfo("No Rec Found From getWeeklySalesChartModel");
			}

			if (lstObj != null) {
				mdl = new WeeklySalesBarChartModel();
				charList = new ArrayList<WeeklySalesBarChartModel>();
				List<String> weekList = new ArrayList<String>();
				List<String> txnTypeList = new ArrayList<String>();
				HashMap<String, List<Float>> mp = new HashMap<String, List<Float>>();
				List<Float> lbw = null;
				for (Object[] obj : lstObj) {
					txnTypeList.add(obj[1].toString());
					weekList.add(obj[3].toString());
					Float txnAmount = Float.parseFloat(obj[4].toString());
					if (txnAmount != 0.0) {
						isAllDataZero = false;
					}
					if (mp.containsKey(obj[1].toString())) {
						lbw = mp.get(obj[1].toString());
						lbw.add(txnAmount);
						mp.put(obj[1].toString(), lbw);
					} else {
						lbw = new ArrayList<Float>();
						lbw.add(txnAmount);
						mp.put(obj[1].toString(), lbw);
					}

				}

				List<String> weekListFinal = weekList.stream().distinct().collect(Collectors.toList());
				List<String> txnTypeListFinal = txnTypeList.stream().distinct().collect(Collectors.toList());
				String weeklyChartBack = AppProp.getProperty("weekly.chart.color");
				String[] color = weeklyChartBack.split(",");
				int i = 0;

				for (String l : txnTypeListFinal) {

					WeeklySalesBarChartModel wsb = new WeeklySalesBarChartModel();
					wsb.setBackgroundColor(color[i]);
					wsb.setBorderColor(color[i]);
					wsb.setData(mp.get(l));
					wsb.setLabel(l);
					charList.add(wsb);
					i++;
				}

				// See if all Data has Zero Values
				if (!isAllDataZero) {
					mdl.setLabels(weekListFinal);
					mdl.setDatasets(charList);
				} else {
					List<String> s = new ArrayList<String>();
					List<Float> f = new ArrayList<Float>();
					s.add("No data");
					f.add(0.0f);
					mdl.setLabels(s);
					List<WeeklySalesBarChartModel> charListFin = new ArrayList<WeeklySalesBarChartModel>();
					WeeklySalesBarChartModel wsb = new WeeklySalesBarChartModel();
					wsb.setBackgroundColor("#D3D3D3");
					wsb.setBorderColor("#D3D3D3");
					wsb.setData(f);
					wsb.setLabel("No data");
					charListFin.add(wsb);
					mdl.setDatasets(charListFin);
				}

				return mdl;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	
	public String[] getCurrentAndPreviousWeekDates(String date) {
		try {
			String[] s = new String[4];
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(date));
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			s[0] = df.format(c.getTime());
			for (int i = 0; i < 6; i++) {
				c.add(Calendar.DATE, 1);
			}
			s[1] = df.format(c.getTime());

			c.add(Calendar.DATE, -7);
			s[3] = df.format(c.getTime());
			c.setTime(c.getTime());
			c.add(Calendar.DATE, -6);
			s[2] = df.format(c.getTime());
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	@Override
	public String[] getCurrentWeekDateAndPreviousWeekDate(String date) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			String[] s = new String[2];
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(date));
			s[0] = df.format(c.getTime());
			c.add(Calendar.DATE, -7);
			s[1] = df.format(c.getTime());
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String[] getDatesByWeekIndex(String date,int dayIndex) {
		try {
			String[] currentWeek = new String[7];
			String[] previousWeek = new String[7];
			String[] result = new String[2];
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(date));
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			currentWeek[0] = df.format(c.getTime());
			int temp = 0;
			for (int i = 0; i < 6; i++) {
				c.add(Calendar.DATE, 1);
				temp = i + 1;
				currentWeek[temp] = df.format(c.getTime());
			}

			c.add(Calendar.DATE, -13);
			temp = 0;
			c.setTime(c.getTime());
			previousWeek[temp] = df.format(c.getTime());
			for (int i = 0; i < 6; i++) {
				c.add(Calendar.DATE, 1);
				temp = i + 1;
				previousWeek[temp] = df.format(c.getTime());
			}

			result[0] = currentWeek[dayIndex];
			result[1] = previousWeek[dayIndex];
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HashMap<String, Object> getTodayPieChartData(List<String> mid,String[] datValues) {
		boolean isAllDataZero = true;
		if (datValues != null) {
			HashMap<String, Object> mp = new HashMap<String, Object>();
			String query = AppProp.getProperty("TodayPieChartData.query");
			query = query.replaceAll("@PARAMDATE1", datValues[0]);
			if (mid != null) {
				if (mid.size() != 0) {
					String jk = "";
					for (String mer : mid) {
						jk += "'" + mer + "',";
					}

					if (jk.length() != 0) {
						jk = jk.substring(0, jk.length() - 1);
					}

					String m = " AND MERCHANT_ID in (" + jk + ") ";

					query = query.replaceAll("@PARAMMIDCLAUSE", m);
				} else {
					query = query.replaceAll("@PARAMMIDCLAUSE", "");
				}
			} else {
				query = query.replaceAll("@PARAMMIDCLAUSE", "");
			}

			List<Object[]> lstObj = null;
			AgLogger.logDebug("0","Fetching Rec From getTodayPieChartData " + query);
			try {
				Query cb = entityManager.createNativeQuery(query);
				lstObj = cb.getResultList();
			} catch (NoResultException nre) {
				AgLogger.logInfo("No Rec Found From getTodayPieChartData");
			}

			if (lstObj != null) {
				PieDataChartModel pieDataChartModel = new PieDataChartModel();
				List<PieDataChartModel> mdlList = new ArrayList<PieDataChartModel>();
				List<String> bg = Arrays.asList(AppProp.getProperty("PieChartData.colorA"),
						AppProp.getProperty("PieChartData.colorB"), AppProp.getProperty("PieChartData.colorC"));
				List<Float> data = new ArrayList<Float>();

				Float[] f = new Float[lstObj.size() + 1];
				List<String> txnTypeList = new ArrayList<String>();
				int countTotalTxn = 0;
				int k = 0;
				for (Object[] obj : lstObj) {
					Float txnAmount = Float.parseFloat(obj[1].toString());
					if (txnAmount != 0.0) {
						isAllDataZero = false;
					}
					f[k] = txnAmount;
					k++;
					txnTypeList.add(obj[0].toString());
					countTotalTxn += Integer.parseInt(obj[2].toString());
				}


				if (!isAllDataZero) {
					data = Arrays.asList(f);

					PieDataChartModel pd = new PieDataChartModel();
					pd.setBackgroundColor(bg);
					pd.setData(data);
					mdlList.add(pd);

					List<String> txnTypeListFinal = txnTypeList.stream().distinct().collect(Collectors.toList());
					pieDataChartModel.setLabels(txnTypeListFinal);
					pieDataChartModel.setDatasets(mdlList);
				} else {
					txnTypeList = new ArrayList<>();
					txnTypeList	.add("No Data");
					PieDataChartModel pdcm = new PieDataChartModel();
					List<String> lstGreyColor = new ArrayList<String>();
					lstGreyColor.add("#D3D3D3");
					pdcm.setBackgroundColor(lstGreyColor);
					List<Float> noDataVal = new ArrayList<Float>();
					noDataVal.add(100.0f);
					pdcm.setData(noDataVal);
					mdlList = new ArrayList<PieDataChartModel>();
					mdlList.add(pdcm);
					pieDataChartModel.setLabels(txnTypeList);
					pieDataChartModel.setDatasets(mdlList);
					
				}

				mp.put("obj", pieDataChartModel);
				mp.put("obj_count", countTotalTxn);
				

				return mp;
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	@Override
	public HashMap<String, Object> getPreviousDayPieChartData(List<String> mid,String[] datValues) {
		boolean isAllDataZero = true;
		if (datValues != null) {
			HashMap<String, Object> mp = new HashMap<String, Object>();
			String query = AppProp.getProperty("PreviousDayPieChartData.query");
			query = query.replaceAll("@PARAMDATE1", datValues[1]);
			if (mid != null) {
				if (mid.size() != 0) {
					String jk = "";
					for (String mer : mid) {
						jk += "'" + mer + "',";
					}

					if (jk.length() != 0) {
						jk = jk.substring(0, jk.length() - 1);
					}

					String m = " AND MERCHANT_ID in (" + jk + ") ";

					query = query.replaceAll("@PARAMMIDCLAUSE", m);
				} else {
					query = query.replaceAll("@PARAMMIDCLAUSE", "");
				}
			} else {
				query = query.replaceAll("@PARAMMIDCLAUSE", "");
			}

			List<Object[]> lstObj = null;
			AgLogger.logDebug("0","Fetching Rec From getTodayPieChartData " + query);
			try {
				Query cb = entityManager.createNativeQuery(query);
				lstObj = cb.getResultList();
			} catch (NoResultException nre) {
				AgLogger.logInfo("No Rec Found From getTodayPieChartData");
			}

			if (lstObj != null) {
				PieDataChartModel pieDataChartModel = new PieDataChartModel();
				List<PieDataChartModel> mdlList = new ArrayList<PieDataChartModel>();
				List<String> bg = Arrays.asList(AppProp.getProperty("PieChartData.colorA"),
						AppProp.getProperty("PieChartData.colorB"), AppProp.getProperty("PieChartData.colorC"));
				List<Float> data = new ArrayList<Float>();

				Float[] f = new Float[lstObj.size() + 1];
				List<String> txnTypeList = new ArrayList<String>();
				int countTotalTxn = 0;
				int k = 0;
				for (Object[] obj : lstObj) {
					Float txnAmount = Float.parseFloat(obj[1].toString());
					if (txnAmount != 0.0) {
						isAllDataZero = false;
					}
					f[k] = txnAmount;
					k++;
					txnTypeList.add(obj[0].toString());
					countTotalTxn += Integer.parseInt(obj[2].toString());
				}
				if (!isAllDataZero) {
					data = Arrays.asList(f);

					PieDataChartModel pd = new PieDataChartModel();
					pd.setBackgroundColor(bg);
					pd.setData(data);
					mdlList.add(pd);

					List<String> txnTypeListFinal = txnTypeList.stream().distinct().collect(Collectors.toList());
					pieDataChartModel.setLabels(txnTypeListFinal);
					pieDataChartModel.setDatasets(mdlList);
				} else {
					txnTypeList = new ArrayList<>();
					txnTypeList	.add("No Data");
					PieDataChartModel pdcm = new PieDataChartModel();
					List<String> lstGreyColor = new ArrayList<String>();
					lstGreyColor.add("#D3D3D3");
					pdcm.setBackgroundColor(lstGreyColor);
					List<Float> noDataVal = new ArrayList<Float>();
					noDataVal.add(100.0f);
					pdcm.setData(noDataVal);
					mdlList = new ArrayList<PieDataChartModel>();
					mdlList.add(pdcm);
					pieDataChartModel.setLabels(txnTypeList);
					pieDataChartModel.setDatasets(mdlList);
					
				}

				mp.put("obj", pieDataChartModel);
				mp.put("obj_count", countTotalTxn);

				return mp;
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	@Override
	public int getOpenComplaintsCount(List<String> mid, String userId,int userGroupCOde) {
		String query = AppProp.getProperty("OpenComplaintsCount.query");
		String mQUery = "";
		if (mid != null) {
			String jk = "";
			for (String mer : mid) {
				jk += "'" + mer + "',";
			}

			if (jk.length() != 0) {
				jk = jk.substring(0, jk.length() - 1);
			}
			mQUery += " AND C.MID in (" + jk + ") ";
		} else {
			mQUery += "";

		}
		int SuperUserGroupCode = Integer.parseInt(AppProp.getProperty("super.user.group.code"));
		if(userGroupCOde!=SuperUserGroupCode) {
			mQUery += " AND  (C.ASSIGNED_TO = " + userId + " or C.ENTRY_BY = " + userId + ")";
		}
		query = query.replaceAll("@PARAMQUERY", mQUery);
		AgLogger.logDebug("0","Fetching Rec From getOpenComplaintsCount " + query);
		try {
			Query cb = entityManager.createNativeQuery(query);
			int resultFIn = 0;
			if(DBUtil.getDialect()==2) {
				BigDecimal bd = (BigDecimal) cb.getSingleResult();
				resultFIn = bd.intValue();
			}else {
				resultFIn = (int) cb.getSingleResult();
			}
			return resultFIn ;
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getTotalTerminalsCount(List<String> mid, String userId) {

		String query = AppProp.getProperty("TotalTerminalsCount.query");
		String mQUery = "";
		if (mid != null) {
			String jk = "";
			for (String mer : mid) {
				jk += "'" + mer + "',";
			}

			if (jk.length() != 0) {
				jk = jk.substring(0, jk.length() - 1);
			}
			mQUery += " where value in (" + jk + ") ";
		} else {
			mQUery += "";

		}

		query = query.replaceAll("@PARAMQUERY", mQUery);

		AgLogger.logDebug("0","Fetching Rec From getTotalTerminalsCount " + query);
		try {
			Query cb = entityManager.createNativeQuery(query);
			int resultFIn = 0;
			if(DBUtil.getDialect()==2) {
				BigDecimal bd = (BigDecimal) cb.getSingleResult();
				resultFIn = bd.intValue();
			}else {
				resultFIn = (int) cb.getSingleResult();
			}
			return resultFIn ;
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public int getTotalTmsUpdatesCount(List<String> mid, String userId) {
		String query = AppProp.getProperty("TotalTmsUpdatesCount.query");

		if (mid != null) {
			String jk = "";
			for (String mer : mid) {
				jk += "'" + mer + "',";
			}

			if (jk.length() != 0) {
				jk = jk.substring(0, jk.length() - 1);
			}
			query = query.replaceAll("@PARAMMID", " AND MERCHANT_ID in (" + jk + ")");
		} else {
			query = query.replaceAll("@PARAMMID", "");

		}

		AgLogger.logDebug("0","Fetching Rec From getTotalTmsUpdatesCount " + query);
		try {
//			Query cb = entityManager.createNativeQuery(query);
//			return (int) cb.getSingleResult();
			return 0;
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public int getTotalProfileDownloadsCount(List<String> mid, String userId) {
		String query = AppProp.getProperty("TotalProfileDownloadsCount.query");
		String mQUery = "";
		if (mid != null) {
			String jk = "";
			for (String mer : mid) {
				jk += "'" + mer + "',";
			}

			if (jk.length() != 0) {
				jk = jk.substring(0, jk.length() - 1);
			}
			mQUery += " AND MERCHANT_ID in (" + jk + ") ";
		} else {
			mQUery += "";

		}

		query = query.replaceAll("@PARAMQUERY", mQUery);
		AgLogger.logDebug("0","Fetching Rec From getTotalProfileDownloadsCount " + query);
		try {
			Query cb = entityManager.createNativeQuery(query);
			int resultFIn = 0;
			if(DBUtil.getDialect()==2) {
				BigDecimal bd = (BigDecimal) cb.getSingleResult();
				resultFIn = bd.intValue();
			}else {
				resultFIn = (int) cb.getSingleResult();
			}
			return resultFIn ;
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public int getTotalTxnPendingforSettlment(List<String> mid, String userId) {

		String query = AppProp.getProperty("TotalTxnPendingforSettlment.query");
		String mQUery = "";
		if (mid != null) {
			String jk = "";
			for (String mer : mid) {
				jk += "'" + mer + "',";
			}

			if (jk.length() != 0) {
				jk = jk.substring(0, jk.length() - 1);
			}
			mQUery += " AND MERCHANT_ID in (" + jk + ") ";
		} else {
			mQUery += "";

		}

		query = query.replaceAll("@PARAMQUERY", mQUery);

		AgLogger.logDebug("0","Fetching Rec From getTotalTxnPendingforSettlment " + query);
		try {
			Query cb = entityManager.createNativeQuery(query);
			int resultFIn = 0;
			if(DBUtil.getDialect()==2) {
				BigDecimal bd = (BigDecimal) cb.getSingleResult();
				resultFIn = bd.intValue();
			}else {
				resultFIn = (int) cb.getSingleResult();
			}
			return resultFIn ;
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public int getTotalNewRequestforApproval(String userId) {
		String query = AppProp.getProperty("TotalNewRequestforApproval.query");
		AgLogger.logDebug("0","Fetching Rec From getTotalNewRequestforApproval " + query);
		try {
			Query cb = entityManager.createNativeQuery(query);
			int resultFIn = 0;
			if(DBUtil.getDialect()==2) {
				BigDecimal bd = (BigDecimal) cb.getSingleResult();
				resultFIn = bd.intValue();
			}else {
				resultFIn = (int) cb.getSingleResult();
			}
			return resultFIn ;
		} catch (NoResultException nre) {
			return 0;
		}
	}

}
