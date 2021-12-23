package com.github.refracta.skplanet.helper;

import com.github.refracta.skplanet.logic.token.TokenConst;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpClientWrapper {
	private HttpClient httpClient = new DefaultHttpClient();
	public static final String CHROME_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36";
	public static final String EXPLORER_AGENT = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; WOW64; Trident/6.0)";
	public static final String FIREFOX_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
	public static final String MOBILE_DOLPHIN_AGENT = "Mozilla/5.0 (Linux; U; Android 4.3; ko-kr; SHV-E210L Build/JSS15J) AppleWebKit/537.16 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.16";
	public static final String CURRENT_TOKEN;

	static {
		CURRENT_TOKEN = TokenConst.getMY_TOKEN();
	}

	public HttpClientWrapper(String userAgent) {
		this.httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
	}

	public HttpClientWrapper() {

	}

	public void setHttpClient(HttpClient client) {
		this.httpClient = client;
	}

	public HttpClient getHttpClient() {
		return this.httpClient;
	}


	public void setHttpClientParameter(String param, String value) {
		httpClient.getParams().setParameter(param, value);
	}

	public String easyRequestGetPage(String targetPage) {
		URL url = null;
		try {
			url = new URL(targetPage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpGet httpGet = new HttpGet(url.getPath()+"?"+url.getQuery());
		httpGet.setHeader("X-Auth-Token",CURRENT_TOKEN);
		httpGet.setHeader("Content-Type","application/json");
		HttpHost httpHost = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
		StringBuffer buffer = new StringBuffer();
		try {
			HttpResponse execute = this.httpClient.execute(httpHost, httpGet);
			if (execute.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(execute.getEntity().getContent(), "UTF-8"));
				String dataLine = null;
				while ((dataLine = reader.readLine()) != null) {
					buffer.append(dataLine);
				}
			}else {
				System.err.println(execute.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	public String easyRequestHtmlGetPage(String targetPage) {
		URL url = null;
		try {
			url = new URL(targetPage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpGet httpGet = new HttpGet(url.getPath()+"?"+url.getQuery());

		httpGet.setHeader("Content-Type","text/html");
		HttpHost httpHost = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
		StringBuffer buffer = new StringBuffer();
		try {
			HttpResponse execute = this.httpClient.execute(httpHost, httpGet);
			if (execute.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(execute.getEntity().getContent(), "UTF-8"));
				String dataLine = null;
				while ((dataLine = reader.readLine()) != null) {
					buffer.append(dataLine);
				}
			}else {
				System.err.println(execute.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}


	public String easyRequestPostPage(String targetPage, String json) {

		URL url = null;
		try {
			url = new URL(targetPage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpPost httpPost = new HttpPost(url.getPath());
		httpPost.setHeader("X-Auth-Token",CURRENT_TOKEN);
		httpPost.setHeader("Content-Type","application/json");

/*		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String,String> entry : paramsHashMap.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			nvps.add(new BasicNameValuePair(key, value));
		}*/
//		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		try {
			StringEntity jsonEntity = new StringEntity(json);
			httpPost.setEntity(jsonEntity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpHost httpHost = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
		StringBuffer buffer = new StringBuffer();
		try {
			HttpResponse execute = this.httpClient.execute(httpHost, httpPost);
			if (execute.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(execute.getEntity().getContent(), "UTF-8"));
				String dataLine = null;
				while ((dataLine = reader.readLine()) != null) {
					buffer.append(dataLine);
				}
			}else{
				System.err.println(execute.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}



	/*private String requestPostPage(PostMethod postMethod, String encode) {
		StringBuffer returnBuffer = new StringBuffer();
		try {
			int statusCode = this.httpClient.executeMethod(postMethod);

			if (statusCode == 200) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(postMethod.getResponseBodyAsStream(), encode));
				String line;
				while ((line = br.readLine()) != null) {
					returnBuffer.append(line + "\n");
				}
			} else {
				returnBuffer.append("Post Error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		postMethod.releaseConnection();
		return returnBuffer.toString();
	}*/


}
