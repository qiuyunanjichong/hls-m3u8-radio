/*
 * 文件名称: Main.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.node;

import java.io.IOException;

import com.ximalaya.m3u8.common.Constant;
import com.ximalaya.m3u8.node.encode.FFmpegCommandRunner;

/**
 * 
 * @author caorong created on 2013-9-3
 * @since 1.0
 */
public class Main {

	public static String m3u8Dir = Constant.BB_m3u8_Dir;

	public static String tsDir = Constant.BB_ts_Dir;

	public static String segmentName = Constant.BB_segmentName;

	public static void main(String[] args) throws IOException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					FFmpegCommandRunner.generateAudioSegment(
							"mmsh://simuledge.shibapon.net/BAN-BAN_Radio", 7,
							tsDir, m3u8Dir, segmentName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "m3u8-worker");
		thread.start();

		// check if worker exists
		System.out.println(" check exists : " + FFmpegCommandRunner.isRunning);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Existing and start to kill ffmpeg!!!");
				FFmpegCommandRunner.killSubProcressAndStop();
			}
		}));

		try {
			Thread.sleep(1000 * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// send shutdown signal!!!
//		System.exit(0);
		
		// kill stop the subprocess and destory the thread
		// FFmpegCommandRunner.killSubProcressAndStop();
		// System.out.println("kill sub procress succ");
		//
		// try {
		// Thread.sleep(1000 * 5);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// System.out.println("start again!!!");
		// // start again
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// try {
		// FFmpegCommandRunner.generateAudioSegment("mmsh://simuledge.shibapon.net/BAN-BAN_Radio",
		// 7, tsDir,
		// m3u8Dir, segmentName);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }, "m3u8-worker").start();
		// try {
		// Thread.sleep(8 * 1000L);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// FFmpegCommandRunner.killSubProcressAndStop();
		// System.out.println("kill sub procress succ");
	}
}