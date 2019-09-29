package com.tang.param.billing;

import java.io.Serializable;
import java.util.Date;

/**
 * <  ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/11 <br>
 */
public class AuditThemeParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -1780491730241163550L;

    private GetDataInputParam GDParam;

    public GetDataInputParam getGDParam() {
        return GDParam;
    }

    public void setGDParam(GetDataInputParam GDParam) {
        this.GDParam = GDParam;
    }

    /**
     * 主题标识
     */
    private Long themeId;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 主题编码
     */
    private String code;

    /**
     * 描述
     */
    private String comments;

    /**
     * 创建人
     */
    private Long createdStaff;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 状态
     */
    private String state;

    /**
     * 状态时间
     */
    private Date stateDate;

    /**
     * 主题类型
     */
    private Long themeType;

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getCreatedStaff() {
        return createdStaff;
    }

    public void setCreatedStaff(Long createdStaff) {
        this.createdStaff = createdStaff;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public Long getThemeType() {
        return themeType;
    }

    public void setThemeType(Long themeType) {
        this.themeType = themeType;
    }
}
