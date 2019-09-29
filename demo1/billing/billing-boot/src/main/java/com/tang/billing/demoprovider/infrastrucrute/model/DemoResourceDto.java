package com.tang.billing.demoprovider.infrastrucrute.model;

import java.util.Date;

public class DemoResourceDto {

    /**
     * < 唯一主键，从1000开始，序列步长为20，使用序列获取工具类获取下一个值 >
     */
    private Long resourceId;

    /**
     * < >
     */
    private String resourceName;

    /**
     * < >
     */
    private Long userId;

    /**
     * < >
     */
    private String userName;

    /**
     * < VARCHAR2(1000) >
     */
    private String resourceDesc;

    private Date createDate;

    private String deliverType;

    private Long deliverPrice;

    private Long price;

    private String classification;

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

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public Long getDeliverPrice() {
        return deliverPrice;
    }

    public void setDeliverPrice(Long deliverPrice) {
        this.deliverPrice = deliverPrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
