/*
 * 文件名称: Segment.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.common.model;

/**
 * 
 * @author caorong created on 2013-12-9
 * @since 1.0
 */
public class Segment {

    private String id;

    private String radioname;

    private Long index;

    private String abspath;

    private Long timestamp;

    public Segment(String radioname, Long index, String abspath, Long timestamp) {
        super();
        this.radioname = radioname;
        this.index = index;
        this.abspath = abspath;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRadioname() {
        return radioname;
    }

    public void setRadioname(String radioname) {
        this.radioname = radioname;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getAbspath() {
        return abspath;
    }

    public void setAbspath(String abspath) {
        this.abspath = abspath;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
