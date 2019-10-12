package com.tang.billing.demoprovider.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.api.billing.DemoResourceService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.Json;
import com.tang.param.billing.HandleOperateParam;
import com.tang.param.billing.ResourceDetailParam;

/**
 *
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
     * < 每一个资源点开都是一个单独页，调用这个接口根据Id查询ResourceDetail > <br>
     *
     * @auther: tang
     */
    @GetMapping("mainView/getResourceDetail/{id}")
    public Map<String, Object> getResourceDetail(@PathVariable Long id) {
        ResourceDetailParam param = null;
        if (param != null) {
            return Json.success(param);
        }
        else {
            return Json.fail();
        }
    }



    /**
     * < 下载操作请求 > <br>
     *
     * @auther: tang
     * @param param < 操作数据 >
     * @return < >
     */
    @PutMapping("mainView/handleDownload")
    public Map<String, Object> handleDownload(HandleOperateParam param) {
        logger.info("handleDownload start, param is {}", BaseCommonUtil.objectToJsonString(param));
        return Json.success(null);
    }

}
