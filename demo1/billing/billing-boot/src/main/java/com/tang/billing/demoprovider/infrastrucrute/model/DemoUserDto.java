package com.tang.billing.demoprovider.infrastrucrute.model;

import java.util.Date;

public class DemoUserDto {


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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
}
