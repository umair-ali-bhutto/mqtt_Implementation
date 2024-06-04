package com.ag.mportal.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ag.generic.entity.ComplaintsWebService;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.TxnLog;
import com.opencsv.CSVWriter;

public class UtilReport {

	public static void createVoidTxnDetailCsvFile(List<ReportModel> txnDetails, String filePath) throws ParseException {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();

			data.add(new String[] { "S.NO", "Card No.", "PEM", "MID", "TID", "Merchant Name", "Merchant Address",
					"TR Date/Time", "Curreny", "TR Amount" });
			int k = 0;
			for (ReportModel m : txnDetails) {
				k++;
				data.add(new String[] { k + "", m.getCardNumber(), m.getPemValue(), m.getMerchantId() + "",
						m.getTerminalId() + "", m.getMerchantName(), m.getMerchantAddress() + "", m.getEntryDate() + "",
						m.getCurrency(), m.getTxnAmount() });
			}

			writer.writeAll(data);

			writer.close();
		} catch (IOException e) {
			AgLogger.logerror(UtilReport.class, "Exception while creating csv void txn : ", e);
		}
	}

	public static void createTerminalCsvFile(List<TxnLog> terminalDetails, String filePath) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);

			List<String[]> data = new ArrayList<String[]>();
			data.add(new String[] { "S.NO", "Activity Type", "Merchant ID", "TID", "Activity Date", "Model",
					"Serial Number", "Remarks" });

			int k = 0;
			for (TxnLog logs : terminalDetails) {
				k++;
				data.add(new String[] { k + "", logs.getActivityType() + "", logs.getMerchantId() + "",
						logs.getTerminalId(), logs.getActivityDate() + "", logs.getModel() + "",
						logs.getSerialNumber() + "", logs.getActivityRemarks() + "" });
			}

			writer.writeAll(data);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			AgLogger.logerror(UtilReport.class, "Exception while creating terminal activity csv: ", e);
		}
	}

	public static void createTxnSummaryDetailCsvFile(List<TxnSummary> txnSummaryModel, List<ReportModel> txnDetails,
			String filePath) throws ParseException {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			if (!Objects.isNull(txnSummaryModel)) {
				data.add(new String[] { "Summary:" });
				data.add(new String[] { "S.NO", "Txn Type", "Count", "Amount" });

				int i = 0;
				for (TxnSummary txnSummary : txnSummaryModel) {
					i++;
					data.add(new String[] { i + "", txnSummary.getTxnType() + "", txnSummary.getCnt() + "",
							UtilAccess.exponent(txnSummary.getAmount() + "") });
				}
				if (txnDetails != null) {
					data.add(new String[] { " " });
					data.add(new String[] { "Details:" });
				}
			}

			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			if (txnDetails != null) {
				data.add(new String[] { "S.NO", "BIN", "MID", "TID", "Txn. Date", "Batch Number", "Invoice Number",
						"STAN", "POS Entry Mode", "Txn. Type", "RRN", "Original Txn. Amount", "Discount Rate",
						"Discount Amount", "Net/Discounted Amount", "Discount ID", "MDR On Us", "MDR Off Us", "FED",
						"Net Amount", "AUTH ID", "Settled", "Status", "Reason", "Tip Amount", "Adjusted Amount",
						"IMSI" });
				int k = 0;
				for (ReportModel m : txnDetails) {
					k++;
					// Date date = (Date) sdf.parse(m.getEntryDate());
					data.add(new String[] { k + "", m.getBin() + "", m.getMerchantId() + "", m.getTerminalId(),
							m.getEntryDate() + " " + m.getEntryTime() + "", m.getBatchNumber() + "",
							m.getInvoiceNumber() + "", m.getAuthId() + "", m.getPosEntryMode() + "", m.getType() + "",
							m.getRefNum() + "", m.getAddress1() + "", m.getAddress2() + "", m.getAddress3() + "",
							m.getTxnAmount() + "", m.getAddress4() + "", m.getMdrOnUs() + "", m.getMdrOffUs() + "",
							m.getFedRate() + "", m.getNetAmount() + "", m.getAuthIdN() + "", m.getSetteled() + "",
							m.getStatus() + "",
							((Objects.isNull(m.getReason()) || m.getReason().isEmpty()) ? "N/A" : m.getReason()) + "",
							((Objects.isNull(m.getTipAmount()) || m.getTipAmount().isEmpty()) ? "0.00"
									: m.getTipAmount()) + "",
							((Objects.isNull(m.getAdjAmount()) || m.getAdjAmount().isEmpty()) ? "0.00"
									: m.getAdjAmount()),
							m.getImsi() });
				}

			}
			writer.writeAll(data);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			AgLogger.logerror(UtilReport.class, "Exception while creating txn detail csv : ", e);
		}
	}

	public static void createMarketSegmentCsvFile(List<ReportModel> txnDetails, String filePath) throws ParseException {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			if (txnDetails != null) {
				data.add(new String[] { "S.NO", "BIN", "MID", "TID", "Txn. Date", "Batch Number", "Invoice Number",
						"STAN", "POS Entry Mode", "Txn. Type", "RRN", "Txn. Amount", "MDR On Us", "MDR Off Us", "FED",
						"Net Amount", "AUTH ID", "Settled", "Status", "Reason", "Tip Amount", "Adjusted Amount",
						"IMSI" });
				int k = 0;
				for (ReportModel m : txnDetails) {
					k++;
					// Date date = (Date) sdf.parse(m.getEntryDate());
					data.add(new String[] { k + "", m.getBin() + "", m.getMerchantId() + "", m.getTerminalId(),
							m.getEntryDate() + " " + m.getEntryTime() + "", m.getBatchNumber() + "",
							m.getInvoiceNumber() + "", m.getAuthId() + "", m.getPosEntryMode() + "", m.getType() + "",
							m.getRefNum() + "", m.getTxnAmount() + "", m.getMdrOnUs() + "", m.getMdrOffUs() + "",
							m.getFedRate() + "", m.getNetAmount() + "", m.getAuthIdN() + "", m.getSetteled() + "",
							m.getStatus() + "",
							((Objects.isNull(m.getReason()) || m.getReason().isEmpty()) ? "N/A" : m.getReason()) + "",
							((Objects.isNull(m.getTipAmount()) || m.getTipAmount().isEmpty()) ? "0.00"
									: m.getTipAmount()) + "",
							((Objects.isNull(m.getAdjAmount()) || m.getAdjAmount().isEmpty()) ? "0.00"
									: m.getAdjAmount()),
							m.getImsi() });
				}

			}
			writer.writeAll(data);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			AgLogger.logerror(UtilReport.class, "Exception while creating txn detail csv : ", e);
		}
	}

	public static void createSettlementTxnDetailCsvFile(List<ReportModel> txnDetails, String filePath)
			throws ParseException {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();

			data.add(new String[] { "S.NO", "Card No.", "PEM", "MID", "TID", "Merchant Name", "Merchant Address",
					"TR Date/Time", "Curreny", "TR Amount", "MDR On Us", "MDR Off Us", "FED", "Net Amount", "AUTH ID",
					"Batch No.", "Settled On", "Payment Date" });
			int k = 0;
			for (ReportModel m : txnDetails) {
				k++;
				data.add(new String[] { k + "", m.getCardNumber(), m.getPemValue(), m.getMerchantId() + "",
						m.getTerminalId() + "", m.getMerchantName(), m.getMerchantAddress() + "",
						m.getFormattedDate() + " " + m.getEntryTime(), m.getCurrency(), m.getTxnAmount(),
						m.getMdrOnUs(), m.getMdrOffUs(), m.getFedRate(), m.getNetAmount(), m.getAuthIdN(),
						m.getBatchNumber(), Objects.isNull(m.getSetteledDate()) ? "" : m.getSetteledDate(),
						Objects.isNull(m.getPaymentDate()) ? "" : m.getPaymentDate() });
			}

			writer.writeAll(data);

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			AgLogger.logerror(UtilReport.class, "Exception while creating settlement csv : ", e);
		}
	}

	public static void createTxnSummaryDetailExcelFile(List<TxnSummary> txnSummaryModel, List<ReportModel> txnDetails,
			String filePath) throws ParseException {
		File file = new File(filePath);

		try {

			ArrayList<String> merchantsCounts = new ArrayList<String>();

			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");

			CellStyle headerStyle = xssfWorkbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont font = xssfWorkbook.createFont();
			font.setUnderline(XSSFFont.U_SINGLE);
			headerStyle.setFont(font);

			int rowNum = 0;
			int cellNum = 0;
			int totalCellCounts = 0;

			rowNum = printReportTitle(xssfSheet, rowNum, xssfWorkbook, AppProp.getProperty("txn.summary.title"));

			rowNum += 5;

			if (!Objects.isNull(txnSummaryModel)) {

				String[] heads = new String[] { "S.NO", "TXN TYPE", "COUNT", "AMOUNT" };

				Row row = xssfSheet.createRow(rowNum++);
				cellNum = 0;
				for (String header : heads) {
					Cell cell = row.createCell(cellNum++);
					cell.setCellValue(header);
					cell.setCellStyle(headerStyle);
				}

				int i = 0;
				for (TxnSummary txnSummary : txnSummaryModel) {
					i++;
					String[] txnSum = new String[] { i + "", txnSummary.getTxnType() + "", txnSummary.getCnt() + "",
							UtilAccess.exponent(txnSummary.getAmount() + "") };
					row = xssfSheet.createRow(rowNum++);
					cellNum = 0;
					for (String txnSumData : txnSum) {
						Cell cell = row.createCell(cellNum++);
						cell.setCellValue(txnSumData);
					}

				}

				rowNum += 5;

			}

			String[] txnDetailHeaders = new String[] { "S.NO", "BIN", "MID", "TID", "Txn Date", "Batch Number",
					"Invoice Number", "STAN", "POS Entry Mode", "Txn Type", "RRN", "Txn Amount", "AUTH ID", "SETTLED",
					"STATUS", "REASON", "TIP AMOUNT", "ADJUSTED AMOUNT" };

			Row row = xssfSheet.createRow(rowNum++);
			cellNum = 0;

			for (String txnDetailheader : txnDetailHeaders) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(txnDetailheader);
				cell.setCellStyle(headerStyle);
				totalCellCounts = cellNum;
			}

			int k = 0;
			for (ReportModel m : txnDetails) {
				k++;
				// Date date = (Date) sdf.parse(m.getEntryDate());
				String[] r = new String[] { k + "", m.getBin() + "", m.getMerchantId() + "", m.getTerminalId(),
						m.getEntryDate() + "", m.getBatchNumber() + "", m.getInvoiceNumber() + "", m.getAuthId() + "",
						m.getPosEntryMode() + "", m.getType() + "", m.getRefNum() + "", m.getTxnAmount() + "",
						m.getAuthIdN() + "", m.getSetteled() + "", m.getStatus() + "",
						((Objects.isNull(m.getReason()) || m.getReason().isEmpty()) ? "N/A" : m.getReason()) + "",
						((Objects.isNull(m.getTipAmount()) || m.getTipAmount().isEmpty()) ? "0.00" : m.getTipAmount())
								+ "",
						((Objects.isNull(m.getAdjAmount()) || m.getAdjAmount().isEmpty()) ? "0.00"
								: m.getAdjAmount()) };
				merchantsCounts.add(m.getMerchantName());
				row = xssfSheet.createRow(rowNum++);
				cellNum = 0;
				for (String txnDetailData : r) {
					Cell cell = row.createCell(cellNum++);
					cell.setCellValue(txnDetailData);
				}
			}

			xssfSheet.addMergedRegion(new CellRangeAddress(0, 3, 0, totalCellCounts));

			Row subDetailRow = xssfSheet.createRow(5);
			printMerchantName(merchantsCounts, subDetailRow, xssfSheet, xssfWorkbook);
			printOnCellExcelReport(totalCellCounts, subDetailRow, xssfSheet);

			FileOutputStream out = new FileOutputStream(file);
			xssfWorkbook.write(out);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			AgLogger.logerror(UtilReport.class, "Exception while creating txn detail excel : ", e);
		}
	}

	public static void createSettlementTxnDetailExcelFile(List<ReportModel> txnDetails, String filePath)
			throws ParseException {
		File file = new File(filePath);

		try {

			ArrayList<String> merchantsCounts = new ArrayList<String>();

			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");

			CellStyle headerStyle = xssfWorkbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont font = xssfWorkbook.createFont();
			font.setBold(true);
			headerStyle.setFont(font);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowNum = 0;
			int cellNum = 0;
			int totalCellCounts = 0;

			rowNum = printReportTitle(xssfSheet, rowNum, xssfWorkbook, AppProp.getProperty("settlement.title"));

			rowNum += 5;

			String[] settlementHeaders = new String[] { "Sr #", "Card #", "PEM", "MID", "TID", "Merchant Name",
					"Merchant Address", "Txn Date", "Curreny", "Txn Amount", "MDR On Us", "MDR Off Us", "FED",
					"Net Amount", "AUTH ID", "Batch No.", "Settled On", "Settled Date", "Payment Date" };

			Row row = xssfSheet.createRow(rowNum++);
			cellNum = 0;
			//
			for (String settlementheader : settlementHeaders) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(settlementheader);
				cell.setCellStyle(headerStyle);
				totalCellCounts = cellNum;
			}

			int k = 0;
			for (ReportModel m : txnDetails) {
				k++;
				// Date date = (Date) sdf.parse(m.getEntryDate());
//				String pem = "N/A";
//				if (!Objects.isNull(m.getPem())) {
//					PosEntryModeConfig posConfig = new PosEntryModeConfigDAOImpl().fetch(m.getPem());
//					pem = Objects.isNull(posConfig) ? "N/A" : posConfig.getPosEntryValue();
//				}

				String[] r = new String[] { k + "", m.getCardNumber(), m.getPemValue(), m.getMerchantId() + "",
						m.getTerminalId() + "", m.getMerchantName(), m.getMerchantAddress() + "",
						m.getEntryDate() + " " + m.getEntryTime(), m.getCurrency(), m.getTxnAmount(), m.getMdrOnUs(),
						m.getMdrOffUs(), m.getFedRate(), m.getNetAmount(), m.getAuthIdN(), m.getBatchNumber(),
						Objects.isNull(m.getSetteledDate()) ? "" : m.getSetteledDate(),
						Objects.isNull(m.getSettledDateWithoutTime()) ? "" : m.getSettledDateWithoutTime(),
						Objects.isNull(m.getPaymentDate()) ? "" : m.getPaymentDate() };
				merchantsCounts.add(m.getMerchantName());
				row = xssfSheet.createRow(rowNum++);
				cellNum = 0;

				for (String txnDetailData : r) {
					Cell cell = row.createCell(cellNum);
					cell.setCellValue(txnDetailData);
//					CellStyle style = cell.getCellStyle();
//					if (cellNum == 0 || cellNum == 1 || cellNum == 3 || cellNum == 4 || cellNum == 7 || cellNum == 15
//							|| cellNum == 16 || cellNum == 17) {
//						style.setAlignment(CellStyle.ALIGN_CENTER);
//					} else if (cellNum == 9 || cellNum == 10 || cellNum == 11 || cellNum == 12 || cellNum == 13) {
//						style.setAlignment(CellStyle.ALIGN_RIGHT);
//					}
//					cell.setCellStyle(style);
					cellNum++;
				}
			}

			xssfSheet.addMergedRegion(new CellRangeAddress(0, 3, 0, totalCellCounts - 1));

			Row subDetailRow = xssfSheet.createRow(5);

			printMerchantName(merchantsCounts, subDetailRow, xssfSheet, xssfWorkbook);
			printOnCellExcelReport(totalCellCounts - 1, subDetailRow, xssfSheet);

			FileOutputStream out = new FileOutputStream(file);
			xssfWorkbook.write(out);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			AgLogger.logerror(UtilReport.class, "Exception while creating settlement excel : ", e);
		}
	}

	public static void createVoidTxnDetailExcelFile(List<ReportModel> txnDetails, String filePath)
			throws ParseException {
		File file = new File(filePath);

		try {

			ArrayList<String> merchantsCounts = new ArrayList<String>();

			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");

			CellStyle headerStyle = xssfWorkbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont font = xssfWorkbook.createFont();
			font.setUnderline(XSSFFont.U_SINGLE);
			headerStyle.setFont(font);

			int rowNum = 0;
			int cellNum = 0;
			int totalCellCounts = 0;

			rowNum = printReportTitle(xssfSheet, rowNum, xssfWorkbook, AppProp.getProperty("void.title"));

			rowNum += 5;

			String[] voidHeaders = new String[] { "S.NO", "Card No.", "PEM", "MID", "TID", "Merchant Name",
					"Merchant Address", "TR Date/Time", "Curreny", "TR Amount" };

			Row row = xssfSheet.createRow(rowNum++);
			cellNum = 0;

			for (String voidheader : voidHeaders) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(voidheader);
				cell.setCellStyle(headerStyle);
				totalCellCounts = cellNum;
			}

			int k = 0;
			for (ReportModel m : txnDetails) {
				k++;
				// Date date = (Date) sdf.parse(m.getEntryDate());
				String[] r = new String[] { k + "", m.getCardNumber(), m.getPemValue(), m.getMerchantId() + "",
						m.getTerminalId() + "", m.getMerchantName(), m.getMerchantAddress() + "", m.getEntryDate() + "",
						m.getCurrency(), m.getTxnAmount() };
				merchantsCounts.add(m.getMerchantName());
				row = xssfSheet.createRow(rowNum++);
				cellNum = 0;
				for (String txnDetailData : r) {
					Cell cell = row.createCell(cellNum++);
					cell.setCellValue(txnDetailData);
				}
			}

			xssfSheet.addMergedRegion(new CellRangeAddress(0, 3, 0, totalCellCounts));

			Row subDetailRow = xssfSheet.createRow(5);

			printMerchantName(merchantsCounts, subDetailRow, xssfSheet, xssfWorkbook);
			printOnCellExcelReport(totalCellCounts, subDetailRow, xssfSheet);

			FileOutputStream out = new FileOutputStream(file);
			xssfWorkbook.write(out);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			AgLogger.logerror(UtilReport.class, "Exception while creating void excel : ", e);
		}
	}

	public static void createTerminalExcelFile(List<TxnLog> terminalDetails, String filePath) throws ParseException {
		File file = new File(filePath);

		try {

			// ArrayList<String> merchantsCounts = new ArrayList<String>();

			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");

			CellStyle headerStyle = xssfWorkbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont font = xssfWorkbook.createFont();
			font.setUnderline(XSSFFont.U_SINGLE);
			headerStyle.setFont(font);

			int rowNum = 0;
			int cellNum = 0;
			int totalCellCounts = 0;

			rowNum = printReportTitle(xssfSheet, rowNum, xssfWorkbook, AppProp.getProperty("terminal.title"));

			rowNum += 5;

			String[] terminalHeaders = new String[] { "S.NO", "Activity Type", "Merchant ID", "TID", "Activity Date",
					"Model", "Serial Number", "Remarks" };

			Row row = xssfSheet.createRow(rowNum++);
			cellNum = 0;

			for (String terminalheader : terminalHeaders) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(terminalheader);
				cell.setCellStyle(headerStyle);
				totalCellCounts = cellNum;
			}

			int k = 0;
			for (TxnLog logs : terminalDetails) {
				k++;
				// Date date = (Date) sdf.parse(m.getEntryDate());
				String[] r = new String[] { k + "", logs.getActivityType() + "", logs.getMerchantId() + "",
						logs.getTerminalId(), logs.getActivityDate() + "", logs.getModel() + "",
						logs.getSerialNumber() + "", logs.getActivityRemarks() + "" };
				// merchantsCounts.add(m.getMerchantName());
				row = xssfSheet.createRow(rowNum++);
				cellNum = 0;
				for (String txnDetailData : r) {
					Cell cell = row.createCell(cellNum++);
					cell.setCellValue(txnDetailData);
				}
			}

			xssfSheet.addMergedRegion(new CellRangeAddress(0, 3, 0, totalCellCounts));

			Row subDetailRow = xssfSheet.createRow(5);

			// printMerchantName(merchantsCounts, subDetailRow, xssfSheet);
			printOnCellExcelReport(totalCellCounts, subDetailRow, xssfSheet);

			FileOutputStream out = new FileOutputStream(file);
			xssfWorkbook.write(out);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			AgLogger.logerror(UtilReport.class, "Exception while creating terminal activity excel : ", e);
		}
	}

	public static void createComplaintExcelFile(List<ComplaintsWebService> lstComplaint, String filePath)
			throws ParseException {
		File file = new File(filePath);

		try {

			// ArrayList<String> merchantsCounts = new ArrayList<String>();

			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");

			CellStyle headerStyle = xssfWorkbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont font = xssfWorkbook.createFont();
			font.setUnderline(XSSFFont.U_SINGLE);
			headerStyle.setFont(font);

			int rowNum = 0;
			int cellNum = 0;
			int totalCellCounts = 0;

			rowNum = printReportTitle(xssfSheet, rowNum, xssfWorkbook, AppProp.getProperty("complaint.title"));

			rowNum += 5;

			String[] complaintHeaders = new String[] { "S.NO", "Complaint No.", "MID", "TID", "Type", "Sub Type",
					"Description", "Model", "Serial Number", "Status", "Entry Date", "Assigned To", "Closed By",
					"Closure Date", "Resolution" };

			Row row = xssfSheet.createRow(rowNum++);
			cellNum = 0;

			for (String complaintHeader : complaintHeaders) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(complaintHeader);
				cell.setCellStyle(headerStyle);
				totalCellCounts = cellNum;
			}

			int k = 0;
			for (ComplaintsWebService c : lstComplaint) {
				k++;
				// Date date = (Date) sdf.parse(m.getEntryDate());
				String[] r = new String[] { k + "", c.getId() + "", c.getMid(), c.getTid(), c.getType(), c.getSubType(),
						c.getComplaintDesc(), c.getModel(), c.getSerialNumber(), c.getStatus(),
						!Objects.isNull(c.getEntryDate()) ? c.getEntryDate().toString() : "",
						!Objects.isNull(c.getAssignedTo()) ? c.getAssignedTo() : "", c.getClosedBy(),
						!Objects.isNull(c.getClosureDate()) ? c.getClosureDate().toString() : "", c.getRemarks() };
				// merchantsCounts.add(m.getMerchantName());
				row = xssfSheet.createRow(rowNum++);
				cellNum = 0;
				for (String txnDetailData : r) {
					Cell cell = row.createCell(cellNum++);
					cell.setCellValue(txnDetailData);
				}
			}

			xssfSheet.addMergedRegion(new CellRangeAddress(0, 3, 0, totalCellCounts));

			Row subDetailRow = xssfSheet.createRow(5);
			// printMerchantName(merchantsCounts, subDetailRow, xssfSheet,xssfWorkbook);
			printOnCellExcelReport(totalCellCounts, subDetailRow, xssfSheet);

			FileOutputStream out = new FileOutputStream(file);
			xssfWorkbook.write(out);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			AgLogger.logerror(UtilReport.class, "Exception while creating complaint excel : ", e);
		}
	}

	public static String getSubTypeCompl(String subtypeid, String type, List<LovDetail> lstLoveDetails) {

		for (LovDetail lo : lstLoveDetails) {
			if (lo.getId().equals(subtypeid) && lo.getLovId().equals(type)) {
				return lo.getValue();
			}
		}
		return "N/A";
	}

	public static String getAssignedTo(int id, HashMap<Integer, Object> userListAll) {

		try {
			UserLogin usl = (UserLogin) userListAll.get(id);
			if (usl != null) {
				return usl.getUserName();
			}
		} catch (Exception e) {

		}
		return "";
	}

	public static String getTypeCompl(String typeid, List<LovMaster> lstLovMaster) {
		try {
			for (LovMaster m : lstLovMaster) {
				if (m.getId().equals(typeid)) {
					return m.getValue();
				}
			}
		} catch (Exception e) {
			AgLogger.logerror(UtilReport.class, "Exception in getTypeCompl in complaint excel : ", e);
		}
		return "";
	}

	public static int printReportTitle(XSSFSheet xssfSheet, int rowNum, XSSFWorkbook xssfWorkbook, String title) {
		int cellNum = 0;
		Row headingRow = xssfSheet.createRow(rowNum++);
		Cell headingCell = headingRow.createCell(cellNum++);
		headingCell.setCellValue(title);
		CellStyle cellStyle = headingCell.getCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setUnderline(XSSFFont.U_SINGLE);
		font.setFontHeightInPoints((short) 24);
		cellStyle.setFont(font);
		headingCell.setCellStyle(cellStyle);
		return rowNum;
	}

	public static String formatDateForReport(String dateFormat) throws ParseException {
		String dateSt = "";
		if (!Objects.isNull(dateFormat) && !dateFormat.isEmpty()) {
			String patternParse = "dd/MM/yy";
			String patternFormat = "dd-MMM-yyyy";

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternParse);
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(patternFormat);
			Date date = simpleDateFormat.parse(dateFormat);
			dateSt = simpleDateFormat2.format(date);
		}

		return dateSt;
	}

	public static void printMerchantName(ArrayList<String> merchantsCounts, Row subDetailRow, XSSFSheet xssfSheet,
			XSSFWorkbook xssfWorkbook) {
		Set<String> set = new HashSet<String>(merchantsCounts);
		int cellNum = 0;
		if (set.size() == 1) {
			List<String> merchantName = new ArrayList<>(set);
			Cell merchantNamecell = subDetailRow.createCell(cellNum);
			CellStyle merchantNameStyle = xssfWorkbook.createCellStyle();
			XSSFFont font = xssfWorkbook.createFont();
			font.setBold(true);
			merchantNameStyle.setFont(font);
			merchantNamecell.setCellValue("Merchant Name: " + merchantName.get(0));
			merchantNamecell.setCellStyle(merchantNameStyle);
			xssfSheet.addMergedRegion(new CellRangeAddress(5, 5, cellNum, cellNum + 5));
		}
	}

	public static void printOnCellExcelReport(int totalCellCounts, Row subDetailRow, XSSFSheet xssfSheet) {
		int cellNum = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
		cellNum = totalCellCounts - 3;
		Cell printOncell = subDetailRow.createCell(cellNum);
		String date = sdf.format(new Date());
		printOncell.setCellValue("Report Print On: " + date);
		CellStyle printOnStyle = printOncell.getCellStyle();
		printOnStyle.setAlignment(HorizontalAlignment.CENTER);
		xssfSheet.addMergedRegion(new CellRangeAddress(5, 5, cellNum, totalCellCounts));
	}

	public static void createComplaintCsvFile(List<ComplaintsWebService> lstComplaint, String filePath) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);

			List<String[]> data = new ArrayList<String[]>();
			data.add(new String[] { "S.NO", "Complaint No.", "MID", "TID", "Type", "Sub Type", "Description", "Model",
					"Serial Number", "Status", "Entry Date", "Assigned To", "Closed By", "Closure Date",
					"Resolution" });

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int k = 0;
			for (ComplaintsWebService c : lstComplaint) {
				k++;
				data.add(new String[] { k + "", c.getId() + "", c.getMid(), c.getTid(), c.getType(), c.getSubType(),
						c.getComplaintDesc(), c.getModel(), c.getSerialNumber(), c.getStatus(),
						!Objects.isNull(c.getEntryDate()) ? c.getEntryDate().toString() : "",
						!Objects.isNull(c.getAssignedTo()) ? c.getAssignedTo() : "", c.getClosedBy(),
						!Objects.isNull(c.getClosureDate()) ? c.getClosureDate().toString() : "", c.getRemarks() });
			}

			writer.writeAll(data);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createDiscountDetailCsvFile(List<DMSReportReportModel> txnDetails, String filePath)
			throws ParseException {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();

			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			if (txnDetails != null) {
				data.add(new String[] { "S.NO", "Discount Id", "Discount Name", "Discount Description",
						"Discount Status", "Start Date", "End Date", "Total Discounted Txn Count",
						"Total Original Txn Amount", "Total Discount Availed", "Total Net Amount",
						"No. of allowed discounted txn per card per day" });
				int k = 0;
				for (DMSReportReportModel m : txnDetails) {
					k++;
					// Date date = (Date) sdf.parse(m.getEntryDate());
					data.add(new String[] { k + "", m.getDiscId() + "", m.getDiscName() + "",
							m.getDiscDescription() + "", m.getDiscStatus() + "", m.getStartDate() + "",
							m.getEndDate() + "", m.getTotalDiscountedTxnCount() + "",
							m.getTotalOriginalTxnAmount() + "", m.getTotalDiscountAvailed() + "",
							m.getTotalNetAmount() + "", m.getDiscAllowedTxtPerDay() });
				}

			}
			writer.writeAll(data);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			AgLogger.logerror(UtilReport.class, "Exception while creating txn detail csv : ", e);
		}
	}

}
