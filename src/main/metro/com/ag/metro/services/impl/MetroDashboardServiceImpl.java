package com.ag.metro.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.ag.metro.services.MetroDashboardService;
import com.ag.mportal.model.WeeklySalesBarChartModel;
import com.ag.mportal.model.YearlySalesChartModel;

@Service
public class MetroDashboardServiceImpl implements MetroDashboardService{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public YearlySalesChartModel getYearSalesChartModel(List<String> mid) {
		String query = AppProp.getProperty("metro.yearly.chart.query");
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
			String YearlyChartcolor = AppProp.getProperty("metro.yearly.chart.color");
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
			String query = AppProp.getProperty("metro.weekly.chart.query");
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
				String weeklyChartBack = AppProp.getProperty("metro.weekly.chart.color");
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
}
