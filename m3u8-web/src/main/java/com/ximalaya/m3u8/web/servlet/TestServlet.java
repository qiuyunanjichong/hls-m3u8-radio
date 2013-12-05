package com.ximalaya.m3u8.web.servlet;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static Logger log = LoggerFactory.getLogger(TestServlet.class);

//    private static final MessageLogger log = LoggerFactory.getLogger(UploadServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
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
        InetAddress addr = InetAddress.getLocalHost();
//        ip=addr.getHostAddress().toString;//获得本机IP
//        System.out.println(System.getProperties().getProperty("os.name").equals("Mac OS X"));
        StringBuffer sb = new StringBuffer();
        sb.append(addr.getHostAddress());
        sb.append("\n");
        sb.append(System.getProperty("os.name"));
        response.getOutputStream().write(sb.toString().getBytes());
    }
}
