package com.tang.param.billing;

import java.io.Serializable;

/**
 * @author tang
 */
public class OperateCartParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -2780491730291163550L;

    private Long userId;
    private Long resourceId;
    private Long itemNum;

    private String deliverType;
    private String deliverTime;
    private String addressType;

    private Long extraCost;
    private Long discount;
    private String comment;
    private Long sumPay;

    private Long radio;
    private String select;
    private String input;
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Long getRadio() {
        return radio;
    }

    public void setRadio(Long radio) {
        this.radio = radio;
    }

    public Long getSumPay() {
        return sumPay;
    }

    public void setSumPay(Long sumPay) {
        this.sumPay = sumPay;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
