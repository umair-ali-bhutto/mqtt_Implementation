package com.ag.mportal.util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.ag.generic.util.AgLogger;

public class ReconUtil {

	public String doPost(String url, String text, int timeout) throws Exception {
		String msg = "";
		try {

			HttpResponse httpResponse = null;
			HttpPost httpPost = new HttpPost(url);
			Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8");
			httpPost.addHeader(contentType);
			StringEntity entity = new StringEntity(text, "UTF-8");
			entity.setContentType(contentType);
			httpPost.setEntity(entity);

			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout)
					.build();
			httpPost.setConfig(requestConfig);

			HttpClient httpClient = HttpClientBuilder.create().build();
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception();
			}
			msg = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  " + e, e);
			throw new Exception();
		}
		return msg;
	}

}
