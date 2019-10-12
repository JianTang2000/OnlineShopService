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
     * @param param 可空，空返回gust用戶，否則根據ID返回具體用戶
     * @return 用戶信息，包含用戶和用戶detail信息
     */
    @PutMapping("mainView/getUserInfo")
    public Map<String, Object> getUserInfo(UserInfoParam param) {
        logger.info("getUserInfo start, param is {}", BaseCommonUtil.objectToJsonString(param));
        UserInfoParam result = demoUserService.getUserInfo(param);
        logger.info("getUserInfo finished.");
        if (StringUtils.isEmpty(result.getHandleSignInErrRet())) {
            return Json.success(result);
        } else {
            return Json.fail(result);
        }

    }

    @PutMapping("mainView/handleFindUserInfo")
    public Map<String, Object> handleFindUserInfo(UserInfoParam param) {
        logger.info("handleFindUserInfo start, param is {}", BaseCommonUtil.objectToJsonString(param));
        UserInfoParam result = demoUserService.handleFindUserInfo(param);
        logger.info("result is {}", BaseCommonUtil.objectToJsonString(result));
        if (StringUtils.isEmpty(result.getHandleFindUserInfoErrRet())) {
            return Json.success(result);
        } else {
            return Json.fail(result);
        }
    }

    @PutMapping("mainView/handleEditProfile")
    public Map<String, Object> handleEditProfile(UserInfoParam param) {
        logger.info("handleEditProfile start, param is {}", BaseCommonUtil.objectToJsonString(param));
        UserInfoParam result = demoUserService.handleEditProfile(param);
        return Json.success(result);
    }
}
