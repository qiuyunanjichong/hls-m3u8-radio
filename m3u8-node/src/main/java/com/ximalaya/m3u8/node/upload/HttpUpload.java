/**
 * 
 */
package com.ximalaya.m3u8.node.upload;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author caorong
 * 
 */
@Component
public class HttpUpload {

	private final static Logger log = LoggerFactory.getLogger(HttpUpload.class);

	private HttpClientPool httpClientPool;

	public HttpUpload(HttpClientPool clientPool) {
		this.httpClientPool = clientPool;
	}

	public String upload(String url, File file) throws Exception {
		if (file.length() == 0) {
			log.error("file length is 0, filename:{0}", file.getAbsolutePath());
			return null;
		}
		// HttpClient httpclient = new DefaultHttpClient();
		HttpClient httpclient = httpClientPool.getHttpClient();
		httpclient.getParams().setParameter(
				HttpProtocolParams.HTTP_CONTENT_CHARSET, "UTF-8");
		HttpResponse response = null;
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			FileEntity entity = new FileEntity(file);
			post.setEntity(entity);
			response = httpclient.execute(post);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity resEntity = response.getEntity();
				// 显示内容
				String ret = new String(EntityUtils.toByteArray(resEntity));
				// 删除原文件
				return ret;
			} else {
				log.error("upload file error, filename:{0}",
						file.getAbsolutePath());
				throw new Exception("Response:" + response.getStatusLine());
			}
		} catch (Exception e) {
			log.error("upload file error, filename:{0}", e,
					file.getAbsolutePath());
			throw new Exception(e);
		} finally {
			// httpclient.getConnectionManager().shutdown();
			if (null != post)
				post.releaseConnection();
		}
	}
}
