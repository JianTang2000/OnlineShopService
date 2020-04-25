package com.tang.billing.demoprovider.infrastrucrute.model;

import java.util.Date;

public class UserOrderHisDto {

    private Long orderId;

    private Long userId;
    private Long resourceId;
    private Long itemNum;

    private String deliverType;
    private String deliverTime;
    private String addressType;

    private Long extraCost;
    private Long discount;
    private String orderComment; // A 和B 类型的取消，仅存商家的评论 在这个字段里

    //--A saler canceled ;B user canceled ;C user paid ;D saler closed
    private String state;
    private String stateComment1;
    private String stateComment2;
    private String stateComment3;
    private String stateComment4;
    //to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'),
    private Date createDate;

    private Long sumPay;

    public Long getSumPay() {
        return sumPay;
    }

    public void setSumPay(Long sumPay) {
        this.sumPay = sumPay;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getItemNum() {
        return itemNum;
    }

    public void setItemNum(Long itemNum) {
        this.itemNum = itemNum;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Long getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(Long extraCost) {
        this.extraCost = extraCost;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateComment1() {
        return stateComment1;
    }

    public void setStateComment1(String stateComment1) {
        this.stateComment1 = stateComment1;
    }

    public String getStateComment2() {
        return stateComment2;
    }

    public void setStateComment2(String stateComment2) {
        this.stateComment2 = stateComment2;
    }

    public String getStateComment3() {
        return stateComment3;
    }

    public void setStateComment3(String stateComment3) {
        this.stateComment3 = stateComment3;
    }

    public String getStateComment4() {
        return stateComment4;
    }

    public void setStateComment4(String stateComment4) {
        this.stateComment4 = stateComment4;
    }
}
