package com.tang.billing.demoprovider.controller;

import java.util.List;
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
import com.tang.param.billing.GetDataInputParam;
import com.tang.param.billing.GetDataResultParam;
import com.tang.param.billing.HandleOperateParam;
import com.tang.param.billing.ResourceDetailParam;

/**
 * < 控制器，写在billing侧，billing正好依赖了api 和 base，这样有一个固定的端口，同时支持分布式多实例 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/12 <br>
 */
// @controller注解中，返回的是字符串，或者是字符串匹配的模板名称，，与html页面配合使用的，在这种情况下，前后端的配合要求比较高;
// @RestController，返回的是一个对象，在没有页面的情况下，也能看到返回的是一个user对象对应的json字符串，java后端的代码比较自由。
// 在日常的工作中，我们往往需要给前端（WEB端、IOS、Android）或者第三方提供接口，这个时候我们就需要给他们提供一份详细的API说明文档。
// 但维护一份详细的文档可不是一件简单的事情。首先，编写一份详细的文档本身就是一件很费时费力的事情，
// 另一方面，由于代码和文档是分离的，所以很容易导致文档和代码的不一致。这篇文章我们就来分享一种API文档维护的方式，即通过Swagger来自动生成Restuful API文档。
@RestController
public class ResourceController {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    DemoResourceService demoResourceService;

    /**
     * < 模糊查询,有很强的时间敏感要求 > <br>
     *
     * @auther: tang
     * @param param < 前台搜索请求的控制层 >
     * @return < >
     */
    @GetMapping("mainView/getDataByInput")
    public Map<String, Object> getDataByInput(GetDataInputParam param) {
        logger.info("getDataByInput start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<GetDataResultParam> result = demoResourceService.getData(param);
        logger.info("result is {}", BaseCommonUtil.objectToJsonString(result));
        return Json.success(result);
    }

    /**
     * < 每一个资源点开都是一个单独页，调用这个接口根据Id查询ResourceDetail > <br>
     *
     * @auther: tang
     * @param id < >
     * @return < >
     */
    @GetMapping("mainView/getResourceDetail/{id}")
    public Map<String, Object> getResourceDetail(@PathVariable Long id) {
        ResourceDetailParam param;
        param = demoResourceService.getResourceDetail(id);
        if (param != null) {
            return Json.success(param);
        }
        else {
            return Json.fail();
        }
    }

    /**
     * < 收藏操作请求 > <br>
     *
     * @auther: tang
     * @param param < 操作数据 >
     * @return < >
     */
    @PutMapping("mainView/handleLike")
    public Map<String, Object> handleLike(HandleOperateParam param) {
        logger.info("handleLike start, param is {}", BaseCommonUtil.objectToJsonString(param));
        return Json.success(null);
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
