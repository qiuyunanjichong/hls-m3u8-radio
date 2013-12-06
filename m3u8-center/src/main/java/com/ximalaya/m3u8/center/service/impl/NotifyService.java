/*
 * 文件名称: NotifyService.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.center.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.ximalaya.m3u8.common.service.INotifyService;

/**
 * 
 * @author caorong created on 2013-12-4
 * @since 1.0
 */
public class NotifyService implements INotifyService {

    @Value("${m3u8.center.upload.rootpath}")
    private String rootPath;
    
    @Override
    public Integer notify(String dirName, String segmentName) {
//        rootPath
        return null;
    }

}
