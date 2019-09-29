package com.tang.profile.demoprovider.infrastrucrute.model;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/29 <br>
 */
public class DemoOpusDetailDto {

    /**
     * < 主键 >
     */
    private Long opusDetailId;

    /**
     * < BLOB类型，用来存放音频文件 >
     */
    private byte[] opusDetail;

    public Long getOpusDetailId() {
        return opusDetailId;
    }

    public void setOpusDetailId(Long opusDetailId) {
        this.opusDetailId = opusDetailId;
    }

    public byte[] getOpusDetail() {
        return opusDetail;
    }

    public void setOpusDetail(byte[] opusDetail) {
        this.opusDetail = opusDetail;
    }
}
