package com.ag.generic.util;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Map;

import com.ag.db.proc.DBProcUtil;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;

public class ReportGenerator {

	public static JasperPrint compileReport(Map parameters, String jrxmlpath) throws Exception {
		JasperPrint jasperPrint = null;
		java.sql.Connection conn = null;
		conn = null;
		try {
			conn = DBProcUtil.getConnection();
			JasperDesign jasperDesign = JRXmlLoader.load(jrxmlpath);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return jasperPrint;
	}

	public static boolean generatePDF(JasperPrint jasperPrint, String filePath) {
		boolean isOk = false;
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			JRPdfExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(simpleOutputStreamExporterOutput);
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			isOk = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;

	}

	public static boolean generateXLS(JasperPrint jasperPrint, String filePath) throws Exception {
		boolean isOk = false;
		try {
			JRXlsExporter exporter = new JRXlsExporter();
			FileOutputStream fos = new FileOutputStream(filePath);
			OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(simpleOutputStreamExporterOutput);
			SimpleXlsExporterConfiguration configuration = new SimpleXlsExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();

			isOk = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;

	}

	

}
