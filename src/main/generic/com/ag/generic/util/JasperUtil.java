package com.ag.generic.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.ag.generic.model.KeyValueModel;
import com.ag.generic.prop.AppProp;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.model.ReportModel;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleGraphics2DExporterOutput;
import net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration;

public class JasperUtil {

	public static String generateReport(ReportModel txnDetail, String rocPath, PosEntryModeConfig pc)
			throws JRException, IOException {
		String path = null;
		File file = null;
		try {
			BufferedImage image = new BufferedImage(300, 600, BufferedImage.TYPE_INT_RGB);
			JRDataSource jds = new JREmptyDataSource();
			JasperReport jr = JasperCompileManager
					.compileReport(AppProp.getProperty("report.template.directory") + rocPath);
			String type = "";
			if (pc != null) {
				type = pc.getPosEntryValue();
			} else {
				type = "N/A";
			}
			Date date = null;
			Date time = null;
			String datef = null;
			String timef = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
			if (txnDetail.getEntryDate() != null && !(txnDetail.getEntryDate()).isEmpty()) {
				date = sdf.parse(txnDetail.getEntryDate());
				datef = sd.format(date);
			}
			SimpleDateFormat stf = new SimpleDateFormat("hh:mm:ss");
			SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss");
			if (txnDetail.getEntryTime() != null && !(txnDetail.getEntryTime()).isEmpty()) {
				time = stf.parse(txnDetail.getEntryTime());
				timef = st.format(time);
			}
			Map<String, Object> mp = new HashMap<String, Object>();

			mp.put("tSerial",
					Objects.isNull(txnDetail.getTxnSerialNumber()) || txnDetail.getTxnSerialNumber().isEmpty() ? "N/A"
							: txnDetail.getTxnSerialNumber());
			mp.put("mid", Objects.isNull(txnDetail.getMerchantId()) || txnDetail.getMerchantId().isEmpty() ? "N/A"
					: txnDetail.getMerchantId());
			mp.put("date", Objects.isNull(datef) ? "N/A" : datef);
			mp.put("time", Objects.isNull(timef) ? "N/A" : timef);
			mp.put("tid", Objects.isNull(txnDetail.getTerminalId()) || txnDetail.getTerminalId().isEmpty() ? "N/A"
					: txnDetail.getTerminalId());
			mp.put("batch", Objects.isNull(txnDetail.getBatchNumber()) || txnDetail.getBatchNumber().isEmpty() ? "N/A"
					: txnDetail.getBatchNumber());
			mp.put("invoice",
					Objects.isNull(txnDetail.getInvoiceNumber()) || txnDetail.getInvoiceNumber().isEmpty() ? "N/A"
							: txnDetail.getInvoiceNumber());
			mp.put("rrn", Objects.isNull(txnDetail.getRefNum()) || txnDetail.getRefNum().isEmpty() ? "N/A"
					: txnDetail.getRefNum());
			mp.put("authId", Objects.isNull(txnDetail.getAuthIdN()) || txnDetail.getAuthIdN().isEmpty() ? "N/A"
					: txnDetail.getAuthIdN());
			mp.put("cardNo", Objects.isNull(txnDetail.getFieldOne()) || txnDetail.getFieldOne().isEmpty() ? "N/A"
					: txnDetail.getFieldOne());
			mp.put("aId",
					Objects.isNull(txnDetail.getAid()) || txnDetail.getAid().isEmpty() ? "N/A" : txnDetail.getAid());
			mp.put("tvr",
					Objects.isNull(txnDetail.getTvr()) || txnDetail.getTvr().isEmpty() ? "N/A" : txnDetail.getTvr());
			mp.put("amount", Objects.isNull(txnDetail.getTxnAmount()) || txnDetail.getTxnAmount().isEmpty() ? "N/A"
					: txnDetail.getTxnAmount());
			mp.put("expiry", Objects.isNull(txnDetail.getCardExpiry()) || txnDetail.getCardExpiry().isEmpty() ? "N/A"
					: txnDetail.getCardExpiry());
			mp.put("currency", "PKR");
			mp.put("name", Objects.isNull(txnDetail.getCustomerName()) || txnDetail.getCustomerName().isEmpty() ? "N/A"
					: txnDetail.getCustomerName());
			mp.put("terminalName",
					Objects.isNull(txnDetail.getMerchantName()) || txnDetail.getMerchantName().isEmpty() ? "N/A"
							: txnDetail.getMerchantName());

			if (!Objects.isNull(txnDetail.getAdditionalData())) {
				String[] addressSplit = txnDetail.getAdditionalData().split("\\|\\|");

				try {
					mp.put("address1",
							(Objects.isNull(addressSplit[0]) || addressSplit[0].isEmpty()) ? "N/A" : addressSplit[0]);
				} catch (Exception ex) {
					AgLogger.logerror(JasperUtil.class, "Exception in Address1: ", ex);
					mp.put("address1", "N/A");
				}

				try {
					mp.put("address2",
							(Objects.isNull(addressSplit[1]) || addressSplit[1].isEmpty()) ? "N/A" : addressSplit[1]);
				} catch (Exception ex) {
					AgLogger.logerror(JasperUtil.class, "Exception in Address2: ", ex);
					mp.put("address2", "N/A");
				}

				try {
					mp.put("address3",
							(Objects.isNull(addressSplit[2]) || addressSplit[2].isEmpty()) ? "N/A" : addressSplit[2]);
				} catch (Exception ex) {
					AgLogger.logerror(JasperUtil.class, "Exception in Address3: ", ex);
					mp.put("address3", "N/A");
				}

				try {
					mp.put("address4",
							(Objects.isNull(addressSplit[3]) || addressSplit[3].isEmpty()) ? "N/A" : addressSplit[3]);
				} catch (Exception ex) {
					AgLogger.logerror(JasperUtil.class, "Exception in Address4: ", ex);
					mp.put("address4", "N/A");
				}
			} else {
				mp.put("address1", "N/A");
				mp.put("address2", "N/A");
				mp.put("address3", "N/A");
				mp.put("address4", "N/A");
			}

			mp.put("status", "APPROVED");
			mp.put("user", "MERCHANT ");
			mp.put("cardType", Objects.isNull(txnDetail.getScheme()) || txnDetail.getScheme().isEmpty() ? "N/A"
					: txnDetail.getScheme());
			mp.put("txnType",
					Objects.isNull(txnDetail.getType()) || txnDetail.getType().isEmpty() ? "N/A" : txnDetail.getType());
			mp.put("transferType", type);
			mp.put("totalAmount", Objects.isNull(txnDetail.getTxnAmount()) || txnDetail.getTxnAmount().isEmpty() ? "N/A"
					: txnDetail.getTxnAmount());
			mp.put("tipAmount", Objects.isNull(txnDetail.getTipAmount()) || txnDetail.getTipAmount().isEmpty() ? "N/A"
					: txnDetail.getTipAmount());

			mp.put("imagesDir",
					Objects.isNull(AppProp.getProperty("report.template.directory"))
							|| AppProp.getProperty("report.template.directory").isEmpty() ? ""
									: AppProp.getProperty("report.template.directory"));

			JasperPrint jp = JasperFillManager.fillReport(jr, mp, jds);
			JRGraphics2DExporter exporter = new JRGraphics2DExporter();

			exporter.setExporterInput(new SimpleExporterInput(jp));
			SimpleGraphics2DExporterOutput output = new SimpleGraphics2DExporterOutput();
			output.setGraphics2D((Graphics2D) image.getGraphics());
			exporter.setExporterOutput(output);
			SimpleGraphics2DReportConfiguration configuration = new SimpleGraphics2DReportConfiguration();
			configuration.setZoomRatio(Float.valueOf(1));
			exporter.setConfiguration(configuration);
			exporter.exportReport();

			path = AppProp.getProperty("report.template.directory") + "ROC-" + txnDetail.getType() + "-"
					+ new SimpleDateFormat("yyyy-MM-ddHHmmss").format(new Date()) + ".png";
			file = new File(path);
			ImageIO.write(image, "PNG", file);
		} catch (Exception e) {
			e.printStackTrace();
			path = null;
		}

		return path;
	}

