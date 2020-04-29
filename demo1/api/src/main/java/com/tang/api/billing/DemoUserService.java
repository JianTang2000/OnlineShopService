package com.tang.api.billing;

import java.util.List;

import com.tang.param.billing.UserInfoParam;
import com.tang.param.billing.operateUserParam;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/23 <br>
 */
public interface DemoUserService {

    /**
     * < > <br>
     *
     * @auther: tang
     * @param userName < >
     * @param email < >
     */
    void createNewUser(String userName, String email, int i);

    /**
     * < 对用户赋予默认detail > <br>
     *
     * @auther: tang
     * @param allUser < 是:扫描所有用户.给不存在detail的user添加Detail,否:只操作指定的userIds的用户 > <不可空>
     * @param userIds < allUser为false时填写 > <可空>
     * @param url < 额外指定detail文件路径，为空则使用默认值 > <可空>
     */
    void createDefaultUserDetail(boolean allUser, List<Long> userIds, String url);

    /**
     * < get User Info> <br>
     *
     * @auther: tang
     * @param param < 可空，空查询gust信息，否则根据Id查询信息 >
     * @return < UserInfoParam,全信息 >
     */
    UserInfoParam getUserInfo(UserInfoParam param);

    /**
     * < handle Find User Info > <br>
     *
     * @auther: tang
     * @param param < 不可空 >
     * @return < >
     */
    UserInfoParam handleFindUserInfo(UserInfoParam param);

    /**
     * < handle Edit Profile > <br>
     *
     * @auther: tang
     * @param param < 不可空 >
     * @return < >
     */
    UserInfoParam handleEditProfile(UserInfoParam param);

    operateUserParam signUp(operateUserParam param);

    void testAlive();


}
