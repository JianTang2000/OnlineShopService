package com.tang.billing.demoprovider.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.api.billing.LocalCacheService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.Json;
import com.tang.base.util.ValidateUtil;
import com.tang.param.billing.localcachequery.LocalCacheTreeChildrenParam;
import com.tang.param.billing.localcachequery.LocalCacheTreeRootParam;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/11 <br>
 */
//@RestController
public class LocalCacheController {

    /**
     * < >
     */
    @Autowired
    LocalCacheService localCacheService;

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(LocalCacheController.class);

    @PutMapping("localCache/getCacheInfo")
    public Map<String, Object> findAllEffKey() {
        logger.info("findAllEffKey start.");
        List<LocalCacheTreeRootParam> result = new ArrayList<>();
        result = localCacheService.getCacheInfo();
        logger.info("result is {}", BaseCommonUtil.objectToJsonString(result));
        if (ValidateUtil.validateNotEmpty(result)) {
            return Json.success(result);
        }
        else {
            return Json.fail(result);
        }

    }

    @PutMapping("localCache/handleRefresh")
    public Map<String, Object> handleRefresh(LocalCacheTreeChildrenParam param) {
        logger.info("Reload cache start. beanMapName is {}, keyLevel1 is {}, keyLevel2 is {}", param.getBeanMapName(), param.getParentLabel(),
            param.getLabel());
        localCacheService.ReloadByKeyLevel2(param);
        return Json.success(null);
    }

    @PutMapping("localCache/handleClean")
    public Map<String, Object> handleClean(LocalCacheTreeChildrenParam param) {
        logger.info("handleClean start. beanMapName is {}, keyLevel1 is {}, keyLevel2 is {}", param.getBeanMapName(), param.getParentLabel(),
            param.getLabel());
        localCacheService.handleClean(param);
        return Json.success(null);
    }

    @PutMapping("localCache/handleSave")
    public Map<String, Object> handleSave(LocalCacheTreeChildrenParam param) {
        logger.info("handleClean start. beanMapName is {}, keyLevel1 is {}, keyLevel2 is {}", param.getBeanMapName(), param.getParentLabel(),
            param.getLabel());
        localCacheService.handleSave(param);
        return Json.success(null);
    }
}
