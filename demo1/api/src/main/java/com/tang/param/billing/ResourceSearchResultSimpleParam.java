package com.tang.param.billing;

import java.io.Serializable;
import java.util.Date;

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
    private byte[] photo1;

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

    public byte[] getPhoto1() {
        return photo1;
    }

    public void setPhoto1(byte[] photo1) {
        this.photo1 = photo1;
    }
}
