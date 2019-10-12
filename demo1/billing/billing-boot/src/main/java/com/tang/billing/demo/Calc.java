package com.tang.billing.demo;

import com.tang.base.util.BaseCommonUtil;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoUserDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoUserDto;
import com.tang.param.billing.UserInfoParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Random;

public class Calc {
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static void main(String[] args) {

        DemoUserDto userDto = new DemoUserDto();
        userDto.setUserId(1L);
        userDto.setEmail("123@163.com");
        DemoUserDetailDto detailDto = new DemoUserDetailDto();
        detailDto.setUserId(2L);
        detailDto.setAddress1Line1("222222");
        UserInfoParam retParam = new UserInfoParam();
        BeanUtils.copyProperties(userDto, retParam, UserInfoParam.class);
        BeanUtils.copyProperties(detailDto, retParam, UserInfoParam.class);
        System.out.println("sum " + BaseCommonUtil.objectToJsonString(retParam));


    }
}
