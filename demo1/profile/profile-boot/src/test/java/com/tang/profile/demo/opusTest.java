package com.tang.profile.demo;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tang.api.billing.OpusDetailService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.param.billing.DemoOpusDetailParam;
import com.tang.profile.demo.testBase.DemoProfileApplicationStartTests;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/29 <br>
 */
public class opusTest extends DemoProfileApplicationStartTests {
    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(opusTest.class);

    /**
     * < >
     */
    @Autowired
    private OpusDetailService opusDetailService;

//     @Test
//    public void opus() throws Exception {
//        String filePath = "C:\\home\\demo\\music\\douwei\\窦唯 - 十一庆1995.mp3";
//        logger.info("------------opus start----------------");
//        logger.info("filePath is:{}", filePath);
//        opusDetailService.newOpusDetail(filePath);
//    }

    @Test
    public void opusQuery() throws Exception {
        logger.info("------------opusQuery start----------------");
        List<DemoOpusDetailParam> ret = opusDetailService.queryAllOpusDetail();
        logger.info("opusQuery ret is {}", BaseCommonUtil.objectToJsonString(ret.get(0).getOpusDetailId()));
    }

}
