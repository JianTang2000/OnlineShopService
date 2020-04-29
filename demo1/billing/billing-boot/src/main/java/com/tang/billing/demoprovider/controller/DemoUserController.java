package com.tang.billing.demoprovider.controller;

import java.util.Map;

import com.tang.param.billing.operateUserParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.api.billing.DemoUserService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.Json;
import com.tang.param.billing.UserInfoParam;

/**
 *  tang
 */
@RestController
public class DemoUserController {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoUserController.class);

    @Autowired
    DemoUserService demoUserService;


    //给postman的测试接口
    @GetMapping("mainView/testAlive")
    public Map<String, Object> testAlive() {
        logger.info("testAlive start");
        demoUserService.testAlive();
        return Json.success("it is alive!");
    }

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

    /**
     * @param param 不可空 根據前臺輸入查找用戶信息
     * @return 用戶信息
     */
    @PutMapping("mainView/handleFindUserInfo")
    public Map<String, Object> handleFindUserInfo(UserInfoParam param) {
        logger.info("handleFindUserInfo start, param is {}", BaseCommonUtil.objectToJsonString(param));
        UserInfoParam result = demoUserService.handleFindUserInfo(param);
        logger.info("handleFindUserInfo finished");
        if (StringUtils.isEmpty(result.getHandleFindUserInfoErrRet())) {
            return Json.success(result);
        } else {
            return Json.fail(result);
        }
    }

    /**
     * @param param 不可空，根據前臺輸入修改用戶資料,可改的包括密碼，郵箱，電話，頭像
     * @return success or not
     */
    @PutMapping("mainView/handleEditProfile")
    public Map<String, Object> handleEditBaseProfile(UserInfoParam param) {
        logger.info("handleEditProfile start, param is {}", BaseCommonUtil.objectToJsonString(param));
        UserInfoParam result = demoUserService.handleEditProfile(param);
        return Json.success(result);
    }

    //注册
    @PutMapping("mainView/signUp")
    public Map<String, Object> signUp(operateUserParam param) {
        logger.info("signUp start, param is {}", BaseCommonUtil.objectToJsonString(param));
        operateUserParam result = demoUserService.signUp(param);
        return Json.success(result);
    }

}
