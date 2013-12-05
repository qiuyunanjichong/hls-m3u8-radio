package com.ximalaya.m3u8.center.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.nali.log.MessageLogger;
//import com.nali.log.impl.LoggerFactory;
//import com.ximalaya.crawler.center.conf.CenterContext;
//import com.ximalaya.crawler.center.conf.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static Logger log = LoggerFactory.getLogger(UploadServlet.class);

//    private static final MessageLogger log = LoggerFactory.getLogger(UploadServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        BufferedOutputStream bos = null;
        String albumName = request.getParameter("tsdir");
        String audioName = request.getParameter("tsname");
        File newFile = null;
//        Configuration config = CenterContext.getBean("config", Configuration.class);
        StringBuffer dirPath = new StringBuffer(config.getRootAudioPath());
        dirPath.append(File.separator).append(albumName).append(File.separator).append(audioName);
        File dir = new File(dirPath.toString());
        try {
            dir.mkdirs();
            newFile = new File(dirPath.toString() + File.separator + fileName);
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
        } catch (Throwable t) {
            log.error("upload file error, file title is {0}", t, fileName);
            response.getWriter().write("ERROR");
        } finally {
            if (bos != null)
                bos.close();
        }
        if (newFile != null) {
            response.setContentType("text/plain;charset=utf-8");
            response.getOutputStream().write(newFile.getAbsolutePath().getBytes());
        }
    }
}
