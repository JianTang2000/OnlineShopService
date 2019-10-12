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
     * < 唯一主键，1是admin,2是gust其他从1000开始，序列步长为10，使用序列获取工具类获取下一个值 >
     */
    private Long userId;

    /**
     * < >
     */
    private String userName;

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
     * < 四位随机数，一般情况下不许允修改，在找回账户时做检验使用，类似于steam的找回密钥 >
     */
    private Long phoneNumber;

    /**
     * < 密码，该字段不可为空 >
     */
    private String passWord;


    /**
     * < BLOB类型，用来存放小型的照片 >
     */
    private byte[] userDetail;

    private String address1Line1;
    private String address1Line2;
    private String address1PostCode;
    private String address2Line1;
    private String address2Line2;
    private String address2PostCode;
    private String address3Line1;
    private String address3Line2;
    private String address3PostCode;




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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress1Line1() {
        return address1Line1;
    }

    public void setAddress1Line1(String address1Line1) {
        this.address1Line1 = address1Line1;
    }

    public String getAddress1Line2() {
        return address1Line2;
    }

    public void setAddress1Line2(String address1Line2) {
        this.address1Line2 = address1Line2;
    }

    public String getAddress1PostCode() {
        return address1PostCode;
    }

    public void setAddress1PostCode(String address1PostCode) {
        this.address1PostCode = address1PostCode;
    }

    public String getAddress2Line1() {
        return address2Line1;
    }

    public void setAddress2Line1(String address2Line1) {
        this.address2Line1 = address2Line1;
    }

    public String getAddress2Line2() {
        return address2Line2;
    }

    public void setAddress2Line2(String address2Line2) {
        this.address2Line2 = address2Line2;
    }

    public String getAddress2PostCode() {
        return address2PostCode;
    }

    public void setAddress2PostCode(String address2PostCode) {
        this.address2PostCode = address2PostCode;
    }

    public String getAddress3Line1() {
        return address3Line1;
    }

    public void setAddress3Line1(String address3Line1) {
        this.address3Line1 = address3Line1;
    }

    public String getAddress3Line2() {
        return address3Line2;
    }

    public void setAddress3Line2(String address3Line2) {
        this.address3Line2 = address3Line2;
    }

    public String getAddress3PostCode() {
        return address3PostCode;
    }

    public void setAddress3PostCode(String address3PostCode) {
        this.address3PostCode = address3PostCode;
    }
}
