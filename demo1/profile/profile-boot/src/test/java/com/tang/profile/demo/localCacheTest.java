package com.tang.profile.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tang.base.util.ApplicationContextHolder;
import com.tang.base.util.BaseCommonUtil;
import com.tang.param.billing.DemoPersonParam;
import com.tang.profile.demo.testBase.DemoProfileApplicationStartTests;
import com.tang.profile.demoprovider.localcache.repository.IDemoPersonRepository;

/**
 * < 这时localCache的测试类，测试了缓存机制的可用性，以及一些缓存框架接口是否可以正确使用 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/24 <br>
 */
public class localCacheTest extends DemoProfileApplicationStartTests {

    private Logger logger = LoggerFactory.getLogger(localCacheTest.class);

     @Test
    public void ApiTest() throws Exception {
        logger.info("------------local cache Api call test----------------");
        logger.info("1 ");


    }

//    @Test
    public void threadsTest() throws InterruptedException {
        logger.info("************getDataFromCacheTest************");
        IDemoPersonRepository iDemoPersonRepository = (IDemoPersonRepository) ApplicationContextHolder.getBeanByType(IDemoPersonRepository.class);
        List<Thread> threadGroup1 = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            localCacheTest.MyThread myThread = new localCacheTest.MyThread("myThread" + String.valueOf(i), i, iDemoPersonRepository);
            threadGroup1.add(myThread);
        }
        for (Thread thread : threadGroup1) {
            thread.start();
        }
        // DENG
        // 休眠
        Thread.sleep(100);
        // TING
        for (Thread thread : threadGroup1) {
            thread.join();
        }
    }

    public class MyThread extends Thread {
        private int i;

        private IDemoPersonRepository iDemoPersonRepository;

        public MyThread(String nameOfParam, int i, IDemoPersonRepository iDemoPersonRepository) {
            super(nameOfParam);
            this.i = i;
            this.iDemoPersonRepository = iDemoPersonRepository;
        }

        @Override
        public void run() {
            logger.info("this is thread : {}, hashCode of IDemoPersonRepository is {}", i, iDemoPersonRepository.hashCode());
            DemoPersonParam demoPersonParam = iDemoPersonRepository.getByPersonId(102L);
            List<DemoPersonParam> demoPersonParams = iDemoPersonRepository.getAllPerson();
            logger.info("thread : {}, demoPersonParam is{}", i, BaseCommonUtil.objectToJsonString(demoPersonParam));
            logger.info("thread : {}, demoPersonParams is{}", i, BaseCommonUtil.objectToJsonString(demoPersonParams));
        }
    }
}
