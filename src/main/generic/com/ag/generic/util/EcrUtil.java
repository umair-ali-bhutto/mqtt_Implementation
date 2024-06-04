package com.ag.generic.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ag.mportal.model.EcrModel;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class EcrUtil {

	public static String convertObjectToJSONMapper(String requestTemplete, EcrModel emModel) {
		String res = requestTemplete;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(EcrModel.class);
			for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
				String propertyName = propertyDesc.getName();
				Object value = propertyDesc.getReadMethod().invoke(emModel);
				// System.out.println(propertyName + "|" + value);
				AgLogger.logInfo(propertyName + "|" + value);
				res = res.replaceAll("@" + propertyName, (value != null) ? value.toString() : "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public JsonObject modifyRequest(JsonObject headerToItem) {
		try {
			String AccountnumberS = "";
			String TransactioncodeS = "";
			String PaymentdateS = "";
			String TransactiondateS = "";
			String DuedateS = "";
			String id = "";
			String Receiptnumber = "";
			JsonObject request = headerToItem.getAsJsonArray("HeaderToItem").get(0).getAsJsonObject();
			AccountnumberS = request.get("Accountnumber").getAsString();
			TransactioncodeS = request.get("Transactioncode").getAsString();
			PaymentdateS = request.get("Paymentdate").getAsString();
			TransactiondateS = request.get("Transactiondate").getAsString();
			DuedateS = request.get("Duedate").getAsString();
			id = request.get("ID").getAsString();
			Receiptnumber = request.get("Receiptnumber").getAsString();
			request.remove("Accountnumber");
			request.remove("Transactioncode");
			request.remove("Paymentdate");
			request.remove("Transactiondate");
			request.remove("Duedate");
			request.remove("TYP_DATA");
			request.remove("MID");
			request.remove("ID");
			request.remove("Receiptnumber");

			if (Receiptnumber.length() > 10) {
				Receiptnumber = Receiptnumber.substring(0, 9);
			}
			request.addProperty("Receiptnumber", Receiptnumber);

			if (AccountnumberS.length() == 13) {
				AccountnumberS = AccountnumberS.substring(1, AccountnumberS.length());
			}
			request.addProperty("Accountnumber", AccountnumberS);

			TransactioncodeS = StringUtils.leftPad(id, 7, "0");
			request.addProperty("Transactioncode", TransactioncodeS);

			SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyyMMdd");
			Date inputDate = inputDateFormat.parse(DuedateS);
			DuedateS = outputDateFormat.format(inputDate);
			request.addProperty("Duedate", DuedateS);

			SimpleDateFormat inputPayDateFormat = new SimpleDateFormat("MMddyyyy");
			int year = Calendar.getInstance().get(Calendar.YEAR);
			Date inputPayDate = inputPayDateFormat.parse(PaymentdateS + year);
			PaymentdateS = outputDateFormat.format(inputPayDate);
			request.addProperty("Paymentdate", PaymentdateS);
			request.addProperty("Transactiondate", PaymentdateS);

			return headerToItem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String processGetRequest(String url) {
		String msg = "N-A";
		AgLogger.logInfo("SENIDING URL ." + url);
		try {
			HttpResponse httpResponse = null;
			HttpGet httpGet = new HttpGet(url);
			Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8");
			httpGet.addHeader(contentType);
			HttpClient httpClient = new HttpUtilClient().getNewHttpClient();
			httpResponse = httpClient.execute(httpGet);
			AgLogger.logInfo("RESPONSE CODE ." + httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception();
			}
			msg = EntityUtils.toString(httpResponse.getEntity());
			AgLogger.logInfo("RESPONSE ." + msg);
			return msg;
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  " + e, e);
		}
		return msg;
	}
	
	
	public String processGetRequestWithProxy(String url,String proxy,int port) {
		String msg = "N-A";
		AgLogger.logInfo("SENIDING URL WITH PROXY ." + url+"|"+proxy+"|"+port);
		try {
			HttpResponse httpResponse = null;
			HttpGet httpGet = new HttpGet(url);
			Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8");
			httpGet.addHeader(contentType);
			HttpClient httpClient = new HttpUtilClient().getNewHttpClient();
			HttpHost proxySer = new HttpHost(proxy,port);
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxySer);
			httpResponse = httpClient.execute(httpGet);
			AgLogger.logInfo("RESPONSE CODE ." + httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception();
			}
			msg = EntityUtils.toString(httpResponse.getEntity());
			AgLogger.logInfo("RESPONSE ." + msg);
			return msg;
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  " + e, e);
		}
		return msg;
	}
    
    public String processXmlString(String xmlString) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode2 = xmlMapper.readTree(xmlString);

            JSONObject jsonObject = new JSONObject(jsonNode2.toString());
            JSONObject propertiesObject = jsonObject.getJSONObject("content").getJSONObject("properties");

            StringBuilder builder1 = new StringBuilder();
            builder1.append(propertiesObject.get("AccNo")).append("|")
                    .append(propertiesObject.get("BillAmount")).append("|")
                    .append(propertiesObject.get("CDate")).append("|")
                    .append(propertiesObject.get("CutomerName")).append("|")
                    .append(propertiesObject.get("CTime")).append("|")
                    .append(propertiesObject.get("IssueDate")).append("|")
                    .append(propertiesObject.get("Arrears")).append("|")
                    .append(propertiesObject.get("Lps")).append("|")
                    .append(propertiesObject.get("Result")).append("|")
                    .append(propertiesObject.get("Response")).append("|")
                    .append(propertiesObject.get("Blcd")).append("|")
                    .append(propertiesObject.get("BillId")).append("|")
                    .append(propertiesObject.get("DueDate")).append("|")
                    .append(propertiesObject.get("Currbill")).append("|")
                    .append(propertiesObject.get("Contract"));

            AgLogger.logInfo("VALUES: " + builder1.toString());
            return builder1.toString();
        } catch (Exception e) {
            e.printStackTrace();
            AgLogger.logerror(e.getClass(),"Error processing XML string: ", e);
            return null;
        }
    }
    
   
	
	
	public String postRequestWithBodyAndBasicAuthProxy(String sUrl, String body, String userName, String userPass,String proxyIp,int port) {
    	try {
    		URL url = new URL(sUrl);
    		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, port));
    		HttpsURLConnection con = (HttpsURLConnection) url.openConnection(proxy);
    		con.setRequestMethod("POST");
    		con.setRequestProperty("X-Requested-With", "XMHttpRequest");
    		con.setRequestProperty("Content-Type", "application/json");
    		con.setRequestProperty("Accept", "application/json");    		
    		String authorizationHeader = basicAuthHeader(userName, userPass);
            con.setRequestProperty("Authorization", authorizationHeader);
            AgLogger.logInfo("RESPONSE HEADER: " + authorizationHeader);
    		con.setDoOutput(true);
    		con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(body.getBytes(StandardCharsets.UTF_8).length));
    		DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(body);
            out.flush();
            out.close();
    		int status = con.getResponseCode();
    		String message = con.getResponseMessage();
    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer content = new StringBuffer();
    		while((inputLine = in.readLine()) != null) {
    			content.append(inputLine);
    		}
    		in.close();
    		con.disconnect();
    		AgLogger.logInfo("Response Code: " + status);
    		AgLogger.logInfo("Response Details: " + message);
    		AgLogger.logInfo("Response Message: " + content.toString());
    		return content.toString();
    		
    		} catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                AgLogger.logInfo("Error Response Message: " + e.getMessage());
                return null;
            }
    	
    }
    
    public String postRequestWithBodyAndBasicAuth(String sUrl, String body, String userName, String userPass) {
    	try {
    		URL url = new URL(sUrl);
    		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
    		con.setRequestMethod("POST");
    		con.setRequestProperty("X-Requested-With", "XMHttpRequest");
    		con.setRequestProperty("Content-Type", "application/json");
    		con.setRequestProperty("Accept", "application/json");    		
    		String authorizationHeader = basicAuthHeader(userName, userPass);
            con.setRequestProperty("Authorization", authorizationHeader);
            AgLogger.logInfo("RESPONSE HEADER: " + authorizationHeader);
    		con.setDoOutput(true);
    		con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(body.getBytes(StandardCharsets.UTF_8).length));
    		DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(body);
            out.flush();
            out.close();
    		int status = con.getResponseCode();
    		String message = con.getResponseMessage();
    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer content = new StringBuffer();
    		while((inputLine = in.readLine()) != null) {
    			content.append(inputLine);
    		}
    		in.close();
    		con.disconnect();
    		AgLogger.logInfo("Response Code: " + status);
    		AgLogger.logInfo("Response Details: " + message);
    		AgLogger.logInfo("Response Message: " + content.toString());
    		return content.toString();
    		
    		} catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                AgLogger.logInfo("Error Response Message: " + e.getMessage());
                return null;
            }
    	
    }
    
    private String basicAuthHeader(String userName, String userPass) {
        String credentials = userName + ":" + userPass;
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return basicAuthHeader;
    }
}
