package com.tang.param.billing;

import java.sql.Clob;
import java.util.Date;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/29 <br>
 */
public class DemoPersonParam {

    /**
     * < 主键 >
     */
    private Long personId;

    /**
     * < >
     */
    private String personName;

    /**
     * < 外键 >
     */
    private Long opusId;

    /**
     * < >
     */
    private Long personPhotoId;

    /**
     * < >
     */
    private Long personMvId;

    /**
     * < >
     */
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * < >
     */
    private String state;

    /**
     * < >
     */
    private Date stateDate;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getOpusId() {
        return opusId;
    }

    public void setOpusId(Long opusId) {
        this.opusId = opusId;
    }

    public Long getPersonPhotoId() {
        return personPhotoId;
    }

    public void setPersonPhotoId(Long personPhotoId) {
        this.personPhotoId = personPhotoId;
    }

    public Long getPersonMvId() {
        return personMvId;
    }

    public void setPersonMvId(Long personMvId) {
        this.personMvId = personMvId;
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