	@SuppressWarnings("unchecked")
	public static String[] generateMetroReports(String jxmlPath, String cardType, String batchNumber, String reportName,
			String reportType, String StoreName, String fromDate, String toDate) throws JRException, IOException {
		String[] sjl = new String[3];
		sjl[0] = "0000";
		sjl[1] = "N/A";
		String Format = null;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			SimpleDateFormat sdfForm = new SimpleDateFormat("dd-MMM-yy");

			String finFromDate = sdfForm.format(sdf.parse(fromDate));

			String finToDate = sdfForm.format(sdf.parse(toDate));

			Map parameters = new HashMap();
			parameters.put("Card_Type", cardType);
			parameters.put("Batch_No", batchNumber);
			parameters.put("Store_Name", StoreName);
			parameters.put("Date_from", finFromDate);
			parameters.put("Date_to", finToDate);

			String outputPath = AppProp.getProperty("report.template.directory");
			JasperPrint jasperPrint = ReportGenerator.compileReport(parameters, jxmlPath);
			AgLogger.logInfo(jxmlPath + " JasperPrint: " + jasperPrint.getPages().isEmpty() + "|" + cardType + "|"
					+ batchNumber + "|" + StoreName + "|" + finFromDate + "|" + finToDate);
			boolean page = jasperPrint.getPages().isEmpty();
			if (!page) {

				if (reportType.equalsIgnoreCase("pdf")) { // Generate PDF File
					Format = ".pdf";
					String finreportName = reportName.substring(reportName.lastIndexOf("/") + 1,
							reportName.lastIndexOf(".jrxml"));
					String outputLocalFile = outputPath + "_" + finreportName + Format;
					boolean isGeneratePdf = ReportGenerator.generatePDF(jasperPrint, outputLocalFile);
					if (isGeneratePdf) {
						sjl[0] = "0000";
						sjl[1] = finreportName + Format;
						sjl[2] = UtilAccess.convertFileToBlob(outputLocalFile);
					} else {
						sjl[0] = "0001";
						sjl[1] = "No Pages Found.";
					}
				} else if (reportType.equalsIgnoreCase("excel")) {// Generate Excel File
					Format = ".xls";
					String finreportName = reportName.substring(reportName.lastIndexOf("/") + 1,
							reportName.lastIndexOf(".jrxml"));
					String outputLocalFile = outputPath + "_" + finreportName + Format;
					boolean isGenerateXls = ReportGenerator.generateXLS(jasperPrint, outputLocalFile);
					if (isGenerateXls) {
						sjl[0] = "0000";
						sjl[1] = finreportName + Format;
						sjl[2] = UtilAccess.convertFileToBlob(outputLocalFile);
					} else {
						sjl[0] = "0001";
						sjl[1] = "No Pages Found.";
					}
				} else {
					sjl[0] = "0001";
					sjl[1] = "Please Select the format either PDF or Excel";
				}

			} else {
				sjl[0] = "0001";
				sjl[1] = "Data Not Found.";
			}

		} catch (Exception e) {
			sjl[0] = "9999";
			sjl[1] = "Something went wrong.";
			e.printStackTrace();

		}

