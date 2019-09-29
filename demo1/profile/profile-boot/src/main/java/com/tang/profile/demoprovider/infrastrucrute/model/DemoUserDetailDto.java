package com.tang.profile.demoprovider.infrastrucrute.model;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/26 <br>
 */
public class DemoUserDetailDto {

    /**
     * < >
     */
    private Long userId;

    /**
     * < BLOB类型，用来存放小型的照片 >
     */
    private byte[] userDetail;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(byte[] userDetail) {
        this.userDetail = userDetail;
    }
}
