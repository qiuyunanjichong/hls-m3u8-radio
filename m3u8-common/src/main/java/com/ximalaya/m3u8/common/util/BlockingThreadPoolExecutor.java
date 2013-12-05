/**
 * 
 */
package com.ximalaya.m3u8.common.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author caorong
 * 
 */
public class BlockingThreadPoolExecutor extends ThreadPoolExecutor {
	private static final ArrayBlockingQueue<Runnable> DEFAULT_QUQUE = new ArrayBlockingQueue<Runnable>(
			1);

	public BlockingThreadPoolExecutor(int corePoolSize) {
		this(corePoolSize,corePoolSize, DEFAULT_QUQUE);
	}

	public BlockingThreadPoolExecutor(int corePoolSize, int maxPoolSize,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, corePoolSize, 0, TimeUnit.SECONDS, workQueue,
				defaultHandler);
	}

	public BlockingThreadPoolExecutor(int corePoolSize, int maxPoolSize,
			String poolName, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, corePoolSize, 0, TimeUnit.SECONDS, workQueue,
				defaultHandler);
	}

	private static final RejectedExecutionHandler defaultHandler = new BlockingRejectHandler();

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
	}

	@Override
	protected void terminated() {
	}

	private static class BlockingRejectHandler implements
			RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
