/**
 * 
 */
package com.ximalaya.m3u8.node.encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ximalaya.m3u8.common.Constant;

/**
 * @author caorong
 * 
 */
@Component
public class Encoder implements InitializingBean {
	private final static Logger log = LoggerFactory.getLogger(Encoder.class);

	public static String m3u8Dir = Constant.BB_m3u8_Dir;

	public static String tsDir = Constant.BB_ts_Dir;

	public static String segmentName = Constant.BB_segmentName;

	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					FFmpegCommandRunner.generateAudioSegment(
							"mmsh://simuledge.shibapon.net/BAN-BAN_Radio", 7,
							tsDir, m3u8Dir, segmentName);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}, "m3u8-worker").start();
	}

}
