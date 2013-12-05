/*
 * 文件名称: Uploader.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.node.upload;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

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
    public void afterPropertiesSet() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        File tsDir = new File(Constant.BB_ts_Dir);
                        File[] sortedFiles = sortedFiles(tsDir);
//                        for (File f : sortedFiles) {
//                            System.out.println(f.lastModified());
//                            System.out.println(f);
//                        }
                        // only upload old file
                        for (int i = 0; i < sortedFiles.length - 1; i++) {
                            // upload f

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
//                System.out.println(sortedFiles[i]);
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
