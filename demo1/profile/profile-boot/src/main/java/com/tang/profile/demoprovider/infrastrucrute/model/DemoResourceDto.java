package com.tang.profile.demoprovider.infrastrucrute.model;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/20 <br>
 */
public class DemoResourceDto {

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
}
