package com.tang.billing.demoprovider.serviceimpl;

import com.tang.api.billing.DemoUserService;
import com.tang.base.util.*;
import com.tang.base.util.sequenceutil.SequenceUtil;
import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.IDemoUserDAO;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoUserDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoUserDto;
import com.tang.constant.FindUserInfoDef;
import com.tang.param.billing.UserInfoParam;
import com.tang.param.billing.operateUserParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("DemoUserService")
public class DemoUserServiceImpl implements DemoUserService {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoUserServiceImpl.class);

    @Autowired
    IDemoUserDAO demoUserDAO;


    private static String userIdSEQ = "SEQ_USER_ID";
    private static String lostKeySEQ = "SEQ_LOST_KEY";

    static String photo_path = System.getenv("photo_path");
    static String pmml_path = System.getenv("pmml_path");
    static String dir = System.getProperty("user.dir");

//    inputStream = new FileInputStream(pmml_path + "/extraData/irisRpart.pmml");
//        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("\\extraData\\irisRpart.pmml");

    private String getPathHead() {
        if (!StringUtils.isEmpty(photo_path)) {
            logger.info("getPathHead find url is {}", photo_path);
            return photo_path;
        } else {
            if(!StringUtils.isEmpty(pmml_path)) {
                return pmml_path;
            }
            return dir;
        }
    }

    private boolean tested = false;

    @Override
    public void testAlive() {
        if(!tested) {
            DemoUserDetailDto detailDto1 = demoUserDAO.selectUserDetailById(1L); //admin
            DemoUserDetailDto detailDto2 = demoUserDAO.selectUserDetailById(2L); //gust
            if(null == detailDto1) {
                byte[] pic;
                try {
                    pic = FileHelper.readFileToByte(getPathHead() + "/extraData/T.jpg");
                } catch (IOException e) {
                    // C:/home/workspace/Demo2ShopService/trunk/demo1/billing/billing-boot/src/main/resources
                    logger.error("createDefaultUserDetail failed, please check url {}", getPathHead() + "/extraData/G.jpg");
                    return;
                }
                DemoUserDetailDto detailDto = new DemoUserDetailDto();
                detailDto.setUserId(1L);
                detailDto.setUserDetail(pic);
                detailDto.setAddress1Line1("default address!");
                detailDto.setAddress1Line2("default address!");
                detailDto.setAddress1PostCode("default address!");
                detailDto.setAddress2Line1("default address!");
                detailDto.setAddress2Line2("default address!");
                detailDto.setAddress2PostCode("default address!");
                detailDto.setAddress3Line1("default address!");
                detailDto.setAddress3Line2("default address!");
                detailDto.setAddress3PostCode("default address!");
                demoUserDAO.createUserDetail(detailDto);
            }
            if(null == detailDto2) {
                byte[] pic;
                try {
                    pic = FileHelper.readFileToByte(getPathHead() + "/extraData/J.jpg");
                } catch (IOException e) {
                    // C:/home/workspace/Demo2ShopService/trunk/demo1/billing/billing-boot/src/main/resources
                    logger.error("createDefaultUserDetail failed, please check url {}", getPathHead() + "/extraData/J.jpg");
                    return;
                }
                DemoUserDetailDto detailDto = new DemoUserDetailDto();
                detailDto.setUserId(2L);
                detailDto.setUserDetail(pic);
                detailDto.setAddress1Line1("default address!");
                detailDto.setAddress1Line2("default address!");
                detailDto.setAddress1PostCode("default address!");
                detailDto.setAddress2Line1("default address!");
                detailDto.setAddress2Line2("default address!");
                detailDto.setAddress2PostCode("default address!");
                detailDto.setAddress3Line1("default address!");
                detailDto.setAddress3Line2("default address!");
                detailDto.setAddress3PostCode("default address!");
                demoUserDAO.createUserDetail(detailDto);
            }
            tested = true;
        }
    }


    @Override
    public operateUserParam signUp(operateUserParam param) {
        String ip = param.getIp();
        CacheMap<Object, Object> ipCache = CacheMap.getDefault();
        //先看IP是否最近用过，没用过允许操作
        if(ipCache.get(ip) == null) {
            ipCache.put(ip, ip);
            operateUserParam ret = new operateUserParam();
            DemoUserDto dto1 = demoUserDAO.selectUserByName(param.getUserName());
            DemoUserDto dto2 = demoUserDAO.selectUserByeEmail(param.getEmail());
            DemoUserDto dto3 = demoUserDAO.selectUserByPhone(param.getPhoneNumber());
            if (dto1 == null && dto2 == null && dto3 == null) {
                Long userId = SequenceUtil.next(userIdSEQ);
                Long key = SequenceUtil.next(lostKeySEQ);
                Date now = DateUtil.getNowTimeDate();
                DemoUserDto dto = new DemoUserDto();
                dto.setUserId(userId);
                dto.setUserName(param.getUserName());
                dto.setEmail(param.getEmail());
                dto.setCreateDate(now);
                dto.setStateDate(now);
                dto.setState("A");
                dto.setLostFoundKey(key);
                dto.setPassWord(param.getPassWord());
                dto.setPhoneNumber(param.getPhoneNumber());

                //插入默认的图片作为头像，还有地址信息
                byte[] pic;
                try {
                    pic = FileHelper.readFileToByte(getPathHead() + "/extraData/G.jpg");
                } catch (IOException e) {
                    // C:/home/workspace/Demo2ShopService/trunk/demo1/billing/billing-boot/src/main/resources
                    logger.error("createDefaultUserDetail failed, please check url {}", getPathHead() + "/extraData/G.jpg");
                    ret.setSuccessOrNot("N");
                    ret.setRetInfo("注册系统似乎出了点问题~");
                    return ret;
                }
                DemoUserDetailDto detailDto = new DemoUserDetailDto();
                detailDto.setUserId(userId);
                detailDto.setUserDetail(pic);
                detailDto.setAddress1Line1(param.getAddress1Line1());
                detailDto.setAddress1Line2(param.getAddress1Line2());
                detailDto.setAddress1PostCode(param.getAddress1PostCode());

                detailDto.setAddress2Line1(defaultAd(param.getAddress2Line1()));
                detailDto.setAddress2Line2(defaultAd(param.getAddress2Line2()));
                detailDto.setAddress2PostCode(defaultAd(param.getAddress2PostCode()));
                detailDto.setAddress3Line1(defaultAd(param.getAddress3Line1()));
                detailDto.setAddress3Line2(defaultAd(param.getAddress3Line2()));
                detailDto.setAddress3PostCode(defaultAd(param.getAddress3PostCode()));

                demoUserDAO.insertUser(dto);
                demoUserDAO.createUserDetail(detailDto);


                ret.setSuccessOrNot("Y");
                ret.setRetInfo("success Sign Up! your lost found key is: " + key);
                return ret;

            } else {
                ret.setSuccessOrNot("N");
                ret.setRetInfo("email/name/phone number has been used! 邮箱/用户名/电话已注册！");
                return ret;
            }
        }
        else {
            operateUserParam ret = new operateUserParam();
            ret.setSuccessOrNot("N");
            ret.setRetInfo("你的IP最近已经注册过了！请等一段时间再来吧！");
            return ret;
        }



    }

    // 检查address,为空则置换成一个默认值
    private String defaultAd(String address) {
        if(StringUtils.isEmpty(address)) {
            return "default address!";
        }
        else {
            return address;
        }
    }

    @Override
    public void createNewUser(String userName, String email, int i) {
        DemoUserDto dto = new DemoUserDto();
        dto.setUserId((long) (i));
        dto.setUserName(userName + i);
        dto.setEmail(email + i + "@qq.com");
        dto.setCreateDate(DateUtil.getNowTimeDate());
        dto.setStateDate(DateUtil.getNowTimeDate());
        dto.setState("0");
        dto.setLostFoundKey((1000L + i));
        demoUserDAO.insertUser(dto);

    }

    /**
     * < 对用户赋予默认detail,这里应该加一个分页操作的~ > <br>
     *
     * @param allUser < 是:扫描所有用户.给不存在detail的user添加Detail,否:只操作指定的userIds的用户 > <不可空>
     * @param userIds < allUser为false时填写 > <可空>
     * @param url     < 额外指定detail文件路径，为空则使用默认值 > <可空>
     * @auther: tang
     */
    @Override
    public void createDefaultUserDetail(boolean allUser, List<Long> userIds, String url) {
        if (allUser) {
            // 分页，一次只查询2000条,然后进行赋值操作，再查再做，知道查不出数据为止
            for (int i = 1; ; i++) {
                // 每次createDetailWithId之后，selectUserWithoutDetail结果都会减少，所以始终pageNum =1
                List<Long> ids = demoUserDAO.selectUserWithoutDetail(2000, 1);
                if (ValidateUtil.validateNotEmpty(ids)) {
                    this.createDetailWithId(ids, url);
                } else {
                    break;
                }
            }
        } else {
            this.createDetailWithId(userIds, url);
        }
    }

    /**
     * < get User Info> <br>
     *
     * @param param < 可空，空查询gust信息，否则根据Id查询信息 >
     * @return < UserInfoParam,全信息 >
     * @auther: tang
     */
    @Override
    public UserInfoParam getUserInfo(UserInfoParam param) {
        // 带ID 根據ID查
        if (param.getUserId() != null) {
            logger.info("getUserInfo by id");
            return getUserInfoById(param.getUserId());
        }
        // 带名字密码，根據名字密码查
        else if (!StringUtils.isEmpty(param.getUserName())) {
            logger.info("getUserInfo by userName");
            return SignInByName(param);
        }
        // 带郵件和密碼，根據郵件密码查
        else if (!StringUtils.isEmpty(param.getEmail())) {
            logger.info("getUserInfo by email");
            return SignInByEmail(param);
        } else {
            // 默认返回gust
            logger.info("get default UserInfo GUST");
            return getUserInfoById(2L);
        }
    }

    private UserInfoParam SignInByEmail(UserInfoParam param) {
        UserInfoParam ret = new UserInfoParam();
        DemoUserDto dto = demoUserDAO.selectUserByeEmail(param.getEmail());
        // 校验是否找得到
        if (dto == null) {
            ret.setHandleSignInErrRet(FindUserInfoDef.EMAIL_NOT_FOUND);
            return ret;
        } else {
            // 校验密码是否正确
            if (dto.getPassWord().equals(param.getPassWord())) {
                return this.getUserInfoById(dto.getUserId());
            } else {
                ret.setHandleSignInErrRet(FindUserInfoDef.PASSWORD_NOT_RIGHT);
                return ret;
            }
        }
    }

    private UserInfoParam SignInByName(UserInfoParam param) {
        UserInfoParam ret = new UserInfoParam();
        DemoUserDto dto = demoUserDAO.selectUserByName(param.getUserName());
        // 校验是否找得到
        if (dto == null) {
            ret.setHandleSignInErrRet(FindUserInfoDef.USER_NAME_NOT_FOUND);
            return ret;
        } else {
            // 校验密码是否正确
            if (dto.getPassWord().equals(param.getPassWord())) {
                return this.getUserInfoById(dto.getUserId());
            } else {
                ret.setHandleSignInErrRet(FindUserInfoDef.PASSWORD_NOT_RIGHT);
                return ret;
            }
        }
    }

    /**
     * < handle Find User Info > <br>
     *
     * @param param < 不可空 >
     * @return < >
     * @auther: tang
     */
    @Override
    public UserInfoParam handleFindUserInfo(UserInfoParam param) {
        // 根据userName/email 和lostFoundKey查询用户信息
        if (!StringUtils.isEmpty(param.getUserName())) {
            return getUserInfoByNameAndLostFoundKey(param.getUserName(), param.getLostFoundKey());
        } else if (!StringUtils.isEmpty(param.getEmail())) {
            return getUserInfoByEmailAndLostFoundKey(param.getEmail(), param.getLostFoundKey());
        } else {
            UserInfoParam retParam = new UserInfoParam();
            retParam.setHandleFindUserInfoErrRet(FindUserInfoDef.USER_NAME_OR_EMAIL_EMPTY);
            return retParam;
        }
    }

    /**
     * < 依次判断，依次更新 > <br>
     *
     * @param param < 不可空 >
     * @return < >
     * @auther: tang
     */
    @Override
    public UserInfoParam handleEditProfile(UserInfoParam param) {
        Long userId = param.getUserId();
        if (!StringUtils.isEmpty(param.getNewPassWord())) {
            demoUserDAO.updateUserPassword(userId, param.getNewPassWord());
        }
        if (!StringUtils.isEmpty(param.getEmail())) {
            demoUserDAO.updateUserEmail(userId, param.getNewEmail());
        }
        if (!StringUtils.isEmpty(param.getPhoneNumber())) {
            demoUserDAO.updateUserPhoneNum(userId, param.getNewPhoneNumber());
        }
        if (!StringUtils.isEmpty(param.getUserDetail())) {
            demoUserDAO.updateUserDetail(userId, param.getNewUserDetail());
        }
        return null;
    }

    private UserInfoParam getUserInfoByNameAndLostFoundKey(String userName, Long key) {
        UserInfoParam ret = new UserInfoParam();
        DemoUserDto dto = demoUserDAO.selectUserByName(userName);
        // 校验是否找得到
        if (dto == null) {
            ret.setHandleFindUserInfoErrRet(FindUserInfoDef.USER_NAME_NOT_FOUND);
            return ret;
        } else {
            // 校验key是否正确
            if (dto.getLostFoundKey().equals(key)) {
                return this.getUserInfoById(dto.getUserId());
            } else {
                ret.setHandleFindUserInfoErrRet(FindUserInfoDef.LOST_FOUND_KEY_NOT_RIGHT);
                return ret;
            }
        }
    }

    private UserInfoParam getUserInfoByEmailAndLostFoundKey(String email, Long key) {
        UserInfoParam ret = new UserInfoParam();
        DemoUserDto dto = demoUserDAO.selectUserByeEmail(email);
        // 校验是否找得到
        if (dto == null) {
            ret.setHandleFindUserInfoErrRet(FindUserInfoDef.EMAIL_NOT_FOUND);
            return ret;
        } else {
            // 校验key是否正确
            if (dto.getLostFoundKey().equals(key)) {
                return this.getUserInfoById(dto.getUserId());
            } else {
                ret.setHandleFindUserInfoErrRet(FindUserInfoDef.LOST_FOUND_KEY_NOT_RIGHT);
                return ret;
            }
        }
    }

    /**
     * < get User Info and detail By Id > <br>
     *
     * @param userId < >
     * @return < >
     * @auther: tang
     */
    private UserInfoParam getUserInfoById(Long userId) {
        UserInfoParam retParam = new UserInfoParam();
        DemoUserDto userDto = demoUserDAO.selectUserById(userId);
        DemoUserDetailDto detailDto = demoUserDAO.selectUserDetailById(userId);
        BeanUtils.copyProperties(userDto, retParam, UserInfoParam.class);
        BeanUtils.copyProperties(detailDto, retParam, UserInfoParam.class);
        return retParam;
    }

