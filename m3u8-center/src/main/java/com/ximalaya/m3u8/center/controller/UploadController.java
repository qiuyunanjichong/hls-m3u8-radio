/*
 * 文件名称: UploadController.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.center.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ximalaya.m3u8.center.TestBean;

/**
 * node upload *.ts to center by HTTP-POST
 * @author caorong created on 2013-12-6
 * @since 1.0
 */
@Controller
public class UploadController {

    private final static Logger log = LoggerFactory.getLogger(UploadController.class);

    @Value("${m3u8.center.upload.rootpath}")
    private String rootPath;

    @Autowired
    private TestBean test;

    @RequestMapping(value = "/upload/tsdir/{tsdir}/tsname/{tsname}", method = RequestMethod.POST)
    @ResponseBody
    public String uploadts(HttpServletRequest request, @PathVariable
    String tsdir, @PathVariable
    String tsname) {
        // because of spring restful's xxx
        tsname += ".ts";
        File newFile = null;
        BufferedOutputStream bos = null;
        log.info("-------->" + tsdir + "------" + tsname + "--------" + rootPath);
//        Configuration config = CenterContext.getBean("config", Configuration.class);
        StringBuffer dirPath = new StringBuffer(rootPath);
//        StringBuffer dirPath = new StringBuffer(config.getRootAudioPath());
        dirPath.append(File.separator).append(tsdir);
        File dir = new File(dirPath.toString());
//        File dir = new File(dirName /* + File.separator + tsName */);
        try {
            dir.mkdirs();
//            newFile = new File(dirPath.toString() + File.separator + fileName);
            newFile = new File(rootPath + File.separator + tsdir + File.separator + tsname);
            newFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(newFile);
            bos = new BufferedOutputStream(fos);
            InputStream in = request.getInputStream();
            int length = 0;
            byte[] b = new byte[8192];
            while ((length = in.read(b)) != -1) {
                bos.write(b, 0, length);
            }
            bos.flush();
        } catch (Exception e) {
            log.error("upload file error, file title is {0}", tsname, e);
            return "ERROR";
        } finally {
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    log.error("bos close error ", e);
                }
        }
        return tsdir;
    }
}
