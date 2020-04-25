package com.tang.param.billing;

import java.io.Serializable;

/**
 * @author tang
 * @Description: TODO
 */
public class OperateOrderParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -2780401730291163550L;

    private Long userId;
    private String userName;
    private Long resourceId;
    private Long orderId;

    private String typeDesc;
    private String orderDetail;
    private String deliverType;
    private String deliverTime;
    private String addressType;

    private Long itemsNum;
    private Long itemsPrice;
    private Long extraCost;
    private Long discount;
    private Long sumPay;

    private String hisComment;

    public String getHisComment() {
        return hisComment;
    }

    public void setHisComment(String hisComment) {
        this.hisComment = hisComment;
    }

    public Long getSumPay() {
        return sumPay;
    }

    public void setSumPay(Long sumPay) {
        this.sumPay = sumPay;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Long getItemsNum() {
        return itemsNum;
    }

    public void setItemsNum(Long itemsNum) {
        this.itemsNum = itemsNum;
    }

    public Long getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(Long itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public Long getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(Long extraCost) {
        this.extraCost = extraCost;
    }
}
