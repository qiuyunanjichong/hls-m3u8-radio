/*
 * 文件名称: SegmentService.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.center.service.mongo;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.ximalaya.m3u8.common.model.Segment;

/**
 * 
 * @author caorong created on 2013-12-9
 * @since 1.0
 */
@Service
public class SegmentService {

    @Autowired
    private MongoOperations mongoTemplate;

    public void insertSegment(Segment segment) {
        System.out.println(segment);
        mongoTemplate.insert(segment);
    }
}