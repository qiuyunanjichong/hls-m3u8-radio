/*
 * 文件名称: NotifyService.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.center.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ximalaya.m3u8.center.service.mongo.SegmentService;
import com.ximalaya.m3u8.common.model.Segment;
import com.ximalaya.m3u8.common.service.INotifyService;

/**
 * 
 * @author caorong created on 2013-12-4
 * @since 1.0
 */
@Service
public class NotifyService implements INotifyService {

    @Value("${m3u8.center.upload.rootpath}")
    private String rootPath;

    @Autowired
    private SegmentService segmentService;

    @Override
    public Integer notify(String dirName, String segmentName) {
        System.out.println(dirName);
        System.out.println(segmentName);
        File f = new File(rootPath + File.separator + dirName + File.separator + segmentName);
        System.out.println(f.exists());
        // write to db
        segmentService.insertSegment(new Segment("na", "d", new Date().getTime()));
        return null;
    }
}
