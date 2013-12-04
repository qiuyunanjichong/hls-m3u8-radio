/*
 * 文件名称: Uploader.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.node.upload;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ximalaya.m3u8.common.Constant;

/**
 * find *.ts and upload it automaticly
 * @author caorong created on 2013-12-4
 * @since 1.0
 */
@Component
public class Uploader implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000 * Constant.SEGMENT_SIZE - 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, ".ts-uploader").start();
    }

}
