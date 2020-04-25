package com.tang.billing.demoprovider.infrastrucrute.model;

public class DemoResourceDetailDto {

    private Long resourceId;

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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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

    public void setPhotoOne(byte[] photo1) {
        this.photoOne = photo1;
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
