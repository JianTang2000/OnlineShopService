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
    private byte[] photoOne;
    private byte[] photoTwo;
    private byte[] photoThree;
    private byte[] photoFour;
    private byte[] photoFive;
    private byte[] photoDetail;

    private Long fileIndex;

    public Long getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(Long fileIndex) {
        this.fileIndex = fileIndex;
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

    public byte[] getPhotoOne() {
        return photoOne;
    }

    public void setPhotoOne(byte[] photoOne) {
        this.photoOne = photoOne;
    }

    public byte[] getPhotoTwo() {
        return photoTwo;
    }

    public void setPhotoTwo(byte[] photoTwo) {
        this.photoTwo = photoTwo;
    }

    public byte[] getPhotoThree() {
        return photoThree;
    }

    public void setPhotoThree(byte[] photoThree) {
        this.photoThree = photoThree;
    }

    public byte[] getPhotoFour() {
        return photoFour;
    }

    public void setPhotoFour(byte[] photoFour) {
        this.photoFour = photoFour;
    }

    public byte[] getPhotoFive() {
        return photoFive;
    }

    public void setPhotoFive(byte[] photoFive) {
        this.photoFive = photoFive;
    }

    public byte[] getPhotoDetail() {
        return photoDetail;
    }

    public void setPhotoDetail(byte[] photoDetail) {
        this.photoDetail = photoDetail;
    }
}
