package com.ag.fuel.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.fuel.entity.FuelProductConfig;
import com.ag.fuel.model.DealerProductsModel;
import com.ag.fuel.model.FuelConfigModel;
import com.ag.fuel.model.LineChartModel;
import com.ag.fuel.model.PieChartModel;
import com.ag.fuel.model.TopDealerModel;
import com.ag.fuel.services.FuelDashboardService;
import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;

@Service
public class FuelDashboardServiceImpl implements FuelDashboardService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String fetchProductsQuery(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.products");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.products");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.products");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<FuelConfigModel> fetchProducts(String dashboardType, String corpId, String userCode) {
		List<FuelConfigModel> lst = new ArrayList<FuelConfigModel>();
		try {
			String query = fetchProductsQuery(dashboardType);
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@USERCODE", userCode);
			AgLogger.logDebug("PRODUCTS QUERY ||", query);
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();
			for (Object[] obj : temp) {
				FuelConfigModel mdl = new FuelConfigModel();

				FuelProductConfig mk = new FuelProductConfig();

				mk.setId(Integer.parseInt(obj[0].toString()));
				mk.setCorpId(obj[1].toString());
				mk.setProdCode(obj[2].toString());
				mk.setProdName(obj[3].toString());
				mk.setIsActive(Integer.parseInt(obj[4].toString()));
				mk.setTheme(obj[5].toString());
				mdl.setFuelProductConfig(mk);
				mdl.setAmount(obj[6].toString());
				mdl.setCount(Integer.parseInt(obj[7].toString()));

				lst.add(mdl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	@Override
	public String fetchCurrentLineChartTitle(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.product.cur.title");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.product.cur.title");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.product.cur.title");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] fetchCurrentLineChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				res[0] = AppProp.getProperty("fuel.omc.product.cur.query");
				res[1] = AppProp.getProperty("fuel.omc.product.cur.color");
				break;
			// CUSTOMER
			case "2":
				res[0] = AppProp.getProperty("fuel.customer.product.cur.query");
				res[1] = AppProp.getProperty("fuel.customer.product.cur.color");
				break;
			// DEALER
			case "3":
				res[0] = AppProp.getProperty("fuel.dealer.product.cur.query");
				res[1] = AppProp.getProperty("fuel.dealer.product.cur.color");
				break;
			default:
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public LineChartModel fetchCurrentLineChartData(String dashboardType, String corpId, String currentMonth,
			String userCode) {
		try {
			boolean isAllDataZero = true;
			LineChartModel mdl = new LineChartModel();
			List<LineChartModel> charList = new ArrayList<LineChartModel>();

			String[] result = fetchCurrentLineChartQuery(dashboardType);
			// RESULT[0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@MONTH", currentMonth);
			query = query.replaceAll("@USERCODE", userCode);
			AgLogger.logDebug("CURRENT LINE CHART QUERY || ", query);
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();
			if (temp != null) {
				List<String> monthList = new ArrayList<String>();
				List<String> typeList = new ArrayList<String>();
				HashMap<String, List<Float>> mp = new HashMap<String, List<Float>>();
				List<Float> lbw = null;
				for (Object[] obj : temp) {
					typeList.add(obj[0].toString());
					monthList.add(obj[1].toString());
					Float amount = Float.parseFloat(obj[2].toString());
					if (amount != 0.0) {
						isAllDataZero = false;
					}
					if (mp.containsKey(obj[0].toString())) {
						lbw = mp.get(obj[0].toString());
						lbw.add(amount);
						mp.put(obj[0].toString(), lbw);
					} else {
						lbw = new ArrayList<Float>();
						lbw.add(amount);
						mp.put(obj[0].toString(), lbw);
					}
				}
				List<String> monthListFinal = monthList.stream().distinct().collect(Collectors.toList());
				List<String> txnTypeListFinal = typeList.stream().distinct().collect(Collectors.toList());
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

			LineChartModel mdl = new LineChartModel();
			List<String> s = new ArrayList<String>();
			List<Float> f = new ArrayList<Float>();
			s.add("No data1");
			f.add(0.0f);
			mdl.setLabels(s);
			List<LineChartModel> charListFin = new ArrayList<LineChartModel>();
			LineChartModel wsb = new LineChartModel();
			wsb.setBackgroundColor("#D3D3D3");
			wsb.setBorderColor("#D3D3D3");
			wsb.setData(f);
			wsb.setLabel("No data2");
			charListFin.add(wsb);
			mdl.setDatasets(charListFin);
			return mdl;
//			return null;
		}
	}

	@Override
	public String fetchPreviousLineChartTitle(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.product.prv.title");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.product.prv.title");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.product.prv.title");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] fetchPreviousLineChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				res[0] = AppProp.getProperty("fuel.omc.product.prv.query");
				res[1] = AppProp.getProperty("fuel.omc.product.prv.color");
				break;
			// CUSTOMER
			case "2":
				res[0] = AppProp.getProperty("fuel.customer.product.prv.query");
				res[1] = AppProp.getProperty("fuel.customer.product.prv.color");
				break;
			// DEALER
			case "3":
				res[0] = AppProp.getProperty("fuel.dealer.product.prv.query");
				res[1] = AppProp.getProperty("fuel.dealer.product.prv.color");
				break;
			default:
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public LineChartModel fetchPreviousLineChartData(String dashboardType, String corpId, String previousMonth,
			String userCode) {
		try {
			boolean isAllDataZero = true;
			LineChartModel mdl = new LineChartModel();
			List<LineChartModel> charList = new ArrayList<LineChartModel>();

			String[] result = fetchPreviousLineChartQuery(dashboardType);
			// RESULT[0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@MONTH", previousMonth);
			query = query.replaceAll("@USERCODE", userCode);
			AgLogger.logDebug("PREVIOUS LINE CHART QUERY || ", query);
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();
			if (temp != null) {
				List<String> monthList = new ArrayList<String>();
				List<String> typeList = new ArrayList<String>();
				HashMap<String, List<Float>> mp = new HashMap<String, List<Float>>();
				List<Float> lbw = null;
				for (Object[] obj : temp) {
					typeList.add(obj[0].toString());
					monthList.add(obj[1].toString());
					Float amount = Float.parseFloat(obj[2].toString());
					if (amount != 0.0) {
						isAllDataZero = false;
					}
					if (mp.containsKey(obj[0].toString())) {
						lbw = mp.get(obj[0].toString());
						lbw.add(amount);
						mp.put(obj[0].toString(), lbw);
					} else {
						lbw = new ArrayList<Float>();
						lbw.add(amount);
						mp.put(obj[0].toString(), lbw);
					}
				}
				List<String> monthListFinal = monthList.stream().distinct().collect(Collectors.toList());
				List<String> txnTypeListFinal = typeList.stream().distinct().collect(Collectors.toList());
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

			LineChartModel mdl = new LineChartModel();
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
			return mdl;
//			return null;
		}
	}

	@Override
	public String fetchDonutChartTitle(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.donut.chart.title");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.donut.chart.title");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.donut.chart.title");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] fetchDonutChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				res[0] = AppProp.getProperty("fuel.omc.donut.chart.query");
				res[1] = AppProp.getProperty("fuel.omc.donut.chart.color");
				break;
			// CUSTOMER
			case "2":
				res[0] = AppProp.getProperty("fuel.customer.donut.chart.query");
				res[1] = AppProp.getProperty("fuel.customer.donut.chart.color");
				break;
			// DEALER
			case "3":
				res[0] = AppProp.getProperty("fuel.dealer.donut.chart.query");
				res[1] = AppProp.getProperty("fuel.dealer.donut.chart.color");
				break;
			default:
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public PieChartModel fetchDonutChartData(String dashboardType, String corpId, String date, String userCode) {
		try {
			boolean isAllDataZero = true;

			String[] dates = fetchWeeklyDates(date);
			String[] result = fetchDonutChartQuery(dashboardType);
			// RESULT[0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@DATE1", dates[0]);
			query = query.replaceAll("@DATE2", dates[1]);
			query = query.replaceAll("@USERCODE", userCode);
			AgLogger.logDebug("DONUT CHART QUERY || ", query);
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
					k++;
					List.add(obj[0].toString());
					// countTotalTxn += Integer.parseInt(obj[2].toString());
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

			List<String> txnTypeList = new ArrayList<String>();
			txnTypeList.add("No Data");
			PieChartModel pdcm = new PieChartModel();
			List<String> lstGreyColor = new ArrayList<String>();
			lstGreyColor.add("#D3D3D3");
			pdcm.setBackgroundColor(lstGreyColor);
			List<Float> noDataVal = new ArrayList<Float>();
			noDataVal.add(100.0f);
			pdcm.setData(noDataVal);
			List<PieChartModel> mdlList = new ArrayList<PieChartModel>();
			mdlList.add(pdcm);
			PieChartModel pieDataChartModel = new PieChartModel();
			pieDataChartModel.setLabels(txnTypeList);
			pieDataChartModel.setDatasets(mdlList);

			return pieDataChartModel;
//			return null;
		}
	}

	@Override
	public String fetchTopDealerTitle(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.top.dealer.title");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.top.dealer.title");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.top.dealer.title");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String fetchTopDealerQuery(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.top.dealer.query");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.top.dealer.query");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.top.dealer.query");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TopDealerModel> fetchTopDealerData(String dashboardType, String corpId, String type, String userCode) {
		List<TopDealerModel> lst = new ArrayList<TopDealerModel>();
		try {
			String query = fetchTopDealerQuery(dashboardType);
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@TYPE", type);
			query = query.replaceAll("@USERCODE", userCode);
			AgLogger.logDebug("TOP DEALER QUERY || ", query);
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();
			for (Object[] obj : temp) {
				TopDealerModel mdl = new TopDealerModel();

				mdl.setName(obj[0].toString());
				mdl.setNumber(obj[1].toString());
				mdl.setCount(Integer.parseInt(obj[2].toString()));
				mdl.setAmount(obj[3].toString());

				lst.add(mdl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	@Override
	public String fetchPieChartTitle(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.pie.chart.title");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.pie.chart.title");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.pie.chart.title");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] fetchPieChartQuery(String dashboardType) {
		String[] res = new String[2];
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				res[0] = AppProp.getProperty("fuel.omc.pie.chart.query");
				res[1] = AppProp.getProperty("fuel.omc.pie.chart.color");
				break;
			// CUSTOMER
			case "2":
				res[0] = AppProp.getProperty("fuel.customer.pie.chart.query");
				res[1] = AppProp.getProperty("fuel.customer.pie.chart.color");
				break;
			// DEALER
			case "3":
				res[0] = AppProp.getProperty("fuel.dealer.pie.chart.query");
				res[1] = AppProp.getProperty("fuel.dealer.pie.chart.color");
				break;
			default:
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public PieChartModel fetchPieChartData(String dashboardType, String corpId, String userCode) {
		try {
			boolean isAllDataZero = true;
			String[] result = fetchPieChartQuery(dashboardType);
			// RESULT[0] QUERY
			String query = result[0];
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@USERCODE", userCode);
			AgLogger.logDebug("PIE CHART QUERY || ", query);
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
					k++;
					List.add(obj[0].toString());
					// countTotalTxn += Integer.parseInt(obj[2].toString());
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

			List<String> txnTypeList = new ArrayList<String>();
			txnTypeList.add("No Data");
			PieChartModel pdcm = new PieChartModel();
			List<String> lstGreyColor = new ArrayList<String>();
			lstGreyColor.add("#D3D3D3");
			pdcm.setBackgroundColor(lstGreyColor);
			List<Float> noDataVal = new ArrayList<Float>();
			noDataVal.add(100.0f);
			pdcm.setData(noDataVal);
			List<PieChartModel> mdlList = new ArrayList<PieChartModel>();
			mdlList.add(pdcm);
			PieChartModel pieDataChartModel = new PieChartModel();
			pieDataChartModel.setLabels(txnTypeList);
			pieDataChartModel.setDatasets(mdlList);

			return pieDataChartModel;
//			return null;
		}
	}

	@Override
	public String fetchProductPriceTitle(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.prod.price.title");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.prod.price.title");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.prod.price.title");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String fetchProductPriceQuery(String dashboardType) {
		try {
			switch (dashboardType) {
			// OMC
			case "1":
				return AppProp.getProperty("fuel.omc.prod.price.query");
			// CUSTOMER
			case "2":
				return AppProp.getProperty("fuel.customer.prod.price.query");
			// DEALER
			case "3":
				return AppProp.getProperty("fuel.dealer.prod.price.query");
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DealerProductsModel> fetchProductPriceList(String dashboardType, String corpId, String userCode) {
		try {
			String query = fetchProductPriceQuery(dashboardType);
			query = query.replaceAll("@CORPID", corpId);
			query = query.replaceAll("@USERCODE", userCode);
			AgLogger.logDebug("DEALER PRODUCT PRICE QUERY || ", query);
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> temp = (List<Object[]>) q.getResultList();
			if (temp != null) {

				Map<String, DealerProductsModel> dealerMap = new HashMap<>();

				for (Object[] data : temp) {

					String dealerName = data[0].toString();
					String tid = data[1].toString();
					String productName = data[2].toString();
					String productPrice = data[3].toString();
					String productColor = data[4].toString();

					DealerProductsModel dealer;
					if (dealerMap.containsKey(tid)) {
						dealer = dealerMap.get(tid);
					} else {
						dealer = new DealerProductsModel();
						dealer.setName(dealerName);
						dealer.setTid(tid);
						dealer.setProduct(new ArrayList<>());
						dealerMap.put(tid, dealer);
					}

					DealerProductsModel.Product product = dealer.new Product();
					product.setProductName(productName);
					product.setProductPrice(productPrice);
					product.setProductColor(productColor);
					dealer.getProduct().add(product);
				}

				List<DealerProductsModel> dealerProductsList = new ArrayList<>(dealerMap.values());
				return dealerProductsList;

			} else {
				return null;
			}

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

}
