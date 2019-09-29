package com.tang.profile.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tang.api.billing.LocalCacheService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.ValidateUtil;
import com.tang.param.billing.localcachequery.LocalCacheTreeRootParam;
import com.tang.param.billing.DemoPersonParam;
import com.tang.profile.demo.testBase.DemoProfileApplicationStartTests;
import com.tang.profile.demoprovider.localcache.repository.IDemoPersonRepository;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/12 <br>
 */
public class localCacheQueryTest extends DemoProfileApplicationStartTests {

    private Logger logger = LoggerFactory.getLogger(localCacheQueryTest.class);

    @Autowired
    LocalCacheService localCacheService;

    @Autowired
    IDemoPersonRepository demoPersonRepository;

    // @Test
    public void ApiTest() throws Exception {
        logger.info("------------local cache test----------------");
        // 先裸查内存，发现二级节点都是空的;然后裸查二级key,发现查询为空，这时程序会自动reload;然后查内存，发现二级key已经被填充
        List<LocalCacheTreeRootParam> ret = localCacheService.getCacheInfo();
        logger.info("先裸查内存，发现二级节点都是空的ret is :{}", BaseCommonUtil.objectToJsonString(ret));
        List<DemoPersonParam> demoPersonParamList = demoPersonRepository.getAllPerson();
        if (ValidateUtil.validateNotEmpty(demoPersonParamList)) {
            List<LocalCacheTreeRootParam> ret2 = localCacheService.getCacheInfo();
            logger.info("再查内存，发现二级key已经被填充ret is :{}", BaseCommonUtil.objectToJsonString(ret2));
        }

    }

    @Test
    public void ruaTest() throws Exception {
        logger.info("------------ruaTest ruaTest test----------------");
        DemoPersonParam demoPersonParam = new DemoPersonParam();
        DemoPersonParam demoPersonParam2 = new DemoPersonParam();
        demoPersonParam.setPersonId(1L);
        demoPersonParam.setPersonName("name1");
        demoPersonParam.setDesc("--按作者检索，\n" + "--u检索，");
        demoPersonParam2.setPersonId(2L);
        demoPersonParam2.setPersonName("name2");
        demoPersonParam2.setDesc("暂无说明");
        List<DemoPersonParam> demoPersonParamList = new ArrayList<>();
        demoPersonParamList.add(demoPersonParam);
        demoPersonParamList.add(demoPersonParam2);
        String cacheValue = BaseCommonUtil.objectToJsonString(demoPersonParamList);
        logger.info("demoPersonParamList is {}", cacheValue);
        cacheValue.replaceAll("\\s*--\\s*", "rua");
        logger.info("after change, demoPersonParamList is {}", cacheValue);

        String makeUpString = "[{\"personId\":9999,\"personName\":\"bababababba\",\"opusId\":null,\"personPhotoId\":null,\"personMvId\":null,\"desc\":\"--按作者检索，\\n--u检索，\",\"state\":null,\"stateDate\":null},{\"personId\":2,\"personName\":\"name2\",\"opusId\":null,\"personPhotoId\":null,\"personMvId\":null,\"desc\":\"暂无说明\",\"state\":null,\"stateDate\":null}]";

        List<DemoPersonParam> demoPersonParamListNew = BaseCommonUtil.StringToObject(makeUpString, demoPersonParamList.getClass());
        logger.info("after Rechange, demoPersonParamList is {}", demoPersonParamListNew);

    }

}
