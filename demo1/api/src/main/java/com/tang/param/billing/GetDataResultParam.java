package com.tang.param.billing;

import java.io.Serializable;

/**
 * < 响应搜索返回前台的包装类，动作有强时间敏感性，这里应尽量不要冗余其他字段 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/12 <br>
 */
public class GetDataResultParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -2780491730241163550L;

    /**
     * < >
     */
    private Long resourceId;

    /**
     * < >
     */
    private String resourceName;

    /**
     * < >
     */
    private String userName;

    /**
     * < >
     */
    private String resourceSize;

    /**
     * < >
     */
    private String hot;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getResourceSize() {
        return resourceSize;
    }

    public void setResourceSize(String resourceSize) {
        this.resourceSize = resourceSize;
    }
}
