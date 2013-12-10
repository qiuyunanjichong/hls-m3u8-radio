/*
 * 文件名称: NotifyService.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.center.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger log = LoggerFactory.getLogger(NotifyService.class);

    @Value("${m3u8.center.upload.rootpath}")
    private String rootPath;

    @Value("${m3u8.center.upload.nginxpath}")
    private String nginxPath;

    @Autowired
    private SegmentService segmentService;

    @Override
    public Integer notify(String dirName, String segmentName) {
        log.debug("notify dir = {} , segment = {}", dirName, segmentName);
        File srcf = new File(rootPath + File.separator + dirName + File.separator + segmentName);

        File destDir = new File(nginxPath + File.separator + dirName);
//        System.out.println(f.exists());
        if (srcf.exists()) {
            // get the index (for simple get it from mongo)
            Long index = segmentService.getLatestSegmentIndex();
            System.out.println(index);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            File destf = new File(destDir.getAbsolutePath() + File.separator + index + ".ts");

            // move the file or upload file to cnd or upaiclould and change name
            try {
//                FileUtils.mo.moveFileToDirectory(f, destDir, true);
                if (destf.exists()) {
                    FileUtils.deleteQuietly(destf);
                }
                FileUtils.moveFile(srcf, destf);

                // write to db
                segmentService
                        .insertSegment(new Segment(dirName, index, destf.getAbsolutePath(), new Date().getTime()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            return 1;
        }
        log.error("file [{}] not exists !!!", srcf);
        return 0;
    }
}