//    private static HashMap<Long, UserInfoParam> sysUser;
//
//    private void sysUserInit() {
//        if(sysUser.size() != 0) {
//            return;
//        }
//
//    }




    private String extraUrl = "\\billing\\billing-boot\\src\\main\\resources\\extraData\\default.png";

    private String extraUrl2 = "\\billing\\billing-boot\\src\\main\\resources\\extraData\\G.jpg";

//    List<Long> ids = new ArrayList<>();
//        ids.add(1L);
//        List<Long> ids2 = new ArrayList<>();
//        ids2.add(2L);
//        List<Long> ids3 = new ArrayList<>();
//        ids3.add(1001L);
//        List<Long> ids4 = new ArrayList<>();
//        ids4.add(1002L);
//        String s1 = "C:\\home\\workspace\\Demo2ShopService\\trunk\\demo1\\billing\\billing-boot\\src\\main\\resources\\extraData\\T.jpg";
//        String s2 = "C:\\home\\workspace\\Demo2ShopService\\trunk\\demo1\\billing\\billing-boot\\src\\main\\resources\\extraData\\J.jpg";
//        String s3 = "C:\\home\\workspace\\Demo2ShopService\\trunk\\demo1\\billing\\billing-boot\\src\\main\\resources\\extraData\\G.jpg";
//        String s4 = "C:\\home\\workspace\\Demo2ShopService\\trunk\\demo1\\billing\\billing-boot\\src\\main\\resources\\extraData\\I.jpg";
//        demoUserService.createDefaultUserDetail(false, ids, s1);
//        demoUserService.createDefaultUserDetail(false, ids2, s2);
//        demoUserService.createDefaultUserDetail(false, ids3, s3);
//        demoUserService.createDefaultUserDetail(false, ids4, s4);
//        return Json.success("12");

    /**
     * < > <br>
     *
     * @param userIds  < 可空 >
     * @param filePath < 可空 >
     * @auther: tang
     */
    private void createDetailWithId(List<Long> userIds, String filePath) {
        if (ValidateUtil.validateNotEmpty(userIds)) {
            String url;
            if (StringUtils.isEmpty(filePath)) {
                url = System.getProperty("user.dir") + extraUrl;
            } else {
                url = filePath;
            }
            logger.info("createDefaultUserDetail, url is {}", url);
            byte[] pic = null;
            try {
                pic = FileHelper.readFileToByte(url);
            } catch (IOException e) {
                logger.error("createDefaultUserDetail failed, please check url {}", url);
            }
            for (Long userId : userIds) {
                DemoUserDetailDto dto = new DemoUserDetailDto();
                dto.setUserId(userId);
                dto.setUserDetail(pic);
                logger.info("dto is {}", BaseCommonUtil.objectToJsonString(dto));
                demoUserDAO.createUserDetail(dto);
            }
        }
    }

}
