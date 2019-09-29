package com.tang.billing.demoprovider.infrastrucrute.model;

public class DemoUserDetailDto {

    /**
     * < >
     */
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(byte[] userDetail) {
        this.userDetail = userDetail;
    }
}