		return sjl;
	}

	@SuppressWarnings("unchecked")
	public static String[] generateGenericReports(String jxmlPath, List<KeyValueModel> filterListResponse,
			String reportName, String reportType, HashMap<Object, Object> extarParams) throws JRException, IOException {
		String[] sjl = new String[3];
		sjl[0] = "0000";
		sjl[1] = "N/A";
		String Format = null;
		try {
			Map parameters = new HashMap();
			StringBuilder sb = new StringBuilder();
			for (KeyValueModel m : filterListResponse) {
				parameters.put(m.getKey(), m.getValue());
				sb.append("|" + m.getKey() + ":" + m.getValue());
			}

			if (!extarParams.isEmpty()) {
				extarParams.forEach((key, value) -> {
					parameters.put(key, value);
					sb.append("|" + key + ":" + value);
				});
			}
			 String outputPath = AppProp.getProperty("report.template.directory");
			//String outputPath = "/home/umair/Desktop/Angular-WS/jrxml-path/output/";
			JasperPrint jasperPrint = ReportGenerator.compileReport(parameters, jxmlPath);
			AgLogger.logInfo(jxmlPath + " JasperPrint: " + jasperPrint.getPages().isEmpty() + "|" + sb.toString());

			boolean page = jasperPrint.getPages().isEmpty();
			if (!page) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date currentDate = new Date();
				String formattedDate = sdf.format(currentDate);

				String finreportName = reportName + formattedDate;

				if (reportType.equalsIgnoreCase("pdf")) { // Generate PDF File
					Format = ".pdf";
					String outputLocalFile = outputPath + finreportName + Format;
					boolean isGeneratePdf = ReportGenerator.generatePDF(jasperPrint, outputLocalFile);
					if (isGeneratePdf) {
						sjl[0] = "0000";
						sjl[1] = finreportName + Format;
						sjl[2] = UtilAccess.convertFileToBlob(outputLocalFile);
					} else {
						sjl[0] = "0001";
						sjl[1] = "No Pages Found.";
					}
				} else if (reportType.equalsIgnoreCase("excel")) {// Generate Excel File
					Format = ".xls";
					String outputLocalFile = outputPath + finreportName + Format;
					boolean isGenerateXls = ReportGenerator.generateXLS(jasperPrint, outputLocalFile);
					if (isGenerateXls) {
						sjl[0] = "0000";
						sjl[1] = finreportName + Format;
						sjl[2] = UtilAccess.convertFileToBlob(outputLocalFile);
					} else {
						sjl[0] = "0001";
						sjl[1] = "No Pages Found.";
					}
				}
				else {
					sjl[0] = "0001";
					sjl[1] = "Please Select the format either PDF or Excel";
				}

			} else {
				sjl[0] = "0001";
				sjl[1] = "Data Not Found.";
			}

		} catch (Exception e) {
			sjl[0] = "9999";
			sjl[1] = "Something went wrong.";
			e.printStackTrace();

		}

		return sjl;
	}

}