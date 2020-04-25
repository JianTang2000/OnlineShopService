package com.tang.param.billing;

import java.io.Serializable;

/**
 * @author tang.jian<br>
 */
public class ResourceSearchResultSimpleParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -2780491730241163550L;
    private Long resourceId;
    private String resourceName;
    private Long userId;
    private String userName;
    private Long price;
    private byte[] photoDetail;

    private Long num;
    private Long sumPrice;

    private Long discount;
    private Long extraCost;
    private String comment;
    private Long sumPay;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(Long extraCost) {
        this.extraCost = extraCost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getSumPay() {
        return sumPay;
    }

    public void setSumPay(Long sumPay) {
        this.sumPay = sumPay;
    }

    public Long getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Long sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public byte[] getPhotoDetail() {
        return photoDetail;
    }

    public void setPhotoDetail(byte[] photoDetail) {
        this.photoDetail = photoDetail;
    }
}
