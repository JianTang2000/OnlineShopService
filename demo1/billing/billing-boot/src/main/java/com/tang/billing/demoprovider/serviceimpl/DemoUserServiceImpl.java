package com.tang.billing.demoprovider.serviceimpl;

import com.tang.api.billing.DemoUserService;
import com.tang.base.util.DateUtil;
import com.tang.base.util.FileHelper;
import com.tang.base.util.ValidateUtil;
import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.IDemoUserDAO;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoUserDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoUserDto;
import com.tang.constant.FindUserInfoDef;
import com.tang.param.billing.UserInfoParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service("DemoUserService")
public class DemoUserServiceImpl implements DemoUserService {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoUserServiceImpl.class);

    @Autowired
    IDemoUserDAO demoUserDAO;

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
     * @param url < 额外指定detail文件路径，为空则使用默认值 > <可空>
     * @auther: tang
     */
    @Override
    public void createDefaultUserDetail(boolean allUser, List<Long> userIds, String url) {
        if (allUser) {
            // 分页，一次只查询2000条,然后进行赋值操作，再查再做，知道查不出数据为止
            for (int i = 1;; i++) {
                // 每次createDetailWithId之后，selectUserWithoutDetail结果都会减少，所以始终pageNum =1
                List<Long> ids = demoUserDAO.selectUserWithoutDetail(2000, 1);
                if (ValidateUtil.validateNotEmpty(ids)) {
                    this.createDetailWithId(ids, url);
                }
                else {
                    break;
                }
            }
        }
        else {
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
        // 带ID 一般是Gust
        if (param.getUserId() != null) {
            logger.info("getUserInfo by id");
            return getUserInfoById(param.getUserId());
        }
        // 带名字密码，一般是登入
        else if (!StringUtils.isEmpty(param.getUserName())) {
            logger.info("getUserInfo by userName");
            return SignInByName(param);
        }
        else if (!StringUtils.isEmpty(param.getEmail())) {
            logger.info("getUserInfo by email");
            return SignInByEmail(param);
        }
        else {
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
        }
        else {
            // 校验密码是否正确
            if (dto.getPassWord().equals(param.getPassWord())) {
                return this.getUserInfoById(dto.getUserId());
            }
            else {
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
        }
        else {
            // 校验密码是否正确
            if (dto.getPassWord().equals(param.getPassWord())) {
                return this.getUserInfoById(dto.getUserId());
            }
            else {
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
        }
        else if (!StringUtils.isEmpty(param.getEmail())) {
            return getUserInfoByEmailAndLostFoundKey(param.getEmail(), param.getLostFoundKey());
        }
        else {
            UserInfoParam retParam = new UserInfoParam();
            retParam.setPassWord("FindUserInfo failed!");
            return retParam;
        }
    }

    /**
     * < handle Edit Profile > <br>
     *
     * @param param < 不可空 >
     * @return < >
     * @auther: tang
     */
    @Override public UserInfoParam handleEditProfile(UserInfoParam param) {
        Long userId = param.getUserId();
        String password = param.getNewPassWord();
        demoUserDAO.updateUserPassword(userId, password);
        return null;
    }

    private UserInfoParam getUserInfoByNameAndLostFoundKey(String userName, Long key) {
        UserInfoParam ret = new UserInfoParam();
        DemoUserDto dto = demoUserDAO.selectUserByName(userName);
        // 校验是否找得到
        if (dto == null) {
            ret.setHandleFindUserInfoErrRet(FindUserInfoDef.USER_NAME_NOT_FOUND);
            return ret;
        }
        else {
            // 校验key是否正确
            if (dto.getLostFoundKey().equals(key)) {
                return this.getUserInfoById(dto.getUserId());
            }
            else {
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
        }
        else {
            // 校验key是否正确
            if (dto.getLostFoundKey().equals(key)) {
                return this.getUserInfoById(dto.getUserId());
            }
            else {
                ret.setHandleFindUserInfoErrRet(FindUserInfoDef.LOST_FOUND_KEY_NOT_RIGHT);
                return ret;
            }
        }
    }

    /**
     * < get User Info and detail By Id > <br>
     *
     * @auther: tang
     * @param userId < >
     * @return < >
     */
    private UserInfoParam getUserInfoById(Long userId) {
        UserInfoParam retParam = new UserInfoParam();
        DemoUserDto userDto = demoUserDAO.selectUserById(userId);
        DemoUserDetailDto detailDto = demoUserDAO.selectUserDetailById(userId);
        BeanUtils.copyProperties(userDto, retParam, UserInfoParam.class);
        retParam.setUserDetail(detailDto.getUserDetail());
        return retParam;
    }

    private String extraUrl = "\\src\\main\\resources\\extraData\\default.png";

    private String extraUrl2 = "\\src\\main\\resources\\extraData\\admin.png";

    /**
     * < > <br>
     *
     * @auther: tang
     * @param userIds < 可空 >
     * @param filePath < 可空 >
     */
    private void createDetailWithId(List<Long> userIds, String filePath) {
        if (ValidateUtil.validateNotEmpty(userIds)) {
            String url;
            if (StringUtils.isEmpty(filePath)) {
                url = System.getProperty("user.dir") + extraUrl2;
            }
            else {
                url = filePath;
            }
            logger.info("createDefaultUserDetail, url is {}", url);
            byte[] pic = null;
            try {
                pic = FileHelper.readFileToByte(url);
            }
            catch (IOException e) {
                logger.error("createDefaultUserDetail failed, please check url {}", url);
            }
            for (Long userId : userIds) {
                DemoUserDetailDto dto = new DemoUserDetailDto();
                dto.setUserId(userId);
                dto.setUserDetail(pic);
                demoUserDAO.createUserDetail(dto);
            }
        }
    }

}
