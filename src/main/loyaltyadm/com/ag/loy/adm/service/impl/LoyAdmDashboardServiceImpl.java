package com.ag.loy.adm.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.ChartConfigTxnSummary;
import com.ag.loy.adm.model.BarChartModel;
import com.ag.loy.adm.model.LineChartModel;
import com.ag.loy.adm.model.PieChartModel;
import com.ag.loy.adm.model.StackedBarChartModel;
import com.ag.loy.adm.model.StackedBarChartModel.stackedBarDatasets;
import com.ag.loy.adm.model.TopCustomerModel;
import com.ag.loy.adm.model.TxnSummaryModel;
import com.ag.loy.adm.service.LoyAdmDashboardService;

@Service
public class LoyAdmDashboardServiceImpl implements LoyAdmDashboardService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String fetchTxnSummaryQuery(String dashboardType) {
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				return AppProp.getProperty("sa.txn.summary");
			// Corporate
			case "2":
				return AppProp.getProperty("lac.txn.summary");
			// Retail
			case "3":
				return AppProp.getProperty("lar.txn.summary");
			// Default
			default:
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TxnSummaryModel> fetchTxnSummary(String dashboardType, String corpId, String channel) {
		List<TxnSummaryModel> lst = new ArrayList<TxnSummaryModel>();
		try {

			String query = fetchTxnSummaryQuery(dashboardType);
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@CHID", channel);
			AgLogger.logDebug("TXN SUMMARY QUERY | ", query.replaceAll("\\n", " "));
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();
			for (Object[] obj : temp) {
				TxnSummaryModel mdl = new TxnSummaryModel();

				ChartConfigTxnSummary mk = new ChartConfigTxnSummary();

				mk.setId(Long.parseLong(obj[0].toString()));
				mk.setCorpId(obj[1].toString());
				mk.setTxnType(obj[2].toString());
				mk.setTitle(obj[3].toString());
				mk.setIsActive(Integer.parseInt(obj[4].toString()));
				mk.setTheme(obj[5].toString());
				mdl.setTxnSummary(mk);
				mdl.setAmount(obj[6].toString());

				mdl.setCount(Integer.parseInt(
						(obj[7].toString().contains(",") ? obj[7].toString().replaceAll(",", "") : obj[7].toString())));

				lst.add(mdl);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] fetchBarChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res[0] = AppProp.getProperty("sa.bar.chart.query");
				res[1] = AppProp.getProperty("sa.bar.chart.color");
				break;
			// Corporate
			case "2":
				res[0] = AppProp.getProperty("lac.bar.chart.query");
				res[1] = AppProp.getProperty("lac.bar.chart.color");
				break;
			// Retail
			case "3":
				res[0] = AppProp.getProperty("lar.bar.chart.query");
				res[1] = AppProp.getProperty("lar.bar.chart.color");
				break;
			// Default
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String fetchBarChartTitle(String dashboardType) {
		String res = "N/A";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.bar.chart.title");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.bar.chart.title");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.bar.chart.title");
				break;
			// Default
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public BarChartModel fetchBarChartData(String dashboardType, String corpId) {

		try {
			String[] result = fetchBarChartQuery(dashboardType);
			// [0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			AgLogger.logDebug("BAR CHART QUERY || ", query.replaceAll("\\n", " "));
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();

			if (temp != null) {
				boolean isAllDataZero = true;
				BarChartModel mdl = new BarChartModel();
				List<BarChartModel> charList = new ArrayList<BarChartModel>();
				List<String> weekList = new ArrayList<String>();
				List<String> txnTypeList = new ArrayList<String>();
				HashMap<String, List<Float>> mp = new HashMap<String, List<Float>>();
				List<Float> lbw = null;
				for (Object[] obj : temp) {
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
				// [1] COLOR
				String weeklyChartBack = result[1];
				String[] color = weeklyChartBack.split(",");
				int i = 0;

				for (String l : txnTypeListFinal) {
					BarChartModel wsb = new BarChartModel();
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
					List<BarChartModel> charListFin = new ArrayList<BarChartModel>();
					BarChartModel wsb = new BarChartModel();
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
		} catch (Exception e) {
			e.printStackTrace();
//			BarChartModel mdl = new BarChartModel();
//			List<String> s = new ArrayList<String>();
//			List<Float> f = new ArrayList<Float>();
//			s.add("No data");
//			f.add(0.0f);
//			mdl.setLabels(s);
//			List<BarChartModel> charListFin = new ArrayList<BarChartModel>();
//			BarChartModel wsb = new BarChartModel();
//			wsb.setBackgroundColor("#D3D3D3");
//			wsb.setBorderColor("#D3D3D3");
//			wsb.setData(f);
//			wsb.setLabel("No data");
//			charListFin.add(wsb);
//			mdl.setDatasets(charListFin);
//
//			return mdl;
			return null;
		}
	}

	@Override
	public String[] fetchPieChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res[0] = AppProp.getProperty("sa.pie.chart.query");
				res[1] = AppProp.getProperty("sa.pie.chart.color");
				break;
			// Corporate
			case "2":
				res[0] = AppProp.getProperty("lac.pie.chart.query");
				res[1] = AppProp.getProperty("lac.pie.chart.color");
				break;
			// Retail
			case "3":
				res[0] = AppProp.getProperty("lar.pie.chart.query");
				res[1] = AppProp.getProperty("lar.pie.chart.color");
				break;
			// Default
			default:
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String fetchPieChartTitle(String dashboardType) {
		String res = "N/A";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.pie.chart.title");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.pie.chart.title");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.pie.chart.title");
				break;
			// Default
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public PieChartModel fetchPieChartData(String dashboardType, String corpId, String date) {
		try {
			boolean isAllDataZero = true;
			// String[] dates = fetchWeeklyDates(date);

			String[] result = fetchPieChartQuery(dashboardType);
			// RESULT[0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@MPARAM", date);

			AgLogger.logDebug("PIE CHART QUERY || ", query.replaceAll("\\n", " "));
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();

			if (temp != null) {

				PieChartModel pieDataChartModel = new PieChartModel();
				List<PieChartModel> mdlList = new ArrayList<PieChartModel>();
				List<String> List = new ArrayList<String>();

				// RESULT[1] == COLOR
				String[] tempcolor = result[1].split(",");
				List<String> bg = Arrays.asList(tempcolor);
				List<Float> data = new ArrayList<Float>();
				Float[] f = new Float[temp.size() + 1];

				// int countTotalTxn = 0;
				int k = 0;

				for (Object[] obj : temp) {
					Float Amount = Float.parseFloat(obj[1].toString());

					if (Amount != 0.0) {
						isAllDataZero = false;
					}
					f[k] = Amount;

					if (obj[0].toString().contains("Count")) {
						List.add(obj[0].toString() + " " + String.format("%,.0f", Amount));
					} else {
						// List.add(obj[0].toString() + " " + String.format("%,.2f", Amount));
						List.add(obj[0].toString() + " " + obj[2].toString());
					}
					k++;
				}

				if (!isAllDataZero) {
					data = Arrays.asList(f);
					PieChartModel pd = new PieChartModel();
					pd.setBackgroundColor(bg);
					pd.setData(data);
					mdlList.add(pd);
					List<String> txnTypeListFinal = List.stream().distinct().collect(Collectors.toList());
					pieDataChartModel.setLabels(txnTypeListFinal);
					pieDataChartModel.setDatasets(mdlList);
				} else {
					List = new ArrayList<>();
					List.add("No Data");
					PieChartModel pdcm = new PieChartModel();
					List<String> lstGreyColor = new ArrayList<String>();
					lstGreyColor.add("#D3D3D3");
					pdcm.setBackgroundColor(lstGreyColor);
					List<Float> noDataVal = new ArrayList<Float>();
					noDataVal.add(100.0f);
					pdcm.setData(noDataVal);
					mdlList = new ArrayList<PieChartModel>();
					mdlList.add(pdcm);
					pieDataChartModel.setLabels(List);
					pieDataChartModel.setDatasets(mdlList);
				}

				return pieDataChartModel;

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();

//			List<String> txnTypeList = new ArrayList<String>();
//			txnTypeList.add("No Data");
//			PieChartModel pdcm = new PieChartModel();
//			List<String> lstGreyColor = new ArrayList<String>();
//			lstGreyColor.add("#D3D3D3");
//			pdcm.setBackgroundColor(lstGreyColor);
//			List<Float> noDataVal = new ArrayList<Float>();
//			noDataVal.add(100.0f);
//			pdcm.setData(noDataVal);
//			List<PieChartModel> mdlList = new ArrayList<PieChartModel>();
//			mdlList.add(pdcm);
//			PieChartModel pieDataChartModel = new PieChartModel();
//			pieDataChartModel.setLabels(txnTypeList);
//			pieDataChartModel.setDatasets(mdlList);
//
//			return pieDataChartModel;
			return null;
		}
	}

	@Override
	public String[] fetchLineChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res[0] = AppProp.getProperty("sa.line.chart.query");
				res[1] = AppProp.getProperty("sa.line.chart.color");
				break;
			// Corporate
			case "2":
				res[0] = AppProp.getProperty("lac.line.chart.query");
				res[1] = AppProp.getProperty("lac.line.chart.color");
				break;
			// Retail
			case "3":
				res[0] = AppProp.getProperty("lar.line.chart.query");
				res[1] = AppProp.getProperty("lar.line.chart.color");
				break;
			// Default
			default:
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String fetchLineChartTitle(String dashboardType) {
		String res = "N/A";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.line.chart.title");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.line.chart.title");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.line.chart.title");
				break;
			// Default
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public LineChartModel fetchLineChartData(String dashboardType, String corpId) {

		try {
			boolean isAllDataZero = true;
			LineChartModel mdl = new LineChartModel();
			List<LineChartModel> charList = new ArrayList<LineChartModel>();

			String[] result = fetchLineChartQuery(dashboardType);
			// RESULT[0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			AgLogger.logDebug("LINE CHART QUERY || ", query.replaceAll("\\n", " "));
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();

			if (temp != null) {
				List<String> monthList = new ArrayList<String>();
				List<String> txnTypeList = new ArrayList<String>();
				HashMap<String, List<Float>> mp = new HashMap<String, List<Float>>();
				List<Float> lbw = null;

				for (Object[] obj : temp) {
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
				// RESULT[1] == COLOR
				String[] color = result[1].split(",");

				int i = 0;
				for (String l : txnTypeListFinal) {
					LineChartModel ysc = new LineChartModel();
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
					List<LineChartModel> charListFin = new ArrayList<LineChartModel>();
					LineChartModel wsb = new LineChartModel();
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

		} catch (Exception e) {
			e.printStackTrace();

//			LineChartModel mdl = new LineChartModel();
//			List<String> s = new ArrayList<String>();
//			List<Float> f = new ArrayList<Float>();
//			s.add("No data");
//			f.add(0.0f);
//			mdl.setLabels(s);
//			List<LineChartModel> charListFin = new ArrayList<LineChartModel>();
//			LineChartModel wsb = new LineChartModel();
//			wsb.setBackgroundColor("#D3D3D3");
//			wsb.setBorderColor("#D3D3D3");
//			wsb.setData(f);
//			wsb.setLabel("No data");
//			charListFin.add(wsb);
//			mdl.setDatasets(charListFin);
//			return mdl;
			return null;
		}

	}

	@Override
	public String[] fetchDonutChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res[0] = AppProp.getProperty("sa.donut.chart.query");
				res[1] = AppProp.getProperty("sa.donut.chart.color");
				break;
			// Corporate
			case "2":
				res[0] = AppProp.getProperty("lac.donut.chart.query");
				res[1] = AppProp.getProperty("lac.donut.chart.color");
				break;
			// Retail
			case "3":
				res[0] = AppProp.getProperty("lar.donut.chart.query");
				res[1] = AppProp.getProperty("lar.donut.chart.color");
				break;
			// Default
			default:
				res[0] = AppProp.getProperty("default.donut.chart.query");
				res[1] = AppProp.getProperty("default.donut.chart.color");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String fetchDonutChartTitle(String dashboardType) {
		String res = "N/A";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.donut.chart.title");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.donut.chart.title");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.donut.chart.title");
				break;
			// Default
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public PieChartModel fetchDonutChartData(String dashboardType, String corpId) {
		try {
			boolean isAllDataZero = true;

			String[] result = fetchDonutChartQuery(dashboardType);
			// RESULT[0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			AgLogger.logDebug("DONUT CHART QUERY || ", query.replaceAll("\\n", " "));
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();

			if (temp != null) {

				PieChartModel pieDataChartModel = new PieChartModel();
				List<PieChartModel> mdlList = new ArrayList<PieChartModel>();
				List<String> List = new ArrayList<String>();

				// RESULT[1] == COLOR
				String[] tempcolor = result[1].split(",");
				List<String> bg = Arrays.asList(tempcolor);
				List<Float> data = new ArrayList<Float>();
				Float[] f = new Float[temp.size() + 1];

				int k = 0;
				Float TotalAmount = 0.0F;

				for (Object[] obj : temp) {
					Float Amount = Float.parseFloat(obj[1].toString());
					if (Amount != 0.0) {
						isAllDataZero = false;
					}
					f[k] = Amount;
					TotalAmount += Amount;
					k++;
				}
				for (Object[] obj : temp) {
					Float Amount = Float.parseFloat(obj[1].toString());
					Float percentage = (Amount / TotalAmount) * 100;
					List.add(obj[0].toString() + " " + String.format("%.0f", Amount) + " ("
							+ String.format("%.2f", percentage) + "%)");
				}

				if (!isAllDataZero) {
					data = Arrays.asList(f);
					PieChartModel pd = new PieChartModel();
					pd.setBackgroundColor(bg);
					pd.setData(data);
					mdlList.add(pd);
					List<String> txnTypeListFinal = List.stream().distinct().collect(Collectors.toList());
					pieDataChartModel.setLabels(txnTypeListFinal);
					pieDataChartModel.setDatasets(mdlList);
				} else {
					List = new ArrayList<>();
					List.add("No Data");
					PieChartModel pdcm = new PieChartModel();
					List<String> lstGreyColor = new ArrayList<String>();
					lstGreyColor.add("#D3D3D3");
					pdcm.setBackgroundColor(lstGreyColor);
					List<Float> noDataVal = new ArrayList<Float>();
					noDataVal.add(100.0f);
					pdcm.setData(noDataVal);
					mdlList = new ArrayList<PieChartModel>();
					mdlList.add(pdcm);
					pieDataChartModel.setLabels(List);
					pieDataChartModel.setDatasets(mdlList);
				}

				return pieDataChartModel;

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();

//			List<String> txnTypeList = new ArrayList<String>();
//			txnTypeList.add("No Data");
//			PieChartModel pdcm = new PieChartModel();
//			List<String> lstGreyColor = new ArrayList<String>();
//			lstGreyColor.add("#D3D3D3");
//			pdcm.setBackgroundColor(lstGreyColor);
//			List<Float> noDataVal = new ArrayList<Float>();
//			noDataVal.add(100.0f);
//			pdcm.setData(noDataVal);
//			List<PieChartModel> mdlList = new ArrayList<PieChartModel>();
//			mdlList.add(pdcm);
//			PieChartModel pieDataChartModel = new PieChartModel();
//			pieDataChartModel.setLabels(txnTypeList);
//			pieDataChartModel.setDatasets(mdlList);
//
//			return pieDataChartModel;
			return null;
		}
	}

	@Override
	public String fetchTopCustomerQuery(String dashboardType) {
		String res = "";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.top.cust.query");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.top.cust.query");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.top.cust.query");
				break;
			// Default
			default:
				res = AppProp.getProperty("default.top.cust.query");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String fetchTopCustomerTitle(String dashboardType) {
		String res = "N/A";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.top.cust.title");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.top.cust.title");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.top.cust.title");
				break;
			// Default
			default:
				res = AppProp.getProperty("default.top.cust.title");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<TopCustomerModel> fetchTopCustomerData(String dashboardType, String corpId, String type) {
		List<TopCustomerModel> lst = new ArrayList<TopCustomerModel>();
		try {
			String result = fetchTopCustomerQuery(dashboardType);
			String query = result;
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@TYPE", type);

			AgLogger.logDebug("TOP CUSTOMER QUERY || ", query.replaceAll("\\n", " "));
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();

			for (Object[] obj : temp) {

				TopCustomerModel mdl = new TopCustomerModel();

				mdl.setName(obj[1].toString());
				mdl.setAccountNumber(obj[0].toString());
				mdl.setCount(obj[3].toString());
				mdl.setAmount(obj[4].toString());

				lst.add(mdl);
			}
			return lst;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String fetchTopMerchantQuery(String dashboardType) {
		String res = "";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.top.mer.query");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.top.mer.query");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.top.mer.query");
				break;
			// Default
			default:
				res = AppProp.getProperty("default.top.mer.query");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String fetchTopMerchantTitle(String dashboardType) {
		String res = "N/A";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.top.mer.title");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.top.mer.title");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.top.mer.title");
				break;
			// Default
			default:
				res = AppProp.getProperty("default.top.mer.title");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<TopCustomerModel> fetchTopMerchantData(String dashboardType, String corpId, String type) {
		List<TopCustomerModel> lst = new ArrayList<TopCustomerModel>();
		try {
			String result = fetchTopMerchantQuery(dashboardType);

			String query = result;
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@MTYPE", type);

			AgLogger.logDebug("TOP MERCHANT QUERY || ", query.replaceAll("\\n", " "));
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();

			for (Object[] obj : temp) {

				TopCustomerModel mdl = new TopCustomerModel();

				mdl.setName(obj[1].toString());
				mdl.setAccountNumber(obj[0].toString());
				mdl.setCount(obj[2].toString());
				mdl.setAmount(obj[3].toString());

				lst.add(mdl);
			}
			return lst;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] fetchWeeklyDates(String date) {
		try {
			String[] s = new String[2];
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(date));
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			s[0] = df.format(c.getTime());
			for (int i = 0; i < 6; i++) {
				c.add(Calendar.DATE, 1);
			}
			s[1] = df.format(c.getTime());

//			c.add(Calendar.DATE, -7);
//			s[3] = df.format(c.getTime());
//			c.setTime(c.getTime());
//			c.add(Calendar.DATE, -6);
//			s[2] = df.format(c.getTime());

			System.out.println("DATE1=" + s[0] + " || DATE2=" + s[1]);
			return s;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String[] fetchStackedBarChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res[0] = AppProp.getProperty("sa.stacked.bar.chart.query");
				res[1] = AppProp.getProperty("sa.stacked.bar.chart.color");
				break;
			// Corporate
			case "2":
				res[0] = AppProp.getProperty("lac.stacked.bar.chart.query");
				res[1] = AppProp.getProperty("lac.stacked.bar.chart.color");
				break;
			// Retail
			case "3":
				res[0] = AppProp.getProperty("lar.stacked.bar.chart.query");
				res[1] = AppProp.getProperty("lar.stacked.bar.chart.color");
				break;
			// Default
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String fetchStackedBarChartTitle(String dashboardType) {
		String res = "N/A";
		try {
			switch (dashboardType) {
			// Super Admin
			case "1":
				res = AppProp.getProperty("sa.stacked.bar.chart.title");
				break;
			// Corporate
			case "2":
				res = AppProp.getProperty("lac.stacked.bar.chart.title");
				break;
			// Retail
			case "3":
				res = AppProp.getProperty("lar.stacked.bar.chart.title");
				break;
			// Default
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public StackedBarChartModel fetchStackedBarChartData(String dashboardType, String corpId) {
		try {
			String[] result = fetchStackedBarChartQuery(dashboardType);
			// [0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			AgLogger.logDebug("STACKED BAR CHART QUERY || ", query.replaceAll("\\n", " "));
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();

			if (temp != null) {
				boolean isAllDataZero = true;
				StackedBarChartModel mdl = new StackedBarChartModel();
				List<String> txnType = new ArrayList<String>();
				List<String> monthList = new ArrayList<String>();
				List<stackedBarDatasets> FinalList = new ArrayList<stackedBarDatasets>();
				HashMap<String, List<Float>> hmFloat = new HashMap<String, List<Float>>();
				HashMap<String, List<Integer>> hmInt = new HashMap<String, List<Integer>>();

				HashMap<String, List<String>> amountComma = new HashMap<String, List<String>>();
				HashMap<String, List<String>> countComma = new HashMap<String, List<String>>();
				List<Float> amounts = null;
				List<Integer> counts = null;
				List<String> amtComma = null;
				List<String> cntComma = null;
				for (Object[] obj : temp) {
					txnType.add(obj[1].toString());
					monthList.add(obj[0].toString());

					Float txnAmount = Float.parseFloat(obj[2].toString());
					Integer txnCount = Integer.parseInt(obj[3].toString());
					if (txnAmount != 0.0) {
						isAllDataZero = false;
					}
					if (hmFloat.containsKey(obj[1].toString())) {
						amounts = hmFloat.get(obj[1].toString());
						amounts.add(txnAmount);
						hmFloat.put(obj[1].toString(), amounts);

						counts = hmInt.get(obj[1].toString());
						counts.add(txnCount);
						hmInt.put(obj[1].toString(), counts);

						amtComma = amountComma.get(obj[1].toString());
						amtComma.add(obj[4].toString().trim());
						amountComma.put(obj[1].toString(), amtComma);

						cntComma = countComma.get(obj[1].toString());
						cntComma.add(obj[5].toString().trim());
						countComma.put(obj[1].toString(), cntComma);
					} else {
						amounts = new ArrayList<Float>();
						amounts.add(txnAmount);
						hmFloat.put(obj[1].toString(), amounts);

						counts = new ArrayList<Integer>();
						counts.add(txnCount);
						hmInt.put(obj[1].toString(), counts);

						amtComma = new ArrayList<String>();
						amtComma.add(obj[4].toString().trim());
						amountComma.put(obj[1].toString(), amtComma);

						cntComma = new ArrayList<String>();
						cntComma.add(obj[5].toString().trim());
						countComma.put(obj[1].toString(), cntComma);
					}

				}

				List<String> txnTypeList = txnType.stream().distinct().collect(Collectors.toList());
				List<String> monthListFinal = monthList.stream().distinct().collect(Collectors.toList());

				// [1] COLOR
				String bgColor = result[1];
				String[] color = bgColor.split(",");
				int i = 0;
				for (String l : txnTypeList) {
					stackedBarDatasets wsb = new StackedBarChartModel().new stackedBarDatasets();
					wsb.setBackgroundColor(color[i]);
					wsb.setData(hmFloat.get(l));
					wsb.setCount(hmInt.get(l));
					wsb.setLabel(l);
					wsb.setDataCommaSeperated(amountComma.get(l));
					wsb.setCountCommaSeperated(countComma.get(l));
					FinalList.add(wsb);
					i++;
				}

//				// See if all Data has Zero Values
//				if (!isAllDataZero) {
				mdl.setLabels(monthListFinal);
				mdl.setDatasets(FinalList);
//				} else {
//					List<String> s = new ArrayList<String>();
//					List<Float> f = new ArrayList<Float>();
//					s.add("No data");
//					f.add(0.0f);
//					mdl.setLabels(s);
//					List<StackedBarChartModel> charListFin = new ArrayList<StackedBarChartModel>();
//					BarChartModel wsb = new BarChartModel();
//					wsb.setBackgroundColor("#D3D3D3");
//					wsb.setBorderColor("#D3D3D3");
//					wsb.setData(f);
//					wsb.setLabel("No data");
//					charListFin.add(wsb);
//					mdl.setDatasets(charListFin);
//				}

				return mdl;

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
