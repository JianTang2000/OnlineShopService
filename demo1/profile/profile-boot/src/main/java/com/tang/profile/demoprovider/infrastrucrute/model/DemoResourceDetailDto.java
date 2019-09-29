package com.tang.profile.demoprovider.infrastrucrute.model;

import java.util.Date;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/22 <br>
 */
public class DemoResourceDetailDto {

    /**
     * < >
     */
    private Long resourceId;

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

    /**
     * < 在服务器上的地址（或者写一个第三方的地址），这里如果用docker部署要注意下容器内的文件映射路径 >
     */
    private String resourceFile;

    /**
     * < >
     */
    private String resourceName;

    /**
     * < >
     */
    private String userName;

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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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

    public String getResourceFile() {
        return resourceFile;
    }

    public void setResourceFile(String resourceFile) {
        this.resourceFile = resourceFile;
    }
}
