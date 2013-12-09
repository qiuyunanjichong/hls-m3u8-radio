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

    private String name;

    private String dir;

    private Long timestamp;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Segment [id=").append(id).append(", name=").append(name).append(", dir=").append(dir)
                .append(", timestamp=").append(timestamp).append("]");
        return builder.toString();
    }

    public Segment(String name, String dir, Long timestamp) {
        super();
        this.name = name;
        this.dir = dir;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
