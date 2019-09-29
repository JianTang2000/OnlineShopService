package com.tang.profile.demoprovider.infrastrucrute.model;

import java.util.Date;

/**
 * <  ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/29 <br>
 */
public class DemoOpusDto {

    /**
     *  < 主键>
     */
    private Long opusId;

    /**
     *  < >
     */
    private String opusName;

    /**
     * < 外键，把音频信息单独写在一个表用外键关联，因为考虑大音频会很大，单独抽出来操作一定程度上方便设计提升效率>
     */
    private Long opusDetailId;

    /**
     *  < >
     */
    private String state;

    /**
     *  < >
     */
    private Date stateDate;

    public Long getOpusId() {
        return opusId;
    }

    public void setOpusId(Long opusId) {
        this.opusId = opusId;
    }

    public String getOpusName() {
        return opusName;
    }

    public void setOpusName(String opusName) {
        this.opusName = opusName;
    }

    public Long getOpusDetailId() {
        return opusDetailId;
    }

    public void setOpusDetailId(Long opusDetailId) {
        this.opusDetailId = opusDetailId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }
}
