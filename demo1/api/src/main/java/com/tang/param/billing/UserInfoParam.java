package com.tang.param.billing;

import java.io.Serializable;
import java.util.Date;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/28 <br>
 */
public class UserInfoParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -2358091730841163550L;

    /**
     * < 唯一主键，1是admin,2是gust其他从100开始，序列步长为10，使用序列获取工具类获取下一个值 >
     */
    private Long userId;

    /**
     * < >
     */
    private String userName;

    /**
     * < BLOB类型，用来存放小型的照片 >
     */
    private byte[] userDetail;

    /**
     * < 0正常，1被ban,2被删除,这里在DB里加触发器，使得admin和gust永远为0 >
     */
    private String state;

    /**
     * < >
     */
    private Date stateDate;

    /**
     * < >
     */
    private Date createDate;

    /**
     * < >
     */
    private String email;

    /**
     * < 四位随机数，一般情况下不许允修改，在找回账户时做检验使用，类似于steam的找回密钥 >
     */
    private Long lostFoundKey;

    /**
     * < 密码，该字段不可为空 >
     */
    private String passWord;

    /**
     * < 新密码 >
     */
    private String newPassWord;

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    /**
     * < 返回结果 >
     */
    private String handleFindUserInfoErrRet;

    /**
     * < 返回结果 >
     */
    private String handleSignInErrRet;

    public String getHandleFindUserInfoErrRet() {
        return handleFindUserInfoErrRet;
    }

    public void setHandleFindUserInfoErrRet(String handleFindUserInfoErrRet) {
        this.handleFindUserInfoErrRet = handleFindUserInfoErrRet;
    }

    public String getHandleSignInErrRet() {
        return handleSignInErrRet;
    }

    public void setHandleSignInErrRet(String handleSignInErrRet) {
        this.handleSignInErrRet = handleSignInErrRet;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLostFoundKey() {
        return lostFoundKey;
    }

    public void setLostFoundKey(Long lostFoundKey) {
        this.lostFoundKey = lostFoundKey;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public byte[] getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(byte[] userDetail) {
        this.userDetail = userDetail;
    }
}
