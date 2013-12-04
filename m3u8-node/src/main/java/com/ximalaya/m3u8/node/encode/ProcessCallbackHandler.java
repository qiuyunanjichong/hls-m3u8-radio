/*
 * 文件名称: ProcessCallbackHandler.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.node.encode;

import java.io.InputStream;

/**
 * 
 * @author caorong created on 2013-12-4
 * @since 1.0
 */
public interface ProcessCallbackHandler {

    public String handle(InputStream errorStream) throws Exception;
}
