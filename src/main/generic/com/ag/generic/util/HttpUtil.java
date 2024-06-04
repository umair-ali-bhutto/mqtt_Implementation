package com.ag.generic.util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;



public class HttpUtil {

	public String doPost(String url, String text) throws Exception {
		String msg = "";
		try {

			HttpResponse httpResponse = null;
			HttpPost httpPost = new HttpPost(url);
			Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8");
			httpPost.addHeader(contentType);
			StringEntity entity = new StringEntity(text, "UTF-8");
			entity.setContentType(contentType);
			httpPost.setEntity(entity);
			HttpClient httpClient = HttpClientBuilder.create().build();
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception();
			}
			msg = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			AgLogger.logerror(getClass()," EXCEPTION  "+ e,e);
			throw new Exception();
		}
		return msg;
	}

	public String doGet(String url) throws Exception {

		String msg = "";
		HttpResponse httpResponse = null;
		HttpGet httpPost = new HttpGet(url);
		Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8");
		httpPost.addHeader(contentType);
		HttpClient httpClient = HttpClientBuilder.create().build();
		httpResponse = httpClient.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 200) {
			throw new Exception();
		}
		msg = EntityUtils.toString(httpResponse.getEntity());
		return msg;
	}
	
	
	public String doGetWithoutTime(String url) throws Exception {

		String msg = "";
		HttpResponse httpResponse = null;
		HttpGet httpPost = new HttpGet(url);
		Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8");
		httpPost.addHeader(contentType);
		HttpClient httpClient = HttpClientBuilder.create().build();
		httpResponse = httpClient.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 200) {
			throw new Exception();
		}
		msg = EntityUtils.toString(httpResponse.getEntity());
		return msg;
	}

	

}
