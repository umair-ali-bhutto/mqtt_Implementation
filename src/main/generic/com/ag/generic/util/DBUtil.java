package com.ag.generic.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ag.generic.prop.AppProp;


public class DBUtil {

	// 1 for MSSQL 2 for ORACLE
	final static String oracleDialect = "org.hibernate.dialect.Oracle10gDialect";
	final static String mssqlDialect = "org.hibernate.dialect.SQLServer2012Dialect";
	final int DefaultDialect = 2;

	public static void main(String args[]) {
		System.out.println(getTxnSummaryReptQuerry());
	}

	public static String getTxnSummaryReptQuerry() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("txn.summary.rept.msql");
		} else if (dialect == 2) {
			return AppProp.getProperty("txn.summary.rept.oracle");
		} else {
			return AppProp.getProperty("txn.summary.rept.oracle");
		}
	}

	public static String getDateQueryParam(String paramName) {
		int dialect = getDialect();
		if (dialect == 1) {
			return "CONVERT(DATE," + paramName + ")";
		} else if (dialect == 2) {
			return "TO_CHAR(" + paramName + ", 'YYYY-MM-DD')";
		} else {
			return "TO_CHAR(" + paramName + ", 'YYYY-MM-DD')";
		}
	}

	public static int getDialect() {
		int dialectLevel = 0;
		dialectLevel = Integer.parseInt(AppProp.getProperty("sql.dialect.level"));
		return dialectLevel;
	}

	public static String getName() {
		int dialect = getDialect();
		if (dialect == 1) {
			return "Bafl";
		} else if (dialect == 2) {
			return "Mpr";
		} else {
			return "Mpr";
		}
	}

	public static String getTitleName() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("title.bafl");
		} else if (dialect == 2) {
			return AppProp.getProperty("title.dp");
		} else {
			return AppProp.getProperty("title.dp");
		}
	}

	public static String getTitleImageName() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("title.bafl.img");
		} else if (dialect == 2) {
			return AppProp.getProperty("title.dp.img");
		} else {
			return AppProp.getProperty("title.dp.img");
		}
	}

	public static String fetchMerchantTerminalUpd() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("profile.fetch.details.without.tid");
		} else if (dialect == 2) {
			return AppProp.getProperty("profile.fetch.details.without.tid.orcl");
		} else {
			return AppProp.getProperty("profile.fetch.details.without.tid.orcl");
		}
	}

	public static String fetchMerchantTerminalUpdN() {

		int dialect =getDialect();
		

		if (dialect == 1) {
			return AppProp.getProperty("profile.fetch.details");
		} else if (dialect == 2) {
			return AppProp.getProperty("profile.fetch.details.orcl");
		} else {
			return AppProp.getProperty("profile.fetch.details.orcl");
		}
	}

	public static String updateVerisys() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("profile.upd.proce");
		} else if (dialect == 2) {
			return AppProp.getProperty("profile.upd.proce.orcl");
		} else {
			return AppProp.getProperty("profile.upd.proce.orcl");
		}
	}

	public static String fetchProfile() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("profile.dwn");
		} else if (dialect == 2) {
			return AppProp.getProperty("profile.dwn.orcl");
		} else {
			return AppProp.getProperty("profile.dwn.orcl");
		}
	}

	public static String fetchMerchantUpdM() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("merchant.search");
		} else if (dialect == 2) {
			return AppProp.getProperty("merchant.search.orcl");
		} else {
			return AppProp.getProperty("merchant.search.orcl");
		}
	}

	public static String fetchTxnSummary() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("txn.summary.rept");
		} else if (dialect == 2) {
			return AppProp.getProperty("txn.summary.rept.orcl");
		} else {
			return AppProp.getProperty("txn.summary.rept.orcl");
		}
	}

	public static double getTxnAmount(Object obj) {
		try {
			int dialect = getDialect();
			if (dialect == 1) {
				return (double) obj;
			} else if (dialect == 2) {

				return getBigDecimal(obj).doubleValue();
			} else {
				return getBigDecimal(obj).doubleValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal ret = null;
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else if (value instanceof Number) {
				ret = new BigDecimal(((Number) value).doubleValue());
			} else {
				throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
						+ " into a BigDecimal.");
			}
		}
		return ret;
	}
	
	public static String getTxnDetailsQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("txn.details.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("txn.details.query.orcl");
		} else {
			return AppProp.getProperty("txn.details.query.orcl");
		}
	}
	
	public static String getTxnDetailsCountQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("txn.details.count.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("txn.details.count.query.orcl");
		} else {
			return AppProp.getProperty("txn.details.count.query.orcl");
		}
	}
	
	public static String getDiscountTxnDetailsQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("discount.txn.details.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("discount.txn.details.query.orcl");
		} else {
			return AppProp.getProperty("discount.txn.details.query.orcl");
		}
	}
	
	public static String getWebEcrTxnDetailsCountQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("web.ecr.txn.count.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("web.ecr.txn.count.query.orcl");
		} else {
			return AppProp.getProperty("web.ecr.txn.count.query.orcl");
		}
	}
	public static String getWebEcrTxnDetailsQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("web.ecr.txn.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("web.ecr.txn.query.orcl");
		} else {
			return AppProp.getProperty("web.ecr.txn.query.orcl");
		}
	}
	public static String getKEBillPaymentQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("bill.payment.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("bill.payment.query.orcl");
		} else {
			return AppProp.getProperty("bill.payment.query.orcl");
		}
	}
	public static String getWebEcrTxnSummaryQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("web.ecr.txn.summary.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("web.ecr.txn.summary.query.orcl");
		} else {
			return AppProp.getProperty("web.ecr.txn.summary.query.orcl");
		}
	}
	
	public static String getOfflineSaleTxnSummaryQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("offline.sale.txn.summary.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("offline.sale.txn.summary.query.orcl");
		} else {
			return AppProp.getProperty("offline.sale.txn.summary.query.orcl");
		}
	}
	
	public static String getOfflineSaleTxnDetailsQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("offline.sale.txn.details.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("offline.sale.txn.details.query.orcl");
		} else {
			return AppProp.getProperty("offline.sale.txn.details.query.orcl");
		}
	}
	
	public static String getMarketSegmentDetailsQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("market.segment.details.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("market.segment.details.query.orcl");
		} else {
			return AppProp.getProperty("market.segment.details.query.orcl");
		}
	}
	
	public static String getDonationTxnDetailsQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("donation.txn.details.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("donation.txn.details.query.orcl");
		} else {
			return AppProp.getProperty("donation.txn.details.query.orcl");
		}
	}
	
	public static String getDonationTxnDetailsCountQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("donation.txn.details.count.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("donation.txn.details.count.query.orcl");
		} else {
			return AppProp.getProperty("donation.txn.details.count.query.orcl");
		}
	}
	
	public static String getOfflineSaleTxnDetailsCountQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("offline.sale.txn.details.count.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("offline.sale.txn.details.count.query.orcl");
		} else {
			return AppProp.getProperty("offline.sale.txn.details.count.query.orcl");
		}
	}
	
	public static String getMarketSegmentCountQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("market.segment.count.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("market.segment.count.query.orcl");
		} else {
			return AppProp.getProperty("market.segment.count.query.orcl");
		}
	}
	
	
	public static String getRocTxnDetailsQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("roc.txn.details.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("roc.txn.details.query.orcl");
		} else {
			return AppProp.getProperty("roc.txn.details.query.orcl");
		}
	}
	
	public static String getRocConfigQuery() {
		int dialect = getDialect();
		if (dialect == 1) {
			return AppProp.getProperty("fetch.roc.config.query");
		} else if (dialect == 2) {
			return AppProp.getProperty("fetch.roc.config.query.orcl");
		} else {
			return AppProp.getProperty("fetch.roc.config.query.orcl");
		}
	}

}
