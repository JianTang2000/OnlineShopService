package com.tang.billing.demoprovider.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.api.billing.DemoUserService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.Json;
import com.tang.param.billing.UserInfoParam;

@RestController
public class DemoUserController {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoUserController.class);

    @Autowired
    DemoUserService demoUserService;

    /**
     * < 查询用户资料 > <br>
     *
     * @auther: tang
     * @param param < 前台搜索请求的控制层 >
     * @return < >
     */
    @PutMapping("mainView/getUserInfo")
    public Map<String, Object> getUserInfo(UserInfoParam param) {
        logger.info("getUserInfo start, param is {}", BaseCommonUtil.objectToJsonString(param));
        UserInfoParam result = demoUserService.getUserInfo(param);
        logger.info("result is {}", BaseCommonUtil.objectToJsonString(result));
        if (StringUtils.isEmpty(result.getHandleSignInErrRet())) {
            return Json.success(result);
        }
        else {
            return Json.fail(result);
        }

    }

    /**
     * < 找回用户资料 > <br>
     *
     * @auther: tang
     * @param param < 前台搜索请求的控制层 >
     * @return < >
     */
    @PutMapping("mainView/handleFindUserInfo")
    public Map<String, Object> handleFindUserInfo(UserInfoParam param) {
        logger.info("handleFindUserInfo start, param is {}", BaseCommonUtil.objectToJsonString(param));
        UserInfoParam result = demoUserService.handleFindUserInfo(param);
        logger.info("result is {}", BaseCommonUtil.objectToJsonString(result));
        if (StringUtils.isEmpty(result.getHandleFindUserInfoErrRet())) {
            return Json.success(result);
        }
        else {
            return Json.fail(result);
        }
    }

    /**
     * < 修改用户资料，目前只支持修改密码，后续会增加一些其他的属性 > <br>
     *
     * @auther: tang
     * @param param < 前台搜索请求的控制层 >
     * @return < >
     */
    @PutMapping("mainView/handleEditProfile")
    public Map<String, Object> handleEditProfile(UserInfoParam param) {
        logger.info("handleEditProfile start, param is {}", BaseCommonUtil.objectToJsonString(param));
        UserInfoParam result = demoUserService.handleEditProfile(param);
        return Json.success(result);
    }
}
