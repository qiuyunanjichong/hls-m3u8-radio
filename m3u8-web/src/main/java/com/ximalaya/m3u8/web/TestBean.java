/*
 * 文件名称: TestBean.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.web;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 
 * @author caorong created on 2013-12-12
 * @since 1.0
 */
@Component
public class TestBean {

    private Long now = null;

    public Long getNow() {
        if (now == null) {
            now = new Date().getTime();
        }
        return now;
    }
}
