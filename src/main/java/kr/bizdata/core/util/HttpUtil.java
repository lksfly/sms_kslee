package kr.bizdata.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	private static final int serverTimeout = 60000; // 60 sec
	
	/**
	 * HTTP GET 요청을 보내고, 결과를 반환한다.
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws HttpResponseException 
	 */
	public static String get(String url, Header... headers) throws HttpResponseException {
		
		return call("GET", url, null, headers);
	}
	
	/**
	 * HTTP POST 요청을 보내고, 결과를 반환한다.
	 * 
	 * @param url
	 * @param param
	 * @param headers
	 * @return
	 * @throws HttpResponseException 
	 */
	public static String post(String url, String param, Header... headers) throws HttpResponseException {
		
		return call("POST", url, param, headers);
	}
	
	/*
	 * @param method
	 * @param url
	 * @param param
	 * @return
	 * @throws HttpResponseException
	 */
	private static String call(String method, String url, String param, Header[] headers) throws HttpResponseException {
		if (logger.isDebugEnabled()) {
			logger.debug("HttpUtil.call(" + method + " " + url + ")\n" + param);
		}
		
		HttpParams hp = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(hp, serverTimeout);
		HttpConnectionParams.setSoTimeout(hp, serverTimeout);
		HttpConnectionParams.setTcpNoDelay(hp, true);
		DefaultHttpClient client = new DefaultHttpClient(hp);
		
		try {
			HttpUriRequest request = getRequest(method, url, param); // request
			if (headers != null) {
				request.setHeaders(headers);
			}
			HttpResponse response = client.execute(request); // response
			int statusCode = response.getStatusLine().getStatusCode();
			String responseText = EntityUtils.toString(response.getEntity());
			
			if (statusCode == 200 || statusCode == 201) { // 성공코드 (200: Success, 201: Created)
				if (logger.isDebugEnabled()) {
					logger.debug("HttpUtil.call(" + method + " " + url + ") -> success. (statusCode: " + statusCode + ")\n" + responseText);
				}
				return responseText;
			} else {
				throw new HttpResponseException(statusCode, responseText);
			}
		} catch (HttpResponseException e) {
			logger.error("HttpUtil.call(" + method + " " + url + ") -> failed. (statusCode: " + e.getStatusCode() + ")\n" + e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.error("HttpUtil.call(" + method + " " + url + ") -> failed.");
			throw new RuntimeException(e);
		}
	}
	
	private static HttpUriRequest getRequest(String method, String url, String param)
			throws ClientProtocolException, UnsupportedEncodingException {
		if ("GET".equals(method)) {
			HttpGet get = new HttpGet(url);
			return get;
		} else if ("POST".equals(method)) {
			HttpPost post = new HttpPost(url);
			post.setEntity(new StringEntity(param, "application/json", "UTF-8"));
			return post;
		} else {
			throw new ClientProtocolException();
		}
	}
	
	/**
	 * (프록시가 아닌) 클라이언트의 실제 IP를 구한다.
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		
		String remoteAddr = request.getHeader("x-forwarded-for");
		
		if (StringUtils.isEmpty(remoteAddr)) {
			Enumeration<String> names = request.getHeaderNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				if ("x-forwarded-for".equalsIgnoreCase(name)) {
					remoteAddr = request.getHeader(name);
					break;
				}
			}
		}
		if (StringUtils.isEmpty(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		}
		
		remoteAddr = StringUtils.trim(StringUtils.split(remoteAddr, ",")[0]); // X-Forwarded-For: <client>, <proxy1>, <proxy2>
		
		return remoteAddr;
	}
	
}
