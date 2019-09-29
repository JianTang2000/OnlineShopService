package com.tang.profile.demoprovider.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tang.api.billing.HelloServiceProf;
import com.tang.base.util.sequenceutil.SequenceUtil;

/**
 * < >
 */
@Service("HelloServiceProf")
public class HelloServiceProfImpl implements HelloServiceProf {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(HelloServiceProfImpl.class);

    @Override
    public String sayHello(String name) {
        String showInfo =
            "========================================================\n" +
                "center    :profile\n" +
                "type      :Spring service\n" +
                "package   :com.tang.profile.demoprovider.serviceimpl.HelloServiceProfImpl\n" +
                "function  :sayHello\n" +
                "desc      :show parameter only\n" +
                "info      :" + name+"\n" +
                "========================================================";
        logger.info(showInfo);
        return showInfo;
    }

    @Override public Long getSeq(String name) {
        return SequenceUtil.next(name);
    }

}