/*
 * 文件名称: Uploader.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.node.upload;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ximalaya.m3u8.common.Constant;
import com.ximalaya.m3u8.common.util.BlockingThreadPoolExecutor;

/**
 * find *.ts and upload it automaticly
 * 
 * @author caorong created on 2013-12-4
 * @since 1.0
 */
@Component
public class Uploader implements InitializingBean {

    private final static Logger log = LoggerFactory.getLogger(Uploader.class);

    private ExecutorService executorService = new BlockingThreadPoolExecutor(1, 20, new ArrayBlockingQueue<Runnable>(
            200));

    @Value("${m3u8.center.upload.url}")
    private String uploadAddress;

    @Autowired
    private HttpUpload httpUpload;

    @Override
    public void afterPropertiesSet() {

        // .append(audio.getAlbumId()).append("&audio=")
        // .append(audio.getId()).append("&file=");

        new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    try {
                        File tsDir = new File(Constant.BB_ts_Dir);
                        File[] sortedFiles = sortedFiles(tsDir);
                        for (File f : sortedFiles) {
//                         System.out.println(f.lastModified());
                            System.out.println(f);
                        }
                        // only upload old file
                        for (int i = 0; i < sortedFiles.length - 1; i++) {
                            // change name and 组装 url
//                            File oldf = sortedFiles[i];
//							File newf = 
//							tmpf.renameTo( )
                            // servlet
//                            StringBuffer url = new StringBuffer().append("http://").append(uploadAddress)
//                                    .append("/m3u8-center/upload.do?tsdir=").append(Constant.BB_ts_Dir)
//                                    .append("&tsname=").append(sortedFiles[i].getName());
                            // controller 
                            StringBuffer url = new StringBuffer().append("http://").append(uploadAddress)
                                    .append("/m3u8-center/upload/tsdir/").append(Constant.BB_ts_Dir).append("/tsname/")
                                    .append(sortedFiles[i].getName());
                            // upload f
                            upload(url.toString(), sortedFiles[i]);
                        }
                        // sleep
                        Thread.sleep(1000 * 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, ".ts-uploader").start();
    }

    public void upload(final String url, final File f) {

        executorService.submit(new Runnable() {

            @Override
            public void run() {
                // System.out.println(f);
                try {
                    log.debug("url {}", url);
                    log.info("upload file [{}] status = {}", f.getName(), httpUpload.upload(url, f));
                } catch (Exception e) {
                    log.error("upload error !!!", e);
                }
            }
        });
    }

    /**
     * get sorted dir array sorted by lastModified time
     * 
     * @param tsDir
     * @return
     */
    public File[] sortedFiles(File tsDir) {
        File[] oriFiles = tsDir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".ts"))
                    return true;
                return false;
            }
        });
        File[] sortedFiles = new File[oriFiles.length];
        for (File f : oriFiles) {
            for (int i = 0; i < oriFiles.length; i++) {
                // System.out.println(sortedFiles[i]);
                if (sortedFiles[i] != null) {
                    if (f.lastModified() < sortedFiles[i].lastModified()) {
                        // 后移
                        for (int j = oriFiles.length - 1; j > i; j--) {
                            sortedFiles[j] = sortedFiles[j - 1];
                        }
                        sortedFiles[i] = f;
                        break;
                    }
                } else {
                    sortedFiles[i] = f;
                    break;
                }
            }
        }
        return sortedFiles;
    }

    public static void main(String[] args) {
        new Uploader().afterPropertiesSet();
    }
}
