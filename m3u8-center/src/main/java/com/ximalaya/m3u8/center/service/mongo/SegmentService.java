/*
 * 文件名称: SegmentService.java  Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.m3u8.center.service.mongo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ximalaya.m3u8.common.model.Segment;

/**
 * 
 * @author caorong created on 2013-12-9
 * @since 1.0
 */
@Service
public class SegmentService {

    private final static Logger log = LoggerFactory.getLogger(SegmentService.class);

    @Autowired
    private MongoOperations mongoTemplate;

    public void insertSegment(Segment segment) {
        log.debug("insert {}", segment);
        mongoTemplate.insert(segment);
    }

    public Long getLatestSegmentIndex() {
        // used for 数据聚合
//        Aggregation agg = newAggregation(
//        sort(Direction.DESC, "timestamp"));
//
//        AggregationResults<TagCount> results = mongoTemplate.aggregate(agg, "tags", TagCount.class);
//         mongoTemplate.aggregate(Aggregation.newAggregation(sort(Direction.DESC, "timestamp")), Segment.class, Segment.class);

//        mongoTemplate.find(new Query().with(new Sort(Direction.DESC, "timestamp")), Segment.class);
        List<Segment> segments = mongoTemplate.find(
                new Query().with(new PageRequest(0, 1, new Sort(Direction.DESC, "timestamp"))), Segment.class);
        if (segments != null && segments.size() > 0) {
            return segments.get(0).getIndex() + 1;
        } else {
            // means no data in it... 
            return 0L;
        }
    }
}