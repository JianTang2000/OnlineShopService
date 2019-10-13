package com.tang.param.billing;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tang.jian<br>
 */
public class ResourceSearchResultParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -2780491730241163550L;
    private Long resourceId;
    private String resourceName;
    private Long userId;
    private String userName;
    private String resourceDesc;
    private Date createDate;
    private String deliverType;
    private Long deliverPrice;
    private Long price;
    private String classification;


    private Long stock;
    /**
     * < 后续会写个算法来计算和改变这个hot >
     */
    private Long hot;
    private byte[] photo1;
    private byte[] photo2;
    private byte[] photo3;
    private byte[] photo4;
    private byte[] photo5;
    private byte[] photoDetail;

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

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getHot() {
        return hot;
    }

    public void setHot(Long hot) {
        this.hot = hot;
    }

    public byte[] getPhoto1() {
        return photo1;
    }

    public void setPhoto1(byte[] photo1) {
        this.photo1 = photo1;
    }

    public byte[] getPhoto2() {
        return photo2;
    }

    public void setPhoto2(byte[] photo2) {
        this.photo2 = photo2;
    }

    public byte[] getPhoto3() {
        return photo3;
    }

    public void setPhoto3(byte[] photo3) {
        this.photo3 = photo3;
    }

    public byte[] getPhoto4() {
        return photo4;
    }

    public void setPhoto4(byte[] photo4) {
        this.photo4 = photo4;
    }

    public byte[] getPhoto5() {
        return photo5;
    }

    public void setPhoto5(byte[] photo5) {
        this.photo5 = photo5;
    }

    public byte[] getPhotoDetail() {
        return photoDetail;
    }

    public void setPhotoDetail(byte[] photoDetail) {
        this.photoDetail = photoDetail;
    }
}
