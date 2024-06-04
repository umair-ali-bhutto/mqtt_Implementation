package com.ag.fuel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.opencsv.CSVWriter;

public class UtilReport {

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

}
