/**
 * 
 */
package com.ximalaya.m3u8.node.upload;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author caorong
 * 
 */
@Component
public class HttpClientPool implements InitializingBean {

	private HttpClient client = null;

//	private HttpClient clientP = null;

	public static final String HEAD_USER_AGENT = "User-Agent";

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36 AlexaToolbar/alxg-3.1";

	public static final String HEAD_REFER = "Referer";

	@Override
	public void afterPropertiesSet() throws Exception {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(
				schemeRegistry);

		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);

		client = new DefaultHttpClient(cm);
//		clientP = new DefaultHttpClient(cm);
		// HttpClient client = new DefaultHttpClient();
		// 连接时间10s
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 3 * 1000);
//		clientP.getParams().setParameter(
//				CoreConnectionPNames.CONNECTION_TIMEOUT, 15 * 1000);
		// 连接时间10s
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				3 * 1000);
//		clientP.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
//				5 * 60 * 1000);
		// utf8
		client.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
//		clientP.getParams().setParameter(
//				CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
	}

	public HttpClient getHttpClient() {
		// if client miss
		if (client == null) {
			try {
				this.afterPropertiesSet();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	// public HttpClient getHttpClient4Parser() {
	// // if client miss
	// if (clientP == null) {
	// try {
	// this.afterPropertiesSet();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return clientP;
	// }

}
