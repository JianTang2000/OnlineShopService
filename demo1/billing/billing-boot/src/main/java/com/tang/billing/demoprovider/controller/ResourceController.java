package com.tang.billing.demoprovider.controller;

import java.util.List;
import java.util.Map;

import com.tang.param.billing.ResourceSearchInputParam;
import com.tang.param.billing.ResourceSearchResultParam;
import com.tang.param.billing.ResourceSearchResultSimpleParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tang.api.billing.DemoResourceService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.Json;

/**
 * @author tang.jian<br>
 */
@RestController
public class ResourceController {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    DemoResourceService demoResourceService;

    /**
     * @param param Search by the Input ,must be quick quick quick!
     * @return Search result
     */
    @GetMapping("mainView/getDataByInput")
    public Map<String, Object> getDataByInput(ResourceSearchInputParam param) {
        logger.info("getDataByInput start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.getData(param);
        logger.info("getDataByInput finished");
        return Json.success(result);
    }

    /**
     * @param id the id of the detail resource
     * @return the  resource detail
     */
    @GetMapping("mainView/getResourceDetail/{id}")
    public Map<String, Object> getResourceDetail(@PathVariable Long id) {
        logger.info("getResourceDetail start, id is {}", BaseCommonUtil.objectToJsonString(id));
        ResourceSearchResultParam param = demoResourceService.getResourceDetail(id);
        logger.info("getResourceDetail finished");
        if (param != null) {
            return Json.success(param);
        } else {
            return Json.fail();
        }
    }
}
