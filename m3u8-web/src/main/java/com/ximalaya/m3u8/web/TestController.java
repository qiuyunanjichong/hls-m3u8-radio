package com.ximalaya.m3u8.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.ximalaya.m3u8.common.model.Segment;

/**
 * 拼接 .m3u8 文件
 */
@Controller
public class TestController {

    private final static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestBean testBean;

    @Value("${m3u8.center.url}")
    private String url;

    @Autowired
    private MongoOperations mongoTemplate;

    @RequestMapping(value = "/test1/{mfile}")
    @ResponseBody
    public ResponseEntity<String> test(HttpServletRequest request, @PathVariable
    String mfile) {
        System.out.println(mfile);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "audio/x-mpegurl");
//        #EXTM3U
//        #EXT-X-TARGETDURATION:7
//        #EXTINF:7,
//        http://192.168.2.126:8990tmp/bbradio-1.ts
//        #EXTINF:7,
//        http://192.168.2.126:8990tmp/bbradio-2.ts
//        #EXTINF:7,
//        http://192.168.2.126:8990tmp/bbradio-3.ts
//        #EXT-X-ENDLIST

        // for test when serverOpen are the first
        Long dbfirst = 1386646241939L;
        Long timeStart = testBean.getNow();
        List<Segment> segments = mongoTemplate.find(
                new Query().with(new PageRequest(0, 1, new Sort(Direction.ASC, "timestamp"))), Segment.class);
        if (segments.size() < 1) {
            log.error("no data in mongo!!!");
        }
        dbfirst = segments.get(0).getTimestamp();

        Long getTime = dbfirst + new Date().getTime() - timeStart;
        mongoTemplate.find(
                query(where("timestamp").gte(getTime)).with(
                        new PageRequest(0, 1, new Sort(Direction.DESC, "timestamp"))), Segment.class);

        Long index = (new Date().getTime() / 1000 - timeStart) / 7;
        Long index2 = index + 1;
        Long index3 = index + 2;
        String bbradio = "http://" + url + "/bbradio-" + index + ".ts";
        String bbradio2 = "http://" + url + "/bbradio-" + index2 + ".ts";
        String bbradio3 = "http://" + url + "/bbradio-" + index3 + ".ts";
        String m3u8 = getM3u8(String.valueOf(index), bbradio, bbradio2, bbradio3);
        return new ResponseEntity<String>(m3u8, responseHeaders, HttpStatus.OK);
    }

    private String getM3u8(String seq, String s1, String s2, String s3) {
        StringBuilder sb = new StringBuilder();
        sb.append("#EXTM3U");
        sb.append("\r\n");
        sb.append("#EXT-X-TARGETDURATION:7");
        sb.append("\r\n");
        sb.append("#EXT-X-MEDIA-SEQUENCE:" + seq);
        sb.append("\r\n");
        sb.append("#EXTINF:7,");
        sb.append("\r\n");
        sb.append(s1);
        sb.append("\r\n");
        sb.append("#EXTINF:7,");
        sb.append("\r\n");
        sb.append(s2);
        sb.append("\r\n");
        sb.append("#EXTINF:7,");
        sb.append("\r\n");
        sb.append(s3);
        sb.append("\r\n");
        //加了这个就END
//        sb.append("#EXT-X-ENDLIST");
        System.out.println(sb.toString());
        return sb.toString();
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String start(HttpServletRequest request, Long startId, Long endId, @RequestParam(defaultValue = "false")
    Boolean isOverride) {
        if (null == startId || null == endId) {
            return "{\"msg\":\"params error\", \"ret\":false}";
        }
        return "{\"msg\":\"start successfully\", \"ret\":true}";
    }
}
