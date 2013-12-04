package com.ximalaya.m3u8.node.encode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FFmpegCommandRunner {

	private final static Logger log = LoggerFactory
			.getLogger(FFmpegCommandRunner.class);

	// private final static MessageLogger log =
	// LoggerFactory.getLogger(FFmpegCommandRunner.class);

	private static String ext = ".mp3";

	// private static IJsonSerializer jsonSerializer = new JackSonSerializer();

	public static Process pro = null;

	public volatile static boolean isRunning = false;

	/**
	 * 生成segmegnt片段 use m3u8-segmenter
	 * 
	 * @throws Exception
	 * @author caorong created on 2013-9-3
	 * @since 1.0
	 */
	public static void generateAudioSegment(String url, Integer segmentLen,
			String tsFolderName, String m3u8FolderName, String segmentName)
			throws Exception {
		// ffmpeg -i mmsh://simuledge.shibapon.net/BAN-BAN_Radio -f mpegts - |
		// m3u8-segmenter -i - -d 10 -p tmp2/bbradio -m tmp2/bb.m3u8 -u
		// http://192.168.2.126:8990
		List<String> args = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		sb.append(BaseCommandOption.FFMPEG);
		sb.append(" ");
		sb.append(BaseCommandOption.INPUT);
		sb.append(" ");
		sb.append(url);
		sb.append(" ");
		sb.append(BaseCommandOption.FORMAT);
		sb.append(" ");
		sb.append(BaseCommandOption.MPEGTS);
		sb.append(" ");
		sb.append(BaseCommandOption.BLANK);
		sb.append(" ");

		sb.append(BaseCommandOption.CONCAT_SPLIT);
		sb.append(" ");
		sb.append(BaseCommandOption.M3U8SEGMENTER);
		sb.append(" ");
		sb.append(BaseCommandOption.INPUT);
		sb.append(" ");
		sb.append(BaseCommandOption.BLANK);
		sb.append(" ");

		sb.append(BaseCommandOption.SEGMENTDURATION);
		sb.append(" ");
		sb.append(String.valueOf(segmentLen));
		sb.append(" ");
		sb.append(BaseCommandOption.SEGMENTPATH);
		sb.append(" ");
		// check if the folder exists ,if not exists create it
		File tsfolder = new File(tsFolderName);
		if (!tsfolder.exists()) {
			tsfolder.mkdir();
		}
		sb.append(tsFolderName + File.separator + segmentName);
		sb.append(" ");
		sb.append(BaseCommandOption.M3U8PATH);
		sb.append(" ");
		File m3u8folder = new File(m3u8FolderName);
		if (!m3u8folder.exists()) {
			m3u8folder.mkdir();
		}
		sb.append(m3u8FolderName + File.separator + segmentName + ".m3u8");
		sb.append(" ");
		sb.append(BaseCommandOption.URLPREFIX);
		sb.append(" ");
		// 暂时写死
		sb.append("http://192.168.2.126:8990");
		// System.out.println(sb.toString());

		// mac下 bash 无效。。。
		if (System.getProperties().getProperty("os.name").equals("Mac OS X"))
			args.add("/bin/zsh");
		else
			args.add("/bin/bash");
		args.add("-c");
		args.add(sb.toString());
		runPorcess(args.toArray(new String[args.size()]),
				new DefaultProcessCallbackHandler());
	}

	public static void runPorcess(String[] commandParamsArray,
			ProcessCallbackHandler handler) throws Exception {

		// ExecuteWatchdog watchdog = null;
		if (isRunning == false) {
			try {
				if (log.isDebugEnabled()) {
					log.debug("start to run ffmpeg process...cmd:{}",
							ArrayUtils.toString(commandParamsArray));
				}

				pro = Runtime.getRuntime().exec(commandParamsArray);
				// System.out.println("after exec : " + pro);
				// change status
				isRunning = true;
				if (null == handler) {
					handler = new DefaultProcessCallbackHandler();
				}
				log.info("inputStream" + handler.handle(pro.getInputStream()));
				// log.info("inputStream" +
				// handler.handle(pro.getOutputStream()));
				String result = null;
				try {
					result = handler.handle(pro.getErrorStream());
				} catch (Exception e) {
					log.error("errorStream {}", result, e);
				}

				// if (log.isDebugEnabled()) {
				// log.debug(result);
				// }
				// try {
				// int flag = pro.waitFor();
				// if (flag != 0) {
				// throw new
				// IllegalThreadStateException("process exit with error value :"
				// + flag);
				// }
				// } catch (InterruptedException e) {
				// log.error("wait for process finish error.", e);
				// }
			} finally {
				if (null != pro) {
					pro.destroy();
				}
			}
		} else {
			log.warn("ffmpeg is already running!!!");
		}
	}

	public static void killSubProcressAndStop() {
		if (null != pro) {
			log.info("ready to kill sub procress {}", pro);
			pro.destroy();
			isRunning = false;
		}
	}

	public static void main(String[] args) {
		// List<String> videos = new ArrayList<String>();
		// videos.add("D:\\Downloads\\source\\1.flv");
		// videos.add("D:\\Downloads\\source\\2.flv");
		// videos.add("D:\\Downloads\\source\\3.flv");
		// videos.add("D:\\Downloads\\source\\4.flv");
		// try {
		// //
		// FFmpegCommandRunner.transcodeToBitrate("D:\\Downloads\\source\\高品质沟通06.rm",
		// // "D:\\Downloads\\source\\22.mp3", "128");
		// System.out.println(FFmpegCommandRunner.transcode(videos,
		// "D:\\Downloads\\source\\", new String[] { "32",
		// "64", "128" }, null));
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
}
