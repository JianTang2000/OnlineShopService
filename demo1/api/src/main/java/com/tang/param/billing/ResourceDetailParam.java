package com.tang.param.billing;

import java.io.Serializable;
import java.util.Date;

/**
 * < 查询ResourceDetail（没什么敏感时间限制）返回给前台的包装类 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/4 <br>
 */
public class ResourceDetailParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -2358791730841163550L;

    /**
     * < 唯一主键，从1开始，序列步长为20，使用序列获取工具类获取下一个值 >
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

    /**
     * < 资源的创建时间，后续可能会加上一些时间字段，用以记录上次被查阅/下载的时间>
     */
    private Date createDate;

    /**
     * < 资源大小，创建时录入的字符串 >
     */
    private String resourceSize;

    /**
     * < 资源的外部链接，后续可能会根据这个链接写一个p2p传递工具 >
     */
    private String resourceLink;

    /**
     * < 后续会写个算法来计算和改变这个hot >
     */
    private Long hot;

    /**
     * < 服务器上是否支持下载，如果支持，resourceFile会存其在服务器上的地址 >
     */
    private String downloadAble;

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

    public String getResourceSize() {
        return resourceSize;
    }

    public void setResourceSize(String resourceSize) {
        this.resourceSize = resourceSize;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

    public Long getHot() {
        return hot;
    }

    public void setHot(Long hot) {
        this.hot = hot;
    }

    public String getDownloadAble() {
        return downloadAble;
    }

    public void setDownloadAble(String downloadAble) {
        this.downloadAble = downloadAble;
    }
}
