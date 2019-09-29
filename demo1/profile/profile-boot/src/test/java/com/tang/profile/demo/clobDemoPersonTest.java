package com.tang.profile.demo;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tang.api.billing.DemoPersonService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.param.billing.DemoPersonParam;
import com.tang.profile.demo.testBase.DemoProfileApplicationStartTests;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/26 <br>
 */
public class clobDemoPersonTest extends DemoProfileApplicationStartTests {
    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(clobDemoPersonTest.class);

    /**
     * < >
     */
    @Autowired
    private DemoPersonService demoPersonService;

    @Test
    public void opusQuery() throws Exception {
//        DemoPersonParam param = demoPersonService.queryPersonById(101L);
        List<DemoPersonParam> params = demoPersonService.queryAllPerson();
//        logger.info("param is {}", BaseCommonUtil.objectToJsonString(param));
        logger.info("params is {}", BaseCommonUtil.objectToJsonString(params));
    }
}
