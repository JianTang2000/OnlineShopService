package com.tang.param.billing;

import java.io.Serializable;

/**
 * < 前台请求“查询资源文件列表”时传到后台的包装类，不论是精确/模糊搜索，指定/不指定 类型/搜索范围，都可以用这个类传递 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/16 <br>
 */
public class GetDataInputParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -1780401730241163550L;

    /**
     * < 用户名，可空 >
     */
    private Long userId;

    /**
     * < 界面录入的搜索关键字，可以为空 >
     */
    private String input;

    /**
     * < 搜索范围，可空 >
     */
    private String searchRange;

    /**
     * < 类型范围，可空 >
     */
    private String typeRange;

    /**
     * < 是否精确匹配，可空 >
     */
    private boolean exactMatch;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getSearchRange() {
        return searchRange;
    }

    public void setSearchRange(String searchRange) {
        this.searchRange = searchRange;
    }

    public String getTypeRange() {
        return typeRange;
    }

    public void setTypeRange(String typeRange) {
        this.typeRange = typeRange;
    }

    public boolean isExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }
}
