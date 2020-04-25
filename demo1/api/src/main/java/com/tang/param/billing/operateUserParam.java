package com.tang.param.billing;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tang
 * @Description: TODO
 */
public class operateUserParam implements Serializable {

    private static final long serialVersionUID = -2158091730841163550L;

    private String userName;
    private String email;
    private Long phoneNumber;
    private String passWord;

    private String address1Line1;
    private String address1Line2;
    private String address1PostCode;
    private String address2Line1;
    private String address2Line2;
    private String address2PostCode;
    private String address3Line1;
    private String address3Line2;
    private String address3PostCode;


    private Long lostFoundKey;
    private String successOrNot;
    private String retInfo;

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSuccessOrNot() {
        return successOrNot;
    }

    public void setSuccessOrNot(String successOrNot) {
        this.successOrNot = successOrNot;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Long getLostFoundKey() {
        return lostFoundKey;
    }

    public void setLostFoundKey(Long lostFoundKey) {
        this.lostFoundKey = lostFoundKey;
    }
}